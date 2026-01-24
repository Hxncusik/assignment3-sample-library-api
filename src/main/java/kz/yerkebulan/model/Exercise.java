package kz.yerkebulan.model;

public class Exercise implements Nameable, Descriptable {
    private int id;
    private String name;
    private String description;

    public Exercise() {}

    public Exercise(int id, String name, String description) {
        this.id = id;
        setName(name);
        this.description = description;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    @Override
    public String getName() { return name; }

    @Override
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Exercise name cannot be empty");
        }
        this.name = name;
    }

    @Override
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String getFullInfo() {
        return "Exercise: " + name + " (" + description + ")";
    }

    @Override
    public String toString() {
        return getFullInfo();
    }
}
