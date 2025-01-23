package nttAccel

import chisel3._
import chisel3.util._

class DataRAM extends Module {
  val io = IO(new Bundle {
    val writeValid1   = Input(Bool())
    val writeAddress1 = Input(UInt(7.W))
    val writeData1    = Input(UInt(16.W))
    
    val writeValid2   = Input(Bool())
    val writeAddress2 = Input(UInt(7.W))
    val writeData2    = Input(UInt(16.W))
    
    val readValid1    = Input(Bool())
    val readAddress1  = Input(UInt(7.W))
    val readData1     = Output(UInt(16.W))
    
    val readValid2    = Input(Bool())
    val readAddress2  = Input(UInt(7.W))
    val readData2     = Output(UInt(16.W))
  })

  // Memory definition
  val mem = SyncReadMem(128, UInt(16.W))  // causing errors, delay data read by a cycle

  // Write operations
  when(io.writeValid1) {
    mem.write(io.writeAddress1, io.writeData1)
  }

  when(io.writeValid2) {
    mem.write(io.writeAddress2, io.writeData2)
  }

  // Read operations
  io.readData1 := Mux(io.readValid1, mem.read(io.readAddress1), 0.U) // Default to 0 if not valid
  io.readData2 := Mux(io.readValid2, mem.read(io.readAddress2), 0.U) // Default to 0 if not valid
}