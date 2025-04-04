import csv
import re

# Configurable parameters
enable = True
select = False
num_cycles = 100

# Initial values
addr1_del = [0] * 8
addr2_del = [0] * 8
fin_count = 0
counter = 0
state = 0
inverse = False
str_reg = False
delay = False
del_regs = [False] * 7

# Initialize variables
i = s = j = k = u7mi = u6mi = u7s1 = u6s1 = usi = u7ss = 0
addr1_var = addr2_var = addr3_var = 0
fin_sig = False

# Simulation output list
output = {
    "cycle": [],
    "addr1": [],
    "addr2": [],
    "addr3": [],
    "addr1w": [],
    "addr2w": [],
    "finish": [],
}

# Simulation loop
for cycle in range(num_cycles):
    if enable:
        str_reg = True
    if fin_sig:
        str_reg = False

    if str_reg:
        delay = True
        counter = (counter + 1) % 128
        if counter == 63 and state != 7:
            counter = 0
            if state == 6:
                if select:
                    fin_sig = True
                    state = 0
                else:
                    fin_sig = False
                    state += 1
            else:
                state += 1
                fin_sig = False
        elif counter == 127 and state == 7:
            fin_sig = True
            state = 0
        else:
            fin_sig = False
    else:
        delay = fin_sig = False

    i = state if select else 6 - state
    if state == 7:
        s = j = k = u7mi = u6mi = u7s1 = u6s1 = usi = u7ss = addr1_var = addr3_var = 0
        addr2_var = counter
        inverse = True
    else:
        s = counter
        j = s >> (6 - i)
        k = s & ((64 >> i) - 1)
        u7mi = 7 - i
        u6mi = 6 - i
        u7s1 = 1 << u7mi
        u6s1 = 1 << u6mi
        usi = 1 << i
        u7ss = s >> u6mi
        addr1_var = j * u7s1 + k
        addr2_var = j * u7s1 + k + u6s1
        addr3_var = usi + u7ss
        inverse = False

    del_regs = [delay] + del_regs[:-1]
    if any(del_regs):
        addr1_del = [addr1_var] + addr1_del[:-1]
        addr2_del = [addr2_var] + addr2_del[:-1]

    if fin_sig or fin_count > 0:
        fin_count += 1

    output["cycle"].append(cycle)
    output["addr1"].append(addr1_var)
    output["addr2"].append(addr2_var)
    output["addr3"].append(addr3_var)
    output["addr1w"].append(addr1_del[-1])
    output["addr2w"].append(addr2_del[-1])
    output["finish"].append(fin_count == 8)

# Simulation output list
output = {
    "cycle": [],
    "addr1": [],
    "addr2": [],
    "addr3": [],
    "addr1w": [],
    "addr2w": [],
    "finish": [],
}

# Placeholder for actual simulation values (calculated by Python)
# Example values - replace this with the actual simulation output from your Python code
sim_output = {
    0: {"addr1": 2, "addr2": 3, "addr3": 65, "addr1w": 0, "addr2w": 0, "finish": False},
    1: {"addr1": 4, "addr2": 5, "addr3": 66, "addr1w": 0, "addr2w": 0, "finish": False},
    2: {"addr1": 6, "addr2": 7, "addr3": 67, "addr1w": 0, "addr2w": 0, "finish": False},
    # Add more cycles as needed
}

# Read and parse the .txt file
parsed_output = {
    "cycle": [],
    "addr1": [],
    "addr2": [],
    "addr3": [],
    "addr1w": [],
    "addr2w": [],
    "finish": [],
}

with open("/Users/atharvakulkarni/Desktop/IITB/BTP/ntt_accel/verification/addrgensimout.txt", "r") as file:
    content = file.readlines()
    cycle = -1  # Initialize cycle counter
    for line in content:
        if "Cycle" in line:
            # Extract the cycle number
            cycle = int(re.search(r'\d+', line).group())
        elif "addr1:" in line:
            parsed_output["addr1"].append(int(re.search(r'\d+', line).group()))
        elif "addr2:" in line:
            parsed_output["addr2"].append(int(re.search(r'\d+', line).group()))
        elif "addr3:" in line:
            parsed_output["addr3"].append(int(re.search(r'\d+', line).group()))
        elif "addr1w:" in line:
            parsed_output["addr1w"].append(int(re.search(r'\d+', line).group()))
        elif "addr2w:" in line:
            parsed_output["addr2w"].append(int(re.search(r'\d+', line).group()))
        elif "finish:" in line:
            parsed_output["finish"].append(line.strip().split(": ")[1].lower() == "true")

# Compare outputs
mismatches = []
for cycle in range(len(parsed_output["cycle"])):
    cycle_num = parsed_output["cycle"][cycle]
    for key in ["addr1", "addr2", "addr3", "addr1w", "addr2w", "finish"]:
        # Compare the parsed values with the expected Python simulation output
        expected_value = sim_output.get(cycle_num, {}).get(key, None)
        if expected_value is None:
            print(f"Warning: Missing expected value for Cycle {cycle_num}, {key}")
            continue

        actual_value = parsed_output[key][cycle]
        if expected_value != actual_value:
            mismatches.append((cycle_num, key, expected_value, actual_value))

# Print mismatches
if mismatches:
    print("Mismatches found at:")
    for mismatch in mismatches:
        cycle_num, key, expected, actual = mismatch
        print(f"Cycle {cycle_num}: {key} - Expected: {expected}, Actual: {actual}")
else:
    print("All values match.")
