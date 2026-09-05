package leavetest;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LeaveFileManager {

    private static final String FILE_NAME = "leaves.txt";
    private static final String ARCHIVE = "archive.txt";
    private static final int MAX_LEAVES = 50;

    public synchronized List<Leave> readLeaves(){
        List<Leave> leaves = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))){

            String line;
            while((line = reader.readLine()) != null){
                String[] parts = line.split("\\|", 3);
                leaves.add(new Leave(parts[0], Leave.Type.valueOf(parts[1]), parts[2]));
            }

        }catch(IOException e){
            e.printStackTrace();
        }
        return leaves;
    }

    public synchronized void writeLeaves(Leave leave){

        List<Leave> leaves = readLeaves();

        if(leaves.size() >= MAX_LEAVES){
                moveToArchive(leaves);
                leaves.clear();
            }

            leaves.add(leave);
            
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))){
            for(Leave l : leaves){
                writer.write(l.toString());
                writer.newLine();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void moveToArchive(List<Leave> oldLeaves){

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVE, true))){
            for(Leave l : oldLeaves){
                writer.write(l.toString());
                writer.newLine();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
