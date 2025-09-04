# DNS-Resolver-Simulation
# DNS Resolver Simulation (Java)

This project simulates a **DNS server–client system** using Java sockets. It demonstrates how DNS queries work, how caching can improve performance, and how server-client communication happens over UDP.

---

## Features

- **DNS Tree Storage**  
  Domains and subdomains are stored in a hierarchical tree structure (`DnsTree`, `DnsNode`).

- **Multiple IP Support**  
  Each domain can map to multiple IP addresses. A round-robin approach is used to balance responses.

- **UDP Communication**  
  Client and server interact using UDP sockets (`DatagramSocket` & `DatagramPacket`).

- **Client-Side Caching**  
  - Stores recently resolved domains with IP addresses.  
  - Tracks cache hits.  
  - Replaces the least-frequently used entry when the cache is full.  

- **Interactive Queries**  
  Users can type domain names and get IP responses in real-time.

- **Preloaded Records**  
  The server starts with predefined DNS records (Google, BBC, universities, etc.) plus 1000 test domains.

---

## Project Structure

- `Client.java` → Client with local caching.  
- `DnsServer.java` → DNS server running on UDP port 8888.  
- `DnsTree.java` & `DnsNode.java` → Data structure for storing domains and IPs.  
- `Main.java` → Starts the server and interactive client.  

---

## How to Run

1. Compile the project:  
   ```bash
  javac question/*.java

2. Run the main program:

   java question.Main
   
3. Type a domain (e.g., google.com) to query it.

   Type exit to quit.
