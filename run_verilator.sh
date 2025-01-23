#!/bin/bash

# Path to your Verilog source files
SRC_DIR=/Users/atharvakulkarni/Desktop/IITB/BTP/ntt_accel         # Directory where NTT.v is located
TB_DIR=/Users/atharvakulkarni/Desktop/IITB/BTP/ntt_accel    # Directory where NTT_tb.v is located

# Create a directory for the compiled Verilog files and simulation executable
mkdir -p build
cd build

# Run Verilator to compile the Verilog files
verilator --cc ${SRC_DIR}/NTT.v --top-module NTT --exe ${TB_DIR}/NTT_tb.v --trace

# Compile the generated C++ code with g++
make -j -C obj_dir -f VNTT.mk VNTT

# Run the simulation
./obj_dir/VNTT
