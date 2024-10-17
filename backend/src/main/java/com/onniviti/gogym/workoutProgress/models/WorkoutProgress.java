package com.onniviti.gogym.workoutProgress.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onniviti.gogym.workouts.models.WorkoutTemplate;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
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

    @OneToMany(mappedBy = "workoutProgress", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WorkoutExerciseProgress> exercises;  // This is correctly named 'exercises'

    private boolean completed;
    private LocalDate date;

    @Column(name = "user_id")
    private Long userId;

    public WorkoutProgress(WorkoutTemplate workout, boolean completed, LocalDate date) {
        this.workout = workout;
        this.completed = completed;
        this.date = date;
        this.exercises = new ArrayList<>();
    }

    public WorkoutProgress() {}

    // Getters and setters
    public List<WorkoutExerciseProgress> getExercises() {
        return exercises;
    }

    public void setExercises(List<WorkoutExerciseProgress> exercises) {
        this.exercises = exercises;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

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
