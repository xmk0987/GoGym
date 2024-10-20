package com.onniviti.gogym.workouts.models;

import com.onniviti.gogym.workoutProgress.models.WorkoutProgress;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class WorkoutTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    private String name;

    @Enumerated(EnumType.STRING)
    private DayOfWorkout dayOfWorkout;

    private String timeOfWorkout;

    @OneToMany(mappedBy = "workoutTemplate", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WorkoutExerciseTemplate> exerciseTemplates = new ArrayList<>();

    @OneToMany(mappedBy = "workoutTemplate", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WorkoutProgress> workoutProgressList = new ArrayList<>();

    public enum DayOfWorkout {
        Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday, Other;
    }


    // Constructors, Getters, and Setters
    public WorkoutTemplate() {}

    public WorkoutTemplate(Long userId, String name, DayOfWorkout dayOfWorkout, String timeOfWorkout) {
        this.userId = userId;
        this.name = name;
        this.dayOfWorkout = dayOfWorkout;
        this.timeOfWorkout = timeOfWorkout;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DayOfWorkout getDayOfWorkout() {
        return dayOfWorkout;
    }

    public void setDayOfWorkout(DayOfWorkout dayOfWorkout) {
        this.dayOfWorkout = dayOfWorkout;
    }

    public String getTimeOfWorkout() {
        return timeOfWorkout;
    }

    public void setTimeOfWorkout(String timeOfWorkout) {
        this.timeOfWorkout = timeOfWorkout;
    }

    public List<WorkoutExerciseTemplate> getExerciseTemplates() {
        return exerciseTemplates;
    }

}
