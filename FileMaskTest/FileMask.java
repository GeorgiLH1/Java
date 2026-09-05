package FileMaskTest;

public class FileMask {

    private static int nextId = 0;

    private int id;
    private String mask;
    private String description;
    private int rating;

    public FileMask(String mask, String description){
        this.id = nextId++;
        this.mask = mask;
        this.description = description;
        this.rating = 0;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getMask() { return mask; }
    public String getDescription() { return description; }
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }


    @Override
    public String toString(){
        return id + " | " + mask + " | " + description + " | " + rating;
    }
    
}
