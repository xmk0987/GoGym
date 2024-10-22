package com.onniviti.gogym.workoutProgress.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onniviti.gogym.workouts.models.WorkoutExerciseTemplate;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class WorkoutExerciseProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "exercise_template_id")
    private WorkoutExerciseTemplate exerciseTemplate;

    @ManyToOne
    @JoinColumn(name = "workout_progress_id")
    @JsonIgnore
    private WorkoutProgress workoutProgress;

    @Column(name = "user_id")
    private Long userId;

    private int setsDone;
    private int weightUsed;
    private LocalDate date;

    // Constructors, Getters, and Setters
    public WorkoutExerciseProgress() {}

    public WorkoutExerciseProgress(WorkoutExerciseTemplate exerciseTemplate, WorkoutProgress workoutProgress, Long userId, int setsDone, int weightUsed, LocalDate date) {
        this.exerciseTemplate = exerciseTemplate;
        this.workoutProgress = workoutProgress;
        this.userId = userId;
        this.setsDone = setsDone;
        this.weightUsed = weightUsed;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WorkoutExerciseTemplate getExerciseTemplate() {
        return exerciseTemplate;
    }

    public void setExerciseTemplate(WorkoutExerciseTemplate exerciseTemplate) {
        this.exerciseTemplate = exerciseTemplate;
    }

    public WorkoutProgress getWorkoutProgress() {
        return workoutProgress;
    }

    public void setWorkoutProgress(WorkoutProgress workoutProgress) {
        this.workoutProgress = workoutProgress;
    }

    public int getSetsDone() {
        return setsDone;
    }

    public void setSetsDone(int setsDone) {
        this.setsDone = setsDone;
    }


    public int getWeightUsed() {
        return weightUsed;
    }

    public void setWeightUsed(int weightUsed) {
        this.weightUsed = weightUsed;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
