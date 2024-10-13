import React from "react";
import styles from "./WorkoutCard.module.css";
import EditIcon from "../../../../assets/icons/EditIcon";
import { useSelector, useDispatch } from "react-redux";
import { RootState } from "../../../../store";

interface WorkoutCardProps {
  workout: string;
}

const WorkoutCard: React.FC<WorkoutCardProps> = ({ workout }) => {
  const { editMode } = useSelector((state: RootState) => state.workout);

  const openExercises = () => {
    console.log("Open Exercises");
  };

  const openEdit = () => {
    console.log("open tedit");
  };

  return (
    <div className={styles["container"]}>
      <div
        className={styles["containerInfo"]}
        onClick={!editMode ? openExercises : openEdit}
      >
        <h2>Single Workout</h2>
        <p>6 Exercises</p>
      </div>
      {editMode ? (
        <button onClick={openEdit}>
          <EditIcon color="black" />
        </button>
      ) : null}
    </div>
  );
};

export default WorkoutCard;
