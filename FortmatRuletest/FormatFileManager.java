package FortmatRuletest;

import java.util.List;
import java.io.*;
import java.util.ArrayList;

public class FormatFileManager {

    private static final String FILE_NAME = "formats.txt";

    public synchronized List<FormatRule> readRules() {
        List<FormatRule> result = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                FormatRule rule = new FormatRule(parts[1], parts[2]);
                rule.setId(Integer.parseInt(parts[0]));
                rule.setRating(Integer.parseInt(parts[3]));

                result.add(rule);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public synchronized boolean addRule(FormatRule rule) {
        List<FormatRule> rules = readRules();

        for(FormatRule r : rules){
            if(r.getPattern().equals(rule.getPattern())){
                return false;
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(rule.toString());
            writer.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public synchronized void updateRating(int id, int delta) {
        List<FormatRule> rules = readRules();

        for (FormatRule r : rules) {
            if (r.getId() == id) {
                r.setRating(r.getRating() + delta);
                ;
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (FormatRule r : rules) {
                writer.write(r.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
