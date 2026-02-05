# Assignment 4: Redundancy and Failover Strategy

# 1. THE CLASS (Represents a server in the system)
class Node:
    def __init__(self, name, failure_type):
        self.name = name
        self.failure_type = failure_type
        self.is_online = True

    # Method to simulate a crash
    def crash(self):
        self.is_online = False

    # Method to process a request
    def process(self, data):
        # Check 1: Is the node physically down?
        if not self.is_online:
            raise RuntimeError("Node is Offline")
        
        # Check 2: Is the node malicious? (Byzantine Failure from table)
        # This is critical because Core1 in your table has this error.
        if self.failure_type == "Byzantine":
            raise RuntimeError("Security Error: Byzantine Failure Detected")

        print("Success: " + self.name + " processed " + data)

# 2. THE SETUP (Data from your Assignment Table)
# We define the risky nodes and their safe backups
edge1 = Node("Edge1", "Crash")
edge2 = Node("Edge2", "None")      # Backup for Edge
core1 = Node("Core1", "Byzantine") # Primary (Risky)
core2 = Node("Core2", "None")      # Backup for Core

# We map each layer to a (Primary, Backup) pair
redundancy_plan = {
    "EDGE": (edge1, edge2),
    "CORE": (core1, core2)
}

# 3. THE LOGIC (The Failover Function)
def send_request(layer_name, data):
    # Get the pair of nodes for this layer
    primary, backup = redundancy_plan[layer_name]
    
    print("\nAttempting to send '" + data + "' to " + layer_name + "...")

    try:
        # Step A: Try the Primary Node
        primary.process(data)
    except RuntimeError as error:
        # Step B: If Primary fails, catch the error and switch to Backup
        print("FAILOVER TRIGGERED: " + primary.name + " failed (" + str(error) + ")")
        print("Redirecting to Backup Node: " + backup.name + "...")
        
        try:
            backup.process(data)
        except RuntimeError:
            print("CRITICAL FAILURE: System Down (Both nodes failed)")

# 4. THE SIMULATION
# Scenario 1: Core1 has a Byzantine error (Security risk). 
# The system should detect this and switch to Core2.
send_request("CORE", "Transaction_001")

# Scenario 2: Edge1 crashes physically.
# The system should switch to Edge2.
edge1.crash()
send_request("EDGE", "Login_Request")