package nttAccel

import chisel3._
import chisel3.util._
import freechips.rocketchip.tile._
import org.chipsalliance.cde.config._
import freechips.rocketchip.diplomacy._
import freechips.rocketchip.rocket.{TLBConfig}
import freechips.rocketchip.subsystem.{SystemBusKey}
import freechips.rocketchip.rocket.constants.MemoryOpConstants

// Simple RoCC accelerator for copying 16-bit values
class DummyRoCC(opcodes: OpcodeSet)(implicit p: Parameters) extends LazyRoCC(opcodes) {
  override lazy val module = new DummyRoCCImp(this)
}

class DummyRoCCImp(outer: DummyRoCC)(implicit p: Parameters) extends LazyRoCCModuleImp(outer)
    with HasCoreParameters
    with MemoryOpConstants {
  
  // States of the state machine
  val idleState :: readState :: writeState :: Nil = Enum(3)
  val state = RegInit(idleState)
  
  // Source and destination pointers
  val srcPtr = RegInit(0.U(64.W))
  val dstPtr = RegInit(0.U(64.W))
  
  // Buffer for the value being copied
  val buffer = RegInit(0.U(16.W))
  
  // Memory request tracking
  val request = RegInit(false.B)
  
  // Back pressure to core: only ready when not computing
  io.cmd.ready := (state === idleState)
  io.busy := (state =/= idleState)
  io.mem.req.valid := request
  
  // Static portions of the request
  val R = 0.U
  val W = 1.U
  val cmd = RegInit(R)
  val data = RegInit(0.U(64.W))
  val ptr = RegInit(0.U(64.W))
  val size = RegInit(1.U(3.W))  // 2 bytes (16 bits)
  
  // Memory request signals
  io.mem.req.bits.cmd := cmd
  io.mem.req.bits.size := size
  io.mem.req.bits.signed := false.B
  io.mem.req.bits.data := data
  io.mem.req.bits.phys := false.B  // Use virtual addresses
  io.mem.req.bits.addr := ptr
  io.mem.req.bits.tag := 0.U
  io.mem.req.bits.dprv := io.cmd.bits.status.dprv
  io.mem.req.bits.dv := io.cmd.bits.status.dv
  
  // Assert that size is always 1 (2 bytes)
  assert(size === 1.U, "Size must be 1 (2 bytes) for 16-bit transfers")
  
  // Log state transitions
  when (state =/= RegNext(state)) {
    NTTLogger.logInfo("State transition: old=%d new=%d\n", RegNext(state), state)
  }
  
  // State machine
  switch(state) {
    is(idleState) {
      when(io.cmd.fire) {
        // Check address alignment
        val srcAligned = (io.cmd.bits.rs1 & 1.U) === 0.U
        val dstAligned = (io.cmd.bits.rs2 & 1.U) === 0.U
        
        assert(srcAligned, "Source address must be 2-byte aligned")
        assert(dstAligned, "Destination address must be 2-byte aligned")
        
        // Initialize source and destination pointers
        srcPtr := io.cmd.bits.rs1
        dstPtr := io.cmd.bits.rs2
        
        // Set up initial read request
        request := true.B
        cmd := R
        ptr := io.cmd.bits.rs1
        data := 0.U
        state := readState
        
        NTTLogger.logInfo("Command received - src: 0x%x, dst: 0x%x\n", 
          io.cmd.bits.rs1, io.cmd.bits.rs2)
      }
    }
    
    is(readState) {
      // Request is now not valid; await response
      when(RegNext(io.mem.req.fire)) {
        request := false.B
        NTTLogger.logInfo("Read request fired - addr: 0x%x, cmd: %d, size: %d\n",
          ptr, cmd, size)
      }
      
      // On memory response
      when(io.mem.resp.valid && !RegNext(io.mem.resp.valid)) {
        // Store the 16-bit value
        buffer := io.mem.resp.bits.data(15, 0)
        
        // Set up write request
        cmd := W
        ptr := dstPtr
        data := Cat(0.U(48.W), buffer)  // Zero extend to 64 bits
        state := writeState
        request := true.B
        
        NTTLogger.logInfo("Read response received - data: 0x%x\n", 
          io.mem.resp.bits.data(15, 0))
      }
    }
    
    is(writeState) {
      // Request is now not valid; await response
      when(RegNext(io.mem.req.fire)) {
        request := false.B
        NTTLogger.logInfo("Write request fired - addr: 0x%x, data: 0x%x\n",
          ptr, data)
      }
      
      when(!request) {
        // Operation complete
        state := idleState
        NTTLogger.logInfo("Write operation complete\n")
      }
    }
  }
  
  // Log memory interface activity
  when (io.mem.req.fire) {
    NTTLogger.logInfo("Memory request - valid: %d, addr: 0x%x, cmd: %d, data: 0x%x\n",
      io.mem.req.valid, io.mem.req.bits.addr, io.mem.req.bits.cmd, io.mem.req.bits.data)
  }
  
  when (io.mem.resp.valid) {
    NTTLogger.logInfo("Memory response - data: 0x%x\n", io.mem.resp.bits.data)
  }
  
  // Response handling
  io.resp.valid := false.B
  io.interrupt := false.B
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