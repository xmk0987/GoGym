import { ExerciseWorkout } from "../../../types/Workouts";
import styles from "./Exercise.module.css";
import { useDispatch } from "react-redux";
import { AppDispatch } from "../../../store";
import { changeSetsProgress } from "../../../redux/workouts/workoutsThunks";
import TrashIcon from "../../../assets/icons/TrashIcon";
import goGym from "../../../assets/images/goGymLogo.png";

interface ExerciseProps {
  exerciseWorkout: ExerciseWorkout;
  edit: boolean;
}

const Exercise: React.FC<ExerciseProps> = ({ exerciseWorkout, edit }) => {
  const dispatch: AppDispatch = useDispatch();

  const updateProgress = (increase: boolean) => {
    dispatch(
      changeSetsProgress({ exerciseId: exerciseWorkout.progress.id, increase })
    );
  };

  const removeExercise = () => {
    console.log("removeExercise");
  };

  return (
    <div className={styles["container"]}>
      <span>
        <img src={goGym} alt="Exercise"></img>
      </span>
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
        {edit ? (
          <button className={styles["deleteExercise"]} onClick={removeExercise}>
            <TrashIcon color="white" />
          </button>
        ) : (
          <>
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
          </>
        )}
      </div>
    </div>
  );
};

export default Exercise;
