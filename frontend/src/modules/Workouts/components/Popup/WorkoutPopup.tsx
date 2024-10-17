import React, { useEffect, useState } from "react";
import styles from "./WorkoutPopup.module.css";
import { useDispatch, useSelector } from "react-redux";
import { AppDispatch, RootState } from "../../../../store";
import {
  createWorkout,
  updateWorkout,
} from "../../../../redux/workouts/workoutsThunks";
import { getCurrentDay, getCurrentTime } from "../../../../utils/date";
import { Workout } from "../../../../types/Workouts";
import Notification from "../../../Notification/Notification";
import useSendMessage from "../../../../hooks/useSendMessage";

interface WorkoutPopupProps {
  close: () => void;
  workout?: Workout | null;
}

const WorkoutPopup: React.FC<WorkoutPopupProps> = ({
  close,
  workout = null,
}) => {
  const dispatch: AppDispatch = useDispatch();
  const user = useSelector((state: RootState) => state.auth.user);
  const sendMessage = useSendMessage();

  const [workoutName, setWorkoutName] = useState<string>("");
  const [dayOfWorkout, setDayOfWorkout] = useState<string>(getCurrentDay());
  const [timeOfWorkout, setTimeOfWorkout] = useState<string>(getCurrentTime());

  useEffect(() => {
    if (workout) {
      setWorkoutName(workout.name);
      setDayOfWorkout(workout.dayOfWorkout);
      setTimeOfWorkout(workout.timeOfWorkout);
    } else {
      setWorkoutName("");
      setDayOfWorkout(getCurrentDay());
      setTimeOfWorkout(getCurrentTime());
    }
  }, [workout]);

  const handleChangeWorkoutName = (e: React.ChangeEvent<HTMLInputElement>) =>
    setWorkoutName(e.target.value);

  const handleChangeDayOfWorkout = (e: React.ChangeEvent<HTMLSelectElement>) =>
    setDayOfWorkout(e.target.value);

  const handleTimeOfWorkout = (e: React.ChangeEvent<HTMLInputElement>) =>
    setTimeOfWorkout(e.target.value);

  const handleSubmitForm = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const trimmedName = workoutName.trim();

    if (!user) {
      sendMessage("Failed to get user.");
      return;
    }

    if (trimmedName === "") {
      sendMessage("Please enter a name.");
      return;
    }

    const workoutData = workout
      ? {
          id: workout.id,
          userId: user?.id,
          name: trimmedName,
          dayOfWorkout,
          timeOfWorkout,
        }
      : {
          userId: user?.id,
          name: trimmedName,
          dayOfWorkout,
          timeOfWorkout,
        };

    const action = workout
      ? updateWorkout(workoutData as Workout)
      : createWorkout(workoutData);

    dispatch(action)
      .unwrap()
      .then(() => {
        close();
      })
      .catch(() => {
        sendMessage(`Failed to ${workout ? "update" : "create"} workout:`);
      });
  };

  return (
    <div className={styles["container"]}>
      <h1>{workout ? "Edit Workout" : "Create Workout"}</h1>
      <Notification />
      <form onSubmit={(e) => handleSubmitForm(e)}>
        <div className={styles["formItem"]}>
          <label htmlFor="workoutName">Workout name</label>
          <input
            type="text"
            id="workoutName"
            placeholder="Enter the name of your workout"
            value={workoutName}
            onChange={handleChangeWorkoutName}
          />
        </div>
        <div className={styles["formItem"]}>
          <label htmlFor="dayOfWorkout">Day of workout</label>
          <select
            id="dayOfWorkout"
            value={dayOfWorkout}
            onChange={handleChangeDayOfWorkout}
          >
            <option value="Monday">Monday</option>
            <option value="Tuesday">Tuesday</option>
            <option value="Wednesday">Wednesday</option>
            <option value="Thursday">Thursday</option>
            <option value="Friday">Friday</option>
            <option value="Saturday">Saturday</option>
            <option value="Sunday">Sunday</option>
            <option value="Other">Other</option>
          </select>
        </div>
        <div className={styles["formItem"]}>
          <label htmlFor="timeOfWorkout">Time of workout</label>
          <input
            type="time"
            id="timeOfWorkout"
            min="00:00"
            max="24:00"
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
