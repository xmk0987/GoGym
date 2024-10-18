package com.onniviti.gogym.workoutProgress.repository;

import com.onniviti.gogym.workoutProgress.models.WorkoutExerciseProgress;
import com.onniviti.gogym.workouts.models.WorkoutExerciseTemplate;
import com.onniviti.gogym.workouts.models.WorkoutTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutExerciseProgressRepository extends JpaRepository<WorkoutExerciseProgress, Long> {
    boolean existsByWorkoutTemplateAndExercise(WorkoutTemplate workoutTemplate, WorkoutExerciseTemplate exercise);
}
