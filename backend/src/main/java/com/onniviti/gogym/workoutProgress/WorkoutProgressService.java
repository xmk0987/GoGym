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
    public void createWorkoutProgress(WorkoutTemplate workout) {
        LocalDate today = LocalDate.now();
        createWorkoutProgressForDate(workout, today);
    }

    @Transactional
    private void createWorkoutProgressForDate(WorkoutTemplate workout, LocalDate scheduledDate) {
        boolean exists = workoutProgressRepository.existsByWorkoutTemplateAndDate(workout, scheduledDate);
        if (!exists) {
            WorkoutProgress workoutProgress = new WorkoutProgress();
            workoutProgress.setWorkoutTemplate(workout);
            workoutProgress.setCompleted(false);
            workoutProgress.setDate(scheduledDate);
            workoutProgressRepository.save(workoutProgress);

            if (workout.getWorkoutExercises() != null) {
                for (WorkoutExerciseTemplate exerciseTemplate : workout.getWorkoutExercises()) {
                    createWorkoutExerciseProgress(workoutProgress, exerciseTemplate);
                }
            }
        }
    }

    // Method to create exercise progress for a workout progress
    private void createWorkoutExerciseProgress(WorkoutProgress workoutProgress, WorkoutExerciseTemplate exerciseTemplate) {
        boolean exists = workoutExerciseProgressRepository.existsByWorkoutProgressAndWorkoutExerciseTemplate(workoutProgress, exerciseTemplate);
        if (!exists) {
            WorkoutExerciseProgress newExerciseProgress = new WorkoutExerciseProgress();
            newExerciseProgress.setWorkoutExerciseTemplate(exerciseTemplate);
            newExerciseProgress.setWorkoutProgress(workoutProgress);
            newExerciseProgress.setSetsDone(0);
            newExerciseProgress.setRepsDone(0);
            newExerciseProgress.setWeightUsed(exerciseTemplate.getWeight());
            workoutExerciseProgressRepository.save(newExerciseProgress);
        }
    }

    @Transactional
    public void createExerciseProgressForExistingWorkout(WorkoutExerciseTemplate newExerciseTemplate) {
        WorkoutTemplate workoutTemplate = newExerciseTemplate.getWorkout();
        List<WorkoutProgress> existingWorkoutProgresses = workoutProgressRepository.findByWorkoutTemplate(workoutTemplate);

        for (WorkoutProgress workoutProgress : existingWorkoutProgresses) {
            createWorkoutExerciseProgress(workoutProgress, newExerciseTemplate);
        }
    }

}
