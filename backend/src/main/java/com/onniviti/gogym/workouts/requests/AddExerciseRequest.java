package com.onniviti.gogym.workouts.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddExerciseRequest {
    private Long exerciseId; // ID of the exercise being added
    private int sets;
    private int reps;
    private int weight;

    // Getters and Setters

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


}
