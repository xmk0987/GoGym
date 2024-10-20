import React from "react";
import style from "./WeeklyWorkout.module.css";
import useWorkouts from "../../hooks/useWorkouts";

interface WeeklyWorkoutProps {
  setDayToShow: (day: string) => void; // Function that sets the day
  activeDay: string; // Active day should be a string
}

const WeeklyWorkout: React.FC<WeeklyWorkoutProps> = ({
  setDayToShow,
  activeDay,
}) => {
  const workouts = useWorkouts();
  const daysShort = ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"];
  const daysFull = [
    "Monday",
    "Tuesday",
    "Wednesday",
    "Thursday",
    "Friday",
    "Saturday",
    "Sunday",
  ];

  return (
    <section className={style["weeklyWorkout"]}>
      {daysShort.map((dayShort, index) => {
        const hasWorkout = workouts.some(
          (workout) => workout.dayOfWorkout === daysFull[index]
        );

        return (
          <div key={index} onClick={() => setDayToShow(daysFull[index])}>
            <p>{dayShort}</p>
            <button
              className={activeDay === daysFull[index] ? style["active"] : ""}
            >
              {hasWorkout ? "W" : "R"}
            </button>
          </div>
        );
      })}
    </section>
  );
};

export default WeeklyWorkout;
