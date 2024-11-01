package nttAccel

import chisel3._
import chisel3.util._

class AddressGenerator extends Module {
  val io = IO(new Bundle {
    val enable  = Input(Bool())
    val select  = Input(Bool())
    val addr1   = Output(UInt(7.W))
    val addr2   = Output(UInt(7.W))
    val addr3   = Output(UInt(7.W))
    val addr1w  = Output(UInt(7.W))
    val addr2w  = Output(UInt(7.W))
    val inverse = Output(Bool())
    val finish  = Output(Bool())
  })

  val addr1_del = RegInit(0.U(7.W))
  val addr2_del = RegInit(0.U(7.W))

  val fin_count = RegInit(0.U(4.W))
  val counter = RegInit(0.U(7.W))
  val state   = RegInit(0.U(3.W))
  val inverse  = RegInit(false.B)
  val str_reg = RegInit(false.B)

  val del_regs = RegInit(0.U(7.W))
  
  val fin_sig = Wire(Bool())
  val delay = Wire(Bool())
  
  // Delay registers
  val addr1_del_chain = RegInit(0.U(7.W))
  val addr2_del_chain = RegInit(0.U(7.W))

  fin_sig := false.B
  delay := false.B
  
  when(io.enable) {
    str_reg := true.B
  }

  when(fin_sig) {
    str_reg := false.B
  }

  when(str_reg) {
    delay := true.B
    counter := counter + 1.U

    when(counter === 63.U && state =/= 7.U) {
      counter := 0.U
      when(state === 6.U) {
        when(io.select) {
          fin_sig := true.B
          state := 0.U
        }.otherwise {
          fin_sig := false.B
          state := state + 1.U
        }
      }.otherwise {
        state := state + 1.U
        fin_sig := false.B
      }
    }.elsewhen(counter === 127.U && state === 7.U) {
      fin_sig := true.B
      state := 0.U
    }.otherwise {
      fin_sig := false.B
    }
  }.otherwise {
    delay := false.B
    fin_sig := false.B
  }

  val i = Mux(io.select, state, (6.U - state))
  
  val (s, j, k) = if (state === (7.U).asUInt(3.W)) {
    (0.U(7.W), 0.U(7.W), 0.U(7.W))
  } else {
    val s = counter
    val j = (s >> (6.U - i))
    val k = (s & ((64.U >> i) - 1.U)).asUInt(7.W)
    (s, j, k)
  }

  val (u7mi, u6mi, u7s1, u6s1, usi, u7ss) = if (state === 7.U) {
    (0.U(3.W), 0.U(3.W), 0.U(7.W), 0.U(7.W), 0.U(7.W), 0.U(7.W))
  } else {
    val u7mi = (7.U - i).asUInt(7.W)
    val u6mi = (6.U - i).asUInt(7.W)
    val u7s1 = (1.U << u7mi).asUInt(7.W)
    val u6s1 = (1.U << u6mi).asUInt(7.W)
    val usi  = (1.U << i).asUInt(7.W)
    val u7ss = (s >> u6mi).asUInt(7.W)
    (u7mi, u6mi, u7s1, u6s1, usi, u7ss)
  }

  val addr1_var = if (state === 7.U) 0.U(7.W) else (j * u7s1 + k).resize(7)
  val addr2_var = if (state === 7.U) 0.U(7.W) else (j * u7s1 + k + u6s1).resize(7)
  val addr3_var = if (state === 7.U) 0.U(7.W) else (usi + u7ss)

  // Address delay chains
  when(delay || del_regs.orR) {
    addr1_del_chain := addr1_var
    addr2_del_chain := addr2_var
  }

  // Fin count logic
  when(fin_sig || fin_count > 0.U) {
    fin_count := fin_count + 1.U
  }

  // Assign outputs
  io.addr1   := addr1_var
  io.addr2   := addr2_var
  io.addr3   := addr3_var
  io.addr1w  := addr1_del_chain
  io.addr2w  := addr2_del_chain
  io.finish  := (fin_count === 8.U)
  io.inverse := (inverse || (io.select === false.B && (fin_count > 0.U && fin_count <= 8.U)))
}

