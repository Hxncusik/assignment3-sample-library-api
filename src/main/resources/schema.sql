-- 1. Создаем базу (если её нет) и выбираем её
CREATE DATABASE IF NOT EXISTS fitness_tracker;
USE fitness_tracker;

-- 2. Удаляем старые таблицы, если они были (чтобы пересоздать чисто)
DROP TABLE IF EXISTS exercises;
DROP TABLE IF EXISTS workouts;

-- 3. Создаем таблицу тренировок
CREATE TABLE workouts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    type VARCHAR(20) NOT NULL,
    duration_minutes INT NOT NULL,
    distance_km DOUBLE,
    reps INT,
    sets INT
);

-- 4. Создаем таблицу упражнений
CREATE TABLE exercises (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    workout_id INT,
    FOREIGN KEY (workout_id) REFERENCES workouts(id) ON DELETE CASCADE
);