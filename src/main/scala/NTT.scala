package nttAccel

import chisel3._
import chisel3.util._

class NTT extends Module {
  val io = IO(new Bundle {
    val input_data = Input(UInt(16.W))
    val input_valid = Input(Bool())
    val input_ready = Output(Bool())
    // val input_last = Input(Bool()) // not used
    
    val output_data = Output(UInt(16.W))
    val output_valid = Output(Bool())
    val output_ready = Input(Bool())
    val output_last = Output(Bool())  // required for FPGA
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
  val lastReg = RegInit(false.B)

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
    is(stateA) {
      lastReg := false.B
      state := stateB
    }

    is(stateB) {
      when(isEntering(stateB)) {
        fillCounter := 0.U
      }
      .elsewhen(isActive(stateB) && io.input_valid) {
        fillCounter := fillCounter + 1.U
        when(fillCounter === 127.U) {
          state := stateC
        }
      }
    }

    is(stateC) {
      when(isEntering(stateC)) {
        fillCounter := 0.U
      }
      .elsewhen(isActive(stateC) && io.input_valid) {
        fillCounter := fillCounter + 1.U
        when(fillCounter === 127.U) {
          state := stateD
        }
      }
    }

    is(stateD) {
      when(io.input_valid) {
        when(io.input_data === 1.U) {
          state := stateE // NTT
        }.otherwise {
          state := stateF // INTT
        }
      }
    }

    is(stateE) {
      when(addr_gen.io.finish) {
        state := stateG
      }
    }

    is(stateF) {
      when(addr_gen.io.finish) {
        state := stateG
      }
    }

    is(stateG) {
      when(isEntering(stateG)) {
        sendCounter := 0.U
      }
      .elsewhen(isActive(stateG) && io.output_ready && io.output_valid) {
        sendCounter := sendCounter + 1.U
        when(sendCounter === 127.U) {
          state := stateH
        }
      }
    }

    is(stateH) {
      when(isEntering(stateH)) {
        sendCounter := 0.U
      }
      .elsewhen(isActive(stateH) && io.output_ready && io.output_valid) {
        sendCounter := sendCounter + 1.U
        when(sendCounter === 127.U) {
          lastReg := true.B
        }
        when(sendCounter === 128.U) {
          state := stateA
        }
      }
    }
  }

  // Input ready logic
  io.input_ready := (state === stateB || state === stateC || state === stateD)

  // Initiates butterfly unit processing in NTT and INTT states (stateE and stateF)
  val start_bf = RegInit(false.B)

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

  bf_unit1.io.enable := true.B
  bf_unit1.io.start := read_done1b || read_done2
  bf_unit1.io.inverse := addr_gen.io.inverse
  bf_unit2.io.enable := true.B
  bf_unit2.io.start := read_done1b || read_done2
  bf_unit2.io.inverse := addr_gen.io.inverse

  // 1st write port for data_ram1
  when(state === 1.U && io.input_valid)
  {
    data_ram1.io.writeValid1 := true.B
    data_ram1.io.writeAddress1 := fillCounter
    data_ram1.io.writeData1 := io.input_data
  }
  .elsewhen(bf_unit1.io.finish && (state === 4.U || state === 5.U) && !addr_gen.io.inverse)
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
  when(state === 2.U && io.input_valid)
  {
    data_ram2.io.writeValid1 := true.B
    data_ram2.io.writeAddress1 := fillCounter
    data_ram2.io.writeData1 := io.input_data
  } 
  .elsewhen(bf_unit2.io.finish && (state === 4.U || state === 5.U) && !addr_gen.io.inverse) 
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
  when(state === 6.U || (state === 7.U && sendCounter === 0.U)) 
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

  // 2nd read port for Butterfly
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
    io.output_last := lastReg
  } 
  .elsewhen(read_done1a && state === 7.U) 
  {
    io.output_valid := true.B
    io.output_data := data_ram2.io.readData1
    io.output_last := lastReg
  } 
  .otherwise 
  {
    io.output_valid := false.B
    io.output_data := 0.U
    io.output_last := false.B
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

// Define a simple top-level object for generating Verilog
object NTTMain extends App {
  (new chisel3.stage.ChiselStage).emitVerilog(new NTT())
}