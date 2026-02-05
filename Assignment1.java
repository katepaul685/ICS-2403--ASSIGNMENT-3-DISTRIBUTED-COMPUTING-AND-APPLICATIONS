public class Assignment1 {

    // 1. The Blueprint
    static class Node {
        String name;
        double latency, throughput, loss, cpu;
        boolean isAlive = true; // Tracks if node is ON or OFF
        // Constructor to load data from the table
        Node(String n, double l, double t, double lo, double c) {
            name = n; latency = l; throughput = t; loss = lo; cpu = c;
        }
        // 2. The Logic: Send Message + Check Bottlenecks
        void send(Node receiver, String eventType) {
            // Step 1: Check if Receiver is Dead (Fault Tolerance)
            if (!receiver.isAlive) {
                System.out.println("[FAILED] " + receiver.name + " is DOWN. Cannot process " + eventType);
                return;
            }
            //  Check for Bottlenecks 
            if (receiver.cpu > 65) {
                System.out.println("[BOTTLENECK WARNING] " + receiver.name + " has High CPU (" + receiver.cpu + "%)");
            }
            // If Packet Loss >= 0.5%, we flag it as a risk.
            if (receiver.loss >= 0.5) {
                System.out.println("[DATA RISK WARNING] " + receiver.name + " has High Packet Loss (" + receiver.loss + "%)");
            }
            // Step 3: Success Message
            System.out.println("[SUCCESS] " + this.name + " -> " + receiver.name + " [" + eventType + "]");
        }
    }

    public static void main(String[] args) {
        
        System.out.println("--- STARTING ASSIGNMENT 1 SIMULATION ---");

        // 3. Create Nodes (Using exact data from your assignment table)
        Node edge1 = new Node("Edge1", 12, 500, 0.2, 40);
        Node edge2 = new Node("Edge2", 15, 480, 0.5, 45); // Note: High Packet Loss
        Node core1 = new Node("Core1", 8, 1000, 0.1, 60);
        Node core2 = new Node("Core2", 10, 950, 0.2, 55);
        Node cloud1 = new Node("Cloud1", 20, 1200, 0.3, 70); // Note: High CPU

        // --- SCENARIO 1: Architecture & Message Passing (Edge -> Core -> Cloud) ---
        edge1.send(core1, "RPC_Call");
        core1.send(cloud1, "Transaction_Commit");

        // --- SCENARIO 2: Identifying Bottlenecks ---
        System.out.println("\n[Testing Weak Links]");
        // This will trigger the 'DATA RISK' warning because Edge2 has 0.5% loss
        edge2.send(core2, "Upload_Data"); 
        // This will trigger the 'BOTTLENECK' warning because Cloud1 has 70% CPU
        core1.send(cloud1, "Big_Computation");

        // --- SCENARIO 3: Fault Tolerance (Node Failure & Recovery) ---
        System.out.println("\n[Testing Failure & Recovery]");
        
        // Kill Core2 (Simulate Hardware Failure)
        System.out.println("[EVENT] Core2 has Crashed!");
        core2.isAlive = false; 
        
        // Try to send message (Should Fail)
        edge2.send(core2, "RPC_Call"); 

        // Recover Core2
        System.out.println("[EVENT] Core2 has Recovered.");
        core2.isAlive = true;
        
        // Try again (Should Succeed)
        edge2.send(core2, "RPC_Call"); 
    }
}