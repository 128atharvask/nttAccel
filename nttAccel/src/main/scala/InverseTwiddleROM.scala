package nttAccel

import chisel3._
import chisel3.util._

// has inv_psis (precomputed inverse twiddle factors)
class InverseTwiddleROM extends Module {
  val io = IO(new Bundle {
    val read_enable  = Input(Bool())
    val read_address = Input(UInt(7.W))
    val read_data    = Output(UInt(16.W))
  })

  // Initialize ROM using SyncReadMem
  val rom = SyncReadMem(128, UInt(16.W)) // 128 entries, each 16 bits wide

  // ROM initialization with precomputed values
  val initValues = Seq(
    1.U, 1600.U, 40.U, 749.U, 2481.U, 1432.U, 2699.U, 687.U,
    1583.U, 2760.U, 69.U, 543.U, 2532.U, 3136.U, 1410.U, 2267.U,
    2508.U, 1355.U, 450.U, 936.U, 447.U, 2794.U, 1235.U, 1903.U,
    1996.U, 1089.U, 3273.U, 283.U, 1853.U, 1990.U, 882.U, 3033.U,
    2419.U, 2102.U, 219.U, 855.U, 2681.U, 1848.U, 712.U, 682.U,
    927.U, 1795.U, 461.U, 1891.U, 2877.U, 2522.U, 1894.U, 1010.U,
    1414.U, 2009.U, 3296.U, 464.U, 2697.U, 816.U, 1352.U, 2679.U,
    1274.U, 1052.U, 1025.U, 2132.U, 1573.U, 76.U, 2998.U, 3040.U,
    1175.U, 2444.U, 394.U, 1219.U, 2300.U, 1455.U, 2117.U, 1607.U,
    2443.U, 554.U, 1179.U, 2186.U, 2303.U, 2926.U, 2237.U, 525.U,
    735.U, 863.U, 2768.U, 1230.U, 2572.U, 556.U, 3010.U, 2266.U,
    1684.U, 1239.U, 780.U, 2954.U, 109.U, 1292.U, 1031.U, 1745.U,
    2688.U, 3061.U, 992.U, 2596.U, 941.U, 892.U, 1021.U, 2390.U,
    642.U, 1868.U, 2377.U, 1482.U, 1540.U, 540.U, 1678.U, 1626.U,
    279.U, 314.U, 1173.U, 2573.U, 3096.U, 48.U, 667.U, 1920.U,
    2229.U, 1041.U, 2606.U, 1692.U, 680.U, 2746.U, 568.U, 3312.U
  )

  // Load values into SyncReadMem during elaboration
  for ((value, index) <- initValues.zipWithIndex) {
    rom.write(index.U, value)
  }

  // Output read data
  io.read_data := Mux(io.read_enable, rom.read(io.read_address), 0.U)
}