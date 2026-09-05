package test2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {

    private static final int PORT = 9500;

    public static void main(String[] args){
        RegexFileManager fileManager = new RegexFileManager();

        try(ServerSocket serverSocket = new ServerSocket(PORT)){
            System.out.println("Server starting on port: " + PORT);

            while(true){
                try{
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client connecting " + clientSocket.getInetAddress());
                    new Thread(new ClientHandler(clientSocket, fileManager)).start();

                }catch(IOException e){
                    System.out.println("Cannot connect client " + e.getMessage());
                }  
            }
        }catch(IOException e){
            System.out.println("Cannot start server " + e.getMessage());
        }
    }
}
