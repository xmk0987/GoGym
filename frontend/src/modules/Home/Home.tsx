import React from "react";
import style from "./Home.module.css";
import Navbar from "../../components/Navbar/Navbar";
import Header from "../../components/Header/Header";
import WeeklyWorkout from "./components/WeeklyWorkout";
import WorkoutCard from "../Workout/components/WorkoutCard/WorkoutCard";
import WorkoutList from "../Workout/components/WorkoutList/WorkoutList";

const Home = () => {
  return (
    <>
      <Header />
      <main className={style["homeContainer"]}>
        <WeeklyWorkout />
        <section className={style["todayWorkout"]}>
          <div className={style["todayWorkoutHeader"]}>
            <h1>Today:</h1>
            <p>Mon. 24</p>
          </div>
{/*           <WorkoutCard  /> */}
          <WorkoutList />
        </section>
        <button className="defaultBtnStyle">Go To Workout</button>
      </main>
      <Navbar />
    </>
  );
};

export default Home;
