import React, { useState } from "react";
import styles from "./WorkoutPopup.module.css";

interface WorkoutPopupProps {
  close: () => void;
}

const WorkoutPopup: React.FC<WorkoutPopupProps> = ({ close }) => {
  const workout = true;
  const [workoutName, setWorkoutName] = useState<string>("");
  const [dayOfWorkout, setDayOfWorkout] = useState<string>("");
  const [timeOfWorkout, setTimeOfWorkout] = useState<string>("");

  const handleChangeWorkoutName = (e: React.ChangeEvent<HTMLInputElement>) =>
    setWorkoutName(e.target.value);
  const handleChangeDayOfWorkout = (e: React.ChangeEvent<HTMLInputElement>) =>
    setDayOfWorkout(e.target.value);
  const handleTimeOfWorkout = (e: React.ChangeEvent<HTMLInputElement>) =>
    setTimeOfWorkout(e.target.value);

  const handleSubmitForm = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (workout) {
      console.log("Update existing workout");
    } else {
      console.log("Create new workout");
    }
  };

  return (
    <div className={styles["container"]}>
      <h1>{workout ? "Edit Workout" : "Create Workout"}</h1>
      <form onSubmit={(e) => handleSubmitForm(e)}>
        <div className={styles["formItem"]}>
          <label htmlFor="workoutName">Workout name</label>
          <input
            type="text"
            id="workoutName"
            value={workoutName}
            onChange={handleChangeWorkoutName}
          />
        </div>
        <div className={styles["formItem"]}>
          <label htmlFor="dayOfWorkout">Day of workout</label>
          <input
            type="text"
            id="dayOfWorkout"
            value={dayOfWorkout}
            onChange={handleChangeDayOfWorkout}
          />
        </div>
        <div className={styles["formItem"]}>
          <label htmlFor="timeOfWorkout">Time of workout</label>
          <input
            type="text"
            id="timeOfWorkout"
            value={timeOfWorkout}
            onChange={handleTimeOfWorkout}
          />
        </div>
        <div className={styles["formButtons"]}>
          <button type="button" className="defaultBorderStyle" onClick={close}>
            Cancel
          </button>
          <button className="defaultBtnStyle">
            {workout ? "Update Workout" : "Create Workout"}
          </button>
        </div>
      </form>
      <button
        className={`defaultBorderStyle ${styles["close"]}`}
        onClick={close}
      >
        X
      </button>
    </div>
  );
};

export default WorkoutPopup;
