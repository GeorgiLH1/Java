package leavetest;

import java.util.ArrayList;
import java.util.List;

public class QueryByEmployeeName implements LeaveQuery{

    public List<Leave> query(List<Leave> input, String searchParam){
        List<Leave> result = new ArrayList<>();

        for(Leave l : input){
            if(l.getEmployeeName().contains(searchParam)){
                result.add(l);
            }
        }
        return result;
    }
}
