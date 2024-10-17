package com.onniviti.gogym.workoutProgress.repository;

import com.onniviti.gogym.exercises.ExerciseTemplate;
import com.onniviti.gogym.workoutProgress.models.WorkoutExerciseProgress;
import com.onniviti.gogym.workoutProgress.models.WorkoutProgress;
import com.onniviti.gogym.workouts.models.WorkoutExerciseTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutExerciseProgressRepository extends JpaRepository<WorkoutExerciseProgress, Long> {
    boolean existsByWorkoutProgressAndExercise(WorkoutProgress workoutProgress, WorkoutExerciseTemplate exercise);
}
