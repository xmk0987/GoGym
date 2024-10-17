import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { RootState, AppDispatch } from "../store";
import { getWorkouts } from "../redux/workouts/workoutsThunks";

const useWorkouts = () => {
  const user = useSelector((state: RootState) => state.auth.user);
  const { workouts, allFetched } = useSelector(
    (state: RootState) => state.workouts
  );
  const dispatch: AppDispatch = useDispatch();

  useEffect(() => {
    if (user && !allFetched) {
      dispatch(getWorkouts(user.id));
    }
  }, [allFetched, dispatch, user, workouts]);

  return workouts;
};

export default useWorkouts;
