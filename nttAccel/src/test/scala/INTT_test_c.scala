import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec
import nttAccel._

class INTTTest_C extends AnyFlatSpec with ChiselScalatestTester {
  "INTT" should "output u[0] from ucap[1]" in {
    test(new NTT_ctrl_dp).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>

      // Initialize the clock
      dut.clock.setTimeout(10000)

      // Reset the module
      dut.reset.poke(true.B)
      dut.clock.step(1)
      dut.reset.poke(false.B)

      val inputData = Seq(
        2254, 856, 2591, 490, 1761, 2482, 2520, 2147, 3300, 1014, 2303, 2766, 2440, 889, 214, 2630, 59, 1111, 1433, 156, 2692, 1148, 1661, 2311, 1894, 1050, 3120, 657, 2349, 2246, 326, 1463, 654, 717, 2493, 914, 1011, 2250, 2702, 2046, 1643, 3012, 1350, 408, 1157, 71, 446, 551, 1952, 168, 835, 2837, 2108, 1630, 1418, 2943, 1485, 2264, 684, 785, 1781, 3150, 1188, 3040, 103, 1093, 2243, 338, 500, 1679, 2816, 87, 2351, 2621, 2960, 1688, 2511, 1114, 692, 1416, 977, 2971, 15, 566, 3208, 168, 1908, 1462, 3188, 2115, 4, 1719, 1863, 3193, 762, 2460, 326, 2997, 1752, 3132, 2988, 1704, 656, 2569, 2448, 147, 1338, 1365, 946, 1580, 1476, 2098, 2389, 2779, 2099, 1657, 1976, 1680, 1598, 1903, 1090, 399, 1522, 41, 2572, 2057, 601, 1242, 106, 2606, 912, 873, 2336, 2067, 1295, 3043, 1336, 1618, 478, 883, 961, 663, 1022, 2588, 1047, 2329, 1388, 3046, 983, 243, 667, 2418, 1669, 1628, 1702, 2252, 643, 3296, 1901, 1734, 108, 2199, 3123, 363, 2205, 2650, 1942, 2526, 602, 3160, 1162, 2742, 195, 2811, 484, 1371, 3110, 3066, 2968, 1395, 1156, 2768, 2436, 787, 86, 1336, 2539, 2429, 2926, 2701, 3316, 1131, 2003, 218, 2627, 998, 1475, 2570, 723, 2252, 3322, 210, 3138, 1485, 2639, 913, 1261, 1913, 853, 673, 1997, 2753, 1677, 560, 449, 137, 2100, 784, 1593, 2605, 46, 1481, 2796, 2867, 2203, 2745, 3234, 2817, 1948, 464, 1403, 2045, 3238, 1418, 1114, 2975, 1911, 100, 3109, 3128, 1298, 992, 1017, 2499, 2251, 861, 175, 1399, 2013, 1156, 152, 608, 2919, 2904, 2594, 1624
      )
      
      dut.io.input_valid.poke(true.B)
      dut.io.input_data.poke(inputData(0).U)
      dut.clock.step(1)

      // Drive the first pass of input values
      println("Starting first pass of input sequence...")
      for (i <- 0 until 128) {
        // dut.io.input_valid.poke(true.B)
        dut.io.input_data.poke(inputData(i).U)
        dut.clock.step(1)
        
        // Print input values
        // println(s"Cycle ${i}: Input Data = ${inputData(i)}, Input Valid = true")

      }

      // Drive the second pass of input values (if needed, you can feed different values for the second pass)
      println("Starting second pass of input sequence...")
      for (i <- 0 until 128) {
        dut.io.input_data.poke(inputData(i+128).U)
        dut.clock.step(1)
        // Print input values
        // println(s"Cycle ${i}: Input Data = ${inputData(i)}, Input Valid = true")
      }
      dut.io.input_valid.poke(false.B)

      // Trigger INTT operation
      dut.io.input_valid.poke(true.B)
      dut.io.input_data.poke(0.U)   // Trigger command for INTT operation
      dut.clock.step(1)
      println("Triggering INTT operation: Input Data = 1, Input Valid = true")
      dut.io.input_valid.poke(false.B)

      // Wait for output_valid to become true before starting validation
      println("Waiting for output_valid to go high...")
      while (!dut.io.output_valid.peek().litToBoolean) {
        dut.clock.step(1)
      }

      // // Check output behavior
      // println("Starting output sequence...")
      // for (i <- 0 until 256) {
      //   dut.io.output_ready.poke(true.B)
      //   dut.clock.step(1)
        
      //   // Capture output values if valid
      //   if (dut.io.output_valid.peek().litToBoolean) {
      //     println(s"Output Data ${i} = ${dut.io.output_data.peek().litValue}")
      //   }
      // }

      val expectedOutput = Seq(
        3081, 946, 3047, 203, 1228, 1747, 2876, 2481, 1832, 1313, 1257, 1882, 1857, 385, 1717, 936, 955, 1624, 1657, 21, 498, 2096, 3005, 1678, 2512, 166, 801, 1711, 2662, 2607, 1491, 2725, 3035, 1, 2747, 1349, 1316, 1919, 2931, 260, 2426, 1355, 975, 1361, 1847, 943, 2884, 1031, 709, 695, 1838, 2954, 1187, 594, 55, 469, 2344, 3106, 758, 471, 2084, 3191, 1499, 2066, 1929, 2706, 2274, 506, 1476, 2043, 1271, 1115, 1113, 2592, 806, 2496, 1964, 651, 2304, 1477, 1895, 691, 2615, 2827, 1057, 607, 2207, 1642, 1370, 103, 912, 4, 726, 383, 1446, 587, 1085, 3038, 3186, 3224, 2865, 2602, 2996, 1510, 1729, 1946, 1028, 1560, 1342, 1443, 2526, 2459, 1139, 2706, 1738, 371, 634, 1868, 1920, 756, 3155, 3018, 1847, 477, 2633, 3247, 790, 1348, 1350, 2872, 2642, 2836, 652, 2931, 319, 158, 1900, 1659, 433, 2075, 3258, 261, 75, 688, 3317, 313, 342, 2083, 2009, 2110, 327, 2440, 2099, 1919, 2971, 1404, 764, 2035, 1951, 3323, 2874, 139, 1322, 1931, 885, 1816, 759, 734, 990, 3290, 3020, 2016, 1649, 2930, 2356, 231, 2858, 1243, 2408, 281, 266, 2467, 998, 2683, 1106, 1306, 1268, 1577, 267, 1979, 406, 3323, 2286, 2396, 184, 451, 2853, 2205, 1551, 2109, 2061, 2834, 3036, 2925, 1224, 1025, 3047, 2417, 2191, 253, 498, 434, 2832, 952, 852, 2195, 2321, 1412, 1832, 1542, 2722, 2927, 961, 2269, 2709, 2252, 135, 2714, 3292, 2547, 2989, 408, 1240, 1193, 534, 370, 2505, 1115, 2768, 1345, 2552, 816, 1537, 2786, 2210, 2585, 385, 1482, 715, 1589, 1042, 1345, 1531, 1100, 2945, 1267
      )

      println("Starting output validation...")
      var mismatch = false
      // even indices
      for (i <- 0 until 128) {
        dut.io.output_ready.poke(true.B)
        dut.clock.step(1)

        // Only validate when output_valid is high
        if (dut.io.output_valid.peek().litToBoolean) {
          val actualOutput = dut.io.output_data.peek().litValue
          val expectedValue = expectedOutput(2*i)

          if (actualOutput != expectedValue) {
            mismatch = true
            println(s"Mismatch at index ${i}: Expected = $expectedValue, Actual = $actualOutput")
          } else {
            println(s"Output matches at index ${i}: Value = $actualOutput")
          }
        }
        // dut.io.output_ready.poke(false.B)
        // dut.clock.step(2)
      }
      dut.io.output_ready.poke(false.B)
      //good till here

      // odd indices
      dut.io.output_ready.poke(true.B)
      dut.clock.step(2)
      for (i <- 0 until 128) {
        dut.clock.step(1)
        // Only validate when output_valid is high
        if (dut.io.output_valid.peek().litToBoolean) {
          val actualOutput = dut.io.output_data.peek().litValue
          val expectedValue = expectedOutput(2*i+1)

          if (actualOutput != expectedValue) {
            mismatch = true
            println(s"Mismatch at index ${i}: Expected = $expectedValue, Actual = $actualOutput")
          } else {
            println(s"Output matches at index ${i}: Value = $actualOutput")
          }
        }
        // dut.io.output_ready.poke(false.B)
        // dut.clock.step(2)
      }
      dut.io.output_ready.poke(false.B)

      assert(!mismatch, "Output validation failed! See mismatches above.")
      println("Output validation completed.")
    }
  }
}