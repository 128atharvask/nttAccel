package projectname

import chisel3._
import chisel3.util._

class UnifiedButterflyUnit extends Module {
  val io = IO(new Bundle {
    val start = Input(Bool())
    val enable = Input(Bool())
    val select = Input(Bool())
    val inverse = Input(Bool())
    val u = Input(UInt(16.W))
    val v = Input(UInt(16.W))
    val w = Input(UInt(16.W))
    val x = Output(UInt(16.W))
    val y = Output(UInt(16.W))
    val finish = Output(Bool())
  })

  // Delay registers
  val str_del0 = RegInit(false.B)
  val str_del1 = RegInit(false.B)
  val str_del2 = RegInit(false.B)
  val str_del3 = RegInit(false.B)
  val str_del4 = RegInit(false.B)
  val str_del5 = RegInit(false.B)
  val str_del6 = RegInit(false.B)

  // Main registers
  val reg0 = RegInit(0.U(16.W))
  val reg1 = RegInit(0.U(16.W))
  val reg2 = RegInit(0.U(16.W))

  // Delayed value registers
  val reg_del1 = RegInit(0.U(16.W))
  val reg_del2 = RegInit(0.U(16.W))
  val reg_del3 = RegInit(0.U(16.W))
  val reg_del4 = RegInit(0.U(16.W))
  val reg_del5 = RegInit(0.U(16.W))

  // Computation registers
  val c1 = RegInit(0.U(32.W))
  val c1_del1 = RegInit(0.U(32.W))
  val c1_del2 = RegInit(0.U(32.W))
  
  val ta = RegInit(0.U(16.W))
  val tb = RegInit(0.U(32.W))

  val c2 = RegInit(0.U(16.W))
  val c3 = RegInit(0.U(16.W))

  val x_reg = RegInit(0.U(16.W))
  val y_reg = RegInit(0.U(16.W))

  // Enable-controlled logic
  when(io.enable) {
    // Cycle 1
    when(io.select === false.B && io.inverse === false.B) {
      when(io.u > io.v) {
        reg1 := (io.u - io.v)
      }.otherwise {
        reg1 := (io.u + 3329.U - io.v)
      }

      when(io.u + io.v > 3329.U) {
        reg0 := (io.u + io.v - 3329.U)
      }.otherwise {
        reg0 := (io.u + io.v)
      }
    }.otherwise {
      reg0 := io.u
      reg1 := io.v
    }

    when(io.inverse === false.B) {
      reg2 := io.w
    }.otherwise {
      reg2 := 3303.U
    }
    str_del0 := io.start

    // Cycle 2
    c1 := reg1 * reg2
    reg_del1 := reg0
    str_del1 := str_del0

    // Cycle 3
    ta := ((c1 * 5039.U) >> 24).asUInt()
    c1_del1 := c1
    reg_del2 := reg_del1
    str_del2 := str_del1

    // Cycle 4
    tb := ((ta << 11) + (ta << 10) + (ta << 8) + ta).asUInt()
    c1_del2 := c1_del1
    reg_del3 := reg_del2
    str_del3 := str_del2

    // Cycle 5
    c2 := (c1_del2 - tb).asUInt()
    reg_del4 := reg_del3
    str_del4 := str_del3

    // Cycle 6
    when(c2 > 3329.U) {
      c3 := (c2 - 3329.U).asUInt()
    }.otherwise {
      c3 := c2.asUInt()
    }
    reg_del5 := reg_del4
    str_del5 := str_del4

    // Cycle 7
    when(io.select === false.B) {
      y_reg := c3
      x_reg := reg_del5
    }.otherwise {
      when(reg_del5 > c3) {
        y_reg := (reg_del5 - c3).asUInt()
      }.otherwise {
        y_reg := (reg_del5 + 3329.U - c3).asUInt()
      }

      when(reg_del5 + c3 > 3329.U) {
        x_reg := (reg_del5 + c3 - 3329.U).asUInt()
      }.otherwise {
        x_reg := (reg_del5 + c3).asUInt()
      }
    }
    str_del6 := str_del5
  }

  // Output assignments
  io.x := x_reg
  io.y := y_reg
  io.finish := str_del6
}


// object UnifiedButterflyUnitVerilog extends App {
//   chisel3.Driver.execute(args, () => new UnifiedButterflyUnit)
// }


// package projectname

// import spinal.core._
// import spinal.lib._

// // Hardware definition
// case class Unified_Butterfly_Unit() extends Component
// {
//   	val io = new Bundle 
//   	{
// 		val start = in Bool()
// 		val enable = in Bool()
// 		val select = in Bool()
// 		val inverse = in Bool()
// 		val u = in UInt(16 bits)
// 		val v = in UInt(16 bits)
// 		val w = in UInt(16 bits)
// 		val x = out UInt(16 bits)
// 		val y = out UInt(16 bits)
// 		val finish = out Bool()
//   	}
  	
