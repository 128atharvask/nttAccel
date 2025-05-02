# B.Tech. Project (2024-25)

## Topic: Chisel-based Number Theoretic Transform Accelerator for RocketChip

This project presents the design and integration of a Chisel-based hardware accelerator for the Number Theoretic Transform (NTT), a computationally intensive operation used in post-quantum cryptographic schemes like CRYSTALS-Kyber. The accelerator is developed using Chisel, an object-oriented hardware description language, and integrated into the RocketChip SoC through the Chipyard framework. The implementation is functionally verified using ChiselTest and benchmarked using software instances of the NTT from Kyber. While standalone simulation succeeded, integration with RocketChip via the RoCC interface and as an MMIO peripheral faced simulation failures in Verilator. The memory interface assertions do not reveal much insight into the bug, thus making the debugging difficult. Despite these issues, foundational work was completed on hardware-software co-design.

## Running Verilator Simulation

cd nttAccel
./mytest.sh

## Note

The files in this branch should be copy pasted in the appropriate locations inside Chipyard.