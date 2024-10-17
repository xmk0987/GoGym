package com.onniviti.gogym.workouts;

import com.onniviti.gogym.workouts.models.WorkoutTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutRepository extends JpaRepository<WorkoutTemplate, Long> {

    List<WorkoutTemplate> findByUserId(Long userId);

    @Query("SELECT w FROM WorkoutTemplate w LEFT JOIN FETCH w.workoutExercises WHERE w.id = :workoutId")
    Optional<WorkoutTemplate> findByIdWithExercises(@Param("workoutId") Long workoutId);

    @Query("SELECT w FROM WorkoutTemplate w WHERE w.dayOfWorkout = :dayOfWorkout")
    List<WorkoutTemplate> findByDayOfWorkout(@Param("dayOfWorkout") DayOfWeek dayOfWorkout);

}
