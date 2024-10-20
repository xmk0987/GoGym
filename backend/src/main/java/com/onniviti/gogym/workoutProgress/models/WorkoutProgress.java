package com.onniviti.gogym.workoutProgress.models;

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
    @JoinColumn(name = "workout_template_id")
    private WorkoutTemplate workoutTemplate;

    private LocalDate date;
    private boolean completed;

    @OneToMany(mappedBy = "workoutProgress", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WorkoutExerciseProgress> exerciseProgressList = new ArrayList<>();

    // Constructors, Getters, and Setters
    public WorkoutProgress() {}

    public WorkoutProgress(WorkoutTemplate workoutTemplate, LocalDate date) {
        this.workoutTemplate = workoutTemplate;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WorkoutTemplate getWorkoutTemplate() {
        return workoutTemplate;
    }

    public void setWorkoutTemplate(WorkoutTemplate workoutTemplate) {
        this.workoutTemplate = workoutTemplate;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<WorkoutExerciseProgress> getExerciseProgressList() {
        return exerciseProgressList;
    }

    public void setExerciseProgressList(List<WorkoutExerciseProgress> exerciseProgressList) {
        this.exerciseProgressList = exerciseProgressList;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
