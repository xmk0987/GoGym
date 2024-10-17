package com.onniviti.gogym.workoutProgress.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onniviti.gogym.workouts.models.WorkoutTemplate;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class WorkoutProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "workout_id")
    @JsonIgnore
    private WorkoutTemplate workout;

    private boolean completed;
    private LocalDate date;

    public WorkoutProgress(WorkoutTemplate workoutTemplate, boolean completed, LocalDate date) {
        this.workout = workoutTemplate;
        this.completed = completed;
        this.date = date;
    }

    public WorkoutProgress() {
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WorkoutTemplate getWorkout() {
        return workout;
    }

    public void setWorkout(WorkoutTemplate workout) {
        this.workout = workout;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

}
