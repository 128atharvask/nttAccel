import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec
import nttAccel.NTT

class NTTTest extends AnyFlatSpec with ChiselScalatestTester {
  "NTT" should "perform computations and handle data transfers" in {
    test(new NTT()) { dut =>
      // Initialize inputs
      dut.io.regWrAddr.poke(0.U)
      dut.io.regWrData.poke(0.U)
      dut.io.regWriteEn.poke(false.B)
      dut.io.regReadAddr.poke(0.U)
      dut.io.regReadData.expect(0.U)
      
      // Reset sequence
      dut.reset.poke(true.B)
      dut.clock.step(1)
      dut.reset.poke(false.B)
      dut.clock.step(4)

      // Send initial data
      for (i <- 0 until 128) {
        // Simulate S_AXIS_TDATA with incremented values
        dut.io.regWrAddr.poke(3.U) // Write to reg3 (data register)
        dut.io.regWrData.poke((2 * i).U)
        dut.io.regWriteEn.poke(true.B)
        dut.clock.step(1)
        dut.io.regWriteEn.poke(false.B)
        dut.clock.step(3)
      }

      // Send additional data for next 128 values
      for (i <- 0 until 128) {
        dut.io.regWrAddr.poke(3.U)
        dut.io.regWrData.poke((2 * i + 1).U)
        dut.io.regWriteEn.poke(true.B)
        dut.clock.step(1)
        dut.io.regWriteEn.poke(false.B)
        dut.clock.step(3)
      }

      // Set operation mode for NTT
      dut.io.regWrAddr.poke(5.U) // Write to reg5 (operation mode)
      dut.io.regWrData.poke(1.U) // Set mode to NTT
      dut.io.regWriteEn.poke(true.B)
      dut.clock.step(1)
      dut.io.regWriteEn.poke(false.B)

      // Wait for computation to complete
      dut.clock.step(600)

      // Read outputs as per M_AXIS_TREADY behavior
      for (_ <- 0 until 128) {
        dut.io.regReadAddr.poke(3.U)
        val output = dut.io.regReadData.peekInt()
        println(f"Output Data: $output%x")
        dut.clock.step(3)
      }
    }
  }
}
