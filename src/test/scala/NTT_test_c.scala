import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec
import nttAccel._

class NTTTest_C extends AnyFlatSpec with ChiselScalatestTester {
  "NTT" should "output scap0" in {
    test(new NTT).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>

      // Initialize the clock
      dut.clock.setTimeout(10000)

      // Reset the module
      dut.reset.poke(true.B)
      dut.clock.step(2)
      dut.reset.poke(false.B)

      // Define the input data for the test (128 values provided by the user)
      val inputData = Seq(
        0, 1, 0, 0, 0, 0, 1, 3327, 0, 3328, 3328, 1, 2, 1, 3328, 1, 1, 0, 3328, 0, 1, 0, 3326, 3328, 1, 3327, 2, 0, 1, 1, 0, 0, 3328, 3328, 
        1, 3328, 1, 0, 0, 3328, 2, 2, 3327, 1, 2, 0, 0, 1, 0, 0, 0, 0, 1, 3328, 1, 1, 0, 0, 0, 0, 1, 1, 3328, 1, 0, 0, 1, 3328, 0, 0, 
        2, 0, 0, 0, 3328, 3327, 3327, 0, 3328, 3328, 0, 3327, 1, 3328, 1, 3328, 0, 2, 0, 3327, 1, 0, 1, 1, 0, 0, 3327, 3328, 0, 1, 0, 0, 
        3328, 0, 0, 3328, 3328, 0, 3328, 3327, 1, 1, 3328, 0, 1, 1, 3328, 0, 3328, 3326, 0, 0, 3328, 0, 2, 3328, 0, 1, 0, 0, 2, 3328, 0, 
        3328, 3328, 0, 0, 0, 1, 2, 3328, 3327, 1, 0, 2, 2, 2, 3327, 2, 0, 0, 1, 0, 3328, 3328, 0, 1, 0, 0, 0, 3328, 3328, 1, 3, 3328, 1, 
        3328, 2, 0, 0, 0, 2, 0, 1, 1, 3328, 1, 0, 3328, 3328, 0, 3328, 3328, 1, 3328, 3327, 1, 0, 1, 3328, 1, 3328, 1, 3328, 1, 0, 3327, 
        3328, 1, 3, 3327, 0, 1, 3327, 3, 0, 1, 1, 1, 1, 3327, 3328, 3328, 3328, 1, 3326, 0, 1, 1, 1, 0, 0, 1, 0, 0, 2, 0, 3328, 3328, 
        3328, 1, 3, 3328, 0, 3328, 2, 1, 3327, 0, 1, 1, 0, 3327, 3328, 1, 1, 0, 3328, 3328, 3328, 0, 3328, 0, 0, 3328, 0
      )

      // Reset input_valid and output_ready signals
      dut.io.input_valid.poke(false.B)
      dut.io.output_ready.poke(false.B)

      // Drive the first pass of input values
      println("Starting first pass of input sequence...")
      for (i <- 0 until 128) {
        dut.io.input_valid.poke(true.B)
        dut.io.input_data.poke(inputData(i).U)
        dut.clock.step(1)
        
        // Print input values
        println(s"Cycle ${i}: Input Data = ${inputData(i)}, Input Valid = true")

        dut.io.input_valid.poke(false.B)
        dut.clock.step(3)
      }

      dut.clock.step(2)

      // Drive the second pass of input values (if needed, you can feed different values for the second pass)
      println("Starting second pass of input sequence...")
      for (i <- 128 until 256) {
        dut.io.input_valid.poke(true.B)
        dut.io.input_data.poke(inputData(i).U)
        dut.clock.step(1)

        // Print input values
        println(s"Cycle ${i}: Input Data = ${inputData(i)}, Input Valid = true")

        dut.io.input_valid.poke(false.B)
        dut.clock.step(3)
      }

      // Trigger NTT operation
      dut.io.input_valid.poke(true.B)
      dut.io.input_data.poke(1.U)   // Trigger command for NTT operation
      dut.clock.step(2) // dev
      println("Triggering NTT operation: Input Data = 1, Input Valid = true")
      dut.io.input_valid.poke(false.B)
      dut.clock.step(600)

      // Check output behavior
      println("Starting output sequence...")
      for (i <- 0 until 128) {
        dut.io.output_ready.poke(true.B)
        dut.clock.step(1)
        
        // Capture output values if valid
        if (dut.io.output_valid.peek().litToBoolean) {
          println(s"Cycle ${i + 256}: Output Data = ${dut.io.output_data.peek().litValue}, Output Valid = true")
        }

        dut.io.output_ready.poke(false.B)
        dut.clock.step(2)
      }

      // Final steps and finish
      dut.clock.step(10)
    }
  }
}
