package com.onniviti.gogym.workoutProgress.repository;

import com.onniviti.gogym.workoutProgress.models.WorkoutExerciseProgress;
import com.onniviti.gogym.workouts.models.WorkoutTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface WorkoutExerciseProgressRepository extends JpaRepository<WorkoutExerciseProgress, Long> {

    // Custom query to find WorkoutExerciseProgress based on WorkoutTemplate and Date
    @Query("SELECT wep FROM WorkoutExerciseProgress wep WHERE wep.exerciseTemplate.workoutTemplate = :workoutTemplate AND wep.date = :date")
    List<WorkoutExerciseProgress> findByWorkoutTemplateAndDate(WorkoutTemplate workoutTemplate, LocalDate date);
}
