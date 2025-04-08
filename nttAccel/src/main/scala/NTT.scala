package nttAccel

import chisel3._
import chisel3.util._
import freechips.rocketchip.tile._
import org.chipsalliance.cde.config._
import freechips.rocketchip.diplomacy._
import freechips.rocketchip.rocket.{HellaCacheReq, M_XRD, M_XWR}

class NTTAccel(opcodes: OpcodeSet)(implicit p: Parameters) extends LazyRoCC(
    opcodes = opcodes) {
  override lazy val module = new NTTAccelImp(this)
}

class WithNTTAccel extends Config((site, here, up) => {
  case RoccMaxTaggedMemXacts => 32
  case BuildRoCC => up(BuildRoCC) ++ Seq(
    (p: Parameters) => {
      implicit val q = p
      val ntt = LazyModule(new NTTAccel(OpcodeSet.custom0))
      ntt
    }
  )
})

class NTTAccelImp(outer: NTTAccel)(implicit p: Parameters) extends LazyRoCCModuleImp(outer) {
  // RoCC Interface
  io.resp.valid := false.B // we never return values with the resp
  
  // State mapping
  object State {
    val stateA = 0.U(3.W)  // Idle state
    val stateB = 1.U(3.W)  // Reading even input data
    val stateC = 2.U(3.W)  // Reading odd input data
    val stateD = 3.U(3.W)  // Processing NTT/INTT
    val stateE = 4.U(3.W)  // Writing output data
    val stateF = 5.U(3.W)  // Done state
  }
  import State._
  
  // State register
  val state = RegInit(stateA)
  val prevState = RegNext(state)
  io.busy := (state =/= stateA)

  // Command handling
  val cmd = io.cmd
  val funct = cmd.bits.inst.funct
  val rs1 = cmd.bits.rs1
  val rs2 = cmd.bits.rs2
  val rd = cmd.bits.inst.rd

  // Store status for memory requests
  val status = RegInit(0.U.asTypeOf(io.cmd.bits.status))
  when(io.cmd.fire) {
    status := io.cmd.bits.status
  }

  // Data counters and tags
  val dataCounter = RegInit(0.U(8.W))
  val reqTag = RegInit(0.U(6.W))  // Proper tag size
  val totalElements = 128.U
  val bytesPerElement = 2.U

  // Memory control registers
  val req_valid = RegInit(false.B)
  val mem_addr = RegInit(0.U(32.W))
  val mem_cmd = RegInit(M_XRD)
  val mem_data = RegInit(0.U(64.W))
  val req_rdy = Wire(Bool())  // Changed to Wire since it's combinational
  
  // Memory request/response tracking
  val req_sent = RegInit(false.B)
  val resp_received = RegInit(false.B)

  def connectHellaCache(req: DecoupledIO[HellaCacheReq]): Unit =  {
    req.valid := req_valid //&& (pendingReqs < maxInFlight)
    req_rdy := req.ready
    req.bits.tag := reqTag
    req.bits.addr := mem_addr
    req.bits.cmd := mem_cmd
    req.bits.size := 1.U  // 2 bytes = log2(2) = 1
    req.bits.data := mem_data
    req.bits.signed := false.B
    req.bits.dprv := status.dprv
    req.bits.dv := status.dv
    req.bits.phys := false.B
    req.bits.no_alloc := false.B
    req.bits.no_xcpt := false.B
    req.bits.mask := 0x3.U
  }

  // // Track pending requests
  // when(io.mem.req.fire) {
  //   pendingReqs := pendingReqs + 1.U
  //   reqTag := reqTag + 1.U
  // }

  // when(io.mem.resp.valid) {
  //   pendingReqs := pendingReqs - 1.U
  // }

  // // Handle cache nacks
  // when(io.mem.s2_nack) {
  //   // Retry the request
  //   req_valid := true.B
  //   pendingReqs := pendingReqs - 1.U
  // }

