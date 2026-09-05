package leavetest;

import java.util.regex.Pattern;

public class Leave {

    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");

    public enum Type{VACATION, SICK, UNPAID}

    private String requestDate;
    private Type type;
    private String employeeName;

    public Leave(String requestDate, Type type, String employeeName){
        if(type == null || !DATE_PATTERN.matcher(requestDate).matches()){
            throw new IllegalArgumentException("Invalid date type: " + requestDate);
        }
        this.requestDate = requestDate;
        this.type = type;
        this.employeeName = employeeName;
    }

    public String getRequestDate() { return requestDate; }
    public Type getType() { return type; }
    public String getEmployeeName() { return employeeName; }

    @Override
    public String toString(){
        return requestDate + " | " + type.name() + " | " + employeeName;
    }
    
}
