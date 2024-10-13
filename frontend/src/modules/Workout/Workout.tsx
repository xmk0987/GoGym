import styles from "./Workout.module.css";
import Header from "../../components/Header/Header";
import Navbar from "../../components/Navbar/Navbar";
import WorkoutCard from "./components/WorkoutCard/WorkoutCard";
import EditIcon from "../../assets/icons/EditIcon";
import EditOffIcon from "../../assets/icons/EditOffIcon";
import WorkoutPopup from "./components/Popup/WorkoutPopup";
import { useDispatch, useSelector } from "react-redux";
import { toggleEdit, togglePopup } from "../../redux/workoutSlice";
import { RootState } from "../../store";

const Workout = () => {
  const { editMode, popup } = useSelector((state: RootState) => state.workout);

  const dispatch = useDispatch();

  const d = new Date();
  const weekday = [
    "Sunday",
    "Monday",
    "Tuesday",
    "Wednesday",
    "Thursday",
    "Friday",
    "Saturday",
  ];

  const currentDayIndex = d.getDay();

  // Reorder the weekdays so the current day comes first, followed by the rest
  const orderedWeekdays = [
    ...weekday.slice(currentDayIndex), // Days from today onwards
    ...weekday.slice(0, currentDayIndex), // Days before today
  ];

  const handleToggleEdit = () => {
    dispatch(toggleEdit());
  };

  const handleTogglePopup = () => {
    dispatch(togglePopup());
  };

  return (
    <>
      <Header />
      <main className={styles["workoutContainer"]}>
        <div className={styles["workoutHeader"]}>
          <h1>Workouts</h1>
          <button onClick={handleToggleEdit}>
            {editMode ? <EditOffIcon /> : <EditIcon />}
          </button>
        </div>
        <div className={styles["dailyWorkoutList"]}>
          {orderedWeekdays.map((day, index) => (
            <div key={index} className={styles["dailyWorkout"]}>
              <p>{index === 0 ? "Today" : index === 1 ? "Tomorrow" : day}</p>
              <WorkoutCard workout="string" />
            </div>
          ))}
          <div className={styles["dailyWorkout"]}>
            <p>Other</p>
            <WorkoutCard workout="string" />
          </div>
        </div>
        {editMode ? (
          <button
            className={`defaultBtnStyle ${styles["createWorkoutBtn"]}`}
            onClick={handleTogglePopup}
          >
            Create Workout
          </button>
        ) : null}
      </main>
      {popup ? (
        <>
          <div className="greyScreen" onClick={handleTogglePopup}></div>
          <WorkoutPopup close={handleTogglePopup} />
        </>
      ) : null}
      <Navbar />
    </>
  );
};

export default Workout;
