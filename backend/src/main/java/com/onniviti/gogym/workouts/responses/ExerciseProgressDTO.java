package com.onniviti.gogym.workouts.responses;

import java.time.LocalDate;

public class ExerciseProgressDTO {
    private int setsDone;
    private int weightUsed;
    private LocalDate date;
    private Long id;

    // Constructor
    public ExerciseProgressDTO(int setsDone, int weightUsed, LocalDate date, Long id) {
        this.setsDone = setsDone;
        this.weightUsed = weightUsed;
        this.date = date;
        this.id = id;
    }

    // Getters and setters
    public int getSetsDone() { return setsDone; }
    public void setSetsDone(int setsDone) { this.setsDone = setsDone; }

    public int getWeightUsed() { return weightUsed; }
    public void setWeightUsed(int weightUsed) { this.weightUsed = weightUsed; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