// object AddressGeneratorVerilog extends App {
//   chisel3.Driver.execute(args, () => new AddressGenerator)
// }



// package projectname

// import spinal.core._
// import spinal.lib._

// // Hardware definition
// case class Address_Generator() extends Component
// {
//   	val io = new Bundle 
//   	{
// 		val enable  = in  Bool()
// 		val select  = in  Bool()
// 		val addr1   = out UInt(7 bits)
// 		val addr2   = out UInt(7 bits)
// 		val addr3   = out UInt(7 bits)
// 		val addr1w  = out UInt(7 bits)
// 		val addr2w  = out UInt(7 bits)
// 		val inverse = out Bool()
// 		val finish  = out Bool()
//   	}

// 	val addr1_del1 = Reg(UInt(7 bits)) init(0)
// 	val addr1_del2 = Reg(UInt(7 bits)) init(0)
// 	val addr1_del3 = Reg(UInt(7 bits)) init(0)
// 	val addr1_del4 = Reg(UInt(7 bits)) init(0)
// 	val addr1_del5 = Reg(UInt(7 bits)) init(0)
// 	val addr1_del6 = Reg(UInt(7 bits)) init(0)
// 	val addr1_del7 = Reg(UInt(7 bits)) init(0)
// 	val addr1_del8 = Reg(UInt(7 bits)) init(0)
	
// 	val addr2_del1 = Reg(UInt(7 bits)) init(0)
// 	val addr2_del2 = Reg(UInt(7 bits)) init(0)
// 	val addr2_del3 = Reg(UInt(7 bits)) init(0)
// 	val addr2_del4 = Reg(UInt(7 bits)) init(0)
// 	val addr2_del5 = Reg(UInt(7 bits)) init(0)
// 	val addr2_del6 = Reg(UInt(7 bits)) init(0)
// 	val addr2_del7 = Reg(UInt(7 bits)) init(0)
// 	val addr2_del8 = Reg(UInt(7 bits)) init(0)
	
// 	val fin_count = Reg(UInt(4 bits)) init(0)
	
// 	val counter = Reg(UInt(7 bits)) init(0)
// 	val state   = Reg(UInt(3 bits)) init(0)
// 	val inverse = Reg(Bool()) init(False)
// 	val str_reg = Reg(Bool()) init(False)
	
// 	val del_reg1 = Reg(Bool()) init(False)
// 	val del_reg2 = Reg(Bool()) init(False)
// 	val del_reg3 = Reg(Bool()) init(False)
// 	val del_reg4 = Reg(Bool()) init(False)
// 	val del_reg5 = Reg(Bool()) init(False)
// 	val del_reg6 = Reg(Bool()) init(False)
// 	val del_reg7 = Reg(Bool()) init(False)
	
// 	var fin_sig = Bool()
// 	var delay   = Bool()

// 	var i = UInt(3 bits)
// 	var s = UInt(7 bits)
// 	var j = UInt(7 bits)
// 	var k = UInt(7 bits)

// 	var u7mi = UInt(3 bits)
// 	var u6mi = UInt(3 bits)
// 	var u7s1 = UInt(7 bits)
// 	var u6s1 = UInt(7 bits)
// 	var usi  = UInt(7 bits)
// 	var u7ss = UInt(7 bits)

// 	var addr1_var = UInt(7 bits)
// 	var addr2_var = UInt(7 bits)
// 	var addr3_var = UInt(7 bits)
		
// 	fin_sig := False
// 	delay := False
	
// 	when(io.enable)
// 	{
// 		str_reg := True		
// 	}
	
// 	when(fin_sig)
// 	{
// 		str_reg := False
// 	}
	
