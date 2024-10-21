package com.onniviti.gogym.workouts.repository;

import com.onniviti.gogym.workouts.models.WorkoutExerciseTemplate;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExerciseTemplate, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM WorkoutExerciseTemplate e WHERE e.workoutTemplate.id = :workoutId")
    void deleteByWorkoutTemplateId(Long workoutId);

}
