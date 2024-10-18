package com.onniviti.gogym.workoutProgress.repository;

import com.onniviti.gogym.workoutProgress.models.WorkoutExerciseProgress;
import com.onniviti.gogym.workouts.models.WorkoutExerciseTemplate;
import com.onniviti.gogym.workouts.models.WorkoutTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutExerciseProgressRepository extends JpaRepository<WorkoutExerciseProgress, Long> {
    boolean existsByWorkoutTemplateAndExerciseAndDate(WorkoutTemplate workoutTemplate, WorkoutExerciseTemplate exerciseTemplate, LocalDate date);

    List<WorkoutExerciseProgress> findByWorkoutTemplateAndDate(WorkoutTemplate workoutTemplate, LocalDate date);

    Optional<WorkoutExerciseProgress> findByWorkoutTemplateAndExerciseAndDate(WorkoutTemplate workoutTemplate, WorkoutExerciseTemplate exerciseTemplate, LocalDate date);
}
