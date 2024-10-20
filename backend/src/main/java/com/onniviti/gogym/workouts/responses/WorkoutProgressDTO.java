package com.onniviti.gogym.workouts.responses;

import java.time.LocalDate;

public class WorkoutProgressDTO {
    private LocalDate date;
    private boolean completed;

    // Constructor
    public WorkoutProgressDTO(LocalDate date, boolean completed) {
        this.date = date;
        this.completed = completed;
    }

    // Getters and setters
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}
