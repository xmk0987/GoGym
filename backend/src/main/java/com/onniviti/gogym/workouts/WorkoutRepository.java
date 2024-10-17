package com.onniviti.gogym.workouts;

import com.onniviti.gogym.workouts.models.WorkoutTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface WorkoutRepository extends JpaRepository<WorkoutTemplate, Long> {

    List<WorkoutTemplate> findByUserId(Long userId);

    @Query("SELECT w FROM WorkoutTemplate w WHERE w.dayOfWorkout = :dayOfWorkout")
    List<WorkoutTemplate> findByDayOfWorkout(@Param("dayOfWorkout") DayOfWeek dayOfWorkout);

}
