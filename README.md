# Fitness Tracker API (Java + JDBC) 🏋️‍♂️

## 1. Project Overview

This project is a REST-like Console API designed to track fitness activities. It allows users to create, read, update, and delete (CRUD) different types of workouts and their associated exercises.

The project demonstrates advanced Object-Oriented Programming (OOP) principles and persistent data management using **Java JDBC** and **MySQL**. It follows a strict **N-Tier Architecture** (Controller → Service → Repository → Database).

### Key Features

* Manage different workout types (**Cardio** vs **Strength**) using polymorphism.
* Add specific **Exercises** to workouts (Composition).
* Calculate calories burned dynamically based on workout type.
* Validate data integrity (e.g., negative duration prevention).
* Custom Exception Handling for robust error management.

---

## 2. OOP Design Documentation

### A. Inheritance & Abstraction

* **Base Class:** `Workout` (Abstract). It holds common fields like `id`, `name`, `durationMinutes`.
* **Subclasses:**
* `CardioWorkout`: Adds `distanceKm`.
* `StrengthWorkout`: Adds `reps` and `sets`.


* **Abstract Methods:** `calculateCaloriesBurned()` is defined in the parent but implemented differently in each subclass.

### B. Interfaces

* `Nameable`: Ensures entities have naming capabilities.
* `Descriptable`: Ensures entities provide detailed descriptions.
* *Implemented by:* `Workout`, `Exercise`.

### C. Polymorphism

The system processes collections of `Workout` objects without knowing their specific type at compile time.

* **Example:** In `Main.java`, a list `List<Workout>` is iterated. calling `getFullInfo()` triggers specific behavior for Cardio or Strength automatically.
* **Repository Layer:** The `WorkoutRepository` reads a discriminator column (`type`) from the DB and instantiates the correct subclass (`new CardioWorkout(...)` or `new StrengthWorkout(...)`).

### D. Composition / Aggregation

* **Relationship:** A `Workout` **has-a** list of `Exercise` objects.
* **Implementation:** The `Workout` class contains `protected List<Exercise> exercises`. When fetching a workout, the service layer lazily loads related exercises.

---

## 3. Database Schema

The project uses **MySQL**.

* **Strategy:** Single Table Inheritance for Workouts.

### Tables

**1. `workouts**`
| Column | Type | Description |
| :--- | :--- | :--- |
| `id` | INT (PK) | Auto-increment ID |
| `name` | VARCHAR | Name of workout |
| `type` | VARCHAR | Discriminator: 'CARDIO' or 'STRENGTH' |
| `duration_minutes` | INT | Duration |
| `distance_km` | DOUBLE | Specific to Cardio (NULL for Strength) |
| `reps` | INT | Specific to Strength (NULL for Cardio) |
| `sets` | INT | Specific to Strength (NULL for Cardio) |

**2. `exercises**`
| Column | Type | Description |
| :--- | :--- | :--- |
| `id` | INT (PK) | Auto-increment ID |
| `name` | VARCHAR | Exercise name |
| `workout_id` | INT (FK) | Links to `workouts.id` (ON DELETE CASCADE) |

---

## 4. Project Structure

```bash
kz.yerkebulan/
├── exception/          # Custom exceptions
│   ├── InvalidInputException.java
│   └── DatabaseOperationException.java
├── model/              # Entity classes (POJOs)
│   ├── Workout.java (Abstract)
│   ├── CardioWorkout.java
│   ├── StrengthWorkout.java
│   └── Exercise.java
├── repository/         # Data Access Layer (JDBC)
│   ├── DatabaseConnection.java
│   ├── WorkoutRepository.java
│   └── ExerciseRepository.java
├── service/            # Business Logic Layer
│   └── WorkoutService.java
└── Main.java           # Controller / Entry Point

```

---

## 5. Setup & Usage

### Prerequisites

* Java JDK 17 or higher
* MySQL Server (Port 3306)
* Gradle

### Configuration

1. Create a database in MySQL:
```sql
CREATE DATABASE fitness_tracker;

```


2. Open `src/main/java/kz/yerkebulan/repository/DatabaseConnection.java`.
3. Update the `USER` and `PASS` constants with your MySQL credentials.

### How to Run

Run the `Main` class from your IDE or use Gradle:

```bash
./gradlew run

```

### Sample Output (CLI)

```text
=== Fitness Tracker API Demo ===

[1] Creating Workouts...
Created: Morning Run
Created: Chest Day

[2] Adding Exercise to Workout...

[3] Listing All Workouts (Polymorphism Demo):
------------------------------------------------
[Cardio] Morning Run: 5.5 km in 30 mins (Burned: 515 kcal)
Type: CARDIO
------------------------------------------------
[Strength] Chest Day: 4 sets x 10 reps (Burned: 380 kcal)
Type: STRENGTH
 Exercises:
  - Bench Press: 100kg bar

[5] Testing Validation Logic:
SUCCESS! CAUGHT EXPECTED ERROR: Duration must be positive

```

---

## 6. Reflection

### What I Learned

* **JDBC & Persistence:** I learned how to bridge Java objects with Relational Tables using `PreparedStatement` to prevent SQL injection.
* **Architecture:** Splitting the code into Repository, Service, and Controller made the code much cleaner and easier to debug than putting everything in one file.
* **Polymorphism in DB:** Mapping a single SQL table to multiple Java subclasses (Single Table Inheritance) was a challenging but powerful concept.

### Challenges Faced

* **Database Drivers:** Initially faced `No suitable driver found` and `SSL connection` errors. Resolved by adding the MySQL dependency in `build.gradle` and configuring the connection string parameters.
* **Connection Management:** Understanding why `Connection` should not be static to avoid "Connection closed" errors was a key learning moment.