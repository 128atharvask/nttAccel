This folder has NTT-INTT unit with generated wishbone interface (via SpinalHDL)

Wishbone register descriptions -
	regs0 indicates start computation
	regs1 indicates finish computation
	regs2 indicates write mode or read mode (write mode => 1, read mode => 2)
	regs3 stores data to be sent into RAM or data that is read from RAM
	regs4 stores write or read address for RAM access
	regs5 indicates NTT or INTT (NTT => 1, INTT => 0)
	regs6 indicates to access RAM1 or RAM2 (RAM1 => 1, RAM2 => 2)


Guidelines to write testbench or software test code 
	First make all regs = 0 (probably done by default)
	
	Make regs6 = 1, to indicate writing into RAM1
	Loop i from 0 to 127:
		Send address i into regs4
		Send data into regs3
		Make regs2 = 1, to indicate write operation
	
	Make regs6 = 2, to indicate writing into RAM2
	Loop i from 0 to 127:
		Send address i into regs4
		Send data into regs3
		Make regs2 = 1, to indicate write operation
	
	Make regs5 = 0 or 1 (for INTT or NTT)
	Make regs0 = 1 to start computation	
	Wait for regs1 to become 1 (use polling) i.e wait for computation to finish
	
	Make regs6 = 1, to indicate read from RAM1
	Loop i from 0 to 127:
		Send address i into regs4
		Make regs2 = 2, to indicate read operation
		Read from regs3 (and store in local array)
	
	Make regs6 = 2, to indicate read from RAM2
	Loop i from 0 to 127:
		Send address i into regs4
		Make regs2 = 2, to indicate read operation
		Read from regs3 (and store in local array)
	
