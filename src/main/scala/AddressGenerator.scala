package nttAccel

import chisel3._
import chisel3.util._

class AddressGenerator extends Module {
  val io = IO(new Bundle {
    val enable  = Input(Bool())
    val select  = Input(Bool())     // ?
    val addr1   = Output(UInt(7.W))
    val addr2   = Output(UInt(7.W))
    val addr3   = Output(UInt(7.W))
    val addr1w  = Output(UInt(7.W))
    val addr2w  = Output(UInt(7.W))
    val inverse = Output(Bool())    // used in gs_ntt
    val finish  = Output(Bool())
  })

  val addr1_del1 = RegInit(0.U(7.W))
  val addr1_del2 = RegInit(0.U(7.W))
  val addr1_del3 = RegInit(0.U(7.W))
  val addr1_del4 = RegInit(0.U(7.W))
  val addr1_del5 = RegInit(0.U(7.W))
  val addr1_del6 = RegInit(0.U(7.W))
  val addr1_del7 = RegInit(0.U(7.W))
  val addr1_del8 = RegInit(0.U(7.W))
  
  val addr2_del1 = RegInit(0.U(7.W))
  val addr2_del2 = RegInit(0.U(7.W))
  val addr2_del3 = RegInit(0.U(7.W))
  val addr2_del4 = RegInit(0.U(7.W))
  val addr2_del5 = RegInit(0.U(7.W))
  val addr2_del6 = RegInit(0.U(7.W))
  val addr2_del7 = RegInit(0.U(7.W))
  val addr2_del8 = RegInit(0.U(7.W))

  val fin_count = RegInit(0.U(4.W))
  val counter = RegInit(0.U(7.W))
  val state   = RegInit(0.U(3.W))
  val inverse  = RegInit(false.B)
  val str_reg = RegInit(false.B)

  val del_reg1 = RegInit(false.B)
  val del_reg2 = RegInit(false.B)
  val del_reg3 = RegInit(false.B)
  val del_reg4 = RegInit(false.B)
  val del_reg5 = RegInit(false.B)
  val del_reg6 = RegInit(false.B)
  val del_reg7 = RegInit(false.B)
  
  val fin_sig = Wire(Bool())
  val delay = Wire(Bool())

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
  
  val s = Mux(state === 7.U, 0.U(7.W), counter)
  val j = Mux(state === 7.U, 0.U(7.W), (s >> (6.U - i)))
  val k = Mux(state === 7.U, 0.U(7.W), (s & ((64.U >> i) - 1.U)).asUInt)


  val u7mi = Mux(state === 7.U, 0.U(3.W), (7.U - i).asUInt)
  val u6mi = Mux(state === 7.U, 0.U(3.W), (6.U - i).asUInt)
  val u7s1 = Mux(state === 7.U, 0.U(7.W), (1.U << u7mi).asUInt)
  val u6s1 = Mux(state === 7.U, 0.U(7.W), (1.U << u6mi).asUInt)
  val usi  = Mux(state === 7.U, 0.U(7.W), (1.U << i).asUInt)
  val u7ss = Mux(state === 7.U, 0.U(7.W), (s >> u6mi).asUInt)

  val addr1_var = Mux(state === 7.U, 0.U(7.W), (j * u7s1 + k).asUInt)
  val addr2_var = Mux(state === 7.U, counter, (j * u7s1 + k + u6s1).asUInt)
  val addr3_var = Mux(state === 7.U, 0.U(7.W), (usi + u7ss))
  inverse := Mux(state === 7.U, true.B, false.B)

  del_reg1 := delay
  del_reg2 := del_reg1
  del_reg3 := del_reg2
  del_reg4 := del_reg3
  del_reg5 := del_reg4
  del_reg6 := del_reg5
  del_reg7 := del_reg6

  when(delay || del_reg1 || del_reg2 || del_reg3 || del_reg4 || del_reg5 || del_reg6 || del_reg7) {
    addr1_del1 := addr1_var
    addr1_del2 := addr1_del1
    addr1_del3 := addr1_del2
    addr1_del4 := addr1_del3
    addr1_del5 := addr1_del4
    addr1_del6 := addr1_del5
    addr1_del7 := addr1_del6

    addr2_del1 := addr2_var
    addr2_del2 := addr2_del1
    addr2_del3 := addr2_del2
    addr2_del4 := addr2_del3
    addr2_del5 := addr2_del4
    addr2_del6 := addr2_del5
    addr2_del7 := addr2_del6
  }

  // Fin count logic
  when(fin_sig || fin_count > 0.U) {
    fin_count := fin_count + 1.U
  }

  // Assign outputs
  io.addr1   := addr1_var
  io.addr2   := addr2_var
  io.addr3   := addr3_var
  io.addr1w  := addr1_del8
  io.addr2w  := addr2_del8
  io.finish  := (fin_count === 8.U)
  io.inverse := (inverse || (io.select === false.B && (fin_count > 0.U && fin_count <= 8.U)))
}

// object AddressGeneratorVerilog extends App {
//   chisel3.Driver.execute(args, () => new AddressGenerator)
// }