package leavetest;

import java.util.ArrayList;
import java.util.List;

public class QueryByType implements LeaveQuery{

    public List<Leave> query(List<Leave> input, String searchParam){
        List<Leave> result = new ArrayList<>();
        
        for(Leave l : input){
            if(l.getType().name().equals(searchParam)){
                result.add(l);
            }
        }
        return result;
    }
}
