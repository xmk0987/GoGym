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

    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean getIsFailure() {
        return isFailure;
    }

    public void setIsFailure(boolean failure) {
        isFailure = failure;
    }
}