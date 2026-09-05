package FileMaskTest;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable {

    Socket socket;
    FileMaskManager fileManager;

    public ClientHandler(Socket socket, FileMaskManager fileManager) {
        this.socket = socket;
        this.fileManager = fileManager;
    }

    public void run(){

        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());

            String line;
            while((line = in.readLine()) != null){
                

                switch(line){

                    case "CREATE": 

                    String mask = in.readLine();
                    String description = in.readLine();

                    FileMask fileMask = new FileMask(mask, description);

                    List<Boolean> results = FileMaskTester.test(fileMask, readValues(in));

                    for(Boolean b : results){
                        out.println(b);
                    }

                    String descision = in.readLine();

                    if(descision.equals("1")){
                        boolean added = fileManager.writeMask(fileMask);
                        out.println(added ? "OK" : "Duplicate");
                    }
                    break;

                    case "SEARCH":

                    
            }
        }
        }catch(IOException e){
            e.printStackTrace();

    }
    
}
