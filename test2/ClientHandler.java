package test2;

import java.net.Socket;
import java.util.*;
import java.io.*;

public class ClientHandler implements Runnable{

    Socket socket;
    RegexFileManager fileManager;

    public ClientHandler(Socket socket, RegexFileManager fileManager){
        this.socket = socket;
        this.fileManager = fileManager;
    }

    public void run(){
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            
            String line;
            while((line = in.readLine()) != null){

                switch (line) {

                    case "CREATE": 

                    String pattern = in.readLine();
                    String description = in.readLine();

                    Regex regex = new Regex(pattern, description);

                    out.println(RegexTester.test(regex, readValues(in)));

                    int choise = Integer.parseInt(in.readLine());

                    if(choise == 1){
                        out.println(fileManager.writeRegex(regex) ? "OK" : "DUPLICATING");
                    }
                    break;

                    case "SEARCH":

                    List<Regex> regexes = fileManager.readRegex();
                    List<Regex> result = new ArrayList<>();
                    String keyword = in.readLine();

                    for(Regex r : regexes){
                        if(r.getDescription().contains(keyword)){
                            result.add(r);
                        }
                    }
                    result.sort(Comparator.comparingInt(Regex :: getRating).reversed());

                    for(Regex r: result){
                        out.println(r.toString());
                    }

                    int typeId = Integer.parseInt(in.readLine());

                    for(Regex r : result){
                        if(typeId == r.getId()){
                            out.println(RegexTester.test(r, readValues(in)));

                            int wantRating = Integer.parseInt(in.readLine());
                            if(wantRating == 1){
                                int delta = Integer.parseInt(in.readLine());
                                if(delta == 1){
                                    fileManager.updateRating(typeId, delta);
                                }else if(delta == -1){
                                    fileManager.updateRating(typeId, delta);
                                }else{
                                    out.println("invalid raing. add 1 or -1");
                                }
                            }
                        }
                    }
                    break;
                }
            }
            socket.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    private String[] readValues(BufferedReader in) throws IOException {
        int count = Integer.parseInt(in.readLine());
        String[] values = new String[count];
        for (int i = 0; i < count; i++) {
            values[i] = in.readLine();
        }
        return values;
    }

}
