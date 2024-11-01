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
  val mem = SyncReadMem(128, UInt(16.W))

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

// object DataRAMVerilog extends App {
//   chisel3.Driver.execute(args, () => new DataRAM)
// }


// package projectname

// import spinal.core._
// import spinal.lib._

// case class Data_RAM() extends Component 
// {
//   	val io = new Bundle 
//   	{
//     	val writeValid1   = in  Bool()
// 		val writeAddress1 = in  (UInt(7 bits))
// 		val writeData1    = in  (UInt(16 bits))
		
// 		val writeValid2   = in  Bool()
// 		val writeAddress2 = in  (UInt(7 bits))
// 		val writeData2    = in  (UInt(16 bits))
		
// 		val readValid1    = in  Bool()
// 		val readAddress1  = in  (UInt(7 bits))
// 		val readData1     = out (UInt(16 bits))
		
// 		val readValid2    = in  Bool()
// 		val readAddress2  = in  (UInt(7 bits))
// 		val readData2     = out (UInt(16 bits))
//     }
	
// 	val mem = Mem(UInt(16 bits), wordCount = 128)

// 	mem.write(enable  = io.writeValid1,
// 			  address = io.writeAddress1,
// 			  data    = io.writeData1)
  							    
// 	mem.write(enable  = io.writeValid2,
// 			  address = io.writeAddress2,
// 			  data    = io.writeData2)

// 	io.readData1 := mem.readSync(enable  = io.readValid1,
//   							    address = io.readAddress1)
  							    
// 	io.readData2 := mem.readSync(enable  = io.readValid2,
//   							    address = io.readAddress2)
// }

// //object MyTopLevelVerilog extends App 
// //{
// //	Config.spinal.generateVerilog(RAM())
// //}
