import styles from "./Workouts.module.css";
import Header from "../../components/Header/Header";
import Navbar from "../../components/Navbar/Navbar";
import WorkoutCard from "./components/WorkoutCard/WorkoutCard";
import EditIcon from "../../assets/icons/EditIcon";
import EditOffIcon from "../../assets/icons/EditOffIcon";
import WorkoutPopup from "./components/Popup/WorkoutPopup";
import { useDispatch, useSelector } from "react-redux";
import {
  toggleEdit,
  togglePopup,
  handleEditWorkout,
  setEdit,
  setPopup,
} from "../../redux/workouts/workoutsSlice";
import { AppDispatch, RootState } from "../../store";
import useWorkouts from "../../hooks/useWorkouts";
import { convertTimeToMinutes } from "../../utils/date";
import { useEffect, useState } from "react";
import WeeklyWorkout from "../../components/WeeklyWorkout/WeeklyWorkout";
import TrashIcon from "../../assets/icons/TrashIcon";
import { deleteWorkout } from "../../redux/workouts/workoutsThunks";

const Workouts = () => {
  const { edit, popup, editWorkout } = useSelector(
    (state: RootState) => state.workouts
  );
  const user = useSelector((state: RootState) => state.auth.user);
  const [dayToShow, setDayToShow] = useState<string>("");
  const workouts = useWorkouts();
  const dispatch: AppDispatch = useDispatch();

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
    ...weekday.slice(currentDayIndex),
    ...weekday.slice(0, currentDayIndex),
  ];

  const handleToggleEdit = () => {
    dispatch(toggleEdit());
  };

  const handleTogglePopup = () => {
    dispatch(togglePopup());
  };

  const close = () => {
    if (editWorkout) {
      dispatch(handleEditWorkout(null));
    } else {
      dispatch(setPopup(false));
    }
  };

  useEffect(() => {
    return () => {
      dispatch(setEdit(false)); // Reset edit to false
      dispatch(setPopup(false)); // Reset popup to false
    };
  }, [dispatch]);

  const handleDayToShow = (day: string) => {
    if (dayToShow !== day) {
      setDayToShow(day);
    } else {
      setDayToShow("");
    }
  };

  const handleDeleteWorkout = (id: number) => {
    console.log("Delete workout", id);
    if (!user) {
      return;
    }
    dispatch(deleteWorkout({ workoutId: id, userId: user.id }));
  };

  return (
    <>
      <Header />
      <main className={styles["workoutContainer"]}>
        <WeeklyWorkout setDayToShow={handleDayToShow} activeDay={dayToShow} />
        <div className={styles["workoutHeader"]}>
          <h1>Workouts</h1>
          <button onClick={handleToggleEdit}>
            {edit ? <EditOffIcon /> : <EditIcon />}
          </button>
        </div>
        {dayToShow === "" ? (
          <div className={styles["dailyWorkoutList"]}>
            {orderedWeekdays.map((day, index) => {
              const dayWorkouts = workouts
                .filter((workout) => workout.dayOfWorkout === day)
                .sort(
                  (a, b) =>
                    convertTimeToMinutes(a.timeOfWorkout) -
                    convertTimeToMinutes(b.timeOfWorkout)
                );

              return (
                <div key={`day${index}`} className={styles["dailyWorkout"]}>
                  <p>
                    <strong>
                      {index === 0 ? "Today" : index === 1 ? "Tomorrow" : day}
                    </strong>
                  </p>

                  {dayWorkouts.length > 0 ? (
                    dayWorkouts.map((workout) => (
                      <div className={styles["editWorkouts"]} key={workout.id}>
                        {edit && (
                          <button
                            className={styles["deleteWorkout"]}
                            onClick={() => handleDeleteWorkout(workout.id)}
                          >
                            <TrashIcon size="20px" color="white" />
                          </button>
                        )}
                        <WorkoutCard key={workout.id} workout={workout} />
                      </div>
                    ))
                  ) : (
                    <p>No workouts for {day}</p>
                  )}
                </div>
              );
            })}
            <div className={styles["dailyWorkout"]}>
              <p>
                <strong>Other</strong>
              </p>
              {workouts
                .filter((workout) => workout.dayOfWorkout === "Other")
                .map((workout) => (
                  <div className={styles["editWorkouts"]} key={workout.id}>
                    {edit && (
                      <button
                        className={styles["deleteWorkout"]}
                        onClick={() => handleDeleteWorkout(workout.id)}
                      >
                        <TrashIcon size="20px" color="white" />
                      </button>
                    )}
                    <WorkoutCard key={workout.id} workout={workout} />
                  </div>
                ))}
            </div>
          </div>
        ) : (
          <div className={styles["dailyWorkoutList"]}>
            {workouts
              .filter((workout) => workout.dayOfWorkout === dayToShow)
              .map((workout) => (
                <div className={styles["editWorkouts"]} key={workout.id}>
                  {edit && (
                    <button
                      className={styles["deleteWorkout"]}
                      onClick={() => handleDeleteWorkout(workout.id)}
                    >
                      <TrashIcon size="20px" color="white" />
                    </button>
                  )}
                  <WorkoutCard key={workout.id} workout={workout} />
                </div>
              ))}
          </div>
        )}
        <button
          className={`defaultBtnStyle ${styles["createWorkoutBtn"]}`}
          onClick={handleTogglePopup}
        >
          Create Workout
        </button>
      </main>
      {popup ? (
        <>
          <div className="greyScreen" onClick={close}></div>
          <WorkoutPopup close={close} />
        </>
      ) : null}
      {editWorkout ? (
        <>
          <div className="greyScreen" onClick={close}></div>
          <WorkoutPopup close={close} workout={editWorkout} />
        </>
      ) : null}
      <Navbar />
    </>
  );
};

export default Workouts;
