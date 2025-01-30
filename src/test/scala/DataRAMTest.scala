package nttAccel

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class DataRAMTest extends AnyFlatSpec with ChiselScalatestTester {
  "DataRAM" should "handle concurrent reads and writes correctly" in {
    test(new DataRAM).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>
      val inputData = Seq(
        80, 1, 90, 0, 0, 0, 1, 3327, 0, 3328, 3328, 1, 2, 1, 3328, 1, 1, 0, 3328, 0, 1, 0, 3326, 3328, 1, 3327, 2, 0, 1, 1, 0, 0, 3328, 3328, 
        1, 3328, 1, 0, 0, 3328, 2, 2, 3327, 1, 2, 0, 0, 1, 0, 0, 0, 0, 1, 3328, 1, 1, 0, 0, 0, 0, 1, 1, 3328, 1, 0, 0, 1, 3328, 0, 0, 
        2, 0, 0, 0, 3328, 3327, 3327, 0, 3328, 3328, 0, 3327, 1, 3328, 1, 3328, 0, 2, 0, 3327, 1, 0, 1, 1, 0, 0, 3327, 3328, 0, 1, 0, 0, 
        3328, 0, 0, 3328, 3328, 0, 3328, 3327, 1, 1, 3328, 0, 1, 1, 3328, 0, 3328, 3326, 0, 0, 3328, 0, 2, 3328, 0, 1, 0, 0, 2, 3328, 0, 
        3328, 3328, 0, 0, 0, 1, 2, 3328, 3327, 1, 0, 2, 2, 2, 3327, 2, 0, 0, 1, 0, 3328, 3328, 0, 1, 0, 0, 0, 3328, 3328, 1, 3, 3328, 1, 
        3328, 2, 0, 0, 0, 2, 0, 1, 1, 3328, 1, 0, 3328, 3328, 0, 3328, 3328, 1, 3328, 3327, 1, 0, 1, 3328, 1, 3328, 1, 3328, 1, 0, 3327, 
        3328, 1, 3, 3327, 0, 1, 3327, 3, 0, 1, 1, 1, 1, 3327, 3328, 3328, 3328, 1, 3326, 0, 1, 1, 1, 0, 0, 1, 0, 0, 2, 0, 3328, 3328, 
        3328, 1, 3, 3328, 0, 3328, 2, 1, 3327, 0, 1, 1, 0, 3327, 3328, 1, 1, 0, 3328, 3328, 3328, 0, 3328, 0, 0, 3328, 0
      )
      
      // Initialize inputs
      dut.io.writeValid1.poke(false.B)
      dut.io.writeValid2.poke(false.B)
      dut.io.readValid1.poke(false.B)
      dut.io.readValid2.poke(false.B)

      // Test write operations
      // Port 1 write
      dut.io.writeValid1.poke(true.B)
      dut.io.writeAddress1.poke(10.U)
      dut.io.writeData1.poke(1234.U)
      dut.clock.step(1)
      dut.io.writeValid1.poke(false.B)

      // Port 2 write
      dut.io.writeValid2.poke(true.B)
      dut.io.writeAddress2.poke(20.U)
      dut.io.writeData2.poke(5678.U)
      dut.clock.step(1)
      dut.io.writeValid2.poke(false.B)

      // Test read operations
      // Port 1 read
      dut.io.readValid1.poke(true.B)
      dut.io.readAddress1.poke(10.U)
      dut.clock.step(1)
      dut.io.readData1.expect(1234.U)

      // Port 2 read
      dut.io.readValid2.poke(true.B)
      dut.io.readAddress2.poke(20.U)
      dut.clock.step(1)
      dut.io.readData2.expect(5678.U)

      // Test invalid read
      dut.io.readValid1.poke(false.B)
      dut.io.readAddress1.poke(10.U)
      dut.clock.step(1)
      dut.io.readData1.expect(0.U) // Should return 0 when readValid1 is false

      dut.io.readValid2.poke(false.B)
      dut.io.readAddress2.poke(20.U)
      dut.clock.step(1)
      dut.io.readData2.expect(0.U) // Should return 0 when readValid2 is false

      // Concurrent operations
      // Write to port 1 and read from port 2
      dut.io.writeValid1.poke(true.B)
      dut.io.writeAddress1.poke(30.U)
      dut.io.writeData1.poke(9999.U)

      dut.io.readValid2.poke(true.B)
      dut.io.readAddress2.poke(20.U)

      dut.clock.step(1)

      dut.io.writeValid1.poke(false.B)
      dut.io.readData2.expect(5678.U)

      // Verify new write
      dut.io.readValid1.poke(true.B)
      dut.io.readAddress1.poke(30.U)
      dut.clock.step(1)
      dut.io.readData1.expect(9999.U)
    }
  }
}
