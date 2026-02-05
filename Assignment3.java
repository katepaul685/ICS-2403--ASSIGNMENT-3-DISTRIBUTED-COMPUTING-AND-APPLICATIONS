public class Assignment3 {

    // ---- Part (a): Bottleneck Identification ----
    static class Node {
        String name; 
        int lat, txps, locks;   // lat=ms, txps=Transactions/sec, locks=%
        
        Node(String n, int L, int T, int K) { 
            name=n; lat=L; txps=T; locks=K; 
        }
        
        // Formula: Higher Score = Worse Bottleneck
        // We divide by txps because Low Throughput is the biggest problem.
        double score() { return (double)(lat * locks) / txps; }
    }

    // ---- Part (c): Deadlock Resolution (Wait-Die Protocol) ----
    static class LockManager {
        int ownerId = -1; // -1 means the lock is free

        void request(int tid) {
            System.out.println("\n[Lock Request] T" + tid + " wants lock...");
            
            if (ownerId == -1) {
                ownerId = tid;
                System.out.println("[GRANTED] T" + tid + " gets lock (was free)");
            } 
            // Case: Requester is OLDER (Lower ID) -> WAITS
            else if (tid < ownerId) {
                System.out.println("[WAITING] T" + tid + " WAITS (Older than Owner T" + ownerId + ")");
            } 
            // Case: Requester is YOUNGER (Higher ID) -> DIES
            else {
                System.out.println("[ABORTED] T" + tid + " DIES (Younger than Owner T" + ownerId + ") -> Prevents Deadlock");
            }
        }
    }

    // ---- Part (b): Protocol Recommendation ----
    static void consensusProtocol() {
        // We recommend 3PC because 2PC blocks and kills throughput
        System.out.println("\n[Protocol] Recommended: 3PC (Three-Phase Commit) to maximize throughput.");
    }

    public static void main(String[] args) {
        System.out.println("--- ASSIGNMENT 3 ---");

        // 1. Setup Data from Table
        Node[] n = {
            new Node("Edge1", 12, 120, 5),
            new Node("Edge2", 15, 100, 8),
            new Node("Core1", 8,  250, 12),
            new Node("Core2",10, 230, 10),
            new Node("Cloud1",20, 300, 15)
        };

        // 2. Find Bottleneck
        Node worst = n[0];
        for (Node x : n) {
            // Print scores to show working
            System.out.printf("Node: %s \t Score: %.2f%n", x.name, x.score());
            if (x.score() > worst.score()) worst = x;
        }
        System.out.println(">>> RESULT: Bottleneck is " + worst.name + " (Lowest Throughput relative to Latency)");

        // 3. Recommend Protocol
        consensusProtocol();

        // 4. Simulate Deadlock
        LockManager lm = new LockManager();
        lm.request(1); // T1 (Older) gets lock
        lm.request(2); // T2 (Younger) tries, sees T1, and Aborts
    }
}