  // // Kill requests if needed
  // io.mem.s1_kill := false.B
  // io.mem.s2_kill := false.B

  // Output from cache
  val mem_resp = io.mem.resp.bits
  val mem_resp_valid = io.mem.resp.valid
  val mem_resp_tag = io.mem.resp.bits.tag

  // Connect accelerator with cache
  connectHellaCache(io.mem.req) // function call
  
  // NTT Control and Data Path
  val ntt_ctrl_dp = Module(new NTT_ctrl_dp())

  // Initialize NTT control signals
  ntt_ctrl_dp.io.input_data := 0.U
  ntt_ctrl_dp.io.input_valid := false.B
  ntt_ctrl_dp.io.output_ready := false.B

  // State machine logic
  switch(state) {
    is(stateA) {
      when(cmd.valid) {
        state := stateB
        dataCounter := 0.U
        req_valid := false.B
        mem_addr := 0.U
        mem_cmd := M_XRD
        mem_data := 0.U
      }
    }
    
    is(stateB) {
      when(dataCounter < totalElements) {
        when(!req_sent && req_rdy) {
          req_valid := true.B
          mem_addr := rs1 + (dataCounter << 1.U)
          mem_cmd := M_XRD
          req_sent := true.B
        }
        
        when(req_sent && io.mem.resp.valid && io.mem.resp.bits.tag === reqTag) {
          ntt_ctrl_dp.io.input_data := io.mem.resp.bits.data(15, 0)
          ntt_ctrl_dp.io.input_valid := true.B
          dataCounter := dataCounter + 1.U
          req_valid := false.B
          req_sent := false.B
        }
      }.otherwise {
        state := stateC
        dataCounter := 0.U
        req_valid := false.B
        req_sent := false.B
      }
    }

    is(stateC) {
      when(dataCounter < totalElements) {
        when(!req_sent && req_rdy) {
          req_valid := true.B
          mem_addr := rs1 + (dataCounter << 1.U) + 2.U
          mem_cmd := M_XRD
          req_sent := true.B
        }
        
        when(req_sent && io.mem.resp.valid && io.mem.resp.bits.tag === reqTag) {
          ntt_ctrl_dp.io.input_data := io.mem.resp.bits.data(15, 0)
          ntt_ctrl_dp.io.input_valid := true.B
          dataCounter := dataCounter + 1.U
          req_valid := false.B
          req_sent := false.B
        }
      }.otherwise {
        state := stateD
        req_valid := false.B
        req_sent := false.B
        ntt_ctrl_dp.io.input_data := Mux(funct === 1.U, 1.U, 0.U)
        ntt_ctrl_dp.io.input_valid := true.B
      }
    }

    is(stateD) {
      when(ntt_ctrl_dp.io.output_valid) {
        state := stateE
        dataCounter := 0.U
        req_valid := false.B
        ntt_ctrl_dp.io.input_valid := false.B
      }
    }

    is(stateE) {
      when(dataCounter < totalElements) {
        when(req_rdy) {  // Only make request when cache is ready
          req_valid := true.B
          mem_addr := rs2 + (dataCounter * bytesPerElement)
          mem_cmd := M_XWR
          mem_data := ntt_ctrl_dp.io.output_data
        }
        when(io.mem.req.ready) {
          dataCounter := dataCounter + 1.U
          req_valid := false.B
        }
      }.otherwise {
        state := stateF
        req_valid := false.B
      }
    }

    is(stateF) {
      when(ntt_ctrl_dp.io.input_ready) {
        state := stateA
        req_valid := false.B
        mem_addr := 0.U
        mem_cmd := M_XRD
        mem_data := 0.U
      }
    }
  }

  // Command ready logic
  io.cmd.ready := (state === stateA)

  // Connect NTT_ctrl_dp with memory interface
  ntt_ctrl_dp.io.output_ready := (state === stateE)
}

