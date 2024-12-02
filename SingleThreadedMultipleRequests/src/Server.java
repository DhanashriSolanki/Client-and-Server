import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public void run() throws IOException {
        int port = 8010;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Main Server is listening on port: " + port);

        while (true) {
            try (Socket acceptedConnection = serverSocket.accept();
                 PrintWriter toClient = new PrintWriter(acceptedConnection.getOutputStream(), true);
                 BufferedReader fromClient = new BufferedReader(new InputStreamReader(acceptedConnection.getInputStream()))) {

                System.out.println("Connected to Proxy: " + acceptedConnection.getRemoteSocketAddress());

                String clientMessage;
                while ((clientMessage = fromClient.readLine()) != null) {
                    System.out.println("Received from Proxy: " + clientMessage);
                    toClient.println("Hello from the Main Server! Echo: " + clientMessage);
                }
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
