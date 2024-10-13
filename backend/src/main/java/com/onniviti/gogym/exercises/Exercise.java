package com.onniviti.gogym.exercises;

import com.onniviti.gogym.workouts.WorkoutExercise;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String category;
    private List<String> muscles;
    private String equipment;
    private String difficulty;
    private String instructions;

    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WorkoutExercise> workoutExercises;

    // No-argument constructor (required by JPA)
    public Exercise() {}

    // Parameterized constructor for easier instance creation
    public Exercise(String name, String category, List<String> muscles, String equipment, String difficulty, String instructions) {
        this.name = name;
        this.category = category;
        this.muscles = muscles;
        this.equipment = equipment;
        this.difficulty = difficulty;
        this.instructions = instructions;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public List<String> getMuscles() { return muscles; }
    public void setMuscles(List<String> muscles) { this.muscles = muscles; }

    public String getEquipment() { return equipment; }
    public void setEquipment(String equipment) { this.equipment = equipment; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }

    public List<WorkoutExercise> getWorkoutExercises() {
        return workoutExercises;
    }

    public void setWorkoutExercises(List<WorkoutExercise> workoutExercises) {
        this.workoutExercises = workoutExercises;
    }
}
