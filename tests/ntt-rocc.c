// The following is a RISC-V program to test the functionality of the
// NTT RoCC accelerator.
// Compile with riscv-gcc ntt-rocc.c
// Run with spike --extension=ntt pk a.out

#include <stdio.h>
#include <stdint.h>
#include "rocc.h"
#include "encoding.h"
#include "compiler.h"

#ifdef __linux
#include <sys/mman.h>
#endif

#define NTT_SIZE 256
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

  do {
    printf("Start NTT test.\n");
    
    // Setup test data (128 16-bit elements)
    static uint16_t input[NTT_SIZE] __aligned(8) = {
      0, 1, 0, 0, 0, 0, 1, 3327, 0, 3328, 3328, 1, 2, 1, 3328, 1, 1, 0, 3328, 0, 1, 0, 3326, 3328, 1, 3327, 2, 0, 1, 1, 0, 0, 3328, 3328, 
        1, 3328, 1, 0, 0, 3328, 2, 2, 3327, 1, 2, 0, 0, 1, 0, 0, 0, 0, 1, 3328, 1, 1, 0, 0, 0, 0, 1, 1, 3328, 1, 0, 0, 1, 3328, 0, 0, 
        2, 0, 0, 0, 3328, 3327, 3327, 0, 3328, 3328, 0, 3327, 1, 3328, 1, 3328, 0, 2, 0, 3327, 1, 0, 1, 1, 0, 0, 3327, 3328, 0, 1, 0, 0, 
        3328, 0, 0, 3328, 3328, 0, 3328, 3327, 1, 1, 3328, 0, 1, 1, 3328, 0, 3328, 3326, 0, 0, 3328, 0, 2, 3328, 0, 1, 0, 0, 2, 3328, 0, 
        3328, 3328, 0, 0, 0, 1, 2, 3328, 3327, 1, 0, 2, 2, 2, 3327, 2, 0, 0, 1, 0, 3328, 3328, 0, 1, 0, 0, 0, 3328, 3328, 1, 3, 3328, 1, 
        3328, 2, 0, 0, 0, 2, 0, 1, 1, 3328, 1, 0, 3328, 3328, 0, 3328, 3328, 1, 3328, 3327, 1, 0, 1, 3328, 1, 3328, 1, 3328, 1, 0, 3327, 
        3328, 1, 3, 3327, 0, 1, 3327, 3, 0, 1, 1, 1, 1, 3327, 3328, 3328, 3328, 1, 3326, 0, 1, 1, 1, 0, 0, 1, 0, 0, 2, 0, 3328, 3328, 
        3328, 1, 3, 3328, 0, 3328, 2, 1, 3327, 0, 1, 1, 0, 3327, 3328, 1, 1, 0, 3328, 3328, 3328, 0, 3328, 0, 0, 3328, 0
    };
    
    uint16_t output[NTT_SIZE] __aligned(8);

    start = rdcycle();

    // Compute NTT with accelerator
    asm volatile ("fence");
    
    // Setup accelerator with addresses of input and output
    //              opcode rd rs1          rs2          funct
    ROCC_INSTRUCTION_SS(0, &input, &output, 0);  // Using custom0 opcode

    // Trigger NTT operation (funct = 1 for NTT)
    ROCC_INSTRUCTION_S(0, 0, 1);
    
    asm volatile ("fence" ::: "memory");

    end = rdcycle();

    // Check result
    int i;
    static const uint16_t expected[NTT_SIZE] = {
      1291, 53, 170, 2072, 1204, 1473, 972, 344, 2474, 2826, 1913, 1328, 207, 3308, 2032, 1126, 652, 1853, 1640, 493, 377, 2634, 1552,
        1406, 2509, 483, 3270, 912, 906, 3062, 93, 691, 1306, 2172, 2824, 515, 2604, 1391, 1289, 3296, 3166, 2913, 1957, 2062, 2495, 822,
        1027, 168, 2510, 2211, 2062, 445, 1536, 589, 1045, 1286, 165, 2948, 638, 2409, 2628, 2726, 2681, 1987, 1401, 63, 47, 2475, 555,
        1508, 1595, 1353, 1843, 722, 2410, 1852, 776, 87, 2773, 3026, 2318, 3160, 2749, 1442, 2034, 1947, 1131, 3129, 853, 874, 129, 1840, 
        491, 2692, 87, 1908, 1995, 1526, 2986, 1887, 3184, 1327, 1455, 274, 2819, 636, 2161, 2927, 1269, 2622, 35, 697, 1679, 1603, 2809, 
        708, 3268, 2789, 3024, 2278, 407, 3132, 1772, 668, 21, 551, 2927, 1822, 291, 478, 1234, 3269, 2244, 2729, 3238, 3040, 950, 2455, 
        1668, 2012, 1452, 2069, 501, 683, 867, 981, 1594, 3293, 1611, 767, 323, 704, 1456, 432, 2369, 2029, 1388, 617, 1580, 2923, 1956, 
        2341, 683, 1397, 3321, 1413, 537, 12, 383, 2109, 1167, 1656, 2022, 2941, 2641, 1365, 1595, 1886, 2695, 811, 1949, 1380, 3324, 1326, 
        887, 3300, 1084, 2661, 2532, 560, 237, 3174, 93, 2943, 1742, 2460, 555, 1344, 392, 344, 2409, 766, 2900, 2800, 670, 1307, 2675, 958, 
        220, 2931, 3045, 2278, 2130, 2671, 1310, 2949, 3297, 2573, 2784, 1640, 2433, 2284, 719, 2997, 1483, 348, 88, 1345, 1389, 2554, 3284, 
        75, 1426, 482, 3220, 1944, 2723, 3004, 823, 1046, 1221, 2920, 2867, 1547, 2229, 2394, 3303, 1417, 1906, 936, 2820, 2351, 2747, 3203, 3278, 2245
    };

    for(i = 0; i < NTT_SIZE; i++) {
      printf("output[%d]:%d ==? expected[%d]:%d\n", i, output[i], i, expected[i]);
      if(output[i] != expected[i]) {
        printf("Failed: Outputs don't match!\n");
        printf("NTT execution took %lu cycles\n", end - start);
        return 1;
      }
    }
  } while(0);

  printf("Success!\n");
  printf("NTT execution took %lu cycles\n", end - start);

  return 0;
} 