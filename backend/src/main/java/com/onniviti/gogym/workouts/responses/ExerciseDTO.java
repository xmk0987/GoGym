package com.onniviti.gogym.workouts.responses;

import com.onniviti.gogym.exercises.ExerciseTemplate;

public class ExerciseDTO {
    private Long id;
    private ExerciseTemplate exercise;
    private int sets;
    private int reps;
    private int weight;
    private boolean isFailure;
    private ExerciseProgressDTO progress;

    // Constructor
    public ExerciseDTO(Long id, ExerciseTemplate exercise, int sets, int reps, int weight, boolean isFailure, ExerciseProgressDTO progress) {
        this.id = id;
        this.exercise = exercise;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.isFailure = isFailure;
        this.progress = progress;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public ExerciseTemplate getExercise() { return exercise; }
    public void setExercise(ExerciseTemplate exercise) { this.exercise = exercise; }

    public int getSets() { return sets; }
    public void setSets(int sets) { this.sets = sets; }

    public int getReps() { return reps; }
    public void setReps(int reps) { this.reps = reps; }

    public int getWeight() { return weight; }
    public void setWeight(int weight) { this.weight = weight; }

    public boolean isFailure() { return isFailure; }
    public void setFailure(boolean isFailure) { this.isFailure = isFailure; }

    public ExerciseProgressDTO getProgress() { return progress; }
    public void setProgress(ExerciseProgressDTO progress) { this.progress = progress; }
}
