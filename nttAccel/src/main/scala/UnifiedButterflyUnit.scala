package nttAccel

import chisel3._
import chisel3.util._

class UnifiedButterflyUnit extends Module {
  val io = IO(new Bundle {
    val start = Input(Bool())   // Input signal to start the butterfly computation
    val enable = Input(Bool())  // Enables the butterfly unit; active when true
    val select = Input(Bool())  // 1 for NTT, 0 for INTT
    val inverse = Input(Bool()) // since intt requires final normalization, we need to compute and store using Write2 interface of DataRAMs
    val u = Input(UInt(16.W))   // First input operand for the butterfly computation
    val v = Input(UInt(16.W))   // Second input operand for the butterfly computation
    val w = Input(UInt(16.W))   // Twiddle factor input for multiplication in the butterfly computation
    val x = Output(UInt(16.W))  // Output result representing one half of the butterfly computation
    val y = Output(UInt(16.W))  // Output result representing the other half of the butterfly computation
    val finish = Output(Bool()) // Output signal indicating computation completion
  })

  // Delay registers
  val str_del0 = RegInit(false.B)
  val str_del1 = RegInit(false.B)
  val str_del2 = RegInit(false.B)
  val str_del3 = RegInit(false.B)
  val str_del4 = RegInit(false.B)
  val str_del5 = RegInit(false.B)
  val str_del6 = RegInit(false.B)
  // val str_del7 = RegInit(false.B)

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
      }
      .otherwise {
        reg1 := (io.u + 3329.U - io.v)
      }

      when(io.u + io.v > 3329.U) {
        reg0 := (io.u + io.v - 3329.U)
      }
      .otherwise {
        reg0 := (io.u + io.v)
      }
    }
    .otherwise {
      reg0 := io.u
      reg1 := io.v
    }

    when(io.inverse === false.B) {
      reg2 := io.w
    }
    .otherwise {
      reg2 := 3303.U
    }
    str_del0 := io.start

    // Cycle 2
    c1 := reg1 * reg2
    reg_del1 := reg0
    str_del1 := str_del0

    // Cycle 3
    ta := ((c1 * 5039.U) >> 24).asUInt
    c1_del1 := c1
    reg_del2 := reg_del1
    str_del2 := str_del1

    // Cycle 4
    tb := ((ta << 11) + (ta << 10) + (ta << 8) + ta).asUInt
    c1_del2 := c1_del1
    reg_del3 := reg_del2
    str_del3 := str_del2

    // Cycle 5
    c2 := (c1_del2 - tb).asUInt
    reg_del4 := reg_del3
    str_del4 := str_del3

    // Cycle 6
    when(c2 > 3329.U) {
      c3 := (c2 - 3329.U).asUInt
    }
    .otherwise {
      c3 := c2.asUInt
    }
    reg_del5 := reg_del4
    str_del5 := str_del4

    // Cycle 7
    // when(io.select === false.B) {
    //   y_reg := c3
    //   x_reg := reg_del5
    // }
    // .otherwise {
    //   when(reg_del5 > c3) {
    //     y_reg := (reg_del5 - c3).asUInt
    //   }
    //   .otherwise {
    //     y_reg := (reg_del5 + 3329.U - c3).asUInt
    //   }

    //   when(reg_del5 + c3 > 3329.U) {
    //     x_reg := (reg_del5 + c3 - 3329.U).asUInt
    //   }
    //   .otherwise {
    //     x_reg := (reg_del5 + c3).asUInt
    //   }
    // }
    when(io.select === false.B) {
      y_reg := c3
      x_reg := reg_del5
    }
    .otherwise {
      when(reg_del5 >= c3) {
        y_reg := (reg_del5 - c3) % 3329.U
      }
      .otherwise {
        y_reg := (3329.U - (c3 - reg_del5) % 3329.U)
      }

      when((reg_del5 + c3) > 3329.U) {
        x_reg := (reg_del5 + c3 - 3329.U)
      }
      .otherwise {
        x_reg := (reg_del5 + c3)
      }
    }


    str_del6 := str_del5
  }

  // Output assignments
  io.x := x_reg
  io.y := y_reg
  io.finish := str_del6
}