class NTT_ctrl_dp extends Module {
  val io = IO(new Bundle {
    val input_data = Input(UInt(16.W))
    val input_valid = Input(Bool())
    val input_ready = Output(Bool())
    
    val output_data = Output(UInt(16.W))
    val output_valid = Output(Bool())
    val output_ready = Input(Bool())
  })

  val bf_unit1 = Module(new UnifiedButterflyUnit())
  val bf_unit2 = Module(new UnifiedButterflyUnit())
  val tf_rom = Module(new TwiddleROM())
  val itf_rom = Module(new InverseTwiddleROM())
  val data_ram1 = Module(new DataRAM())
  val data_ram2 = Module(new DataRAM())
  val addr_gen = Module(new AddressGenerator())

  val fillCounter = RegInit(0.U(8.W))
  val sendCounter = RegInit(0.U(8.W))

  // State mapping
  object State {
    val stateA = 0.U(3.W)
    val stateB = 1.U(3.W)
    val stateC = 2.U(3.W)
    val stateD = 3.U(3.W)
    val stateE = 4.U(3.W)
    val stateF = 5.U(3.W)
    val stateG = 6.U(3.W)
    val stateH = 7.U(3.W)
  }
  import State._

  // State register
  val state = RegInit(stateA)
  val prevState = RegNext(state) // Previous state to detect transitions

  // Utils for state checking
  def isActive(s: UInt): Bool = state === s
  def isEntering(s: UInt): Bool = (state =/= prevState) && (state === s)

  // State machine logic
  switch(state) {
    // init state
    is(stateA) {
      state := stateB
      fillCounter := 0.U
    }
    
    // fill up dataRAM1
    is(stateB) {
      when(fillCounter === 127.U) {
        state := stateC
        fillCounter := 0.U
      }.otherwise {
        fillCounter := fillCounter + 1.U
      }
    }

    // fill up dataRAM2
    is(stateC) {
      when(fillCounter === 127.U) {
        state := stateD
      }.otherwise {
        fillCounter := fillCounter + 1.U
      }
    }

    // NTT/INTT depending on input
    is(stateD) {
      when(io.input_valid) {
        when(io.input_data === 1.U) {
          state := stateE // NTT
        }.otherwise {
          state := stateF // INTT
        }
      }
    }

    // NTT
    is(stateE) {
      when(addr_gen.io.finish) {
        state := stateG
      }
    }

    // INTT
    is(stateF) {
      when(addr_gen.io.finish) {
        state := stateG
      }
    }

    // Send out data
    is(stateG) {
      when(isEntering(stateG)) {
        sendCounter := 0.U
      }
      .elsewhen(isActive(stateG) && io.output_ready && io.output_valid) {
        sendCounter := sendCounter + 1.U
        when(sendCounter === 128.U) {
          state := stateH
        }
      }
    }

    // 
    is(stateH) {
      when(isEntering(stateH)) {
        sendCounter := 0.U
      }
      .elsewhen(isActive(stateH) && io.output_ready && io.output_valid) {
        sendCounter := sendCounter + 1.U
        when(sendCounter === 128.U) {
          state := stateA
        }
      }
    }
  }

  // Input ready logic
  io.input_ready := (state === stateB || state === stateC || state === stateD)

  // Initiates butterfly unit processing in NTT and INTT states (stateE and stateF)
  // val start_bf = RegInit(false.B)
  val start_bf = Wire(Bool())
  start_bf := false.B

  // Signals completion of read from data_ram1 for output generation in states 6 and 7
  val read_done1a = RegInit(false.B)

  // Indicates data is ready in data_ram1 and data_ram2 for butterfly unit processing
  val read_done1b = RegInit(false.B)

  // Signals data availability in second read ports for butterfly computation
  val read_done2 = RegInit(false.B)

  // Address generator and Butterfly unit control logic
  addr_gen.io.enable := (state === stateE || state === stateF)
  addr_gen.io.select := !(state === stateF)

  start_bf := (state === stateE || state === stateF)
  bf_unit1.io.select := addr_gen.io.select
  bf_unit2.io.select := addr_gen.io.select