//   	val str_del0 = Reg(Bool()) init(false)
//   	val str_del1 = Reg(Bool()) init(false)
// 	val str_del2 = Reg(Bool()) init(false)
// 	val str_del3 = Reg(Bool()) init(false)
// 	val str_del4 = Reg(Bool()) init(false)
// 	val str_del5 = Reg(Bool()) init(false)
// 	val str_del6 = Reg(Bool()) init(false)
	
// 	val reg0 = Reg(UInt(16 bits)) init(0)
// 	val reg1 = Reg(UInt(16 bits)) init(0)
// 	val reg2 = Reg(UInt(16 bits)) init(0)
	
// 	val reg_del1 = Reg(UInt(16 bits)) init(0)
// 	val reg_del2 = Reg(UInt(16 bits)) init(0)
// 	val reg_del3 = Reg(UInt(16 bits)) init(0)
// 	val reg_del4 = Reg(UInt(16 bits)) init(0)
// 	val reg_del5 = Reg(UInt(16 bits)) init(0)
	
// 	val c1 = Reg(UInt(32 bits)) init(0)
// 	val c1_del1 = Reg(UInt(32 bits)) init(0)
// 	val c1_del2 = Reg(UInt(32 bits)) init(0)
	
// 	val ta = Reg(UInt(16 bits)) init(0)
	
// 	val tb = Reg(UInt(32 bits)) init(0)
	
// 	val c2 = Reg(UInt(16 bits)) init(0)
	
// 	val c3 = Reg(UInt(16 bits)) init(0)
	
// 	val x_reg = Reg(UInt(16 bits)) init(0)
// 	val y_reg = Reg(UInt(16 bits)) init(0)
	
// 	when(io.enable) 
// 	{
// 		//cycle 1
// 		when ((io.select === False) & (io.inverse === False))
// 		{
// 			when (io.u > io.v)
// 			{
// 				reg1 := (io.u - io.v).resized
// 			}
// 			.otherwise
// 			{
// 				reg1 := (io.u + U(3329) - io.v).resized
// 			}

// 			when (io.u + io.v > U(3329))
// 			{
// 				reg0 := (io.u + io.v - U(3329)).resized
// 			}
// 			.otherwise
// 			{
// 				reg0 := (io.u + io.v).resized
// 			}
// 		}
// 		.otherwise
// 		{
// 			reg0 := io.u
// 			reg1 := io.v
// 		}
		
// 		when (io.inverse === False)
// 		{
// 			reg2 := io.w
// 		}
// 		.otherwise
// 		{
// 			reg2 := U(3303)
// 		}
// 		str_del0  := io.start
		
// 		//cycle 2
// 		c1 := reg1 * reg2
// 		reg_del1 := reg0
// 		str_del1  := str_del0
		
// 		//cycle 3
// 		ta := ((c1 * U(5039)) >> U(24)).resized
// 		c1_del1 := c1
// 		reg_del2 := reg_del1
// 		str_del2  := str_del1
		
// 		//cycle 4
// 		tb := ((ta << U(11)) + (ta << U(10)) + (ta << U(8)) + ta).resized
// 		c1_del2 := c1_del1
// 		reg_del3 := reg_del2
// 		str_del3  := str_del2
		
// 		//cycle 5
// 		c2 := (c1_del2 - tb).resized
// 		reg_del4 := reg_del3
// 		str_del4  := str_del3
		
// 		//cycle 6
// 		when (c2 > U(3329))
// 		{
// 			c3 := (c2 - U(3329)).resized
// 		}
// 		.otherwise
// 		{
// 			c3 := c2.resized
// 		}
// 		reg_del5 := reg_del4
// 		str_del5  := str_del4
		
// 		//cycle 7
// 		when (io.select === False)
// 		{
// 			y_reg := c3
// 			x_reg := reg_del5
// 		}
// 		.otherwise
// 		{
// 			when (reg_del5 > c3)
// 			{
// 				y_reg := (reg_del5 - c3).resized
// 			}
// 			.otherwise
// 			{
// 				y_reg := (reg_del5 + U(3329) - c3).resized
// 			}

// 			when (reg_del5 + c3 > U(3329))
// 			{
// 				x_reg := (reg_del5 + c3 - U(3329)).resized
// 			}
// 			.otherwise
// 			{
// 				x_reg := (reg_del5 + c3).resized
// 			}
// 		}
// 		str_del6  := str_del5
// 	}
	
// 	io.x := x_reg
// 	io.y := y_reg
// 	io.finish := str_del6
// }

// //object MyTopLevelVerilog extends App 
// //{
// //	Config.spinal.generateVerilog(Butterfly_Unit_DIT())
// //}
