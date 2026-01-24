package kz.yerkebulan.repository;

import kz.yerkebulan.db.DatabaseConnection;
import kz.yerkebulan.exception.DatabaseOperationException;
import kz.yerkebulan.model.Exercise;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExerciseRepository implements Repository<Exercise> {

    private final DatabaseConnection dbConnection;

    public ExerciseRepository(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void createForWorkout(int workoutId, Exercise exercise) {
        String sql = "INSERT INTO exercises (name, description, workout_id) VALUES (?, ?, ?)";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, exercise.getName());
            stmt.setString(2, exercise.getDescription());
            stmt.setInt(3, workoutId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error creating exercise", e);
        }
    }

    public List<Exercise> findByWorkoutId(int workoutId) {
        List<Exercise> list = new ArrayList<>();
        String sql = "SELECT * FROM exercises WHERE workout_id = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, workoutId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Exercise(rs.getInt("id"), rs.getString("name"), rs.getString("description")));
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error finding exercises", e);
        }
        return list;
    }

    @Override public void create(Exercise entity) { }
    @Override public Exercise findById(int id) { return null; }
    @Override public List<Exercise> findAll() { return new ArrayList<>(); }
    @Override public void update(int id, Exercise entity) { }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM exercises WHERE id=?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error deleting exercise", e);
        }
    }
}