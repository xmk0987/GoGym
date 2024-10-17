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



// Method to create exercise progress for a workout progress
    private WorkoutExerciseProgress createWorkoutExerciseProgress(WorkoutProgress workoutProgress, WorkoutExerciseTemplate exerciseTemplate) {
        boolean exists = workoutExerciseProgressRepository.existsByWorkoutProgressAndExercise(workoutProgress, exerciseTemplate);
        if (!exists) {
            WorkoutExerciseProgress newExerciseProgress = new WorkoutExerciseProgress();
            newExerciseProgress.setExercise(exerciseTemplate);
            newExerciseProgress.setWorkoutProgress(workoutProgress);
            newExerciseProgress.setSetsDone(0);
            newExerciseProgress.setRepsDone(0);
            newExerciseProgress.setWeightUsed(exerciseTemplate.getWeight());

            // Save the new WorkoutExerciseProgress
            WorkoutExerciseProgress savedExerciseProgress = workoutExerciseProgressRepository.save(newExerciseProgress);

            // Add the new exercise progress to the workoutProgress's exercises list
            if (workoutProgress.getExercises() == null) {
                workoutProgress.setExercises(new ArrayList<>());
            }
            workoutProgress.getExercises().add(savedExerciseProgress);

            // Save the updated WorkoutProgress
            workoutProgressRepository.save(workoutProgress);

            return savedExerciseProgress;
        }
        return null;  // Return null if the progress already exists
    }


    @Transactional
    public WorkoutExerciseProgress createExerciseProgressForExistingWorkout(WorkoutExerciseTemplate newExerciseTemplate) {
        WorkoutTemplate workoutTemplate = newExerciseTemplate.getWorkout();
        List<WorkoutProgress> existingWorkoutProgresses = workoutProgressRepository.findByWorkout(workoutTemplate);

        WorkoutExerciseProgress latestExerciseProgress = null;

        // For each workout progress, create a new exercise progress
        for (WorkoutProgress workoutProgress : existingWorkoutProgresses) {
            latestExerciseProgress = createWorkoutExerciseProgress(workoutProgress, newExerciseTemplate);
        }

        // Return the latest created WorkoutExerciseProgress
        return latestExerciseProgress;
    }


}
