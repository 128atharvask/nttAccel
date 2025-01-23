// package nttAccel

// import chisel3._
// import chisel3.util._

// // has psis (precomputed twiddle factors)
// class TwiddleROM extends Module {
//   val io = IO(new Bundle {
//     val read_enable  = Input(Bool())
//     val read_address = Input(UInt(7.W))
//     val read_data    = Output(UInt(16.W))
//   })

//   // ROM initialization with a Vec
//   val rom = VecInit(Seq(
//     1.U, 1729.U, 2580.U, 3289.U, 2642.U, 630.U, 1897.U, 848.U,
//     1062.U, 1919.U, 193.U, 797.U, 2786.U, 3260.U, 569.U, 1746.U,
//     296.U, 2447.U, 1339.U, 1476.U, 3046.U, 56.U, 2240.U, 1333.U,
//     1426.U, 2094.U, 535.U, 2882.U, 2393.U, 2879.U, 1974.U, 821.U,
//     289.U, 331.U, 3253.U, 1756.U, 1197.U, 2304.U, 2277.U, 2055.U,
//     650.U, 1977.U, 2513.U, 632.U, 2865.U, 33.U, 1320.U, 1915.U,
//     2319.U, 1435.U, 807.U, 452.U, 1438.U, 2868.U, 1534.U, 2402.U,
//     2647.U, 2617.U, 1481.U, 648.U, 2474.U, 3110.U, 1227.U, 910.U,
//     17.U, 2761.U, 583.U, 2649.U, 1637.U, 723.U, 2288.U, 1100.U,
//     1409.U, 2662.U, 3281.U, 233.U, 756.U, 2156.U, 3015.U, 3050.U,
//     1703.U, 1651.U, 2789.U, 1789.U, 1847.U, 952.U, 1461.U, 2687.U,
//     939.U, 2308.U, 2437.U, 2388.U, 733.U, 2337.U, 268.U, 641.U,
//     1584.U, 2298.U, 2037.U, 3220.U, 375.U, 2549.U, 2090.U, 1645.U,
//     1063.U, 319.U, 2773.U, 757.U, 2099.U, 561.U, 2466.U, 2594.U,
//     2804.U, 1092.U, 403.U, 1026.U, 1143.U, 2150.U, 2775.U, 886.U,
//     1722.U, 1212.U, 1874.U, 1029.U, 2110.U, 2935.U, 885.U, 2154.U
//   ))

//   // Read data from ROM using Vec
//   io.read_data := Mux(io.read_enable, rom(io.read_address), 0.U) // Default to 0 if not enabled
// }

package nttAccel

import chisel3._
import chisel3.util._

// Has psis (precomputed twiddle factors)
class TwiddleROM extends Module {
  val io = IO(new Bundle {
    val read_enable  = Input(Bool())
    val read_address = Input(UInt(7.W))
    val read_data    = Output(UInt(16.W))
  })

  // Initialize ROM using SyncReadMem
  val rom = SyncReadMem(128, UInt(16.W)) // 128 entries, each 16 bits wide

  // ROM initialization with precomputed values
  val initValues = Seq(
    1.U, 1729.U, 2580.U, 3289.U, 2642.U, 630.U, 1897.U, 848.U,
    1062.U, 1919.U, 193.U, 797.U, 2786.U, 3260.U, 569.U, 1746.U,
    296.U, 2447.U, 1339.U, 1476.U, 3046.U, 56.U, 2240.U, 1333.U,
    1426.U, 2094.U, 535.U, 2882.U, 2393.U, 2879.U, 1974.U, 821.U,
    289.U, 331.U, 3253.U, 1756.U, 1197.U, 2304.U, 2277.U, 2055.U,
    650.U, 1977.U, 2513.U, 632.U, 2865.U, 33.U, 1320.U, 1915.U,
    2319.U, 1435.U, 807.U, 452.U, 1438.U, 2868.U, 1534.U, 2402.U,
    2647.U, 2617.U, 1481.U, 648.U, 2474.U, 3110.U, 1227.U, 910.U,
    17.U, 2761.U, 583.U, 2649.U, 1637.U, 723.U, 2288.U, 1100.U,
    1409.U, 2662.U, 3281.U, 233.U, 756.U, 2156.U, 3015.U, 3050.U,
    1703.U, 1651.U, 2789.U, 1789.U, 1847.U, 952.U, 1461.U, 2687.U,
    939.U, 2308.U, 2437.U, 2388.U, 733.U, 2337.U, 268.U, 641.U,
    1584.U, 2298.U, 2037.U, 3220.U, 375.U, 2549.U, 2090.U, 1645.U,
    1063.U, 319.U, 2773.U, 757.U, 2099.U, 561.U, 2466.U, 2594.U,
    2804.U, 1092.U, 403.U, 1026.U, 1143.U, 2150.U, 2775.U, 886.U,
    1722.U, 1212.U, 1874.U, 1029.U, 2110.U, 2935.U, 885.U, 2154.U
  )

  // Load values into SyncReadMem during elaboration
  for ((value, index) <- initValues.zipWithIndex) {
    rom.write(index.U, value)
  }

  // Output read data
  io.read_data := Mux(io.read_enable, rom.read(io.read_address), 0.U)
}
