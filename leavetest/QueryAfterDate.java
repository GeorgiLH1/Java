package leavetest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class QueryAfterDate implements LeaveQuery{

    public List<Leave> query(List<Leave> input, String searchParam){
        List<Leave> result = new ArrayList<>();
        LocalDate boundary = LocalDate.parse(searchParam);

        for(Leave l : input){
            if(LocalDate.parse(l.getRequestDate()).isAfter(boundary)){
                result.add(l);
            }
        }
        return result;
    }   
}