  tf_rom.io.read_enable := start_bf
  tf_rom.io.read_address := addr_gen.io.addr3
  itf_rom.io.read_enable := start_bf
  itf_rom.io.read_address := addr_gen.io.addr3

  // Assign read data to butterfly units
  bf_unit1.io.w := Mux(addr_gen.io.select, tf_rom.io.read_data, itf_rom.io.read_data)
  bf_unit2.io.w := Mux(addr_gen.io.select, tf_rom.io.read_data, itf_rom.io.read_data)

  bf_unit1.io.enable := true.B    // make true for stateE and F only
  bf_unit1.io.start := read_done1b || read_done2
  bf_unit1.io.inverse := addr_gen.io.inverse
  bf_unit2.io.enable := true.B    // make true for stateE and F only
  bf_unit2.io.start := read_done1b || read_done2
  bf_unit2.io.inverse := addr_gen.io.inverse

  val delay_for_inverse = RegInit(0.U(3.W))
  when(addr_gen.io.inverse && delay_for_inverse < 7.U) {
    delay_for_inverse := delay_for_inverse + 1.U
  }

  // 1st write port for data_ram1
  // val write_valid_reg11 = RegInit(false.B)
  // data_ram1.io.writeValid1 := write_valid_reg11
  // val write_data11 = RegInit(0.U(16.W)) // bcz testbench writes on falling edge causing mismatch with address
  // data_ram1.io.writeData1 := write_data11
  when(state === 1.U && io.input_valid)
  {
    data_ram1.io.writeValid1 := true.B
    data_ram1.io.writeAddress1 := fillCounter
    data_ram1.io.writeData1 := io.input_data
  }
  .elsewhen(bf_unit1.io.finish && (state === 4.U || state === 5.U) && (!addr_gen.io.inverse || delay_for_inverse < 7.U))
  {
    data_ram1.io.writeValid1 := true.B
    data_ram1.io.writeAddress1 := addr_gen.io.addr1w
    data_ram1.io.writeData1 := bf_unit1.io.x
  }
  .otherwise
  {
    data_ram1.io.writeValid1 := false.B
    data_ram1.io.writeAddress1 := 0.U
    data_ram1.io.writeData1 := 0.U
  }

  // 1st write port for data_ram2
  // val write_valid_reg21 = RegInit(false.B)
  // data_ram2.io.writeValid1 := write_valid_reg21
  // val write_data21 = RegInit(0.U(16.W))
  // data_ram2.io.writeData1 := write_data21
  when(state === 2.U && io.input_valid)
  {
    data_ram2.io.writeValid1 := true.B
    data_ram2.io.writeAddress1 := fillCounter
    data_ram2.io.writeData1 := io.input_data
  } 
  .elsewhen(bf_unit2.io.finish && (state === 4.U || state === 5.U) && (!addr_gen.io.inverse || delay_for_inverse < 7.U) )
  {
    data_ram2.io.writeValid1 := true.B
    data_ram2.io.writeAddress1 := addr_gen.io.addr1w
    data_ram2.io.writeData1 := bf_unit2.io.x
  } 
  .otherwise 
  {
    data_ram2.io.writeValid1 := false.B
    data_ram2.io.writeAddress1 := 0.U
    data_ram2.io.writeData1 := 0.U
  }

  // 2nd write port for data_ram1
  // val write_data12 = RegInit(0.U(16.W))
  // data_ram1.io.writeData2 := write_data12
  when(bf_unit1.io.finish && (state === 4.U || state === 5.U)) 
  {
    data_ram1.io.writeValid2 := true.B
    data_ram1.io.writeAddress2 := addr_gen.io.addr2w
    data_ram1.io.writeData2 := bf_unit1.io.y
  }
  .otherwise 
  {
    data_ram1.io.writeValid2 := false.B
    data_ram1.io.writeAddress2 := 0.U
    data_ram1.io.writeData2 := 0.U
  }

