package kz.yerkebulan;

import kz.yerkebulan.db.DatabaseConnection;
import kz.yerkebulan.exception.*;
import kz.yerkebulan.model.*;
import kz.yerkebulan.service.WorkoutService;

import java.util.List;

public class Main {
    private static final WorkoutService service = new WorkoutService(
        new DatabaseConnection("jdbc:postgresql://localhost:5432/fitness_db", "root", "")
    );

    public static void main(String[] args) {
        System.out.println("=== Fitness Tracker API Demo ===");

        try {
            System.out.println("\n[1] Creating Workouts...");

            Workout run = new CardioWorkout(0, "Morning Run", "Run in the park", 30, 5.5);
            service.createWorkout(run);
            System.out.println("Created: " + run.getName());

            Workout gym = new StrengthWorkout(0, "Chest Day", "Heavy lifting", 60, 10, 4);
            service.createWorkout(gym);
            System.out.println("Created: " + gym.getName());

            List<Workout> all = service.getAllWorkouts();
            int lastId = all.get(all.size() - 1).getId();

            System.out.println("\n[2] Adding Exercise to Workout ID: " + lastId);
            service.addExercise(lastId, new Exercise(0, "Bench Press", "100kg bar"));
            service.addExercise(lastId, new Exercise(0, "Push ups", "Bodyweight"));

            System.out.println("\n[3] Listing All Workouts (Polymorphism Demo):");
            List<Workout> workouts = service.getAllWorkouts();
            for (Workout w : workouts) {
                Workout fullInfo = service.getWorkoutById(w.getId());

                System.out.println("------------------------------------------------");
                System.out.println(fullInfo.getFullInfo()); // Полиморфный вызов
                System.out.println("Type: " + fullInfo.getType());

                List<Exercise> exList = fullInfo.getExercises();
                if (!exList.isEmpty()) {
                    System.out.println(" Exercises:");
                    for (Exercise ex : exList) {
                        System.out.println("  - " + ex.getName() + ": " + ex.getDescription());
                    }
                }
            }

            if (!workouts.isEmpty()) {
                int idToUpdate = workouts.get(0).getId();
                System.out.println("\n[4] Updating Workout ID: " + idToUpdate);
                Workout toUpdate = service.getWorkoutById(idToUpdate);
                toUpdate.setDurationMinutes(90); // Меняем длительность
                service.updateWorkout(idToUpdate, toUpdate);
                System.out.println("Updated successfully.");
            }

            System.out.println("\n[5] Testing Validation Logic:");
            try {
                service.createWorkout(new CardioWorkout(0, "Bad Run", "Test", -10, 5));
            } catch (InvalidInputException e) {
                System.out.println("CAUGHT EXPECTED ERROR: " + e.getMessage());
            }

            System.out.println("\n[6] Deleting a workout...");
            if (!workouts.isEmpty()) {
                service.deleteWorkout(workouts.get(0).getId());
                System.out.println("Deleted successfully.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}