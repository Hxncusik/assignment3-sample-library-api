package kz.yerkebulan.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public abstract class Workout implements Nameable, Descriptable {
    private final int id;
    private String name;
    private String description;
    private boolean isRunning;
    private int reps;
    private int sets;
    private int weight;
    private long duration;
}
