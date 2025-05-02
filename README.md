# B.Tech. Project (2024-25)

## Topic: Chisel-based Number Theoretic Transform Accelerator for RocketChip

This project presents the design and integration of a Chisel-based hardware accelerator for the Number Theoretic Transform (NTT), a computationally intensive operation used in post-quantum cryptographic schemes like CRYSTALS-Kyber. The accelerator is developed using Chisel, an object-oriented hardware description language, and integrated into the RocketChip SoC through the Chipyard framework. The implementation is functionally verified using ChiselTest and benchmarked using software instances of the NTT from Kyber. While standalone simulation succeeded, integration with RocketChip via the RoCC interface and as an MMIO peripheral faced simulation failures in Verilator. The memory interface assertions do not reveal much insight into the bug, thus making the debugging difficult. Despite these issues, foundational work was completed on hardware-software co-design.

## Running a Specific Test

To run a specific test from the `src/test` directory using sbt, use the `testonly` command followed by the fully qualified test class name. For example, to run the `NTTTest` defined in `src/test/scala/NTT_test.scala`, use:

```
sbt testonly NTTTest
```

Replace `NTTTest` with the name of the test class you want to run (e.g., `DataRAMTest`, `AddressGenTest`, etc.).

## Note

To see the files for integration of NTT accelerator with RocketChip, checkout to `rocc` branch
