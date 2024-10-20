package com.onniviti.gogym.exercises;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseTemplateRepository extends JpaRepository<ExerciseTemplate, Long> {
}
