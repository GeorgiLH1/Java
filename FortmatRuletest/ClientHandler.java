package FortmatRuletest;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
 

public class ClientHandler implements Runnable{

    Socket socket;
    FormatFileManager fileManager;

    public ClientHandler(Socket scoket, FormatFileManager fileManager){
        this.socket = scoket;
        this.fileManager = fileManager;
    }

    public void run(){

        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String line;
            while((line = in.readLine()) != null){
                int command = Integer.parseInt(line);

                switch(command){

                    case 1:{
                        String pattern = in.readLine();
                        String description= in.readLine();

                        FormatRule rule = new FormatRule(pattern, description);
                        List<Boolean> results = FormatTester.test(rule, readValues(in));

                        for(Boolean result : results){
                            out.println(result);
                        }
                        String decision = in.readLine();

                        if(decision.equals("1")){
                            fileManager.addRule(rule);
                        }
                        break;
                    }
                    case 2:

                }
            }
            socket.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    
}
