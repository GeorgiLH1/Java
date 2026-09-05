package FortmatRuletest;

import java.util.List;
import java.util.ArrayList;

public class FormatTester {

    public static List<Boolean> test(FormatRule rule, String[] values){
        List<Boolean> result = new ArrayList<>();
        for(String value : values){
            result.add(value.matches(rule.getPattern()));
        }
        return result;
    }
}
