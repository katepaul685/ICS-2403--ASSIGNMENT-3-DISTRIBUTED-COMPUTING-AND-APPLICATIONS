# BLOCK 1: THE BLUEPRINT (Node Class)
class Node:
    def __init__(self, name, latency, cpu, mem):
        self.name = name
        self.lat = latency
        self.cpu = cpu          # Current CPU usage %
        self.mem_total = mem    # Total RAM in GB
        self.mem_used = 0.0     # Track used RAM

    def can_accept(self, cpu_need, mem_need):
        # Rule: Must have enough RAM *AND* CPU must not go over 100%
        has_ram = (self.mem_total - self.mem_used) >= mem_need
        has_cpu = (self.cpu + cpu_need) <= 100
        return has_ram and has_cpu

    def add_task(self, task_name, cpu, mem):
        self.cpu += cpu
        self.mem_used += mem
        print("Assigned " + task_name + " to " + self.name)

# BLOCK 2: THE SETUP (Data from Table)
nodes = [
    Node("EdgeA", 10, 40, 4),
    Node("EdgeB", 14, 48, 5),
    Node("CoreX", 7,  65, 8),
    Node("CoreY", 9,  58, 7),
    Node("CloudZ", 22, 72, 16)
]

# BLOCK 3: THE ANSWERS

# Part (a): Identify Max Latency Node
# Logic: "Find the node with the highest .lat value"
worst_node = max(nodes, key=lambda n: n.lat)
print("Max Latency Node: " + worst_node.name + " (" + str(worst_node.lat) + "ms)")

# Part (b) & (c): Load Balancing Simulation
# Logic: "For each process, find the node with the LOWEST CPU that fits."
processes = [
    ("Migration", 12, 2),
    ("Analytics", 25, 8),
    ("Recovery", 18, 3),
    ("Transaction", 10, 1.5)
]

print("\n--- Starting Allocation ---")
for task, cpu_need, mem_need in processes:
    best_node = None
    
    for n in nodes:
        # Step 1: Check constraints (RAM & CPU limit)
        if n.can_accept(cpu_need, mem_need):
            # Step 2: Pick the one with lowest CPU load (Load Balancing)
            if best_node is None or n.cpu < best_node.cpu:
                best_node = n
    
    # Step 3: Final Assign
    if best_node:
        best_node.add_task(task, cpu_need, mem_need)
    else:
        print("REJECTED " + task + " (No sufficient resources)")