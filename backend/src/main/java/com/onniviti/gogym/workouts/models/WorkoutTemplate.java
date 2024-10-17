package com.onniviti.gogym.workouts.models;

import com.onniviti.gogym.workoutProgress.models.WorkoutProgress;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class WorkoutTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")  // Maps to "id" in the database
    private Long id;

    @Column(name = "user_id")  // Maps to "user_id" in the database
    private Long userId;

    @Column(name = "name")  // Maps to "name" in the database (same name)
    private String name;

   @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   private List<WorkoutExerciseTemplate> workoutExercises;

    @OneToOne(mappedBy ="workout", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private WorkoutProgress progress;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_workout")  // Maps to "day_of_workout" in the database
    private DayOfWorkout dayOfWorkout;

    @Column(name = "time_of_workout")  // Maps to "time_of_workout" in the database
    private String timeOfWorkout;

    public enum DayOfWorkout {
        Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday, Other;
    }

    // No-argument constructor (required by JPA)
    public WorkoutTemplate() {}

    // Parameterized constructor
    public WorkoutTemplate(Long userId, String name, DayOfWorkout dayOfWorkout, String timeOfWorkout) {
        this.userId = userId;
        this.name = name;
        this.dayOfWorkout = dayOfWorkout;
        this.timeOfWorkout = timeOfWorkout;
        this.workoutExercises = new ArrayList<>();
    }

    // Getters and Setters

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

      public List<WorkoutExerciseTemplate> getWorkoutExercises() {
           return workoutExercises;
       }

    public void setWorkoutExercises(List<WorkoutExerciseTemplate> workoutExercises) {
        this.workoutExercises = workoutExercises;
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

    public WorkoutProgress getProgress() {
        return progress;
    }

    public void setProgress(WorkoutProgress progress) {
        this.progress = progress;
    }
}
