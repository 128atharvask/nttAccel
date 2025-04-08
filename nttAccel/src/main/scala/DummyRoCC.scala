package nttAccel

import chisel3._
import chisel3.util._
import freechips.rocketchip.tile._
import org.chipsalliance.cde.config._
import freechips.rocketchip.diplomacy._
import freechips.rocketchip.rocket.{HellaCacheReq, M_XRD, M_XWR}

// Main RoCC accelerator class
class DummyRoCC(opcodes: OpcodeSet)(implicit p: Parameters) extends LazyRoCC(
    opcodes = opcodes) {
  override lazy val module = new DummyImp(this)
}

// Config class to add the accelerator to Chipyard config
class WithDummyRoCC extends Config((site, here, up) => {
  case BuildRoCC => up(BuildRoCC) ++ Seq(
    (p: Parameters) => {
      implicit val q = p
      val dummy = LazyModule(new DummyRoCC(OpcodeSet.custom1))
      dummy
    }
  )
})

// Implementation of the dummy accelerator
class DummyImp(outer: DummyRoCC)(implicit p: Parameters) extends LazyRoCCModuleImp(outer) {
  // RoCC Interface
  io.resp.valid := false.B
  
  // State mapping
  object State {
    val idle = 0.U(3.W)    // Waiting for command
    val reading = 1.U(3.W) // Reading from source
    val waiting = 2.U(3.W) // Waiting for response
    val writing = 3.U(3.W) // Writing to destination
    val done = 4.U(3.W)    // Operation complete
  }
  import State._
  
  // State register
  val state = RegInit(idle)
  io.busy := (state =/= idle)

  // Command handling
  val cmd = io.cmd
  val rs1 = cmd.bits.rs1  // Source address
  val rs2 = cmd.bits.rs2  // Destination address

  // Store status for memory requests
  val status = RegEnable(io.cmd.bits.status, io.cmd.fire)

  // Buffer for the single element and request tracking
  val buffer = RegInit(0.U(16.W))
  val req_tag = RegInit(0.U(5.W))

  // Memory request tracking
  val req_valid = RegInit(false.B)
  val req_rdy = Wire(Bool())
  val mem_addr = RegInit(0.U(64.W))
  val mem_cmd = RegInit(M_XRD)
  val mem_data = RegInit(0.U(64.W))

  // Cache interface connection
  def connectHellaCache(req: DecoupledIO[HellaCacheReq]): Unit = {
    req.valid := req_valid
    req_rdy := req.ready
    req.bits.tag := req_tag
    req.bits.addr := mem_addr
    req.bits.cmd := mem_cmd
    req.bits.size := 1.U  // 2 bytes = log2(2) = 1
    req.bits.data := mem_data
    req.bits.signed := false.B
    req.bits.dprv := status.dprv
    req.bits.dv := status.dv
    req.bits.phys := false.B
  }

  // Connect to cache
  connectHellaCache(io.mem.req)

  val mem_resp_valid = io.mem.resp.valid
  val mem_resp_tag = io.mem.resp.bits.tag
  val mem_resp_data = io.mem.resp.bits.data
  val mem_resp_valid_reg = RegNext(mem_resp_valid)
  val mem_resp_tag_reg = RegNext(mem_resp_tag)


  // State machine logic
  switch(state) {
    is(idle) {
      when(cmd.valid) {
        state := reading
      }
    }

    is(reading) {
      req_valid := true.B
      mem_addr := rs1
      mem_cmd := M_XRD
      req_tag := 0.U
      when(req_rdy && req_valid) {
        state := waiting
        req_valid := false.B
      }.otherwise {
        state := reading
      }
    }

    is(waiting) {
      when(mem_resp_valid && mem_resp_tag === req_tag) {
        buffer := mem_resp_data
        state := writing
      }.otherwise {
        state := waiting
      }
    }

    is(writing) {
      req_valid := true.B
      mem_addr := rs2
      mem_cmd := M_XWR
      req_tag := 1.U
      when(mem_resp_valid && mem_resp_tag === req_tag) {
        req_valid := false.B
        state := done
      }
    }

    is(done) {
      state := idle
    }
  }

  // Command ready logic
  io.cmd.ready := (state === idle)
} 