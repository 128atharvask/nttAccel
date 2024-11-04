package nttAccel

import chisel3._
import chisel3.util._

class NTT extends Module {
  val io = IO(new Bundle {
    val regWrAddr = Input(UInt(3.W)) // Address for writing to registers
    val regWrData = Input(UInt(32.W)) // Data for writing to registers
    val regWriteEn = Input(Bool()) // Enable signal for register write
    val regReadAddr = Input(UInt(3.W)) // Address for reading from registers
    val regReadData = Output(UInt(32.W)) // Data output for register read
  })
    
  //regs0 indicates start computation
  //regs1 indicates finish computation
  //regs2 indicates write mode or read mode
  //regs3 stores data to be sent into BRAM or data that is read from BRAM
  //regs4 stores write or read address for BRAM access
  //regs5 indicates NTT or INTT
  //regs6 indicates to access BRAM1 or BRAM2
  val regs = RegInit(VecInit(Seq.fill(7)(0.U(32.W))))

  // Write to the registers
  when(io.regWriteEn) {
    regs(io.regWrAddr) := io.regWrData
  }

  // Read from the registers
  io.regReadData := regs(io.regReadAddr)

  // Butterfly units, ROMs, and RAMs
  val bf_unit1 = Module(new UnifiedButterflyUnit())
  val bf_unit2 = Module(new UnifiedButterflyUnit())
  val tf_rom = Module(new TwiddleROM())
  val itf_rom = Module(new InverseTwiddleROM())
  val data_ram1 = Module(new DataRAM())
  val data_ram2 = Module(new DataRAM())
  val addr_gen = Module(new AddressGenerator())

  // Control signals
  // TODO: BDataRAM??
  val start_bf = RegInit(false.BDataRAM)
  val read_done1a = RegInit(false.B)
  val read_done1b = RegInit(false.B)
  val read_done2 = RegInit(false.B)

  // Start address generator when regs(0) becomes 1
  when(regs(0) === 1.U) {
    addr_gen.io.enable := true.B
    regs(0) := 0.U
    regs(1) := 0.U
    start_bf := true.B
  }.otherwise {
    addr_gen.io.enable := false.B
  }

  // Select NTT or INTT operation based on regs(5)
  when(regs(5) === 0.U) {
    addr_gen.io.select := false.B
    bf_unit1.io.select := false.B
    bf_unit2.io.select := false.B
  }.otherwise {
    addr_gen.io.select := true.B
    bf_unit1.io.select := true.B
    bf_unit2.io.select := true.B
  }

  // When address generator finishes, mark computation as done
  when(addr_gen.io.finish) {
    regs(1) := 1.U
    start_bf := false.B
  }

  // Twiddle factor ROMs controlled by start_bf
  tf_rom.io.read_enable := start_bf
  tf_rom.io.read_address := addr_gen.io.addr3
  itf_rom.io.read_enable := start_bf
  itf_rom.io.read_address := addr_gen.io.addr3

  // Assign twiddle factor data to butterfly units
  when(addr_gen.io.select) {
    bf_unit1.io.w := tf_rom.io.read_data
    bf_unit2.io.w := tf_rom.io.read_data
  }.otherwise {
    bf_unit1.io.w := itf_rom.io.read_data
    bf_unit2.io.w := itf_rom.io.read_data
  }

  // Control butterfly unit enable and start signals
  bf_unit1.io.enable := true.B
  bf_unit1.io.start := read_done1b || read_done2
  bf_unit1.io.inverse := addr_gen.io.inverse
  bf_unit2.io.enable := true.B
  bf_unit2.io.start := read_done1b || read_done2
  bf_unit2.io.inverse := addr_gen.io.inverse

  // Data RAM1 Write
  when((regs(2) === 1.U) && (regs(6) === 1.U)) {
    data_ram1.io.writeValid := true.B
    data_ram1.io.writeAddress := regs(4)
    data_ram1.io.writeData := regs(3)
    regs(2) := 0.U
  }.elsewhen(bf_unit1.io.finish && (regs(1) === 0.U) && !addr_gen.io.inverse) {
    data_ram1.io.writeValid := true.B
    data_ram1.io.writeAddress := addr_gen.io.addr1w
    data_ram1.io.writeData := bf_unit1.io.x
  }.otherwise {
    data_ram1.io.writeValid := false.B
  }

  // Data RAM2 Write
  when((regs(2) === 1.U) && (regs(6) === 2.U)) {
    data_ram2.io.writeValid := true.B
    data_ram2.io.writeAddress := regs(4)
    data_ram2.io.writeData := regs(3)
    regs(2) := 0.U
  }.elsewhen(bf_unit2.io.finish && (regs(1) === 0.U) && !addr_gen.io.inverse) {
    data_ram2.io.writeValid := true.B
    data_ram2.io.writeAddress := addr_gen.io.addr1w
    data_ram2.io.writeData := bf_unit2.io.x
  }.otherwise {
    data_ram2.io.writeValid := false.B
  }

