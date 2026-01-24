-- Очистка (для тестов)
DROP TABLE IF EXISTS exercises;
DROP TABLE IF EXISTS workouts;

-- Таблица тренировок (Single Table Inheritance)
CREATE TABLE workouts (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    type VARCHAR(20) NOT NULL, -- 'CARDIO' или 'STRENGTH'
    duration_minutes INT NOT NULL,
    -- Поля для Cardio
    distance_km DOUBLE PRECISION,
    -- Поля для Strength
    reps INT,
    sets INT
);

-- Таблица упражнений
CREATE TABLE exercises (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    workout_id INT REFERENCES workouts(id) ON DELETE CASCADE
);