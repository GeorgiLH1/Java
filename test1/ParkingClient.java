package test1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ParkingClient {
    private static final String SERVER_IP = "127.0.0.1"; // Локалният адрес (localhost)
    private static final int PORT = 1643;

    public static void main(String[] args) {
        System.out.println("Свързване към паркинг сървъра...");

        try (
            Socket socket = new Socket(SERVER_IP, PORT);
            BufferedReader serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter serverWriter = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))
        ) {
            System.out.println("Успешно свързани със сървъра!");

            // Нишка или отделен поток за четене на отговорите от сървъра в реално време
            Thread readThread = new Thread(() -> {
                try {
                    String serverResponse;
                    while ((serverResponse = serverReader.readLine()) != null) {
                        System.out.println(serverResponse);
                    }
                } catch (Exception e) {
                    System.out.println("Връзката със сървъра е прекъсната.");
                }
            });
            readThread.start();

            // Четене на това, което пишеш в конзолата, и изпращането му към сървъра
            String userInput;
            while ((userInput = consoleReader.readLine()) != null) {
                serverWriter.println(userInput);
                if (userInput.equalsIgnoreCase("exit")) {
                    break;
                }
            }

        } catch (Exception e) {
            System.err.println("Грешка при клиента: " + e.getMessage());
        }
    }
}