import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec
import nttAccel.AddressGenerator

class AddressGenTest extends AnyFlatSpec with ChiselScalatestTester {
  "AddressGenerator" should "generate addresses and handle enable and select" in {
    test(new AddressGenerator()) { dut =>
      // Initialize inputs
      dut.io.enable.poke(false.B)
      dut.io.select.poke(false.B)
      dut.clock.step(1)

      // Ensure initial values
      dut.io.finish.expect(false.B)
      dut.io.addr1.expect(0.U)
      dut.io.addr2.expect(1.U)
      dut.io.addr3.expect(64.U)
      dut.io.addr1w.expect(0.U)
      dut.io.addr2w.expect(0.U)
      dut.io.inverse.expect(false.B)

      // Enable address generation
      dut.io.enable.poke(true.B)
      dut.clock.step(1)

      // Test address generation and delay chains
      for (cycle <- 0 until 100) {
        dut.clock.step(1)
        // Print the generated addresses and delayed values for observation
        println(s"Cycle $cycle:")
        println(s"  addr1: ${dut.io.addr1.peekInt()}")
        println(s"  addr2: ${dut.io.addr2.peekInt()}")
        println(s"  addr3: ${dut.io.addr3.peekInt()}")
        println(s"  addr1w: ${dut.io.addr1w.peekInt()}")
        println(s"  addr2w: ${dut.io.addr2w.peekInt()}")
        println(s"  finish: ${dut.io.finish.peekBoolean()}")
      }

      // Test select and finish behavior
      dut.io.select.poke(true.B)
      dut.clock.step(10)
      dut.io.finish.expect(false.B)

      // Observe finish signal after sequence completion
      dut.io.enable.poke(false.B)
      dut.clock.step(10)
      dut.io.finish.expect(true.B)

      // Test inverse mode
      dut.io.select.poke(false.B)
      dut.io.enable.poke(true.B)
      dut.clock.step(10)
      println(s"Inverse Mode: ${dut.io.inverse.peekBoolean()}")
    }
  }
}