  // Data RAM1 Write (port 2)
  when(bf_unit1.io.finish && (regs(1) === 0.U)) {
    data_ram1.io.writeValid2 := true.B
    data_ram1.io.writeAddress2 := addr_gen.io.addr2w
    data_ram1.io.writeData2 := bf_unit1.io.y
  }.otherwise {
    data_ram1.io.writeValid2 := false.B
  }

  // Data RAM2 Write (port 2)
  when(bf_unit2.io.finish && (regs(1) === 0.U)) {
    data_ram2.io.writeValid2 := true.B
    data_ram2.io.writeAddress2 := addr_gen.io.addr2w
    data_ram2.io.writeData2 := bf_unit2.io.y
  }.otherwise {
    data_ram2.io.writeValid2 := false.B
  }

  // Data RAM1 Read
  when((regs(2) === 2.U) && (regs(1) === 1.U) && (regs(6) === 1.U)) {
    data_ram1.io.readValid := true.B
    data_ram1.io.readAddress := regs(4)
  }.elsewhen(start_bf) {
    data_ram1.io.readValid := true.B
    data_ram1.io.readAddress := addr_gen.io.addr1
  }.otherwise {
    data_ram1.io.readValid := false.B
  }

  // Data RAM2 Read
  when((regs(2) === 2.U) && (regs(1) === 1.U) && (regs(6) === 2.U)) {
    data_ram2.io.readValid := true.B
    data_ram2.io.readAddress := regs(4)
  }.elsewhen(start_bf) {
    data_ram2.io.readValid := true.B
    data_ram2.io.readAddress := addr_gen.io.addr1
  }.otherwise {
    data_ram2.io.readValid := false.B
  }

  // Update read_done flags based on read operations
  when((regs(2) === 2.U) && (regs(1) === 1.U)) {
    read_done1a := true.B
    read_done1b := false.B
    regs(2) := 0.U
  }.elsewhen(start_bf) {
    read_done1a := false.B
    read_done1b := true.B
  }.otherwise {
    read_done1a := false.B
    read_done1b := false.B
  }

  // Data RAM1 and RAM2 Read (port 2 for butterfly unit input)
  when(start_bf) {
    data_ram1.io.readValid2 := true.B
    data_ram1.io.readAddress2 := addr_gen.io.addr2
    data_ram2.io.readValid2 := true.B
    data_ram2.io.readAddress2 := addr_gen.io.addr2
    read_done2 := true.B
  }.otherwise {
    data_ram1.io.readValid2 := false.B
    data_ram2.io.readValid2 := false.B
    read_done2 := false.B
  }

  // Assign read data to regs(3) when done reading
  when(read_done1a && (regs(6) === 1.U)) {
    regs(3) := data_ram1.io.readData
  }

  when(read_done1a && (regs(6) === 2.U)) {
    regs(3) := data_ram2.io.readData
  }

  // Assign butterfly unit inputs based on read_done1b
  when(read_done1b) {
    when(addr_gen.io.inverse) {
      bf_unit1.io.u := 0.U
      bf_unit2.io.u := 0.U
    }.otherwise {
      bf_unit1.io.u := data_ram1.io.readData
      bf_unit2.io.u := data_ram2.io.readData
    }
  }.otherwise {
    bf_unit1.io.u := 0.U
    bf_unit2.io.u := 0.U
  }

  // Assign butterfly unit v inputs based on read_done2
  when(read_done2) {
    bf_unit1.io.v := data_ram1.io.readData2
    bf_unit2.io.v := data_ram2.io.readData2
  }.otherwise {
    bf_unit1.io.v := 0.U
    bf_unit2.io.v := 0.U
  }

}

// Define a simple top-level object for generating Verilog
object NTTMain extends App {
  (new chisel3.stage.ChiselStage).emitVerilog(new NTT())
}


// package projectname

// import spinal.core._
// import spinal.lib._
// import spinal.lib.bus.wishbone._

// // Hardware definition
// case class NTT() extends Component
// {	
//     val wb = slave(Wishbone(WishboneConfig(5, 32)))
//     val slv = WishboneSlaveFactory(wb)  
//     val regs = Vec(Reg(UInt(32 bits)) init 0, 7)
//     (0 until 7).map(i=>slv.readAndWrite(regs(i), address= i*4))
//   	//regs0 indicates start computation
//   	//regs1 indicates finish computation
//   	//regs2 indicates write mode or read mode
//   	//regs3 stores data to be sent into BRAM or data that is read from BRAM
//   	//regs4 stores write or read address for BRAM access
//   	//regs5 indicates NTT or INTT
//   	//regs6 indicates to access BRAM1 or BRAM2
  		
