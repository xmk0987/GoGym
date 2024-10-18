import React from "react";
import styles from "./WorkoutCard.module.css";
import EditIcon from "../../../../assets/icons/EditIcon";
import { useSelector, useDispatch } from "react-redux";
import { AppDispatch, RootState } from "../../../../store";
import { Workout } from "../../../../types/Workouts";
import { handleEditWorkout } from "../../../../redux/workouts/workoutsSlice";
import { useNavigate } from "react-router-dom";

interface WorkoutCardProps {
  workout: Workout;
  opened?: boolean;
  selected?: boolean;
}

const WorkoutCard: React.FC<WorkoutCardProps> = ({
  workout,
  opened = false,
  selected = false,
}) => {
  const { edit } = useSelector((state: RootState) => state.workouts);
  const dispatch: AppDispatch = useDispatch();
  const navigate = useNavigate();

  const openExercises = () => {
    navigate(`/workouts/${workout.id}`);
  };

  const openEdit = (workout: Workout | null) => {
    dispatch(handleEditWorkout(workout));
  };

  return (
    <div
      className={`${styles["container"]} ${
        selected ? styles["selected"] : styles["notSelected"]
      }`}
    >
      <div
        className={styles["containerInfo"]}
        onClick={edit || opened ? () => openEdit(workout) : openExercises}
      >
        <h2>{workout.name}</h2>
        <p>{workout.timeOfWorkout}</p>
        {opened ? null : (
          <p>
            {workout.exercises?.length > 0
              ? `${workout.exercises?.length} Exercises`
              : "No Exercises"}
          </p>
        )}
      </div>
      {edit || opened ? (
        <button onClick={() => openEdit(workout)}>
          <EditIcon color={selected ? "white" : "black"} size="20px" />
        </button>
      ) : null}
    </div>
  );
};

export default WorkoutCard;
