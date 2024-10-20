package com.onniviti.gogym.workouts.requests;

import com.onniviti.gogym.workouts.models.WorkoutTemplate;

public class UpdateWorkoutRequest {
    private Long id;
    private String name;
    private String timeOfWorkout;
    private WorkoutTemplate.DayOfWorkout dayOfWorkout;
    private Long userId;

    public UpdateWorkoutRequest(Long id, String name, String timeOfWorkout, WorkoutTemplate.DayOfWorkout dayOfWorkout, Long userId) {
        this.id = id;
        this.name = name;
        this.timeOfWorkout = timeOfWorkout;
        this.dayOfWorkout = dayOfWorkout;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimeOfWorkout() {
        return timeOfWorkout;
    }

    public void setTimeOfWorkout(String timeOfWorkout) {
        this.timeOfWorkout = timeOfWorkout;
    }

    public WorkoutTemplate.DayOfWorkout getDayOfWorkout() {
        return dayOfWorkout;
    }

    public void setDayOfWorkout(WorkoutTemplate.DayOfWorkout dayOfWorkout) {
        this.dayOfWorkout = dayOfWorkout;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
