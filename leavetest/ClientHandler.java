package leavetest;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {

    Socket socket;
    LeaveFileManager fileManager;

    public ClientHandler(Socket socket, LeaveFileManager fileManager) {
        this.socket = socket;
        this.fileManager = fileManager;
    }

    public void run() {

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String line;
            while ((line = in.readLine()) != null) {
                int command = Integer.parseInt(line);

                switch (command) {

                    case 1: // all
                        for (Leave l : fileManager.readLeaves()) {
                            out.println(l.toString());
                        }
                        break;

                    case 2: // add leave
                        String date = in.readLine();
                        String type = in.readLine();
                        String name = in.readLine();

                        fileManager.writeLeaves(new Leave(date, Leave.Type.valueOf(type), name));
                        out.println("OK!");
                        break;

                    case 3: {// query by type
                        List<Leave> leaves = fileManager.readLeaves();

                        String param = in.readLine();
                        List<Leave> result = new QueryByType().query(leaves, param);
                        for (Leave l : result) {
                            out.println(l);
                        }
                        break;
                    }
                    case 4: {// query by name
                        String param = in.readLine();

                        List<Leave> result = new QueryByEmployeeName().query(fileManager.readLeaves(), param);
                        for (Leave l : result) {
                            out.println(l);
                        }
                        break;
                    }
                    case 5: {// query after date
                        String param = in.readLine();

                        List<Leave> result = new QueryAfterDate().query(fileManager.readLeaves(), param);
                        for (Leave l : result) {
                            out.println(l);
                        }
                        break;
                    }
                }
            }
            socket.close();
        } catch (IOException e) {
            System.out.println("Error with client: " + e.getMessage());
        }
    }
}
