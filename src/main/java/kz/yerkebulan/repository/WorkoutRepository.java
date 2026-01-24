package kz.yerkebulan.repository;

import kz.yerkebulan.db.DatabaseConnection;
import kz.yerkebulan.exception.DatabaseOperationException;
import kz.yerkebulan.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkoutRepository implements Repository<Workout> {

    private final DatabaseConnection dbConnection;

    public WorkoutRepository(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public void create(Workout workout) {
        String sql = "INSERT INTO workouts (name, description, type, duration_minutes, distance_km, reps, sets) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, workout.getName());
            stmt.setString(2, workout.getDescription());
            stmt.setInt(4, workout.getDurationMinutes());

            if (workout instanceof CardioWorkout) {
                stmt.setString(3, "CARDIO");
                stmt.setDouble(5, ((CardioWorkout) workout).getDistanceKm());
                stmt.setObject(6, null);
                stmt.setObject(7, null);
            } else if (workout instanceof StrengthWorkout) {
                stmt.setString(3, "STRENGTH");
                stmt.setObject(5, null);
                stmt.setInt(6, ((StrengthWorkout) workout).getReps());
                stmt.setInt(7, ((StrengthWorkout) workout).getSets());
            }

            stmt.executeUpdate();

            // Получаем сгенерированный ID
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    workout.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error creating workout", e);
        }
    }

    @Override
    public Workout findById(int id) {
        String sql = "SELECT * FROM workouts WHERE id = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return mapRowToWorkout(rs);
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error finding workout by ID", e);
        }
        return null;
    }

    @Override
    public List<Workout> findAll() {
        List<Workout> workouts = new ArrayList<>();
        String sql = "SELECT * FROM workouts ORDER BY id";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) workouts.add(mapRowToWorkout(rs));
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error fetching all workouts", e);
        }
        return workouts;
    }

    @Override
    public void update(int id, Workout workout) {
        String sql = "UPDATE workouts SET name=?, description=?, duration_minutes=?, distance_km=?, reps=?, sets=? WHERE id=?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, workout.getName());
            stmt.setString(2, workout.getDescription());
            stmt.setInt(3, workout.getDurationMinutes());

            if (workout instanceof CardioWorkout) {
                stmt.setDouble(4, ((CardioWorkout) workout).getDistanceKm());
                stmt.setObject(5, null);
                stmt.setObject(6, null);
            } else if (workout instanceof StrengthWorkout) {
                stmt.setObject(4, null);
                stmt.setInt(5, ((StrengthWorkout) workout).getReps());
                stmt.setInt(6, ((StrengthWorkout) workout).getSets());
            }
            stmt.setInt(7, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error updating workout", e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM workouts WHERE id = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error deleting workout", e);
        }
    }

    private Workout mapRowToWorkout(ResultSet rs) throws SQLException {
        String type = rs.getString("type");
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String desc = rs.getString("description");
        int dur = rs.getInt("duration_minutes");

        if ("CARDIO".equals(type)) {
            return new CardioWorkout(id, name, desc, dur, rs.getDouble("distance_km"));
        } else {
            return new StrengthWorkout(id, name, desc, dur, rs.getInt("reps"), rs.getInt("sets"));
        }
    }
}