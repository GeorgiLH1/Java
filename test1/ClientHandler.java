package test1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {
    private Socket socket;
    private List<ParkingSpot> parkingSpots;

    public ClientHandler(Socket socket, List<ParkingSpot> parkingSpots) {
        this.socket = socket;
        this.parkingSpots = parkingSpots;
    }

    @Override
    public void run() {
        try (
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)
        ) {
            writer.println("Добре дошли в системата за управление на паркинг!");
            writer.println("Команди: 1 - Показване, 2 <ID> <Име> - Заемане, 3 <ID> - Освобождаване");

            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                String[] parts = inputLine.trim().split("\\s+");
                if (parts.length == 0) continue;

                String command = parts[0];

                switch (command) {
                    case "1":
                        // 10т. - Показване на всички паркоместа
                        StringBuilder sb = new StringBuilder("--- Списък с паркоместа ---\n");
                        synchronized (parkingSpots) {
                            for (ParkingSpot spot : parkingSpots) {
                                sb.append("ID: ").append(spot.getId())
                                  .append(", Номер: ").append(spot.getNumber())
                                  .append(", Заето: ").append(spot.isOccupied() ? "Да (от " + spot.getClientName() + ")" : "Не")
                                  .append("\n");
                            }
                        }
                        writer.println(sb.toString());
                        break;

                    case "2":
                        // 15т. - Команда за заемане на паркомясто (с проверка и синхронизация)
                        if (parts.length < 3) {
                            writer.println("Грешка: Непълна команда. Използвайте: 2 <ID> <Име>");
                            break;
                        }
                        String spotIdToOccupy = parts[1];
                        String clientName = parts[2];
                        boolean foundAndOccupied = false;
                        String message = "";

                        // Синхронизация по списъка и съответното място за предотвратяване на конфликти
                        synchronized (parkingSpots) {
                            ParkingSpot targetSpot = null;
                            for (ParkingSpot spot : parkingSpots) {
                                if (spot.getId().equalsIgnoreCase(spotIdToOccupy)) {
                                    targetSpot = spot;
                                    break;
                                }
                            }

                            if (targetSpot == null) {
                                message = "Грешка: Паркомясто с ID " + spotIdToOccupy + " не съществува.";
                            } else {
                                // Синхронизиране върху самото място за гарантиране на коректност при паралелни заявки
                                synchronized (targetSpot) {
                                    if (!targetSpot.isOccupied()) {
                                        targetSpot.occupy(clientName);
                                        foundAndOccupied = true;
                                        message = "Успех: Паркомясто " + spotIdToOccupy + " беше заето от " + clientName + ".";
                                    } else {
                                        message = "Грешка: Паркомясто " + spotIdToOccupy + " вече е заето!";
                                    }
                                }
                            }
                        }
                        writer.println(message);
                        break;

                    case "3":
                        // 10т. - Команда за освобождаване на паркомясто
                        if (parts.length < 2) {
                            writer.println("Грешка: Непълна команда. Използвайте: 3 <ID>");
                            break;
                        }
                        String spotIdToRelease = parts[1];
                        boolean released = false;
                        String releaseMsg = "";

                        synchronized (parkingSpots) {
                            ParkingSpot targetSpot = null;
                            for (ParkingSpot spot : parkingSpots) {
                                if (spot.getId().equalsIgnoreCase(spotIdToRelease)) {
                                    targetSpot = spot;
                                    break;
                                }
                            }

                            if (targetSpot == null) {
                                releaseMsg = "Грешка: Паркомясто с ID " + spotIdToRelease + " не съществува.";
                            } else {
                                synchronized (targetSpot) {
                                    if (targetSpot.isOccupied()) {
                                        targetSpot.release();
                                        released = true;
                                        releaseMsg = "Успех: Паркомясто " + spotIdToRelease + " е освободено.";
                                    } else {
                                        releaseMsg = "Информация: Паркомясто " + spotIdToRelease + " вече е свободно.";
                                    }
                                }
                            }
                        }
                        writer.println(releaseMsg);
                        break;

                    default:
                        writer.println("Непозната команда. Използвайте 1, 2 или 3.");
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Клиентът прекъсна връзката.");
        }
    }
}