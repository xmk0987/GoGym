import style from "./Home.module.css";
import Navbar from "../../components/Navbar/Navbar";
import Header from "../../components/Header/Header";
import useWorkouts from "../../hooks/useWorkouts";
import { getCurrentDay, getFormattedDate } from "../../utils/date";
import WorkoutCard from "../Workouts/components/WorkoutCard/WorkoutCard";

const Home = () => {
  const workouts = useWorkouts();

  const dateToday = new Date();
  const formattedDate = getFormattedDate(dateToday);

  const today = getCurrentDay();

  const todaysWorkouts = workouts.filter(
    (workout) => workout.dayOfWorkout === today
  );

  return (
    <>
      <Header />
      <main className={style["homeContainer"]}>
        <section className={style["todayWorkout"]}>
          <div className={style["todayWorkoutHeader"]}>
            <h1>Today:</h1>
            <p>{formattedDate}</p>
          </div>
          <div className={style["todayWorkouts"]}>
            {todaysWorkouts.map((workout) => (
              <WorkoutCard workout={workout} key={workout.id} />
            ))}
          </div>
        </section>
      </main>
      <Navbar />
    </>
  );
};

export default Home;
