package kz.yerkebulan.model;

public class StrengthWorkout extends Workout {
    private int reps;
    private int sets;

    public StrengthWorkout(int id, String name, String description, int durationMinutes, int reps, int sets) {
        super(id, name, description, durationMinutes);
        this.reps = reps;
        this.sets = sets;
    }

    public int getReps() { return reps; }
    public void setReps(int reps) { this.reps = reps; }

    public int getSets() { return sets; }
    public void setSets(int sets) { this.sets = sets; }

    @Override
    public int calculateCaloriesBurned() {
        return (durationMinutes * 5) + (sets * reps * 2);
    }

    @Override
    public String getType() {
        return "STRENGTH";
    }

    @Override
    public String getFullInfo() {
        return String.format("[Strength] %s: %d sets x %d reps (Burned: %d kcal)",
                name, sets, reps, calculateCaloriesBurned());
    }
}