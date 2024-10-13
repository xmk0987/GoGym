import React from "react";
import styles from "./SingleWorkout.module.css";

const SingleWorkout = () => {
  return (
    <div className={styles["container"]}>
      <span></span>
      <div className={styles["info"]}>
        <p>
          <strong>Bench Press</strong>
        </p>
        <p>3 x 6 x 100kg</p>
      </div>
      <div className={styles["sets"]}>
        <p>
          <strong>Sets Done:</strong>
        </p>
        <p> 2 / 3</p>
      </div>
    </div>
  );
};

export default SingleWorkout;
