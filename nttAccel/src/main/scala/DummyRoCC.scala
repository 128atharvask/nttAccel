package nttAccel

import chisel3._
import chisel3.util._
import freechips.rocketchip.tile._
import org.chipsalliance.cde.config._
import freechips.rocketchip.diplomacy._
import freechips.rocketchip.rocket.{TLBConfig}
import freechips.rocketchip.subsystem.{SystemBusKey}
import freechips.rocketchip.rocket.constants.MemoryOpConstants
import roccaccutils._

case object DummyAccelTLB extends Field[Option[TLBConfig]](None)

class DummyRoCC(opcodes: OpcodeSet)(implicit p: Parameters) extends MemStreamerAccel(
    opcodes = opcodes) {
  override lazy val module = new DummyRoCCImp(this)

  lazy val tlbConfig = p(DummyAccelTLB).get
  lazy val xbarBetweenMem = false // No need for xbar in this simple example
  lazy val logger = NTTLogger
}

// Memory streaming implementation for 16-bit value copy
class DummyStreamer extends MemStreamer {
  val io = IO(new Bundle {
    val mem_stream = Flipped(new MemLoaderConsumerIO)
    val memwrites_in = new MemWriterIO
    val src_addr = Input(UInt(64.W))
    val dst_addr = Input(UInt(64.W))
    val start = Input(Bool())
    val busy = Output(Bool())
  })

  val busy = RegInit(false.B)
  io.busy := busy

  // Buffer for the value being copied
  val buffer = RegInit(0.U(16.W))

  // Simple state machine
  when (io.start && !busy) {
    busy := true.B
  }

  // Handle memory reads
  when (busy && io.mem_stream.output.valid) {
    buffer := io.mem_stream.output.bits.data(15, 0)
    io.memwrites_in.valid := true.B
    io.memwrites_in.data := buffer
    io.memwrites_in.addr := io.dst_addr
    io.memwrites_in.size := 1.U // 2 bytes
    busy := false.B
  } .otherwise {
    io.memwrites_in.valid := false.B
    io.memwrites_in.data := 0.U
    io.memwrites_in.addr := 0.U
    io.memwrites_in.size := 0.U
  }

  io.mem_stream.output.ready := busy
}

class DummyRoCCImp(outer: DummyRoCC)(implicit p: Parameters) extends MemStreamerAccelImp(outer) {
  
  lazy val queueDepth = 2 // Small queue depth since we only do single transfers

  lazy val cmd_router = Module(new CommandRouter(queueDepth))
  lazy val streamer = Module(new DummyStreamer())

  // Connect command router to streamer
  streamer.io.src_addr := cmd_router.io.src_info.addr
  streamer.io.dst_addr := cmd_router.io.dest_info.addr
  streamer.io.start := cmd_router.io.src_info.valid
  cmd_router.io.bufs_completed := !streamer.io.busy
  cmd_router.io.no_writes_inflight := !streamer.io.memwrites_in.valid

  // Connect memory interfaces
  streamer.io.mem_stream <> memloader.io.consumer
  memwriter.io.memwrites_in <> streamer.io.memwrites_in
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
  case DummyAccelTLB => Some(TLBConfig(nSets = 4, nWays = 4, nSectors = 1, nEntries = 4))
}) 