package com.onniviti.gogym.workoutProgress;

import com.onniviti.gogym.workoutProgress.models.WorkoutExerciseProgress;
import com.onniviti.gogym.workoutProgress.models.WorkoutProgress;
import com.onniviti.gogym.workoutProgress.repository.WorkoutExerciseProgressRepository;
import com.onniviti.gogym.workoutProgress.repository.WorkoutProgressRepository;
import com.onniviti.gogym.workouts.WorkoutRepository;
import com.onniviti.gogym.workouts.models.WorkoutExerciseTemplate;
import com.onniviti.gogym.workouts.models.WorkoutTemplate;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class WorkoutProgressService {

    private final WorkoutRepository workoutRepository;
    private final WorkoutProgressRepository workoutProgressRepository;
    private final WorkoutExerciseProgressRepository workoutExerciseProgressRepository;

    @Autowired
    public WorkoutProgressService(WorkoutRepository workoutRepository, WorkoutProgressRepository workoutProgressRepository, WorkoutExerciseProgressRepository workoutExerciseProgressRepository) {
        this.workoutRepository = workoutRepository;
        this.workoutProgressRepository = workoutProgressRepository;
        this.workoutExerciseProgressRepository = workoutExerciseProgressRepository;
    }


    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")  // Runs every day at midnight
    public void createWorkoutProgressForNextWeek() {
        LocalDate tomorrowDate = LocalDate.now().plusDays(1);
        DayOfWeek nextDayOfWeek = tomorrowDate.getDayOfWeek();

        List<WorkoutTemplate> workoutsForNextDay = workoutRepository.findByDayOfWorkout(nextDayOfWeek);

        for (WorkoutTemplate workout : workoutsForNextDay) {
            createWorkoutProgressForDate(workout, tomorrowDate);
        }
    }

    @Transactional
    public WorkoutProgress createWorkoutProgressForDate(WorkoutTemplate workout, LocalDate scheduledDate) {
        // Check if any WorkoutProgress exists for the given workout
        List<WorkoutProgress> existingProgresses = workoutProgressRepository.findByWorkout(workout);

        if (!existingProgresses.isEmpty()) {
            // Fetch the existing progress (you can choose to get the latest, first, or any specific logic)
            WorkoutProgress existingProgress = existingProgresses.get(0);

            // Disassociate the old progress from the workout by nullifying the workout reference
            existingProgress.setWorkout(null);
            workoutProgressRepository.save(existingProgress);
        }

        // Now create a new progress
        WorkoutProgress newWorkoutProgress = new WorkoutProgress();
        newWorkoutProgress.setWorkout(workout); // Set the current workout
        newWorkoutProgress.setCompleted(false);
        newWorkoutProgress.setDate(scheduledDate);
        newWorkoutProgress.setUserId(workout.getUserId());

        // Save the new WorkoutProgress with a new ID and return it
        workoutProgressRepository.save(newWorkoutProgress);

        return newWorkoutProgress;
    }


    // Method to create exercise progress for a workout
    private WorkoutExerciseProgress createWorkoutExerciseProgress(WorkoutTemplate workoutTemplate, WorkoutExerciseTemplate exerciseTemplate) {
        // Check if an exercise progress already exists for this workout and exercise
        boolean exists = workoutExerciseProgressRepository.existsByWorkoutTemplateAndExercise(workoutTemplate, exerciseTemplate);
        if (!exists) {
            // Create a new WorkoutExerciseProgress entry
            WorkoutExerciseProgress newExerciseProgress = new WorkoutExerciseProgress();
            newExerciseProgress.setExercise(exerciseTemplate);  // Set the exercise
            newExerciseProgress.setWorkoutTemplate(workoutTemplate);  // Associate it with the workout
            newExerciseProgress.setSetsDone(0);  // Initialize progress values
            newExerciseProgress.setRepsDone(0);
            newExerciseProgress.setWeightUsed(exerciseTemplate.getWeight());

            // Save the new WorkoutExerciseProgress
            WorkoutExerciseProgress savedExerciseProgress = workoutExerciseProgressRepository.save(newExerciseProgress);

            // Add the newly created exercise progress to the list of exercises in WorkoutTemplate
            if (workoutTemplate.getExercises() == null) {
                workoutTemplate.setExercises(new ArrayList<>());  // Initialize if null
            }
            workoutTemplate.getExercises().add(savedExerciseProgress);  // Add the exercise progress

            // Save the updated workout template with the new exercises
            workoutRepository.save(workoutTemplate);

            return savedExerciseProgress;
        }
        return null;  // Return null if the exercise progress already exists
    }

    @Transactional
    public WorkoutExerciseProgress createExerciseProgressForExistingWorkout(WorkoutExerciseTemplate newExerciseTemplate) {
        WorkoutTemplate workoutTemplate = newExerciseTemplate.getWorkout();

        // Create exercise progress for the workout and return the latest created one
        return createWorkoutExerciseProgress(workoutTemplate, newExerciseTemplate);
    }


}
