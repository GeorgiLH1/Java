package FortmatRuletest;

public class FormatRule {

    private int id;
    private String pattern;
    private String description;
    private int rating;

    private static int nextId = 0;

    public FormatRule(String pattern, String description) {
        this.id = nextId++;
        this.pattern = pattern;
        this.description = description;
        this.rating = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPattern() {
        return pattern;
    }

    public String getDescription() {
        return description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return id + " | " + pattern + " | " + description + " | " + rating;
    }

}
