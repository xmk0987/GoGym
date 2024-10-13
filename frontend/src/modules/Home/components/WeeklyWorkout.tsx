import React from "react";
import style from "../Home.module.css";

const WeeklyWorkout = () => {
  return (
    <section className={style["weeklyWorkout"]}>
      <div>
        <p>Mon</p>
        <span></span>
      </div>
      <div>
        <p>Tue</p>
        <span></span>
      </div>
      <div>
        <p>Wed</p>
        <span></span>
      </div>
      <div>
        <p>Thu</p>
        <span></span>
      </div>
      <div>
        <p>Fri</p>
        <span></span>
      </div>
      <div>
        <p>Sat</p>
        <span></span>
      </div>
      <div>
        <p>Sun</p>
        <span></span>
      </div>
    </section>
  );
};

export default WeeklyWorkout;
