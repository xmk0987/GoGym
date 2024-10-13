package com.onniviti.gogym.exercises;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class ExerciseService {

    private List<Exercise> exercises;

    @PostConstruct
    public void loadExercises() {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("exercises.json");

            ObjectMapper objectMapper = new ObjectMapper();
            ExerciseWrapper wrapper = objectMapper.readValue(inputStream, ExerciseWrapper.class);
            this.exercises = wrapper.getExercises();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public Exercise getExerciseByName(String name) {
        return exercises.stream().filter(exercise -> exercise
                        .getName()
                        .equalsIgnoreCase(name))
                        .findFirst()
                        .orElse(null);
    }

}
