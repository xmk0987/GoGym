import React, { useState, ChangeEvent, FormEvent, useEffect } from "react";
import styles from "./Frontpage.module.css";
import logo from "../../assets/images/goGymLogo.png";
import { useLocation, useNavigate } from "react-router-dom";
import ClosedEyeIcon from "../../assets/icons/ClosedEyeIcon";
import EyeIcon from "../../assets/icons/EyeIcon";
import Notification from "../Notification/Notification";
import { useDispatch } from "react-redux";
import { AppDispatch } from "../../store";
import { setMessage } from "../../redux/notification/notificationSlice";
import { loginUser } from "../../redux/auth/authThunks";
import { useAuthenticate } from "../../hooks/useAuthenticate";

interface FormValues {
  email: string;
  password: string;
}

const Login: React.FC = () => {
  const [formValues, setFormValues] = useState<FormValues>({
    email: "",
    password: "",
  });
  const [showPassword, setShowPassword] = useState<boolean>(false);

  const navigate = useNavigate();
  const location = useLocation();
  const dispatch: AppDispatch = useDispatch();

  useAuthenticate();

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const confirmed = params.get("confirmed");

    if (confirmed === "true") {
      dispatch(
        setMessage({
          message: "Your email has been confirmed! You can now log in.",
          error: false,
        })
      );
    } else if (confirmed === "false") {
      dispatch(
        setMessage({
          message: "There was an issue confirming your email.",
          error: true,
        })
      );
    }
  }, [dispatch, location]);

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { id, value } = e.target;
    setFormValues({
      ...formValues,
      [id]: value,
    });
  };

  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    dispatch(
      loginUser({ username: formValues.email, password: formValues.password })
    );
  };

  const goToRegister = () => {
    navigate("/register");
  };

  return (
    <main className={styles["container"]}>
      <form onSubmit={handleSubmit}>
        <div className={styles["header"]}>
          <img className={styles["logo"]} src={logo} alt="Go GYM" />
          <h1>Go Gym</h1>
        </div>
        <Notification />
        <div className={styles["formItem"]}>
          <label htmlFor="email">Email</label>
          <input
            type="email"
            id="email"
            placeholder="Enter your email"
            value={formValues.email}
            onChange={handleChange}
          />
        </div>
        <div className={styles["formItem"]}>
          <label htmlFor="password">Password</label>
          <div className={styles["inputContainer"]}>
            <input
              type={showPassword ? "text" : "password"}
              id="password"
              placeholder="Enter your password"
              value={formValues.password}
              onChange={handleChange}
            />
            {showPassword ? (
              <button
                className={styles["passwordVisibility"]}
                onClick={() => setShowPassword(false)}
                type="button"
              >
                <ClosedEyeIcon size="20px" color={"rgb(119, 119, 119)"} />
              </button>
            ) : (
              <button
                className={styles["passwordVisibility"]}
                onClick={() => setShowPassword(true)}
                type="button"
              >
                <EyeIcon size="20px" color={"rgb(119, 119, 119)"} />
              </button>
            )}
          </div>
        </div>
        <button
          type="submit"
          className={`${styles["submitBtn"]} defaultBtnStyle`}
        >
          Login
        </button>
        <button
          className={styles["changeTypeBtn"]}
          type="button"
          onClick={goToRegister}
        >
          Don't have an account? <span>Register</span>
        </button>
      </form>
    </main>
  );
};

export default Login;
