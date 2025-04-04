// The following is a RISC-V program to test the software implementation of
// NTT (Number Theoretic Transform).
// Compile with riscv-gcc ntt-sw.c
// Run with spike pk a.out

#include <stdio.h>
#include <stdint.h>
#include "encoding.h"
#include "compiler.h"
#include "crystals_kyber.h"

#define NTT_SIZE 128
#define BYTES_PER_ELEMENT 2

// Software implementation of NTT
void ntt_sw(uint16_t* input, uint16_t* output, unsigned long* start, unsigned long* end) {
  // Generate twiddle factors
  uint32_t psis[N];
  uint32_t inv_psis[N];
  uint32_t pwmf[N];
  
  // Generate twiddle factors and scaling factors
  gen_tf(PSIN, INV_PSIN, psis, inv_psis);
  gen_pwmf(PSIN, pwmf);

  // Convert input to uint32_t for ntt_256
  uint32_t input32[N2];
  for(int i = 0; i < N2; i++) {
    input32[i] = (uint32_t)input[i];
  }

  // Perform NTT
  uint32_t output32[N2];
  
  start = rdcycle();

  ntt_256(input32, psis, output32);
  
  end = rdcycle();

  // Convert output back to uint16_t
  for(int i = 0; i < N2; i++) {
    output[i] = (uint16_t)output32[i];
  }
}

int main() {
  unsigned long start, end;
  do {
    printf("Start NTT software test.\n");
    
    // Setup test data (128 16-bit elements)
    static uint16_t input[NTT_SIZE] __aligned(8) = {
      0, 1, 0, 0, 0, 0, 1, 3327, 0, 3328, 3328, 1, 2, 1, 3328, 1,
      1, 0, 3328, 0, 1, 0, 3326, 3328, 1, 3327, 2, 0, 1, 1, 0, 0,
      3328, 3328, 1, 3328, 1, 0, 0, 3328, 2, 2, 3327, 1, 2, 0, 0, 1,
      0, 0, 0, 0, 1, 3328, 1, 1, 0, 0, 0, 0, 1, 1, 3328, 1, 0, 0, 1,
      3328, 0, 0, 2, 0, 0, 0, 3328, 3327, 3327, 0, 3328, 3328, 0, 3327,
      1, 3328, 1, 3328, 0, 2, 0, 3327, 1, 0, 1, 1, 0, 0, 3327, 3328,
      0, 1, 0, 0, 3328, 0, 0, 3328, 3328, 0, 3328, 3327, 1, 1, 3328,
      0, 1, 1, 3328, 0, 3328, 3326, 0, 0, 3328, 0, 2, 3328, 0, 1, 0,
      0, 2, 3328, 0, 3328, 3328, 0, 0, 0, 1, 2, 3328, 3327, 1, 0, 2
    };
    
    uint16_t output[NTT_SIZE] __aligned(8);


    // Compute NTT in software
    ntt_sw(input, output, start, end);

    // Check result
    int i;
    static const uint16_t expected[NTT_SIZE] = {
      1291, 53, 170, 2072, 1204, 1473, 972, 344, 2474, 2826, 1913, 1328, 207, 3308, 2032, 1126,
      652, 1853, 1640, 493, 377, 2634, 1552, 1406, 2509, 483, 3270, 912, 906, 3062, 93, 691,
      1306, 2172, 2824, 515, 2604, 1391, 1289, 3296, 3166, 2913, 1957, 2062, 2495, 822, 1027, 168,
      2510, 2211, 2062, 445, 1536, 589, 1045, 1286, 165, 2948, 638, 2409, 2628, 2726, 2681, 1987,
      1401, 63, 47, 2475, 555, 1508, 1595, 1353, 1843, 722, 2410, 1852, 776, 87, 2773, 3026, 2318,
      3160, 2749, 1442, 2034, 1947, 1131, 3129, 853, 874, 129, 1840, 491, 2692, 87, 1908, 1995,
      1526, 2986, 1887, 3184, 1327, 1455, 274, 2819, 636, 2161, 2927, 1269, 2622, 35, 697, 1679,
      1603, 2809, 708, 3268, 2789, 3024, 2278, 407, 3132, 1772, 668, 21, 551, 2927, 1822, 291, 478,
      1234, 3269, 2244, 2729, 3238, 3040, 950, 2455, 1668, 2012, 1452, 2069, 501, 683, 867, 981
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