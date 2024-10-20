import { ExerciseWorkout } from "../../../types/Workouts";
import styles from "./Exercise.module.css";

interface ExerciseProps {
  exerciseWorkout: ExerciseWorkout;
}

const Exercise: React.FC<ExerciseProps> = ({ exerciseWorkout }) => {
  return (
    <div className={styles["container"]}>
      <span></span>
      <div className={styles["info"]}>
        <p>
          <strong>{exerciseWorkout.exercise.name}</strong>
        </p>
        <p>
          {`${exerciseWorkout.sets} x ${
            exerciseWorkout.failure ? "Failure" : exerciseWorkout.reps
          } x ${exerciseWorkout.weight} kg`}{" "}
        </p>
      </div>
      <div className={styles["sets"]}>
        <p>
          <strong>Sets Done:</strong>
        </p>
        <p>{`${exerciseWorkout.progress?.setsDone} / ${exerciseWorkout.sets}`}</p>
      </div>
    </div>
  );
};

export default Exercise;
