#include <stdio.h>
#include <stdint.h>
#include <string.h>
#include <math.h>
#include "crystals_kyber.h"

#define Q 3329          // all ops performed modulo Q
#define N 128           // degree of polynomials
#define N2 256          // 2*N
#define INV_N 3303      // (N * INV_N) % Q = 1
#define PSIN 17         // "primitive root" modulo Q (generates elements of the field modulo Q in a cyclic fashion)
#define INV_PSIN 1175   // modular inverse of INV_PSIN

int main() {
    uint32_t psis[N], inv_psis[N], pwmf[N]; // forward and inverse twiddle factors , scaling factors for point wise multiplication (pwmf)
    gen_tf(PSIN, INV_PSIN, psis, inv_psis);
    gen_pwmf(PSIN, pwmf);

    // printf("PSIS : \n");
    // print_debug_1d(psis, N, "psis");
    // printf("INV_PSIS : \n");
    // print_debug_1d(inv_psis, N, "inv_psis");
    // printf("PWMF : \n");
    // print_debug_1d(pwmf, N, "pwmf");

    // printf("hey\n");
    
    uint32_t scap[2][N2]; // Private key
    uint32_t bcap[2][N2]; // Public key
    key_gen(scap, bcap, psis, pwmf);

    // print_debug_2d((uint32_t*)scap, 2, N2, "scap");
    // print_debug_2d((uint32_t*)bcap, 2, N2, "bcap");
    
    uint32_t message[N2];
    for(uint32_t i=0; i<N; i++){
        message[i] = 0;
        message[i+N] = 1664;
    }
    // print_debug_1d(message,N2, "message");
    uint32_t u[2][N2], v[N2];
    
    encrypt(message, bcap, psis, inv_psis, pwmf, u, v);
    // // print_debug_2d(u, 2, N2, "u");
    // // print_debug_1d(v,N2,"v");
    // // uint32_t testu[N2] = {2685, 978, 716, 1137, 1385, 1482, 1470, 334, 3042, 532, 2943, 2250, 2739, 32, 1126, 977, 2309, 2242, 630, 1355, 1308, 1853, 2261, 897, 1591, 382, 1423, 390, 1448, 2619, 2839, 68, 1201, 110, 1486, 2261, 1578, 1830, 3291, 1628, 1735, 3069, 1284, 225, 1959, 1498, 3171, 2465, 491, 692, 2362, 2876, 674, 1933, 2597, 544, 3255, 1962, 2598, 1130, 3027, 2996, 451, 2464, 913, 975, 2715, 3070, 1706, 2290, 884, 169, 812, 1310, 2603, 24, 1839, 1291, 1136, 969, 3241, 74, 2653, 1633, 1808, 1700, 1165, 1981, 3057, 3089, 1233, 2761, 2256, 2956, 2990, 276, 699, 2706, 2689, 845, 1609, 2667, 849, 2199, 2426, 2417, 3250, 1600, 334, 2176, 690, 1962, 2088, 473, 3044, 1163, 980, 303, 1218, 663, 642, 3151, 523, 3217, 2190, 2610, 1661, 2997, 2736, 2017, 2627, 172, 1984, 81, 3280, 2445, 298, 3000, 1522, 321, 2525, 1781, 1233, 1708, 1750, 3152, 3276, 2864, 65, 905, 3205, 1564, 351, 1178, 1994, 1873, 1312, 1382, 818, 2449, 2970, 756, 2830, 2163, 887, 799, 844, 2469, 1094, 3166, 1681, 1055, 2931, 1338, 918, 2001, 2291, 1781, 3174, 1202, 449, 919, 588, 1655, 861, 624, 3217, 3153, 1614, 879, 2628, 731, 2527, 1623, 682, 2242, 2210, 2869, 1256, 746, 3104, 2525, 2002, 1078, 1769, 349, 2102, 548, 2342, 2412, 2374, 2204, 1254, 1980, 1442, 3063, 2984, 552, 1121, 983, 2571, 1539, 2160, 1980, 458, 871, 3053, 849, 570, 2637, 773, 1394, 975, 3094, 2738, 2758, 94, 2258, 548, 3083, 44, 1110, 3015, 2594, 205, 387, 2209, 3220, 2933, 395, 1498, 2818, 1088, 503, 245, 2697};
    // // compare_arrays_1d(v,testu,N2);
    
    // uint32_t decrypted_message[N2];
    // decrypt(scap, u, v, psis, inv_psis, pwmf, decrypted_message);
    
    // uint32_t final_message[N2];
    // decode_message(decrypted_message, final_message);
    // print_debug_1d(final_message,N2,"final_msg");
    
    return 0;
}
