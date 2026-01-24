package kz.yerkebulan.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Workout implements Nameable, Descriptable {
    protected int id;
    protected String name;
    protected String description;
    protected int durationMinutes;

    protected List<Exercise> exercises = new ArrayList<>();

    protected Workout(int id, String name, String description, int durationMinutes) {
        this.id = id;
        setName(name);
        this.description = description;
        setDurationMinutes(durationMinutes);
    }

    public abstract int calculateCaloriesBurned();

    public abstract String getType();

    public void addExercise(Exercise exercise) {
        this.exercises.add(exercise);
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    @Override
    public String getName() { return name; }

    @Override
    public void setName(String name) {
        if (name == null || name.length() < 2) {
            throw new IllegalArgumentException("Workout name must be at least 2 chars");
        }
        this.name = name;
    }

    @Override
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getDurationMinutes() { return durationMinutes; }

    public void setDurationMinutes(int durationMinutes) {
        if (durationMinutes <= 0) {
            throw new IllegalArgumentException("Duration must be positive");
        }
        this.durationMinutes = durationMinutes;
    }
}
