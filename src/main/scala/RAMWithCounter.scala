package nttAccel

import chisel3._
import chisel3.util._

class RAMWithCounter extends Module {
  val io = IO(new Bundle {
    val dataIn      = Input(UInt(16.W)) // Input data
    val writeEnable = Input(Bool())    // Enable signal for write phase
    val readEnable  = Input(Bool())    // Enable signal for read phase
    val dataOut     = Output(UInt(16.W)) // Output data
    val done        = Output(Bool())   // Signal to indicate completion
  })

  // Memory definition
  val mem = SyncReadMem(128, UInt(16.W))

  // Counter for addressing
  val counter = RegInit(0.U(7.W)) // 7-bit counter for 128 addresses

  // States for write and read phases
  val sIdle :: sWrite :: sRead :: Nil = Enum(3)
  val state = RegInit(sIdle)

  // Default values
  io.dataOut := 0.U
  io.done := false.B

  // State machine logic
  switch(state) {
    is(sIdle) {
      when(io.writeEnable) {
        state := sWrite
      }.elsewhen(io.readEnable) {
        state := sRead
      }
    }
    is(sWrite) {
      // Write data to memory
      mem.write(counter, io.dataIn)

      // Increment counter
      counter := counter + 1.U

      // Transition to read phase when done writing
      when(counter === 127.U) {
        counter := 0.U // Reset counter for read phase
        state := sRead
      }
    }
    is(sRead) {
      // Read data from memory
      io.dataOut := mem.read(counter, true.B)

      // Increment counter
      counter := counter + 1.U

      // Signal done when read completes
      when(counter === 127.U) {
        counter := 0.U // Reset counter for the next operation
        io.done := true.B
        state := sIdle
      }
    }
  }
}
