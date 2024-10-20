package com.onniviti.gogym.workouts.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onniviti.gogym.exercises.ExerciseTemplate;
import com.onniviti.gogym.workoutProgress.models.WorkoutExerciseProgress;
import com.onniviti.gogym.workoutProgress.models.WorkoutProgress;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class WorkoutExerciseTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "workout_id")
    @JsonIgnore
    private WorkoutTemplate workoutTemplate;

    @ManyToOne
    @JoinColumn(name = "exercise_id")
    private ExerciseTemplate exercise;

    private int sets;
    private int reps;
    private int weight;
    private boolean isFailure;

    @OneToMany(mappedBy = "exerciseTemplate", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WorkoutExerciseProgress> exerciseProgressList = new ArrayList<>();

    // Constructors, Getters, and Setters
    public WorkoutExerciseTemplate() {}

    public WorkoutExerciseTemplate(WorkoutTemplate workoutTemplate, ExerciseTemplate exercise, int sets, int reps, int weight, boolean isFailure) {
        this.workoutTemplate = workoutTemplate;
        this.exercise = exercise;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.isFailure = isFailure;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WorkoutTemplate getWorkoutTemplate() {  // Fix the return type to WorkoutTemplate
        return workoutTemplate;
    }

    public void setWorkoutTemplate(WorkoutTemplate workoutTemplate) {
        this.workoutTemplate = workoutTemplate;
    }

    public ExerciseTemplate getExercise() {
        return exercise;
    }

    public void setExercise(ExerciseTemplate exercise) {
        this.exercise = exercise;
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

    public boolean isFailure() {
        return isFailure;
    }

    public void setFailure(boolean isFailure) {
        this.isFailure = isFailure;
    }

    public List<WorkoutExerciseProgress> getExerciseProgressList() {
        return exerciseProgressList;
    }

    public void setExerciseProgressList(List<WorkoutExerciseProgress> exerciseProgressList) {
        this.exerciseProgressList = exerciseProgressList;
    }
}
