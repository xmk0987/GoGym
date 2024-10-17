package com.onniviti.gogym.workoutProgress.repository;

import com.onniviti.gogym.workoutProgress.models.WorkoutProgress;
import com.onniviti.gogym.workouts.models.WorkoutTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorkoutProgressRepository extends JpaRepository<WorkoutProgress, Long> {

    // Update field reference to workout
    boolean existsByWorkoutAndDate(WorkoutTemplate workout, LocalDate scheduledDate);

    List<WorkoutProgress> findByWorkout(WorkoutTemplate workout);

    @Query("SELECT wp FROM WorkoutProgress wp WHERE wp.workout = :workout ORDER BY wp.date DESC")
    WorkoutProgress findLatestProgressByWorkout(@Param("workout") WorkoutTemplate workout);

    WorkoutProgress findByWorkoutAndDate(WorkoutTemplate workout, LocalDate scheduledDate);
}
