package kz.yerkebulan.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Exercise implements Nameable, Descriptable {
    private final int id;
    private String name;
    private String description;
    private List<Workout> workouts;

    public Exercise(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.workouts = new ArrayList<>();
    }

    public void start() {
        System.out.println("Starting exercise");
    }

    public void pause() {
        System.out.println("Pausing exercise");
    }

    public void stop() {
        System.out.println("Stopping exercise");
    }
}
