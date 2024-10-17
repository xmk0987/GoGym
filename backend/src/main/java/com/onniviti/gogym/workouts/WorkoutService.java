package com.onniviti.gogym.workouts;

import com.onniviti.gogym.exercises.ExerciseRepository;
import com.onniviti.gogym.exercises.ExerciseTemplate;
import com.onniviti.gogym.workoutProgress.WorkoutProgressService;
import com.onniviti.gogym.workoutProgress.models.WorkoutProgress;
import com.onniviti.gogym.workoutProgress.repository.WorkoutProgressRepository;
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
    private final WorkoutProgressRepository workoutProgressRepository;

    @Autowired
    public WorkoutService(WorkoutRepository workoutRepository, ExerciseRepository exerciseRepository, WorkoutExerciseRepository workoutExerciseRepository, WorkoutProgressService workoutProgressService, WorkoutProgressRepository workoutProgressRepository) {
        this.workoutRepository = workoutRepository;
        this.exerciseRepository = exerciseRepository;
        this.workoutExerciseRepository = workoutExerciseRepository;
        this.workoutProgressService = workoutProgressService;
        this.workoutProgressRepository = workoutProgressRepository;
    }

    public WorkoutTemplate saveWorkout(WorkoutTemplate workout) {
        // Save the workout
        WorkoutTemplate savedWorkout = workoutRepository.save(workout);

        // Create progress for the saved workout
        WorkoutProgress createdProgress = createProgress(savedWorkout);

        // Set the created progress to the saved workout
        savedWorkout.setProgress(createdProgress);

        // Return the workout with the progress set
        return savedWorkout;
    }

    public WorkoutProgress createProgress(WorkoutTemplate workout) {
        LocalDate today = LocalDate.now();
        DayOfWeek todayDayOfWeek = today.getDayOfWeek();
        DayOfWeek workoutDayOfWeek = DayOfWeek.valueOf(workout.getDayOfWorkout().toString().toUpperCase());

        // Calculate the number of days until the workout
        int daysUntilWorkout = (workoutDayOfWeek.getValue() - todayDayOfWeek.getValue() + 7) % 7;

        // If the workout day is today, set the workoutDate to today
        LocalDate workoutDate = today.plusDays(daysUntilWorkout);

        if (daysUntilWorkout == 0) {
            System.out.println("Creating progress for today: " + today);
        } else {
            System.out.println("Creating progress for " + workoutDayOfWeek + " (Date: " + workoutDate + ")");
        }

        // Create progress for the calculated workout date (either today or a future date)
        return workoutProgressService.createWorkoutProgressForDate(workout, workoutDate);
    }

    public List<WorkoutTemplate> getWorkouts(Long userId) {
        List<WorkoutTemplate> workouts = workoutRepository.findByUserId(userId);

        for (WorkoutTemplate workout : workouts) {
            WorkoutProgress progress = workoutProgressRepository.findLatestProgressByWorkout(workout);
            System.out.println("Progress found in get workouts");
            System.out.println(progress.getDate());
            workout.setProgress(progress);
        }

        System.out.println(workouts);
        return workouts;
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

        WorkoutProgress progress = workoutProgressRepository.findLatestProgressByWorkout(workout);
        if (progress != null) {
            workout.setProgress(progress);
            System.out.println("Progress found and set: " + progress.getDate());
        } else {
            System.out.println("No progress found for workout: " + workout.getId());
        }

        return workout;
    }


}
