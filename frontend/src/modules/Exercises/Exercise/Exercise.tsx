import { ExerciseWorkout } from "../../../types/Workouts";
import styles from "./Exercise.module.css";
import { useDispatch } from "react-redux";
import { AppDispatch } from "../../../store";
import { changeSetsProgress } from "../../../redux/workouts/workoutsThunks";

interface ExerciseProps {
  exerciseWorkout: ExerciseWorkout;
}

const Exercise: React.FC<ExerciseProps> = ({ exerciseWorkout }) => {
  const dispatch: AppDispatch = useDispatch();

  const updateProgress = (increase: boolean) => {
    dispatch(
      changeSetsProgress({ exerciseId: exerciseWorkout.progress.id, increase })
    );
  };

  return (
    <div className={styles["container"]}>
      <span></span>
      <div className={styles["info"]}>
        <p>
          <strong>{exerciseWorkout.exercise.name}</strong>
        </p>
        <p>
          {`${exerciseWorkout.sets} x ${
            exerciseWorkout.failure ? "Failure" : exerciseWorkout.sets
          } x ${exerciseWorkout.weight} kg`}{" "}
        </p>
      </div>
      <div className={styles["sets"]}>
        <p>
          <strong>Sets Done:</strong>
        </p>
        <div className={styles["setProgress"]}>
          <button
            disabled={exerciseWorkout.progress.setsDone === 0}
            onClick={() => updateProgress(false)}
          >
            -
          </button>
          <p>{`${exerciseWorkout.progress?.setsDone} / ${exerciseWorkout.sets}`}</p>
          <button
            disabled={
              exerciseWorkout.progress.setsDone === exerciseWorkout.sets
            }
            onClick={() => updateProgress(true)}
          >
            +
          </button>
        </div>
      </div>
    </div>
  );
};

export default Exercise;
