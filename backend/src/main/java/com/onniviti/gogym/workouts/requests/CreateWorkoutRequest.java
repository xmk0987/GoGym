package com.onniviti.gogym.workouts.requests;

import com.onniviti.gogym.workouts.models.WorkoutTemplate;

import java.util.List;

public class CreateWorkoutRequest {
    private String name;
    private String timeOfWorkout;
    private WorkoutTemplate.DayOfWorkout dayOfWorkout;
    private Long userId;

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
