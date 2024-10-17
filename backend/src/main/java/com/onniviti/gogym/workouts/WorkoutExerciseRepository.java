package com.onniviti.gogym.workouts;

import com.onniviti.gogym.workouts.models.WorkoutExerciseTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExerciseTemplate, Long> {
}
