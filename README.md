# ICS 2403: Distributed Systems and Applications
**Student:** Kate Paul
**Reg No:** [Add Reg No Here]

This repository contains the practical implementation of the Distributed Systems assignment, covering architecture modeling, load balancing, concurrency, and fault tolerance.

## Project Overview

### Assignment 1: Distributed Architecture
* **Goal:** Simulate a 3-layer network (Edge-Core-Cloud).
* **Key Feature:** Detects bottlenecks when CPU usage > 65% or Packet Loss > 0.5%.

### Assignment 2: Load Balancing
* **Goal:** Optimize resource allocation.
* **Key Feature:** Uses a "Least-Loaded" algorithm to assign tasks to nodes with the lowest CPU, ensuring RAM limits are met.

### Assignment 3: Concurrency & Deadlock
* **Goal:** Manage database transactions.
* **Key Feature:** Implements the "Wait-Die" protocol to prevent deadlocks between older and younger transactions.

### Assignment 4: Fault Tolerance
* **Goal:** Ensure system reliability.
* **Key Feature:** Primary-Backup strategy that automatically switches to a backup node during Crash or Byzantine failures.

## How to Run

1.  **Java Code (Assign 1 & 3):**
    ```bash
    javac Assignment1.java
    java Assignment1
    ```
2.  **Python Code (Assign 2 & 4):**
    ```bash
    python Assignment2.py
    ```