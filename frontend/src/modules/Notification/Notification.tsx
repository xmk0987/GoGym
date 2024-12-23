import { useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import { useLocation } from "react-router-dom";
import { RootState } from "../../store";
import styles from "./Notification.module.css";
import { AppDispatch } from "../../store";
import { resetState } from "../../redux/notification/notificationSlice";

const Notification = () => {
  const { message, error } = useSelector((state: RootState) => state.message);
  const dispatch: AppDispatch = useDispatch();
  const location = useLocation();

  useEffect(() => {
    // Clear notification when navigating to a different route
    dispatch(resetState());
  }, [location, dispatch]);

  useEffect(() => {
    if (message !== "") {
      const timer = setTimeout(() => {
        dispatch(resetState());
      }, 5000);

      return () => clearTimeout(timer);
    }
  }, [message, dispatch]);

  return (
    <>
      {message !== "" ? (
        <p
          className={`${styles["message"]} ${
            error ? styles["error"] : styles["notError"]
          }`}
        >
          {message}
        </p>
      ) : null}
    </>
  );
};

export default Notification;
