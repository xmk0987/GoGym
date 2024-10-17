package com.onniviti.gogym.workoutProgress.models;

import com.onniviti.gogym.workouts.models.WorkoutExerciseTemplate;
import jakarta.persistence.*;

@Entity
public class WorkoutExerciseProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "exercise_template_id")
    private WorkoutExerciseTemplate workoutExerciseTemplate;

    @ManyToOne
    @JoinColumn(name = "workout_progress_id")
    private WorkoutProgress workoutProgress;

    private int setsDone;
    private int repsDone;
    private int weightUsed;

    public WorkoutExerciseProgress() {
    }

    public WorkoutExerciseProgress(WorkoutExerciseTemplate workoutExerciseTemplate, WorkoutProgress workoutProgress, int setsDone, int repsDone, int weightUsed) {
        this.workoutExerciseTemplate = workoutExerciseTemplate;
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

    public WorkoutExerciseTemplate getWorkoutExerciseTemplate() {
        return workoutExerciseTemplate;
    }

    public void setWorkoutExerciseTemplate(WorkoutExerciseTemplate workoutExerciseTemplate) {
        this.workoutExerciseTemplate = workoutExerciseTemplate;
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
