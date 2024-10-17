import { WorkoutProgressExercise } from "../../../types/Workouts";
import styles from "./Exercise.module.css";

interface ExerciseProps {
  exerciseWorkout: WorkoutProgressExercise;
}

const Exercise: React.FC<ExerciseProps> = ({ exerciseWorkout }) => {
  return (
    <div className={styles["container"]}>
      <span></span>
      <div className={styles["info"]}>
        <p>
          <strong>{exerciseWorkout.exercise.exercise.name}</strong>
        </p>
        <p>
          {`${exerciseWorkout.exercise.sets} x ${
            exerciseWorkout.exercise.sets
              ? "Failure"
              : exerciseWorkout.exercise.reps
          } x ${exerciseWorkout.exercise.weight} kg`}{" "}
        </p>
      </div>
      <div className={styles["sets"]}>
        <p>
          <strong>Sets Done:</strong>
        </p>
        <p>{`${exerciseWorkout.setsDone} / ${exerciseWorkout.exercise.sets}`}</p>
      </div>
    </div>
  );
};

export default Exercise;
