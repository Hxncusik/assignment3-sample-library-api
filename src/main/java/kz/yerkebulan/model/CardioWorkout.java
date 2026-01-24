package kz.yerkebulan.model;

public class CardioWorkout extends Workout {
    private double distanceKm;

    public CardioWorkout(int id, String name, String description, int durationMinutes, double distanceKm) {
        super(id, name, description, durationMinutes);
        setDistanceKm(distanceKm);
    }

    public double getDistanceKm() { return distanceKm; }

    public void setDistanceKm(double distanceKm) {
        if (distanceKm < 0) throw new IllegalArgumentException("Distance cannot be negative");
        this.distanceKm = distanceKm;
    }

    @Override
    public int calculateCaloriesBurned() {
        // Простая формула: 8 калорий в минуту + 50 калорий за км
        return (int) ((durationMinutes * 8) + (distanceKm * 50));
    }

    @Override
    public String getType() {
        return "CARDIO";
    }

    @Override
    public String getFullInfo() {
        return String.format("[Cardio] %s: %.1f km in %d mins (Burned: %d kcal)",
                name, distanceKm, durationMinutes, calculateCaloriesBurned());
    }
}