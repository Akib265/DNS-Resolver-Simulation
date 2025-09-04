package question;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
    private class CachedContent {
        private String domainName;
        private String ipAddress;
        private int hitNo;

        private CachedContent(String domainName, String ipAddress) {
            this.domainName = domainName;
            this.ipAddress = ipAddress;
            this.hitNo = 0;
        }

        @Override
        public String toString() {
            return this.domainName + "-" + this.hitNo;
        }
    }

    private CachedContent[] cacheList;
    private static final int SERVER_PORT = 8888;
    private static final String SERVER_HOST = "localhost";

    public Client() {
        cacheList = new CachedContent[10];
    }

    public String sendRequest(String domainName) {
        // Check if the domain is in the cache
        for (int i = 0; i < cacheList.length; i++) {
            if (cacheList[i] != null && cacheList[i].domainName.equals(domainName)) {
                cacheList[i].hitNo++;
                System.out.println("Cache Hit: " + cacheList[i].ipAddress);
                return cacheList[i].ipAddress;
            }
        }

        // Query the DNS server
        try (DatagramSocket socket = new DatagramSocket()) {
            byte[] requestData = domainName.getBytes();
            InetAddress serverAddress = InetAddress.getByName(SERVER_HOST);

            DatagramPacket requestPacket = new DatagramPacket(requestData, requestData.length, serverAddress, SERVER_PORT);
            socket.send(requestPacket);

            byte[] responseData = new byte[1024];
            DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length);
            socket.receive(responsePacket);

            String response = new String(responsePacket.getData(), 0, responsePacket.getLength());
            if (!"NOT_FOUND".equals(response)) {
                addToCache(domainName, response);
            }
            return "NOT_FOUND".equals(response) ? null : response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void addToCache(String domainName, String ipAddress) {
        boolean added = false;
        for (int i = 0; i < cacheList.length; i++) {
            if (cacheList[i] == null) {
                cacheList[i] = new CachedContent(domainName, ipAddress);
                added = true;
                break;
            }
        }

        if (!added) {
            int index = 0;
            int minHits = Integer.MAX_VALUE;
            for (int i = 0; i < cacheList.length; i++) {
                if (cacheList[i].hitNo < minHits) {
                    minHits = cacheList[i].hitNo;
                    index = i;
                }
            }
            cacheList[index] = new CachedContent(domainName, ipAddress);
        }
    }
}
