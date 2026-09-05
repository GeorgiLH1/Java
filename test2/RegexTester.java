package test2;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTester {

    public static List<Boolean> test(Regex regex, String[] strings){
        List<Boolean> results = new ArrayList<>();
        Pattern p = Pattern.compile(regex.getPattern());

        for(String s : strings){
                results.add(p.matcher(s).matches());
        }
        return results;
    }
}
