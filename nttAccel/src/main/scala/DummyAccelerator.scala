package nttAccel

import chisel3._
import chisel3.util._
import chisel3.experimental.{IntParam, BaseModule}
import freechips.rocketchip.amba.axi4._
import freechips.rocketchip.subsystem.BaseSubsystem
import org.chipsalliance.cde.config.{Parameters, Field, Config}
import freechips.rocketchip.diplomacy._
import freechips.rocketchip.regmapper.{HasRegMap, RegField}
import freechips.rocketchip.tilelink._
import freechips.rocketchip.util.UIntIsOneOf

// DOC include start: GCD params
case class DummyParams(
  address: BigInt = 0x4000,
  width: Int = 16,
  useAXI4: Boolean = false,
  useBlackBox: Boolean = true)
// DOC include end: GCD params

// DOC include start: GCD key
case object DummyKey extends Field[Option[DummyParams]](None)
// DOC include end: GCD key

class DummyIO(val w: Int) extends Bundle {
  val clock = Input(Clock())
  val reset = Input(Bool())
  val input_ready = Output(Bool())
  val input_valid = Input(Bool())
  val x = Input(UInt(w.W))
  val output_ready = Input(Bool())
  val output_valid = Output(Bool())
  val y = Output(UInt(w.W))
  val busy = Output(Bool())
}

trait DummyTopIO extends Bundle {
  val dummy_busy = Output(Bool())
}

trait HasDummyIO extends BaseModule {
  val w: Int
  val io = IO(new DummyIO(w))
}

// DOC include start: GCD blackbox
class DummyMMIOBlackBox(val w: Int) extends BlackBox(Map("WIDTH" -> IntParam(w))) with HasBlackBoxResource
  with HasDummyIO
{
  addResource("/vsrc/DummyMMIOBlackBox.v")
}
// DOC include end: GCD blackbox

// DOC include start: GCD chisel
class DummyMMIOChiselModule(val w: Int) extends Module
  with HasDummyIO
{
  val s_idle :: s_copy :: s_done :: Nil = Enum(3)

  val state = RegInit(s_idle)
  val input_val = Reg(UInt(w.W))
  val output_val = Reg(UInt(w.W))

  io.input_ready := state === s_idle
  io.output_valid := state === s_done
  io.y := output_val

  when (state === s_idle && io.input_valid) {
    input_val := io.x
    state := s_copy
  } .elsewhen (state === s_copy) {
    output_val := input_val  // Copy the value
    state := s_done
  } .elsewhen (state === s_done && io.output_ready) {
    state := s_idle
  }

  io.busy := state =/= s_idle
}
// DOC include end: GCD chisel

// DOC include start: GCD instance regmap

trait DummyModule extends HasRegMap {
  val io: DummyTopIO

  implicit val p: Parameters
  def params: DummyParams
  val clock: Clock
  val reset: Reset


  val x = Wire(new DecoupledIO(UInt(params.width.W)))
  val y = Wire(new DecoupledIO(UInt(params.width.W)))
  val status = Wire(UInt(2.W))

  val impl = if (params.useBlackBox) {
    Module(new DummyMMIOBlackBox(params.width))
  } else {
    Module(new DummyMMIOChiselModule(params.width))
  }

  impl.io.clock := clock
  impl.io.reset := reset.asBool

  impl.io.x := x.bits
  impl.io.input_valid := x.valid
  x.ready := impl.io.input_ready

  y.bits := impl.io.y
  y.valid := impl.io.output_valid
  impl.io.output_ready := y.ready

  status := Cat(impl.io.input_ready, impl.io.output_valid)
  io.dummy_busy := impl.io.busy

  regmap(
    0x00 -> Seq(
      RegField.r(2, status)), // a read-only register capturing current status
    0x04 -> Seq(
      RegField.w(params.width, x)), // write-only, x.valid is set on write
    0x08 -> Seq(
      RegField.r(params.width, y))) // read-only, y.ready is set on read
}
// DOC include end: GCD instance regmap

// DOC include start: Dummy router
class DummyTL(params: DummyParams, beatBytes: Int)(implicit p: Parameters)
  extends TLRegisterRouter(
    params.address, "dummy", Seq("ucbbar,dummy"),
    beatBytes = beatBytes)(
      new TLRegBundle(params, _) with DummyTopIO)(
      new TLRegModule(params, _, _) with DummyModule)

class DummyAXI4(params: DummyParams, beatBytes: Int)(implicit p: Parameters)
  extends AXI4RegisterRouter(
    params.address,
    beatBytes=beatBytes)(
      new AXI4RegBundle(params, _) with DummyTopIO)(
      new AXI4RegModule(params, _, _) with DummyModule)
// DOC include end: GCD router

// DOC include start: Dummy lazy trait
trait CanHavePeripheryDummy { this: BaseSubsystem =>
  private val portName = "dummy"

  // Only build if we are using the TL (nonAXI4) version
  val dummy_busy = p(DummyKey) match {
    case Some(params) => {
      val dummy = if (params.useAXI4) {
        val dummy = pbus { LazyModule(new DummyAXI4(params, pbus.beatBytes)(p)) }
        pbus.coupleTo(portName) {
          dummy.node :=
          AXI4Buffer () :=
          TLToAXI4 () :=
          // toVariableWidthSlave doesn't use holdFirstDeny, which TLToAXI4() needsx
          TLFragmenter(pbus.beatBytes, pbus.blockBytes, holdFirstDeny = true) := _
        }
        dummy
      } else {
        val dummy = pbus { LazyModule(new DummyTL(params, pbus.beatBytes)(p)) }
        pbus.coupleTo(portName) { dummy.node := TLFragmenter(pbus.beatBytes, pbus.blockBytes) := _ }
        dummy
      }
      val pbus_io = pbus { InModuleBody {
        val busy = IO(Output(Bool()))
        busy := dummy.module.io.dummy_busy
        busy
      }}
      val dummy_busy = InModuleBody {
        val busy = IO(Output(Bool())).suggestName("dummy_busy")
        busy := pbus_io
        busy
      }
      Some(dummy_busy)
    }
    case None => None
  }
}
// DOC include end: GCD lazy trait

// DOC include start: GCD config fragment
class WithDummy(useAXI4: Boolean = false, useBlackBox: Boolean = false) extends Config((site, here, up) => {
  case DummyKey => Some(DummyParams(useAXI4 = useAXI4, useBlackBox = useBlackBox))
})
// DOC include end: Dummy config fragment
