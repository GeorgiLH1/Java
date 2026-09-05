package leavetest;

import java.util.List;

public interface LeaveQuery {
    List<Leave> query(List<Leave> input, String searchParam);
}
