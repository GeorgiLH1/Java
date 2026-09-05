package test2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RegexFileManager {

    private static final String FILE_NAME = "regexes.txt";
    

    public synchronized List<Regex> readRegex(){
        List<Regex> result = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))){
            String line;
            while((line = reader.readLine()) != null){
                String[] parts = line.split("\\|");

                Regex regex = new Regex(parts[1], parts[2]);
                regex.setId(Integer.parseInt(parts[0]));
                regex.setRating(Integer.parseInt(parts[3]));

                result.add(regex);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return result;
    }

    public synchronized boolean writeRegex(Regex regex){
        List<Regex> regexes = readRegex();

        for(Regex r : regexes){
            if(r.getPattern().equals(regex.getPattern())){
                return false;
            }
        }

        regexes.add(regex);

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))){
            for(Regex r : regexes){
                writer.write(r.toString());
                writer.newLine();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return true;
    }

    public synchronized void updateRating(int id, int delta){
        List<Regex> regexes = readRegex();
        for(Regex r : regexes){
            if(r.getId() == (id)){
                r.setRating(r.getRating() + delta);
            }
        }
    }
}
