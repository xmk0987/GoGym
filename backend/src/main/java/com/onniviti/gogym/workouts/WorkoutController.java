package com.onniviti.gogym.workouts;

import com.onniviti.gogym.workouts.requests.AddExerciseRequest;
import com.onniviti.gogym.workouts.requests.CreateWorkoutRequest;
import com.onniviti.gogym.workouts.requests.UpdateWorkoutRequest;
import com.onniviti.gogym.workouts.responses.WorkoutDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public WorkoutDTO createWorkout(@RequestBody CreateWorkoutRequest workout) {
        return workoutService.createWorkout(workout);
    }

    @GetMapping("/{userId}")
    public List<WorkoutDTO> getWorkouts(@PathVariable Long userId) {
        return workoutService.getWorkoutsWithProgress(userId);
    }

    @GetMapping("/{userId}/{workoutId}")
    public WorkoutDTO getWorkout(@PathVariable Long userId, @PathVariable Long workoutId) {
        return workoutService.getWorkoutWithProgress(userId, workoutId);
    }

    @PutMapping("/{userId}/{workoutId}")
    public WorkoutDTO updateWorkout(@PathVariable Long userId, @PathVariable Long workoutId, @RequestBody UpdateWorkoutRequest request) {
        return workoutService.updateWorkout(userId, workoutId, request );
    }

    @DeleteMapping("/{userId}/{workoutId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWorkout(@PathVariable Long userId, @PathVariable Long workoutId) {
        workoutService.deleteWorkout(userId, workoutId);
    }


    @PostMapping("/{userId}/{workoutId}/exercise")
    public WorkoutDTO addExerciseToWorkout(
            @PathVariable Long workoutId,
            @PathVariable Long userId,
            @RequestBody AddExerciseRequest addExerciseRequest
    ) {
        return workoutService.addExerciseToWorkout(userId, workoutId, addExerciseRequest);
    }


    @PutMapping("/progress/{exerciseId}/{increase}")
    public void updateExerciseProgressSets(@PathVariable Long exerciseId,@PathVariable String increase) {
        workoutService.updateExerciseProgressSets(exerciseId, increase);
    }

}
