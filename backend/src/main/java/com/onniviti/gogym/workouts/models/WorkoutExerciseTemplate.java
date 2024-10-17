package com.onniviti.gogym.workouts.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onniviti.gogym.exercises.ExerciseTemplate;
import jakarta.persistence.*;

@Entity
public class WorkoutExerciseTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "workout_id")
    @JsonIgnore
    private WorkoutTemplate workout;

    @ManyToOne
    @JoinColumn(name = "exercise_id")
    private ExerciseTemplate exercise;

    private int sets;
    private int reps;
    private int weight;
    private boolean isFailure;

    // Constructors
    public WorkoutExerciseTemplate() {

    }

    public WorkoutExerciseTemplate(WorkoutTemplate workout, ExerciseTemplate exercise, int sets, int reps, int weight, boolean isFailure) {
        this.workout = workout;
        this.exercise = exercise;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.isFailure = isFailure;
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

    public void setFailure(boolean failure) {
        isFailure = failure;
    }
}
