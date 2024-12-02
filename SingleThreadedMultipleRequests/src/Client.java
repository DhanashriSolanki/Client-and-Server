import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public void runSequentialRequests(int numberOfRequests) throws IOException {
        int port = 8020; // Proxy server port
        InetAddress address = InetAddress.getByName("localhost");

        for (int i = 1; i <= numberOfRequests; i++) {
            try (Socket socket = new Socket(address, port);
                 PrintWriter toSocket = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader fromSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                // Send a request message
                String message = "Hello Proxy. This is Client request #" + i;
                toSocket.println(message);

                // Read the response from the ProxyServer
                String response = fromSocket.readLine();
                System.out.println("Response from Proxy for request #" + i + ": " + response);
            }
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        try {
            client.runSequentialRequests(5); // Send 5 requests to the Proxy Server
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

