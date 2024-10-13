import React from "react";
import styles from "./Workoutlist.module.css";
import SingleWorkout from "../Single/SingleWorkout";

const WorkoutList = () => {
  return (
    <div className={styles["workout"]}>
      <SingleWorkout />
      <SingleWorkout />
      <SingleWorkout />
      <SingleWorkout />
      <SingleWorkout />
      <SingleWorkout />
      <SingleWorkout />
      <SingleWorkout />
      <SingleWorkout />
      <SingleWorkout />
      <SingleWorkout />
    </div>
  );
};

export default WorkoutList;
