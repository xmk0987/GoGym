package com.onniviti.gogym.exercises;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExerciseService {

    private final ExerciseTemplateRepository exerciseRepository;

    @Autowired
    public ExerciseService(ExerciseTemplateRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public List<ExerciseTemplate> getExercises() {
        // Fetch all exercises
        List<ExerciseTemplate> exercises = exerciseRepository.findAll();

        // Transform muscles from comma-separated String to List<String>
        return exercises.stream().peek(exercise -> {
            List<String> musclesList = Arrays.asList(exercise.getMuscles().split(","));
            exercise.setMusclesList(musclesList);
        }).collect(Collectors.toList());
    }
}
