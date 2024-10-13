package com.onniviti.gogym.workouts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutService {

    private final WorkoutRepository workoutRepository;

    @Autowired
    public WorkoutService(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    public Workout saveWorkout(Workout workout) {
        return workoutRepository.save(workout); // Save the workout to the DB
    }

    public List<Workout> getWorkouts(int userId) {
        System.out.println("Arrives");
        System.out.println(userId);
        return workoutRepository.findByUserId(userId);
    }
}
