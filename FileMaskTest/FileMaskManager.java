package FileMaskTest;

import java.io.*;
import java.util.*;

public class FileMaskManager {

    private static final String FILE_NAME = "filemasks.txt";

    public synchronized List<FileMask> readMask() {
        List<FileMask> result = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                FileMask fileMask = new FileMask(parts[1], parts[2]);
                fileMask.setRating(Integer.parseInt(parts[3]));
                fileMask.setId(Integer.parseInt(parts[0]));

                result.add(fileMask);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public synchronized boolean writeMask(FileMask mask) {

        List<FileMask> masks = readMask();

        for (FileMask m : masks) {
            if (m.getMask().equalsIgnoreCase(mask.getMask())) {
                return false;
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {

            writer.write(mask.toString());
            writer.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public synchronized void updateRating(int id, int delta){
        List<FileMask> masks = readMask();

        for(FileMask m : masks){
            if(m.getId() == id){
                m.setRating(m.getRating() + delta);
            }
        }
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))){
            for(FileMask m : masks){
                
                writer.write(m.toString());
                writer.newLine();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
