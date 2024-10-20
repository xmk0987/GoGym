package com.onniviti.gogym.workouts;

import com.onniviti.gogym.exercises.ExerciseTemplate;
import com.onniviti.gogym.exercises.ExerciseTemplateRepository;
import com.onniviti.gogym.workoutProgress.models.WorkoutExerciseProgress;
import com.onniviti.gogym.workoutProgress.models.WorkoutProgress;
import com.onniviti.gogym.workoutProgress.repository.WorkoutExerciseProgressRepository;
import com.onniviti.gogym.workoutProgress.repository.WorkoutProgressRepository;
import com.onniviti.gogym.workouts.models.WorkoutExerciseTemplate;
import com.onniviti.gogym.workouts.models.WorkoutTemplate;
import com.onniviti.gogym.workouts.repository.WorkoutExerciseRepository;
import com.onniviti.gogym.workouts.repository.WorkoutTemplateRepository;
import com.onniviti.gogym.workouts.requests.AddExerciseRequest;
import com.onniviti.gogym.workouts.requests.CreateWorkoutRequest;
import com.onniviti.gogym.workouts.requests.UpdateWorkoutRequest;
import com.onniviti.gogym.workouts.responses.ExerciseDTO;
import com.onniviti.gogym.workouts.responses.ExerciseProgressDTO;
import com.onniviti.gogym.workouts.responses.WorkoutDTO;
import com.onniviti.gogym.workouts.responses.WorkoutProgressDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkoutService {

    private final WorkoutTemplateRepository workoutTemplateRepository;
    private final WorkoutProgressRepository workoutProgressRepository;
    private final WorkoutExerciseProgressRepository workoutExerciseProgressRepository;
    private final ExerciseTemplateRepository exerciseTemplateRepository;
    private final WorkoutExerciseRepository workoutExerciseRepository;

    @Autowired
    public WorkoutService(WorkoutTemplateRepository workoutTemplateRepository,
                          WorkoutProgressRepository workoutProgressRepository, WorkoutExerciseProgressRepository workoutExerciseProgressRepository, ExerciseTemplateRepository exerciseTemplateRepository, WorkoutExerciseRepository workoutExerciseRepository
    ) {
        this.workoutTemplateRepository = workoutTemplateRepository;
        this.workoutProgressRepository = workoutProgressRepository;
        this.workoutExerciseProgressRepository = workoutExerciseProgressRepository;
        this.exerciseTemplateRepository = exerciseTemplateRepository;
        this.workoutExerciseRepository = workoutExerciseRepository;
    }

    // Controller calls to service:
    @Transactional
    public List<WorkoutDTO> getWorkoutsWithProgress(Long userId) {
        // Fetch all workout templates for the user
        List<WorkoutTemplate> workoutTemplates = workoutTemplateRepository.findByUserId(userId);
        List<WorkoutDTO> result = new ArrayList<>();

        for (WorkoutTemplate workoutTemplate : workoutTemplates) {
            WorkoutDTO workoutDTO = getWorkoutWithDTO(workoutTemplate);
            result.add(workoutDTO);
        }

        return result;
    }

    @Transactional
    public WorkoutDTO getWorkoutWithProgress(Long userId, Long workoutId) {
        WorkoutTemplate workoutTemplate = workoutTemplateRepository.findById(workoutId)
                .orElseThrow(() -> new IllegalArgumentException("Workout not found"));

        return getWorkoutWithDTO(workoutTemplate);
    }

    @Transactional
    public WorkoutDTO createWorkout(CreateWorkoutRequest request) {
        // Step 1: Create the WorkoutTemplate
        WorkoutTemplate workoutTemplate = new WorkoutTemplate();
        workoutTemplate.setName(request.getName());
        workoutTemplate.setTimeOfWorkout(request.getTimeOfWorkout());

        // Convert the day of the week to uppercase before using Enum.valueOf()
        workoutTemplate.setDayOfWorkout(request.getDayOfWorkout());

        workoutTemplate.setUserId(request.getUserId());

        // Save the WorkoutTemplate
        workoutTemplate = workoutTemplateRepository.save(workoutTemplate);

        // Step 5: Map the result to WorkoutDTO and return
        return getWorkoutWithDTO(workoutTemplate);
    }

    @Transactional
    public WorkoutDTO addExerciseToWorkout(Long userId, Long workoutId, AddExerciseRequest request) {
        // Step 1: Fetch the WorkoutTemplate by ID
        WorkoutTemplate workoutTemplate = workoutTemplateRepository.findById(workoutId)
                .orElseThrow(() -> new IllegalArgumentException("WorkoutTemplate not found"));

        // Fetch the ExerciseTemplate by ID (to avoid the TransientPropertyValueException)
        ExerciseTemplate exerciseTemplate = exerciseTemplateRepository.findById(request.getExerciseId())
                .orElseThrow(() -> new IllegalArgumentException("ExerciseTemplate not found"));

        // Step 2: Create and persist the new WorkoutExerciseTemplate
        WorkoutExerciseTemplate newExercise = new WorkoutExerciseTemplate();
        newExercise.setWorkoutTemplate(workoutTemplate);
        newExercise.setExercise(exerciseTemplate); // Associate the fetched exercise template
        newExercise.setSets(request.getSets());
        newExercise.setReps(request.getReps());
        newExercise.setWeight(request.getWeight());
        newExercise.setFailure(request.isFailure());
        workoutExerciseRepository.save(newExercise);

        // Persist WorkoutExerciseTemplate first
        workoutTemplate.getExerciseTemplates().add(newExercise);
        workoutTemplateRepository.save(workoutTemplate);

        return getWorkoutWithDTO(workoutTemplate);
    }

    @Transactional
    public WorkoutDTO updateWorkout(Long userId, Long workoutId, UpdateWorkoutRequest request) {
        // Fetch the WorkoutTemplate by ID
        WorkoutTemplate workoutTemplate = workoutTemplateRepository.findById(workoutId)
                .orElseThrow(() -> new IllegalArgumentException("WorkoutTemplate not found"));

        // Check if the DayOfWorkout has changed
        WorkoutTemplate.DayOfWorkout oldDayOfWorkout = workoutTemplate.getDayOfWorkout();
        if (request.getDayOfWorkout() != null && !oldDayOfWorkout.equals(request.getDayOfWorkout())) {
            // If the day has changed, update it and handle any additional logic here
            workoutTemplate.setDayOfWorkout(request.getDayOfWorkout());

            // Example: You might want to create a new progress for the new day
            // or handle specific logic when the day is changed
            System.out.println("Day of workout has been changed from " + oldDayOfWorkout + " to " + request.getDayOfWorkout());
        }

        // Update the name if it's provided and different
        if (request.getName() != null && !workoutTemplate.getName().equals(request.getName())) {
            workoutTemplate.setName(request.getName());
        }

        // Update the time of workout if it's provided and different
        if (request.getTimeOfWorkout() != null && !workoutTemplate.getTimeOfWorkout().equals(request.getTimeOfWorkout())) {
            workoutTemplate.setTimeOfWorkout(request.getTimeOfWorkout());
        }

        // Save the updated workout template
        workoutTemplateRepository.save(workoutTemplate);

        // Map the updated workout to DTO and return
        return getWorkoutWithDTO(workoutTemplate);
    }

    // Private functions:

    private WorkoutDTO getWorkoutWithDTO(WorkoutTemplate workoutTemplate) {
        // Fetch the workout progress
        WorkoutProgress workoutProgress = createOrFetchWorkoutProgressByDate(workoutTemplate);

        WorkoutProgressDTO progressDTO = workoutProgress != null ?
                new WorkoutProgressDTO(workoutProgress.getDate(), workoutProgress.isCompleted()) : null;

        // Fetch and map each exercise template and progress
        List<ExerciseDTO> exercises = new ArrayList<>();
        if (workoutProgress != null) {
            for (WorkoutExerciseTemplate exerciseTemplate : workoutTemplate.getExerciseTemplates()) {
                // Fetch progress for the exercise
                WorkoutExerciseProgress exerciseProgress = workoutExerciseProgressRepository
                        .findByWorkoutTemplateAndDate(exerciseTemplate.getWorkoutTemplate(), workoutProgress.getDate())
                        .stream().findFirst().orElse(null);

                // If exercise progress does not exist, create and save it
                if (exerciseProgress == null) {
                    exerciseProgress = new WorkoutExerciseProgress(
                            exerciseTemplate,           // The exercise template
                            workoutProgress,            // The workout progress (this links the exercise progress to the workout)
                            0,                          // Default setsDone (if needed)
                            0,                          // Default repsDone (if needed)
                            exerciseTemplate.getWeight(),// Default weight used (or set some other value)
                            workoutProgress.getDate()    // The date for this progress
                    );
                    // Save the new exercise progress to the database
                    exerciseProgress = workoutExerciseProgressRepository.save(exerciseProgress);
                }

                // Map the saved or existing progress to ExerciseProgressDTO
                ExerciseProgressDTO exerciseProgressDTO = new ExerciseProgressDTO(
                        exerciseProgress.getSetsDone(),
                        exerciseProgress.getRepsDone(),
                        exerciseProgress.getWeightUsed(),
                        exerciseProgress.getDate()
                );

                // Map to ExerciseDTO
                exercises.add(new ExerciseDTO(
                        exerciseTemplate.getId(),
                        exerciseTemplate.getExercise(),
                        exerciseTemplate.getSets(),
                        exerciseTemplate.getReps(),
                        exerciseTemplate.getWeight(),
                        exerciseTemplate.isFailure(),
                        exerciseProgressDTO
                ));
            }
        }

        // Return the single WorkoutDTO
        return new WorkoutDTO(
                workoutTemplate.getId(),
                workoutTemplate.getUserId(),
                workoutTemplate.getName(),
                workoutTemplate.getDayOfWorkout().name(),
                workoutTemplate.getTimeOfWorkout(),
                exercises,
                progressDTO
        );
    }


    private LocalDate getNextWorkoutDate(WorkoutTemplate workoutTemplate) {
        LocalDate today = LocalDate.now();
        DayOfWeek todayDayOfWeek = today.getDayOfWeek();
        DayOfWeek workoutDayOfWeek = DayOfWeek.valueOf(workoutTemplate.getDayOfWorkout().toString().toUpperCase());

        // Calculate the number of days until the workout
        int daysUntilWorkout = (workoutDayOfWeek.getValue() - todayDayOfWeek.getValue() + 7) % 7;

        return today.plusDays(daysUntilWorkout);
    }


    private WorkoutProgress createOrFetchWorkoutProgressByDate (WorkoutTemplate workoutTemplate) {
        LocalDate workoutDate = getNextWorkoutDate(workoutTemplate); // Get the next workout date
        return workoutProgressRepository.findByWorkoutTemplateAndDate(workoutTemplate, workoutDate)
                .orElseGet(() -> {
                    WorkoutProgress progress = new WorkoutProgress(workoutTemplate, workoutDate);
                    workoutProgressRepository.save(progress);
                    return progress;
                });
    }


}
