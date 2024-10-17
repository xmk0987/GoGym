package com.onniviti.gogym.exercises;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;

    @Autowired
    public ExerciseController (ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping()
    public List<ExerciseTemplate> getExercises() {
        return exerciseService.getExercises();
    }

}
