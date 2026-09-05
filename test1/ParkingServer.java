package test1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ParkingServer {
    private static final int PORT = 1643;

    private static final List<ParkingSpot> parkingSpots = new ArrayList<>();

    public static void main(String[] args){
        parkingSpots.add(new ParkingSpot("P1, 101"));
        parkingSpots.add(new ParkingSpot("P2, 102"));
        parkingSpots.add(new ParkingSpot("P3, 103"));
        parkingSpots.add(new ParkingSpot("P4, 104"));
        parkingSpots.add(new ParkingSpot("P5, 105"));
        parkingSpots.add(new ParkingSpot("P6, 106"));
        parkingSpots.add(new ParkingSpot("P7, 107"));

        System.out.println("Parking server starting on port: " + PORT + "... ");

        try(ServerSocket serverSocket = new ServerSocket(PORT)){
            while(true){
                Socket clientSocket = serverSocket.accept();
                System.out.println("Successfull connection of a new client: " + clientSocket.getRemoteSocketAddress());

                ClientHandler clientHandler = new ClientHandler(clientSocket, parkingSpots);
                new Thread(clientHandler).start();;
            }
        }catch(IOException e){
            System.err.println("Error with the server: " + e.getMessage());
        }
    }
    
}
