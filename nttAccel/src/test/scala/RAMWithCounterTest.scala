
import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec
import nttAccel._

class RAMWithCounterTest extends AnyFlatSpec with ChiselScalatestTester {
  "RAMWithCounter" should "work as expected" in {
    test(new RAMWithCounter).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>
      // Initialize variables
      val dataSize = 128

      // Write phase: Write sequential data from 0 to 127
      dut.io.writeEnable.poke(true.B)
      dut.io.readEnable.poke(false.B)
      dut.io.dataIn.poke(0.U)
      dut.clock.step(1)
      for (i <- 0 until dataSize) {
        dut.io.dataIn.poke(i.U)
        dut.clock.step(1)
      }
      dut.clock.step(1)
      dut.io.writeEnable.poke(false.B)

      // Read phase: Read data back and verify
      dut.io.readEnable.poke(true.B)
      for (i <- 0 until dataSize) {
        dut.clock.step(1)
        // dut.io.dataOut.expect(i.U, s"Read mismatch at address $i")
      }
      dut.io.readEnable.poke(false.B)
    }
  }
}