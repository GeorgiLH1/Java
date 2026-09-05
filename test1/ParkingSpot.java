package test1;

public class ParkingSpot {

    private String id;
    private int number;
    private boolean isOccupied;
    private String clientName;

    public ParkingSpot(String id, int number){
        this.id = id;
        this.number = number;
        this.isOccupied = false;
        this.clientName = clientName;
    }

    public synchronized String getId() { return id; }
    public synchronized int getNumber() { return number; }
    public synchronized boolean isOccupied() { return isOccupied; }
    public synchronized String getClientName() { return clientName; }

    public synchronized void occupy(String name){
        this.isOccupied = true;
        this.clientName = name;
    }

    public synchronized void release(){
        this.isOccupied = false;
        this.clientName = null;
    }
    
}
