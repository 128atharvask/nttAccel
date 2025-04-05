// The following is a RISC-V program to test the functionality of the
// Dummy RoCC accelerator that copies memory.
// Compile with riscv-gcc dummy-rocc.c
// Run with spike --extension=dummy pk a.out

#include <stdio.h>
#include <stdint.h>
#include "rocc.h"
#include "encoding.h"
#include "compiler.h"

#ifdef __linux
#include <sys/mman.h>
#endif

#define ARRAY_SIZE 1  // Just one element
#define BYTES_PER_ELEMENT 2

int main() {
  unsigned long start, end;

#ifdef __linux
  // Ensure all pages are resident to avoid accelerator page faults
  if (mlockall(MCL_CURRENT | MCL_FUTURE)) {
    perror("mlockall");
    return 1;
  }
#endif

  printf("Start Single-Element Dummy RoCC test.\n");
    
  // Setup test data (single 16-bit element)
  static uint16_t input[ARRAY_SIZE] __aligned(8) = { 0x1234 };  // Easy to recognize test value
  uint16_t output[ARRAY_SIZE] __aligned(8);

  printf("Input address: %p, value: 0x%04x\n", (void*)input, input[0]);
  printf("Output address: %p\n", (void*)output);

  start = rdcycle();

  // Memory fence before accelerator
  asm volatile ("fence");
    
  // Call accelerator to copy one element
  ROCC_INSTRUCTION_SS(1, &input, &output, 0);
    
  // Memory fence after accelerator
  asm volatile ("fence" ::: "memory");

  end = rdcycle();

  // Check result
  printf("Input: 0x%04x, Output: 0x%04x\n", input[0], output[0]);
  if(output[0] != input[0]) {
    printf("Failed: Output doesn't match!\n");
    printf("Expected: 0x%04x, Got: 0x%04x\n", input[0], output[0]);
    printf("Operation took %lu cycles\n", end - start);
    return 1;
  }

  printf("Success! Element copied correctly.\n");
  printf("Operation took %lu cycles\n", end - start);

  return 0;
} 