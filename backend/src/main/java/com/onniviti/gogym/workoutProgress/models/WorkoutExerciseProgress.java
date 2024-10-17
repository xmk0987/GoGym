package com.onniviti.gogym.workoutProgress.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onniviti.gogym.workouts.models.WorkoutExerciseTemplate;
import jakarta.persistence.*;


@Entity
public class WorkoutExerciseProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "exercise_template_id")
    private WorkoutExerciseTemplate exercise;  // Renamed to 'exercise' to match usage

    @ManyToOne
    @JoinColumn(name = "workout_progress_id")
    @JsonIgnore
    private WorkoutProgress workoutProgress;

    private int setsDone;
    private int repsDone;
    private int weightUsed;

    // Constructors
    public WorkoutExerciseProgress() {}

    public WorkoutExerciseProgress(WorkoutExerciseTemplate exercise, WorkoutProgress workoutProgress, int setsDone, int repsDone, int weightUsed) {
        this.exercise = exercise;
        this.workoutProgress = workoutProgress;
        this.setsDone = setsDone;
        this.repsDone = repsDone;
        this.weightUsed = weightUsed;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WorkoutExerciseTemplate getExercise() {
        return exercise;
    }

    public void setExercise(WorkoutExerciseTemplate exercise) {
        this.exercise = exercise;
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

    public int getRepsDone() {
        return repsDone;
    }

    public void setRepsDone(int repsDone) {
        this.repsDone = repsDone;
    }

    public int getWeightUsed() {
        return weightUsed;
    }

    public void setWeightUsed(int weightUsed) {
        this.weightUsed = weightUsed;
    }
}
