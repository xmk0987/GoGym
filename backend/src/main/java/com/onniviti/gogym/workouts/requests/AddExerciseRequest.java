package com.onniviti.gogym.workouts.requests;

public class AddExerciseRequest {
    private Long exerciseId;
    private int sets;
    private int reps;
    private int weight;
    private boolean isFailure;

    // Getters and setters
    public Long getExerciseId() {
        return exerciseId;
    }

    public int getSets() {
        return sets;
    }

    public int getReps() {
        return reps;
    }

    public int getWeight() {
        return weight;
    }

    public boolean getIsFailure() {
        return isFailure;
    }
}