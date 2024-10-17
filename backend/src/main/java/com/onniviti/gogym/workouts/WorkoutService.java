package com.onniviti.gogym.workouts;

import com.onniviti.gogym.exercises.ExerciseRepository;
import com.onniviti.gogym.exercises.ExerciseTemplate;
import com.onniviti.gogym.workoutProgress.WorkoutProgressService;
import com.onniviti.gogym.workouts.models.WorkoutExerciseTemplate;
import com.onniviti.gogym.workouts.models.WorkoutTemplate;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;
    private final WorkoutExerciseRepository workoutExerciseRepository;
    private final WorkoutProgressService workoutProgressService;

    @Autowired
    public WorkoutService(WorkoutRepository workoutRepository, ExerciseRepository exerciseRepository, WorkoutExerciseRepository workoutExerciseRepository, WorkoutProgressService workoutProgressService) {
        this.workoutRepository = workoutRepository;
        this.exerciseRepository = exerciseRepository;
        this.workoutExerciseRepository = workoutExerciseRepository;
        this.workoutProgressService = workoutProgressService;
    }

    public WorkoutTemplate saveWorkout(WorkoutTemplate workout) {
        WorkoutTemplate savedWorkout = workoutRepository.save(workout);

        createProgress(savedWorkout);

        return savedWorkout;
    }

    public void createProgress (WorkoutTemplate workout) {
        String today = LocalDate.now().getDayOfWeek().toString();
        if (today.equalsIgnoreCase(workout.getDayOfWorkout().toString())) {
            workoutProgressService.createWorkoutProgress(workout);
        }
    }

    public List<WorkoutTemplate> getWorkouts(Long userId) {
        return workoutRepository.findByUserId(userId);
    }

    public WorkoutTemplate updateWorkout(Long id, WorkoutTemplate updatedWorkout) {
        WorkoutTemplate existingWorkout = workoutRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Workout not found"));

        existingWorkout.setName(updatedWorkout.getName());
        existingWorkout.setDayOfWorkout(updatedWorkout.getDayOfWorkout());
        existingWorkout.setTimeOfWorkout(updatedWorkout.getTimeOfWorkout());

        return workoutRepository.save(existingWorkout);
    }

    @Transactional
    public WorkoutExerciseTemplate addExerciseToWorkout(Long workoutId, Long exerciseId, int sets, int reps, int weight, boolean isFailure) {
        // Fetch the workout and exercise or throw an exception if not found
        WorkoutTemplate workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new IllegalArgumentException("Workout not found"));
        ExerciseTemplate exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new IllegalArgumentException("Exercise not found"));

        // Create and save the new WorkoutExerciseTemplate
        WorkoutExerciseTemplate workoutExerciseTemplate = new WorkoutExerciseTemplate(workout, exercise, sets, reps, weight, isFailure);
        WorkoutExerciseTemplate savedWorkoutExercise = workoutExerciseRepository.save(workoutExerciseTemplate);

        // Update the workoutExercises list in the WorkoutTemplate
        if (workout.getWorkoutExercises() == null) {
            workout.setWorkoutExercises(new ArrayList<>());
        }
        workout.getWorkoutExercises().add(savedWorkoutExercise);

        // Save the WorkoutTemplate to ensure the exercises are properly linked
        workoutRepository.save(workout);

        // Create progress for this new exercise in all existing workout progress entries
        workoutProgressService.createExerciseProgressForExistingWorkout(savedWorkoutExercise);

        return savedWorkoutExercise;
    }


    public WorkoutTemplate getWorkout(Long userId, Long workoutId) {
        WorkoutTemplate workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new IllegalArgumentException("Workout not found"));

        if (!Objects.equals(workout.getUserId(), userId)) {
            throw new IllegalStateException("Not your workout");
        }

        return workout;
    }

}
