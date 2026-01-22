package kz.yerkebulan.model;

public class CardioWorkout extends Workout {
    public CardioWorkout(int id, String name, String description, boolean isRunning, int reps, int sets, int weight, long duration) {
        super(id, name, description, isRunning, reps, sets, weight, duration);
    }
}
