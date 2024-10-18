package com.onniviti.gogym.workouts;

import com.onniviti.gogym.exercises.ExerciseRepository;
import com.onniviti.gogym.exercises.ExerciseTemplate;
import com.onniviti.gogym.workoutProgress.WorkoutProgressService;
import com.onniviti.gogym.workoutProgress.models.WorkoutExerciseProgress;
import com.onniviti.gogym.workoutProgress.models.WorkoutProgress;
import com.onniviti.gogym.workoutProgress.repository.WorkoutExerciseProgressRepository;
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
    private final WorkoutExerciseProgressRepository workoutExerciseProgressRepository;

    @Autowired
    public WorkoutService(WorkoutRepository workoutRepository, ExerciseRepository exerciseRepository, WorkoutExerciseRepository workoutExerciseRepository, WorkoutProgressService workoutProgressService, WorkoutProgressRepository workoutProgressRepository, WorkoutExerciseProgressRepository workoutExerciseProgressRepository) {
        this.workoutRepository = workoutRepository;
        this.exerciseRepository = exerciseRepository;
        this.workoutExerciseRepository = workoutExerciseRepository;
        this.workoutProgressService = workoutProgressService;
        this.workoutProgressRepository = workoutProgressRepository;
        this.workoutExerciseProgressRepository = workoutExerciseProgressRepository;
    }

    // Method to get exercise progress for a workout based on the workout and date
    public List<WorkoutExerciseProgress> getExerciseProgressByDate(WorkoutTemplate workout, LocalDate date) {
        return workoutExerciseProgressRepository.findByWorkoutTemplateAndDate(workout, date);
    }

    public WorkoutTemplate saveWorkout(WorkoutTemplate workout) {
        // Save the workout
        WorkoutTemplate savedWorkout = workoutRepository.save(workout);

        LocalDate workoutDate = getWorkoutDate(workout);

        // Create progress for the saved workout
        WorkoutProgress createdProgress =  workoutProgressService.createWorkoutProgressForDate(savedWorkout, workoutDate);
        // Set the created progress to the saved workout
        savedWorkout.setProgress(createdProgress);

        // Return the workout with the progress set
        return savedWorkout;
    }

    public List<WorkoutTemplate> getWorkouts(Long userId) {
        List<WorkoutTemplate> workouts = workoutRepository.findByUserId(userId);

        for (WorkoutTemplate workout : workouts) {
            WorkoutProgress progress = workoutProgressRepository.findLatestProgressByWorkout(workout);

            // Set the latest progress to the workout
            if (progress != null) {
                workout.setProgress(progress);

                // Fetch the exercise progress matching the progress date
                List<WorkoutExerciseProgress> exercisesForDate = getExerciseProgressByDate(workout, progress.getDate());
                workout.setExercises(exercisesForDate);  // Set filtered exercises to the workout
            }
        }

        return workouts;
    }



    public WorkoutTemplate updateWorkout(Long id, WorkoutTemplate updatedWorkout) {
        WorkoutTemplate existingWorkout = workoutRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Workout not found"));

        existingWorkout.setName(updatedWorkout.getName());
        existingWorkout.setDayOfWorkout(updatedWorkout.getDayOfWorkout());
        existingWorkout.setTimeOfWorkout(updatedWorkout.getTimeOfWorkout());

        WorkoutTemplate savedWorkout = workoutRepository.save(existingWorkout);

        LocalDate workoutDate = getWorkoutDate(savedWorkout);

        // Create a new progress for the updated workout if it doesn't exist
        WorkoutProgress createdProgress = workoutProgressService.createWorkoutProgressForDate(savedWorkout, workoutDate);
        savedWorkout.setProgress(createdProgress);

        // Get existing exercises only for the specific date
        List<WorkoutExerciseProgress> existingExercisesForDate = workoutExerciseProgressRepository
                .findByWorkoutTemplateAndDate(savedWorkout, workoutDate);

        // Temporary list to store new or updated exercise progress
        List<WorkoutExerciseProgress> newExerciseProgresses = new ArrayList<>();

        // Update or create exercise progress for the workout for the specific date
        for (WorkoutExerciseProgress exercise : new ArrayList<>(existingExercisesForDate)) {
            WorkoutExerciseProgress progress = workoutProgressService.updateOrCreateExerciseProgress(savedWorkout, exercise.getExercise(), workoutDate);
            if (progress != null) {
                newExerciseProgresses.add(progress);  // Collect new progress
            }
        }

        // Replace the exercises in the workout with the ones for the specific date
        savedWorkout.setExercises(newExerciseProgresses);

        return savedWorkout;
    }


    private LocalDate getWorkoutDate(WorkoutTemplate workout) {
        LocalDate today = LocalDate.now();
        DayOfWeek todayDayOfWeek = today.getDayOfWeek();
        DayOfWeek workoutDayOfWeek = DayOfWeek.valueOf(workout.getDayOfWorkout().toString().toUpperCase());

        // Calculate the number of days until the workout
        int daysUntilWorkout = (workoutDayOfWeek.getValue() - todayDayOfWeek.getValue() + 7) % 7;

        return today.plusDays(daysUntilWorkout);
    }

    @Transactional
    public WorkoutExerciseProgress addExerciseToWorkout(Long workoutId, Long exerciseId, int sets, int reps, int weight, boolean isFailure) {
        // Fetch the workout and exercise or throw an exception if not found
        WorkoutTemplate workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new IllegalArgumentException("Workout not found"));
        ExerciseTemplate exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new IllegalArgumentException("Exercise not found"));

        // If the workout day is today, set the workoutDate to today
        LocalDate workoutDate = getWorkoutDate(workout);

        // Create and save the new WorkoutExerciseTemplate
        WorkoutExerciseTemplate workoutExerciseTemplate = new WorkoutExerciseTemplate(workout, exercise, sets, reps, weight, isFailure);
        WorkoutExerciseTemplate savedWorkoutExercise = workoutExerciseRepository.save(workoutExerciseTemplate);

        // Save the WorkoutTemplate to ensure the exercises are properly linked
        workoutRepository.save(workout);

        // Create progress for this new exercise in the workout template and return it
        return workoutProgressService.updateOrCreateExerciseProgress(workout, savedWorkoutExercise, workoutDate);
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

            // Fetch the exercise progress matching the progress date
            List<WorkoutExerciseProgress> exercisesForDate = getExerciseProgressByDate(workout, progress.getDate());
            workout.setExercises(exercisesForDate);  // Set filtered exercises to the workout

            System.out.println("Progress found and set: " + progress.getDate());
        } else {
            System.out.println("No progress found for workout: " + workout.getId());
        }

        return workout;
    }



}
