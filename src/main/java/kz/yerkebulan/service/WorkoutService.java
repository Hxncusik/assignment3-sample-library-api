package kz.yerkebulan.service;

import kz.yerkebulan.db.DatabaseConnection;
import kz.yerkebulan.exception.*;
import kz.yerkebulan.model.*;
import kz.yerkebulan.repository.*;

import java.util.List;

public class WorkoutService {
    private final WorkoutRepository workoutRepo;
    private final ExerciseRepository exerciseRepo;

    public WorkoutService(DatabaseConnection dbConnection) {
        this.workoutRepo = new WorkoutRepository(dbConnection);
        this.exerciseRepo = new ExerciseRepository(dbConnection);
    }

    public void createWorkout(Workout workout) throws InvalidInputException {
        validateWorkout(workout);
        workoutRepo.create(workout);
    }

    public Workout getWorkoutById(int id) throws ResourceNotFoundException {
        Workout workout = workoutRepo.findById(id);
        if (workout == null) {
            throw new ResourceNotFoundException("Workout with ID " + id + " not found.");
        }
        List<Exercise> exercises = exerciseRepo.findByWorkoutId(id);
        for (Exercise ex : exercises) {
            workout.addExercise(ex);
        }
        return workout;
    }

    public List<Workout> getAllWorkouts() {
        return workoutRepo.findAll();
    }

    public void updateWorkout(int id, Workout updatedData) throws ResourceNotFoundException, InvalidInputException {
        if (workoutRepo.findById(id) == null) {
            throw new ResourceNotFoundException("Cannot update: Workout ID " + id + " not found.");
        }
        validateWorkout(updatedData);
        workoutRepo.update(id, updatedData);
    }

    public void deleteWorkout(int id) throws ResourceNotFoundException {
        if (workoutRepo.findById(id) == null) {
            throw new ResourceNotFoundException("Cannot delete: Workout ID " + id + " not found.");
        }
        workoutRepo.delete(id);
    }

    public void addExercise(int workoutId, Exercise exercise) throws ResourceNotFoundException, InvalidInputException {
        if (workoutRepo.findById(workoutId) == null) {
            throw new ResourceNotFoundException("Workout ID " + workoutId + " not found.");
        }
        if (exercise.getName() == null || exercise.getName().isEmpty()) {
            throw new InvalidInputException("Exercise name cannot be empty");
        }
        exerciseRepo.createForWorkout(workoutId, exercise);
    }

    private void validateWorkout(Workout w) throws InvalidInputException {
        if (w.getDurationMinutes() <= 0) {
            throw new InvalidInputException("Duration must be positive.");
        }
        if (w instanceof CardioWorkout) {
            if (((CardioWorkout) w).getDistanceKm() < 0)
                throw new InvalidInputException("Distance cannot be negative.");
        }
        if (w instanceof StrengthWorkout) {
            if (((StrengthWorkout) w).getReps() <= 0)
                throw new InvalidInputException("Reps must be > 0.");
        }
    }
}