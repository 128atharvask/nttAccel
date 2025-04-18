#include <stdio.h>
#include "mmio.h"

// Memory-mapped register addresses for DummyAccelerator
#define DUMMY_STATUS 0x4000
#define DUMMY_X 0x4004
#define DUMMY_Y 0x4008

int main(void)
{
  uint16_t result, ref = 0x1234;

  // Wait for accelerator to be ready (not busy)
  while ((reg_read8(DUMMY_STATUS) & 0x2) == 0) ;

  // Write the input value
  reg_write16(DUMMY_X, ref);

  // wait for peripheral to complete
  while ((reg_read8(DUMMY_STATUS) & 0x1) == 0) ;
  
  // Read the result
  result = reg_read16(DUMMY_Y);

  // Verify the result
  if (result != ref) {
    printf("Hardware result 0x%x does not match reference value 0x%x\n", result, ref);
    return 1;
  }

  printf("Hardware successfully copied value 0x%x\n", result);
  return 0;
} 