  // 2nd write port for data_ram2
  // val write_data22 = RegInit(0.U(16.W))
  // data_ram2.io.writeData2 := write_data22
  when(bf_unit2.io.finish && (state === 4.U || state === 5.U))
  {
    data_ram2.io.writeValid2 := true.B
    data_ram2.io.writeAddress2 := addr_gen.io.addr2w
    data_ram2.io.writeData2 := bf_unit2.io.y
  } 
  .otherwise 
  {
    data_ram2.io.writeValid2 := false.B
    data_ram2.io.writeAddress2 := 0.U
    data_ram2.io.writeData2 := 0.U
  }

  // 1st read port for data_ram1
  when(state === 6.U) 
  {
    data_ram1.io.readValid1 := true.B
    data_ram1.io.readAddress1 := sendCounter
  } 
  .elsewhen(start_bf) 
  {
    data_ram1.io.readValid1 := true.B
    data_ram1.io.readAddress1 := addr_gen.io.addr1
  } 
  .otherwise 
  {
    data_ram1.io.readValid1 := false.B
    data_ram1.io.readAddress1 := 0.U
  }

  // 1st read port for data_ram2
  when(state === 7.U) 
  {
    data_ram2.io.readValid1 := true.B
    data_ram2.io.readAddress1 := sendCounter
  } 
  .elsewhen(start_bf) {
    data_ram2.io.readValid1 := true.B
    data_ram2.io.readAddress1 := addr_gen.io.addr1
  } 
  .otherwise {
    data_ram2.io.readValid1 := false.B
    data_ram2.io.readAddress1 := 0.U
  }

  // read_done flags
  when(state === 6.U || state === 7.U) 
  {
    read_done1a := true.B
    read_done1b := false.B
  } 
  .elsewhen(start_bf) 
  {
    read_done1a := false.B
    read_done1b := true.B
  } 
  .otherwise {
    read_done1a := false.B
    read_done1b := false.B
  }

  // 2nd read port is used only for Butterfly, hence handling separately
  when(start_bf) 
  {
    data_ram1.io.readValid2 := true.B
    data_ram1.io.readAddress2 := addr_gen.io.addr2
    data_ram2.io.readValid2 := true.B
    data_ram2.io.readAddress2 := addr_gen.io.addr2
    read_done2 := true.B
  } 
  .otherwise 
  {
    data_ram1.io.readValid2 := false.B
    data_ram1.io.readAddress2 := 0.U
    data_ram2.io.readValid2 := false.B
    data_ram2.io.readAddress2 := 0.U
    read_done2 := false.B
  }

  // Output generation
  when(read_done1a && (state === 6.U || (state === 7.U && sendCounter === 0.U)))
  {
    io.output_valid := true.B
    io.output_data := data_ram1.io.readData1
  } 
  .elsewhen(read_done1a && state === 7.U) 
  {
    io.output_valid := true.B
    io.output_data := data_ram2.io.readData1
  } 
  .otherwise 
  {
    io.output_valid := false.B
    io.output_data := 0.U
  }

  // Assigning bf_unit inputs
  when(read_done1b) 
  {
    when(addr_gen.io.inverse) 
    {
      bf_unit1.io.u := 0.U
      bf_unit2.io.u := 0.U
    } 
    .otherwise 
    {
      bf_unit1.io.u := data_ram1.io.readData1
      bf_unit2.io.u := data_ram2.io.readData1
    }
  } 
  .otherwise 
  {
    bf_unit1.io.u := 0.U
    bf_unit2.io.u := 0.U
  }

  when(read_done2) 
  {
    bf_unit1.io.v := data_ram1.io.readData2
    bf_unit2.io.v := data_ram2.io.readData2
  } 
  .otherwise 
  {
    bf_unit1.io.v := 0.U
    bf_unit2.io.v := 0.U
  }
}

// // Define a simple top-level object for generating Verilog
// object NTTMain extends App {
//   (new chisel3.stage.ChiselStage).emitVerilog(new NTT())
// }