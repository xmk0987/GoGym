import { useEffect, useState } from "react";
import styles from "./Workout.module.css";
import { useParams } from "react-router-dom";
import Header from "../../../../components/Header/Header";
import Navbar from "../../../../components/Navbar/Navbar";
import { useSelector, useDispatch } from "react-redux";
import { RootState, AppDispatch } from "../../../../store";
import { Workout as WorkoutType } from "../../../../types/Workouts";
import WorkoutCard from "../WorkoutCard/WorkoutCard";
import WorkoutPopup from "../Popup/WorkoutPopup";
import { handleEditWorkout } from "../../../../redux/workouts/workoutsSlice";
import Exercise from "../../../Exercises/Exercise/Exercise";
import ExercisePopup from "../../../Exercises/Popup/ExercisePopup";
import { getWorkout } from "../../../../redux/workouts/workoutsThunks";

const Workout = () => {
  const { workoutId } = useParams<{ workoutId: string }>();
  const dispatch: AppDispatch = useDispatch();
  const { workouts, editWorkout } = useSelector(
    (state: RootState) => state.workouts
  );
  const user = useSelector((state: RootState) => state.auth.user);

  const [addExercise, setAddExercise] = useState<boolean>(false);

  const parsedWorkoutId = workoutId ? parseInt(workoutId) : null;

  // Find the current workout by comparing workout IDs
  const currentWorkout = workouts.find(
    (workout: WorkoutType) => workout.id === parsedWorkoutId
  );

  // Fetch the workout if it's not found in the state
  useEffect(() => {
    if (!currentWorkout && parsedWorkoutId && user) {
      dispatch(getWorkout({ workoutId: parsedWorkoutId, userId: user.id }));
    }
  }, [currentWorkout, dispatch, parsedWorkoutId, user, workoutId]);

  useEffect(() => {
    return () => {
      dispatch(handleEditWorkout(null));
    };
  }, [dispatch]);

  const close = () => {
    if (editWorkout) {
      dispatch(handleEditWorkout(null));
    }
  };

  const toggleAddExercise = () => {
    setAddExercise(!addExercise);
  };

  console.log(currentWorkout);
  return (
    <>
      <Header />
      <main className={styles["workoutContainer"]}>
        {currentWorkout ? (
          <>
            <WorkoutCard workout={currentWorkout} opened={true} selected />
            <section className={styles["exercises"]}>
              {currentWorkout.workoutExercises?.map((exerciseWorkout) => (
                <Exercise
                  exerciseWorkout={exerciseWorkout}
                  key={exerciseWorkout.id}
                />
              ))}
            </section>

            <button
              className={`defaultBtnStyle ${styles["createExerciseBtn"]}`}
              onClick={toggleAddExercise}
            >
              Add Exercise
            </button>
          </>
        ) : (
          <p>Loading Workout Details</p>
        )}
      </main>
      {editWorkout ? (
        <>
          <div className="greyScreen" onClick={close}></div>
          <WorkoutPopup close={close} workout={editWorkout} />
        </>
      ) : null}
      {addExercise && parsedWorkoutId ? (
        <>
          <div className="greyScreen" onClick={toggleAddExercise}></div>
          <ExercisePopup
            close={toggleAddExercise}
            workoutId={parsedWorkoutId}
          />
        </>
      ) : null}
      <Navbar />
    </>
  );
};

export default Workout;
