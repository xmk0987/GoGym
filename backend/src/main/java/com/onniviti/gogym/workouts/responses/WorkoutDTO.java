package com.onniviti.gogym.workouts.responses;

import java.util.List;

public class WorkoutDTO {
    private Long id;
    private Long userId;
    private String name;
    private String dayOfWorkout;
    private String timeOfWorkout;
    private List<ExerciseDTO> exercises;
    private WorkoutProgressDTO progress;

    // Constructor
    public WorkoutDTO(Long id, Long userId, String name, String dayOfWorkout, String timeOfWorkout, List<ExerciseDTO> exercises, WorkoutProgressDTO progress) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.dayOfWorkout = dayOfWorkout;
        this.timeOfWorkout = timeOfWorkout;
        this.exercises = exercises;
        this.progress = progress;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDayOfWorkout() { return dayOfWorkout; }
    public void setDayOfWorkout(String dayOfWorkout) { this.dayOfWorkout = dayOfWorkout; }

    public String getTimeOfWorkout() { return timeOfWorkout; }
    public void setTimeOfWorkout(String timeOfWorkout) { this.timeOfWorkout = timeOfWorkout; }

    public List<ExerciseDTO> getExercises() { return exercises; }
    public void setExercises(List<ExerciseDTO> exercises) { this.exercises = exercises; }

    public WorkoutProgressDTO getProgress() { return progress; }
    public void setProgress(WorkoutProgressDTO progress) { this.progress = progress; }
}