// 	when(str_reg)
// 	{
// 		delay := True
// 		counter := counter + 1
// 		when(counter === U(63) && state =/= U(7)) 
// 		{
// 			counter := 0
// 			when (state === U(6))
// 			{
// 				when(io.select)
// 				{
//   					fin_sig := True
//   					state := 0
//   				}
//   				.otherwise
//   				{
//   					fin_sig := False
//   					state := state + 1
//   				}
//   			}
//   			.otherwise
//   			{
//   				state := state + 1
//   				fin_sig := False
//   			}
// 		}
// 		.elsewhen(counter === U(127) && state === U(7))
// 		{
// 			fin_sig := True
// 			state := 0
// 		}
// 		.otherwise
// 		{
// 			fin_sig := False
// 		}
// 	}
// 	.otherwise
// 	{
// 		delay := False
// 		fin_sig := False
// 	}
    	
// 	when(io.select)
// 	{
// 		i := state
// 	}
// 	.otherwise
// 	{
// 		i := (U(6) - state)
// 	}
	
// 	when(state === U(7))
// 	{
// 		s := U(0)
		
// 		j := U(0)
// 		k := U(0)
		
// 		u7mi := U(0)
// 		u6mi := U(0)
// 		u7s1 := U(0)
// 		u6s1 := U(0)
// 		usi  := U(0)
// 		u7ss := U(0)
		 
// 		addr1_var := U(0)
// 		addr2_var := counter.resize(7)
// 		addr3_var := U(0)
// 		inverse := True
// 	}
// 	.otherwise
// 	{
// 		s := counter
		
// 		j := (s >> (U(6) - i)).resize(7)
// 		k := (s & ((U(64) >> i) - U(1))).resize(7)
		
// 		u7mi := (U(7) - i).resize(3)
// 		u6mi := (U(6) - i).resize(3)
// 		u7s1 := (U(1) << u7mi).resize(7)
// 		u6s1 := (U(1) << u6mi).resize(7)
// 		usi  := (U(1) << i).resize(7)
// 		u7ss := (s >> u6mi).resize(7)
		
// 		addr1_var := (j * u7s1 + k).resize(7)
// 		addr2_var := (j * u7s1 + k + u6s1).resize(7)
// 		addr3_var := (usi + u7ss)//.resize(7)
// 		inverse := False
// 	}
	
// 	del_reg1 := delay
// 	del_reg2 := del_reg1 
// 	del_reg3 := del_reg2 
// 	del_reg4 := del_reg3 
// 	del_reg5 := del_reg4 
// 	del_reg6 := del_reg5 
// 	del_reg7 := del_reg6 
	
// 	when (delay || del_reg1 || del_reg2 || del_reg3 || del_reg4 || del_reg5 || del_reg6 || del_reg7)
// 	{
// 		addr1_del1 := addr1_var
// 		addr1_del2 := addr1_del1
// 		addr1_del3 := addr1_del2
// 		addr1_del4 := addr1_del3
// 		addr1_del5 := addr1_del4
// 		addr1_del6 := addr1_del5
// 		addr1_del7 := addr1_del6
// 		addr1_del8 := addr1_del7
		
// 		addr2_del1 := addr2_var
// 		addr2_del2 := addr2_del1
// 		addr2_del3 := addr2_del2
// 		addr2_del4 := addr2_del3
// 		addr2_del5 := addr2_del4
// 		addr2_del6 := addr2_del5
// 		addr2_del7 := addr2_del6
// 		addr2_del8 := addr2_del7
// 	}
	
// 	when (fin_sig | fin_count > 0)
// 	{
// 		fin_count := fin_count + 1
// 	}
	
// 	io.addr1   := addr1_var //addr1_del1
// 	io.addr2   := addr2_var //addr2_del1
// 	io.addr3   := addr3_var
// 	io.addr1w  := addr1_del8
// 	io.addr2w  := addr2_del8
// 	io.finish  := (fin_count === U(8))
// 	io.inverse := (inverse | ((io.select === False) & ((fin_count > U(0)) & (fin_count <= U(8)))))
// }

// //object MyTopLevelVerilog extends App 
// //{
// //	Config.spinal.generateVerilog(Address_Generator())
// //}
