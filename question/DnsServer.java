package question;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class DnsServer {
    private DnsTree dnsTree;
    private static final int PORT = 8888;

    public DnsServer(DnsTree dnsTree) {
        this.dnsTree = dnsTree;
    }

    public void start() {
        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            System.out.println("DNS Server started on port " + PORT);
            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket requestPacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(requestPacket);

                String domainName = new String(requestPacket.getData(), 0, requestPacket.getLength());
                System.out.println("Received query for: " + domainName);

                String response = dnsTree.queryDomain(domainName);
                if (response == null) {
                    response = "NOT_FOUND";
                }

                byte[] responseData = response.getBytes();
                InetAddress clientAddress = requestPacket.getAddress();
                int clientPort = requestPacket.getPort();

                DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length, clientAddress, clientPort);
                socket.send(responsePacket);
                System.out.println("Sent response: " + response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
