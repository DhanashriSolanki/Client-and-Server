import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ProxyServer {
    private static final int CLIENT_PORT = 8020; // Port for Proxy-Client communication
    private static final int SERVER_PORT = 8010; // Port of the main Server

    public void run() throws IOException {
        ServerSocket proxySocket = new ServerSocket(CLIENT_PORT);
        System.out.println("Proxy Server is listening on port: " + CLIENT_PORT);

        while (true) {
            try (Socket clientSocket = proxySocket.accept();
                 Socket serverSocket = new Socket("localhost", SERVER_PORT);
                 PrintWriter toServer = new PrintWriter(serverSocket.getOutputStream(), true);
                 BufferedReader fromServer = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
                 PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                System.out.println("Connected to Client: " + clientSocket.getRemoteSocketAddress());

                // Forward the client's message to the server
                String clientMessage;
                while ((clientMessage = fromClient.readLine()) != null) {
                    System.out.println("Received from Client: " + clientMessage);
                    toServer.println(clientMessage);

                    // Get the response from the main server
                    String serverResponse = fromServer.readLine();
                    System.out.println("Received from Server: " + serverResponse);

                    // Send the server response back to the client
                    toClient.println(serverResponse);
                }
            }
        }
    }

    public static void main(String[] args) {
        ProxyServer proxyServer = new ProxyServer();
        try {
            proxyServer.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

