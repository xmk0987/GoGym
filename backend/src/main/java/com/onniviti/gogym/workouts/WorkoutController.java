package com.onniviti.gogym.workouts;

import com.onniviti.gogym.workoutProgress.models.WorkoutExerciseProgress;
import com.onniviti.gogym.workouts.models.WorkoutExerciseTemplate;
import com.onniviti.gogym.workouts.models.WorkoutTemplate;
import com.onniviti.gogym.workouts.requests.AddExerciseRequest;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {

    private final WorkoutService workoutService;

    @Autowired
    public WorkoutController (WorkoutService workoutService) {
        this.workoutService = workoutService;
    }


    @PostMapping()
    public WorkoutTemplate createWorkout(@RequestBody WorkoutTemplate workout) {
        return workoutService.saveWorkout(workout);
    }

    @GetMapping("/{userId}")
    public List<WorkoutTemplate> getWorkouts(@PathVariable Long userId) {
        System.out.println("Got to workout controleler");
        return workoutService.getWorkouts(userId);
    }

    @GetMapping("/{userId}/{workoutId}")
    public WorkoutTemplate getWorkout(@PathVariable Long userId, @PathVariable Long workoutId) {
        return workoutService.getWorkout(userId , workoutId);
    }

    @PutMapping()
    public WorkoutTemplate updateWorkout(@RequestBody WorkoutTemplate workout) {
        return workoutService.updateWorkout(workout.getId(), workout);
    }

    @PostMapping("/{workoutId}/exercise")
    public ResponseEntity<WorkoutExerciseProgress> addExerciseToWorkout(
            @PathVariable Long workoutId,
            @RequestBody AddExerciseRequest addExerciseRequest
    ) {
        WorkoutExerciseProgress updatedWorkout = workoutService.addExerciseToWorkout(
                workoutId,
                addExerciseRequest.getExerciseId(),
                addExerciseRequest.getSets(),
                addExerciseRequest.getReps(),
                addExerciseRequest.getWeight(),
                addExerciseRequest.getIsFailure()
        );
        return ResponseEntity.ok(updatedWorkout);
    }
}
