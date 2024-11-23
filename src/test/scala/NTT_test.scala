import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec
import nttAccel._

class NTTTest extends AnyFlatSpec with ChiselScalatestTester {
  "NTT" should "perform input and output operations as expected" in {
    test(new NTT).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>

      // Initialize the clock
      dut.clock.setTimeout(10000)

      // Reset the module
      dut.reset.poke(true.B)
      dut.clock.step(2)
      dut.reset.poke(false.B)

      // Set initial values
      dut.io.input_valid.poke(false.B)
      dut.io.output_ready.poke(false.B)

      // Drive inputs (first pass)
      println("Starting first pass of input sequence...")
      for (i <- 0 until 128) {
        dut.io.input_valid.poke(true.B)
        dut.io.input_data.poke((2 * i).U)
        dut.clock.step(1)
        
        // Print input values
        println(s"Cycle ${i}: Input Data = ${(2 * i)}, Input Valid = true")

        dut.io.input_valid.poke(false.B)
        dut.clock.step(3)
      }

      dut.clock.step(2)

      // Drive inputs, second pass with new data
      println("Starting second pass of input sequence...")
      for (i <- 0 until 128) {
        dut.io.input_valid.poke(true.B)
        dut.io.input_data.poke((2 * i + 1).U)
        dut.clock.step(1)

        // Print input values
        println(s"Cycle ${i + 128}: Input Data = ${(2 * i + 1)}, Input Valid = true")

        dut.io.input_valid.poke(false.B)
        dut.clock.step(3)
      }

    // //   ---- RAM test ----

    //   dut.clock.step(2)

    // //   printf(p"readValid1: ${dut.data_ram1.io.readValid1}\n")

    //   // Reading back data from DataRAM1
    //   println("Reading back DataRAM1...")
    //   for (i <- 0 until 128) {
    //     dut.io.input_valid.poke(false.B) // Ensure no new writes
    //     dut.data_ram1.io.readValid1.poke(true.B)
    //     dut.data_ram1.io.readAddress1.poke(i.U)
    //     dut.clock.step(1)

    //     val readData = dut.data_ram1.io.readData1.peek().litValue
    //     println(s"Address: $i, DataRAM1 Data: $readData")
    //   }

    //   // Reading back data from DataRAM2
    //   println("Reading back DataRAM2...")
    //   for (i <- 0 until 128) {
    //     dut.io.input_valid.poke(false.B) // Ensure no new writes
    //     dut.data_ram2.io.readValid1.poke(true.B)
    //     dut.data_ram2.io.readAddress1.poke(i.U)
    //     dut.clock.step(1)

    //     val readData = dut.data_ram2.io.readData1.peek().litValue
    //     println(s"Address: $i, DataRAM2 Data: $readData")
    //   }

    // //   ---- RAM test ends ----

      // Send a command to trigger NTT operation
      dut.io.input_valid.poke(true.B)
      dut.io.input_data.poke(1.U)   // Trigger command for NTT operation
      dut.clock.step(2) // dev
      println("Triggering NTT operation: Input Data = 1, Input Valid = true")
      dut.io.input_valid.poke(false.B)
      dut.clock.step(600)

      // Check output behavior, replicating M_AXIS_TREADY logic
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

      dut.clock.step(10) // Add extra cycles

      for (i <- 0 until 128) {
        dut.io.output_ready.poke(true.B)
        dut.clock.step(1)
        
        // Capture output values if valid
        if (dut.io.output_valid.peek().litToBoolean) {
          println(s"Cycle ${i + 384}: Output Data = ${dut.io.output_data.peek().litValue}, Output Valid = true")
        }

        dut.io.output_ready.poke(false.B)
        dut.clock.step(2)
      }

      // Final steps and finish
      dut.clock.step(10)
    }
  }
}