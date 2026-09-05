package FileMaskTest;

import java.util.ArrayList;
import java.util.List;

public class FileMaskTester {

    public static List<Boolean> test(FileMask mask, String[] filenames){
        List<Boolean> result = new ArrayList<>();
        for(String value : filenames){
            if(mask.getMask().equals(value)){
                result.add(true);
            }
        }
        return result;
    }
    
}
