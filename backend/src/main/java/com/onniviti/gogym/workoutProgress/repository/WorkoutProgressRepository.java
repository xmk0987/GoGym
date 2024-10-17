package com.onniviti.gogym.workoutProgress.repository;

import com.onniviti.gogym.workoutProgress.models.WorkoutProgress;
import com.onniviti.gogym.workouts.models.WorkoutTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorkoutProgressRepository  extends JpaRepository<WorkoutProgress, Long> {
    boolean existsByWorkoutTemplateAndDate(WorkoutTemplate workout, LocalDate scheduledDate);

    List<WorkoutProgress> findByWorkoutTemplate(WorkoutTemplate workoutTemplate);
}
