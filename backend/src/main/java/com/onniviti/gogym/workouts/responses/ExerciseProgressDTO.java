package com.onniviti.gogym.workouts.responses;

import java.time.LocalDate;

public class ExerciseProgressDTO {
    private int setsDone;
    private int repsDone;
    private int weightUsed;
    private LocalDate date;

    // Constructor
    public ExerciseProgressDTO(int setsDone, int repsDone, int weightUsed, LocalDate date) {
        this.setsDone = setsDone;
        this.repsDone = repsDone;
        this.weightUsed = weightUsed;
        this.date = date;
    }

    // Getters and setters
    public int getSetsDone() { return setsDone; }
    public void setSetsDone(int setsDone) { this.setsDone = setsDone; }

    public int getRepsDone() { return repsDone; }
    public void setRepsDone(int repsDone) { this.repsDone = repsDone; }

    public int getWeightUsed() { return weightUsed; }
    public void setWeightUsed(int weightUsed) { this.weightUsed = weightUsed; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}
