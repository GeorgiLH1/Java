package FortmatRuletest;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final int PORT = 9500;

    public static void main(String[] args) {
        FormatFileManager fileManager = new FormatFileManager();

        try (ServerSocket clientSocket = new ServerSocket(PORT)) {
            System.out.println("Server staring on port: " + PORT);

            while (true) {
                try {
                    Socket socket = clientSocket.accept();
                    System.out.println("new CLient: " + clientSocket.getInetAddress());
                    new Thread(new ClientHandler(clientSocket, fileManager)).start();
                } catch (IOException e) {
                    System.out.println("Cannot connect client" + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("cannot connect server: " + e.getMessage());
        }
    }
}
