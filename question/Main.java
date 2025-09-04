package question;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        int testSize = 1000;
        DnsTree tree = new DnsTree();

        // Prepopulate the DNS tree
        tree.insertRecord("google.com", "3.3.3.30");
        tree.insertRecord("mail.google.com", "4.4.4.4");
        tree.insertRecord("google.com", "3.3.3.40");
        tree.insertRecord("boun.edu.tr", "1.1.1.1");
        tree.insertRecord("cmpe.boun.edu.tr", "2.2.2.2");
        tree.insertRecord("metu.edu.tr", "1.1.1.1");
        tree.insertRecord("bbc.co.uk", "7.7.7.7");
        tree.insertRecord("cambridge.ac.uk", "8.8.8.8");

        for (int i = 0; i < testSize; i++) {
            tree.insertRecord("site" + i + ".com.tr", i + ".234.24.1");
        }

        // Start the DNS server in a separate thread
        new Thread(() -> new DnsServer(tree).start()).start();

        // Create the client and query DNS
        Client user = new Client();
        PrintStream printer = new PrintStream(new File("output.txt"));
        printer.println("Preloaded DNS Records: " + tree.getAllRecords());

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter a domain to query (or type 'exit' to quit): ");
            String query = scanner.nextLine();

            if ("exit".equalsIgnoreCase(query)) {
                System.out.println("Exiting program...");
                break;
            }

            String response = user.sendRequest(query);
            if (response == null) {
                System.out.println("Domain not found in DNS records.");
            } else {
                System.out.println("Response: " + response);
            }
        }

        printer.println("Final DNS Records: " + tree.getAllRecords());
        scanner.close();
    }
}