//   	val bf_unit1  = new Unified_Butterfly_Unit()
//   	val bf_unit2  = new Unified_Butterfly_Unit()
//   	val tf_rom    = new Twiddle_ROM()
//   	val itf_rom   = new Inverse_Twiddle_ROM()
//   	val data_ram1 = new Data_RAM()
//   	val data_ram2 = new Data_RAM()
//   	val addr_gen  = new Address_Generator()
		
// 	val start_bf    = Reg(Bool()) init(False)
// 	val read_done1a = Reg(Bool()) init(False)
// 	val read_done1b = Reg(Bool()) init(False)
// 	val read_done2  = Reg(Bool()) init(False)
	 
// 	//when regs(0) becomes 1, start address_generator and set itself and regs(1) to 0
//   	when (regs(0) === U(1))
//   	{
//   		addr_gen.io.enable := True
//   		regs(0)  := U(0)
//   		regs(1)  := U(0)
//   		start_bf := True
//   	}
//   	.otherwise
//   	{
//   		addr_gen.io.enable := False
//   	}
  	
//   	when (regs(5) === U(0))
//   	{
// 	  	addr_gen.io.select := False
// 	  	bf_unit1.io.select := False
// 	  	bf_unit2.io.select := False
//   	}
//   	.otherwise
//   	{
// 	  	addr_gen.io.select := True
// 	  	bf_unit1.io.select := True
// 	  	bf_unit2.io.select := True
//   	}
  	
//   	//when address_generator is finished, set regs(1) to 1 indicating it is done
//   	when (addr_gen.io.finish)
//   	{
//   		regs(1)  := U(1)
//   		start_bf := False
//   	}
  	
//   	tf_rom.io.read_enable  := start_bf
//   	tf_rom.io.read_address := addr_gen.io.addr3
//   	itf_rom.io.read_enable  := start_bf
//   	itf_rom.io.read_address := addr_gen.io.addr3
  	
//   	when (addr_gen.io.select)
//   	{ 
//   		bf_unit1.io.w := tf_rom.io.read_data
//   		bf_unit2.io.w := tf_rom.io.read_data
//   	}.
//   	otherwise
//   	{
//   		bf_unit1.io.w := itf_rom.io.read_data
//   		bf_unit2.io.w := itf_rom.io.read_data
//   	}
  	
//   	bf_unit1.io.enable := True
//   	bf_unit1.io.start  := read_done1b | read_done2
//   	bf_unit1.io.inverse := addr_gen.io.inverse
//   	bf_unit2.io.enable := True
//   	bf_unit2.io.start  := read_done1b | read_done2
//   	bf_unit2.io.inverse := addr_gen.io.inverse
  	
//   	//1st write port is used either when filling data into memory or to update Butterfly result
//   	when ((regs(2) === U(1)) & (regs(6) === U(1)))
//   	{
//   		data_ram1.io.writeValid1   := True
//   		data_ram1.io.writeAddress1 := regs(4).resized
//   		data_ram1.io.writeData1    := regs(3).resized
//   		regs(2) := U(0)
//   	}
//   	.elsewhen (bf_unit1.io.finish & (regs(1) === U(0)) & (addr_gen.io.inverse === False))
//   	{
//   		data_ram1.io.writeValid1   := True
//   		data_ram1.io.writeAddress1 := addr_gen.io.addr1w
//   		data_ram1.io.writeData1    := bf_unit1.io.x
//   	}
//   	.otherwise
//   	{
//   		data_ram1.io.writeValid1   := False
//   		data_ram1.io.writeAddress1 := U(0)
//   		data_ram1.io.writeData1    := U(0)
//   	}
  	
//   	when ((regs(2) === U(1)) & (regs(6) === U(2)))
//   	{
//   		data_ram2.io.writeValid1   := True
//   		data_ram2.io.writeAddress1 := regs(4).resized
//   		data_ram2.io.writeData1    := regs(3).resized
//   		regs(2) := U(0)
//   	}
//   	.elsewhen (bf_unit2.io.finish & (regs(1) === U(0)) & (addr_gen.io.inverse === False))
//   	{
//   		data_ram2.io.writeValid1   := True
//   		data_ram2.io.writeAddress1 := addr_gen.io.addr1w
//   		data_ram2.io.writeData1    := bf_unit2.io.x
//   	}
//   	.otherwise
//   	{
//   		data_ram2.io.writeValid1   := False
//   		data_ram2.io.writeAddress1 := U(0)
//   		data_ram2.io.writeData1    := U(0)
//   	}
  	
