package com.onniviti.gogym.workoutProgress.repository;

import com.onniviti.gogym.workoutProgress.models.WorkoutProgress;
import com.onniviti.gogym.workouts.models.WorkoutTemplate;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutProgressRepository extends JpaRepository<WorkoutProgress, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM WorkoutProgress wp WHERE wp.workoutTemplate.id = :workoutTemplateId")
    void deleteByWorkoutTemplateId(Long workoutTemplateId);

    Optional<WorkoutProgress> findByWorkoutTemplateAndDate(WorkoutTemplate workoutTemplate, LocalDate workoutDate);

    List<WorkoutProgress> findByWorkoutTemplateId(Long workoutId);
}
