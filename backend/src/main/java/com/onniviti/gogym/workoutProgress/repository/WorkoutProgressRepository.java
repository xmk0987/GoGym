package com.onniviti.gogym.workoutProgress.repository;

import com.onniviti.gogym.workoutProgress.models.WorkoutProgress;
import com.onniviti.gogym.workouts.models.WorkoutTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutProgressRepository extends JpaRepository<WorkoutProgress, Long> {
    List<WorkoutProgress> findByWorkoutTemplate(WorkoutTemplate workoutTemplate);

    Optional<WorkoutProgress> findByWorkoutTemplateAndDate(WorkoutTemplate workoutTemplate, LocalDate workoutDate);
}
