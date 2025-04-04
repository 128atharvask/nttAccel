set -e

TARGET="ntt-rocc"
# CONFIG="RocketConfig"
CONFIG="NTTRocketConfig"

cd ../../tests
# make clean
make $TARGET.riscv
cd ../sims/verilator
make run-binary-debug BINARY=../../tests/$TARGET.riscv CONFIG=$CONFIG
# make CONFIG=$CONFIG