//   	//2nd write port is used only to update Butterfly result
//   	when (bf_unit1.io.finish & (regs(1) === U(0)))
//   	{
//   		data_ram1.io.writeValid2   := True
//   		data_ram1.io.writeAddress2 := addr_gen.io.addr2w
//   		data_ram1.io.writeData2    := bf_unit1.io.y
//   	}
//   	.otherwise
//   	{
//   		data_ram1.io.writeValid2   := False
//   		data_ram1.io.writeAddress2 := U(0)
//   		data_ram1.io.writeData2    := U(0)
//   	}

//   	when (bf_unit2.io.finish & (regs(1) === U(0)))
//   	{
//   		data_ram2.io.writeValid2   := True
//   		data_ram2.io.writeAddress2 := addr_gen.io.addr2w
//   		data_ram2.io.writeData2    := bf_unit2.io.y
//   	}
//   	.otherwise
//   	{
//   		data_ram2.io.writeValid2   := False
//   		data_ram2.io.writeAddress2 := U(0)
//   		data_ram2.io.writeData2    := U(0)
//   	}
  	  	
//   	//1st read port is used when sending data from memory or to send input to Butterfly
//   	when ((regs(2) === U(2)) & (regs(1) === U(1)) & (regs(6) === U(1)))
//   	{
//   		data_ram1.io.readValid1   := True
//   		data_ram1.io.readAddress1 := regs(4).resized
//   	}
//   	.elsewhen (start_bf)
//   	{
//   		data_ram1.io.readValid1   := True
//   		data_ram1.io.readAddress1 := addr_gen.io.addr1
//   	}
//   	.otherwise
//   	{
//   		data_ram1.io.readValid1   := False
//   		data_ram1.io.readAddress1 := U(0)
//   	}
  	
//   	when ((regs(2) === U(2)) & (regs(1) === U(1)) & (regs(6) === U(2)))
//   	{
//   		data_ram2.io.readValid1   := True
//   		data_ram2.io.readAddress1 := regs(4).resized
//   	}
//   	.elsewhen (start_bf)
//   	{
//   		data_ram2.io.readValid1   := True
//   		data_ram2.io.readAddress1 := addr_gen.io.addr1
//   	}
//   	.otherwise
//   	{
//   		data_ram2.io.readValid1   := False
//   		data_ram2.io.readAddress1 := U(0)
//   	}
  	
//   	when ((regs(2) === U(2)) & (regs(1) === U(1)))
//   	{
//   		read_done1a := True
//   		read_done1b := False
//   		regs(2) := U(0)
//   	}
//   	.elsewhen (start_bf)
//   	{
//   		read_done1a := False
//   		read_done1b := True
//   	}
//   	.otherwise
//   	{
//   		read_done1a := False
//   		read_done1b := False
//   	}
  	
//   	//2nd read port is used only to send input to Butterfly
//   	when (start_bf)
//   	{
//   		data_ram1.io.readValid2   := True
//   		data_ram1.io.readAddress2 := addr_gen.io.addr2
//   		data_ram2.io.readValid2   := True
//   		data_ram2.io.readAddress2 := addr_gen.io.addr2
//   		read_done2 := True
//   	}
//   	.otherwise
//   	{
//   		data_ram1.io.readValid2   := False
//   		data_ram1.io.readAddress2 := U(0)
//   		data_ram2.io.readValid2   := False
//   		data_ram2.io.readAddress2 := U(0)
//   		read_done2 := False
//   	}
  	
//   	when (read_done1a & (regs(6) === U(1)))
//   	{
//   		regs(3) := data_ram1.io.readData1.resized
//   	}
  	
//   	when (read_done1a & (regs(6) === U(2)))
//   	{
//   		regs(3) := data_ram2.io.readData1.resized
//   	}

// 	when (read_done1b)
//   	{
//   		when (addr_gen.io.inverse)
//   		{
//   			bf_unit1.io.u := U(0)
//   			bf_unit2.io.u := U(0)
//   		}
//   		.otherwise
//   		{
//   			bf_unit1.io.u := data_ram1.io.readData1
//   			bf_unit2.io.u := data_ram2.io.readData1
//   		}
//   	}
//   	.otherwise
//   	{
//   		bf_unit1.io.u := U(0)
//   		bf_unit2.io.u := U(0)
//   	}


// 	when (read_done2)
//   	{
//   		bf_unit1.io.v := data_ram1.io.readData2
//   		bf_unit2.io.v := data_ram2.io.readData2
//   	}
//   	.otherwise
//   	{
//   		bf_unit1.io.v := U(0)
//   		bf_unit2.io.v := U(0)
//   	}
// }

// object MyTopLevelVerilog extends App 
// {
// 	Config.spinal.generateVerilog(NTT())
// }
