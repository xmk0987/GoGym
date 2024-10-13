package com.onniviti.gogym.workouts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class WorkoutController {

    private final WorkoutService workoutService;

    @Autowired
    public WorkoutController (WorkoutService workoutService) {
        this.workoutService = workoutService;
    }


    @PostMapping("/workout")
    public Workout createWorkout(@RequestBody Workout workout) {
        return workoutService.saveWorkout(workout);
    }

    @GetMapping("/workouts/{userId}")
    public List<Workout> getWorkouts(@PathVariable int userId) {
        System.out.println("Arrives");
        System.out.println(userId);
        return workoutService.getWorkouts(userId);
    }

}
