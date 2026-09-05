package leavetest;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final int PORT = 9500;

    public static void main(String[] args) {
        LeaveFileManager fileManager = new LeaveFileManager();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server starting on port: " + PORT);

            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("new client: " + clientSocket.getInetAddress());
                    new Thread(new ClientHandler(clientSocket, fileManager)).start();
                } catch (IOException e) {
                    System.out.println("accepting client unsuccessfull: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Server is not starting! " + e.getMessage());
        }
    }
}
