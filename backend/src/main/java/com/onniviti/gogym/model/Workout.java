package com.onniviti.gogym.model;

import jakarta.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "workouts")  // Map to the "workouts" table
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")  // Maps to "id" in the database
    private int id;

    @Column(name = "user_id")  // Maps to "user_id" in the database
    private int userId;

    @Column(name = "name")  // Maps to "name" in the database (same name)
    private String name;

   // @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   // private List<WorkoutExercise> workoutExercises;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_workout")  // Maps to "day_of_workout" in the database
    private DayOfWorkout dayOfWorkout;

    @Column(name = "time_of_workout")  // Maps to "time_of_workout" in the database
    private LocalTime timeOfWorkout;

    public enum DayOfWorkout {
        Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday, Other;
    }

    // No-argument constructor (required by JPA)
    public Workout() {}

    // Parameterized constructor
    public Workout(int userId, String name, List<WorkoutExercise> workoutExercises, DayOfWorkout dayOfWorkout, LocalTime timeOfWorkout) {
        this.userId = userId;
        this.name = name;
        //this.workoutExercises = workoutExercises;
        this.dayOfWorkout = dayOfWorkout;
        this.timeOfWorkout = timeOfWorkout;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

  //  public List<WorkoutExercise> getWorkoutExercises() {
    //    return workoutExercises;
   // }

   // public void setWorkoutExercises(List<WorkoutExercise> workoutExercises) {
   //     this.workoutExercises = workoutExercises;
   // }

    public DayOfWorkout getDayOfWorkout() {
        return dayOfWorkout;
    }

    public void setDayOfWorkout(DayOfWorkout dayOfWorkout) {
        this.dayOfWorkout = dayOfWorkout;
    }

    public LocalTime getTimeOfWorkout() {
        return timeOfWorkout;
    }

    public void setTimeOfWorkout(LocalTime timeOfWorkout) {
        this.timeOfWorkout = timeOfWorkout;
    }
}
