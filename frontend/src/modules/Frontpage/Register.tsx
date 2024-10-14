import React, { useState, ChangeEvent, FormEvent } from "react";
import styles from "./Frontpage.module.css";
import logo from "../../assets/images/goGymLogo.png";
import { useNavigate } from "react-router-dom";
import ClosedEyeIcon from "../../assets/icons/ClosedEyeIcon";
import EyeIcon from "../../assets/icons/EyeIcon";
import { useDispatch } from "react-redux";
import { registerUser } from "../../redux/registration/registrationSlice";
import { AppDispatch } from "../../store";
import Notification from "../Notification/Notification";
import { useAuthenticate } from "../../hooks/useAuthenticate";

interface FormValues {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  confirmPassword: string;
}

interface FormErrors {
  firstName?: string;
  lastName?: string;
  email?: string;
  password?: string;
  confirmPassword?: string;
}

const Register: React.FC = () => {
  const [formValues, setFormValues] = useState<FormValues>({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    confirmPassword: "",
  });
  const dispatch: AppDispatch = useDispatch();
  const [formErrors, setFormErrors] = useState<FormErrors>({});
  const [showPassword, setShowPassword] = useState<boolean>(false);
  const [showConfirmPassword, setShowConfirmPassword] =
    useState<boolean>(false);

  const navigate = useNavigate();

  useAuthenticate();

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { id, value } = e.target;
    setFormValues({
      ...formValues,
      [id]: value,
    });
  };

  const validateForm = (): FormErrors => {
    const errors: FormErrors = {};

    // First Name Validation
    if (!formValues.firstName.trim()) {
      errors.firstName = "First name is required.";
    }

    // Last Name Validation
    if (!formValues.lastName.trim()) {
      errors.lastName = "Last name is required.";
    }

    // Email Validation
    if (!formValues.email.trim()) {
      errors.email = "Email is required.";
    } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formValues.email)) {
      errors.email = "Please enter a valid email address.";
    }

    // Password Validation
    if (!formValues.password) {
      errors.password = "Password is required.";
    } else if (formValues.password.length < 8) {
      errors.password = "Password must be at least 8 characters long.";
    }

    // Confirm Password Validation
    if (!formValues.confirmPassword) {
      errors.confirmPassword = "Please confirm your password.";
    } else if (formValues.password !== formValues.confirmPassword) {
      errors.confirmPassword = "Passwords do not match.";
    }

    return errors;
  };

  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const errors = validateForm();

    if (Object.keys(errors).length === 0) {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const { confirmPassword, ...submittedFormValues } = formValues;

      const result = await dispatch(registerUser(submittedFormValues)).unwrap();

      if (result) {
        goToLogin();
      }
    } else {
      setFormErrors(errors);
    }
  };

  const goToLogin = () => {
    navigate("/login");
  };

  return (
    <main className={styles["container"]}>
      <form onSubmit={handleSubmit}>
        <div className={styles["header"]}>
          <img className={styles["logo"]} src={logo} alt="Go GYM" />
          <h1>Go Gym</h1>
        </div>
        <Notification />
        <div className={styles["longFormItem"]}>
          <div className={styles["formItem"]}>
            <label htmlFor="firstName">First Name</label>
            <input
              type="text"
              id="firstName"
              placeholder="Enter your first name"
              value={formValues.firstName}
              onChange={handleChange}
              className={formErrors.firstName ? `invalid` : ""}
            />
            {formErrors.firstName && (
              <span className={"error"}>{formErrors.firstName}</span>
            )}
          </div>
          <div className={styles["formItem"]}>
            <label htmlFor="lastName">Last Name</label>
            <input
              type="text"
              id="lastName"
              placeholder="Enter your last name"
              value={formValues.lastName}
              onChange={handleChange}
              className={formErrors.lastName ? `invalid` : ""}
            />
            {formErrors.lastName && (
              <span className={"error"}>{formErrors.lastName}</span>
            )}
          </div>
        </div>
        <div className={styles["formItem"]}>
          <label htmlFor="email">Email</label>
          <input
            type="email"
            id="email"
            placeholder="Enter your email"
            value={formValues.email}
            onChange={handleChange}
            className={formErrors.email ? `invalid` : ""}
          />
          {formErrors.email && (
            <span className={"error"}>{formErrors.email}</span>
          )}
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
              className={formErrors.password ? `invalid` : ""}
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
          {formErrors.password && (
            <span className={"error"}>{formErrors.password}</span>
          )}
        </div>
        <div className={styles["formItem"]}>
          <label htmlFor="confirmPassword">Confirm Password</label>
          <div className={styles["inputContainer"]}>
            <input
              type={showConfirmPassword ? "text" : "password"}
              id="confirmPassword"
              placeholder="Confirm your password"
              value={formValues.confirmPassword}
              onChange={handleChange}
              className={formErrors.confirmPassword ? `invalid` : ""}
            />
            {showConfirmPassword ? (
              <button
                className={styles["passwordVisibility"]}
                onClick={() => setShowConfirmPassword(false)}
                type="button"
              >
                <ClosedEyeIcon size="20px" color={"rgb(119, 119, 119)"} />
              </button>
            ) : (
              <button
                className={styles["passwordVisibility"]}
                onClick={() => setShowConfirmPassword(true)}
                type="button"
              >
                <EyeIcon size="20px" color={"rgb(119, 119, 119)"} />
              </button>
            )}
          </div>
          {formErrors.confirmPassword && (
            <span className={"error"}>{formErrors.confirmPassword}</span>
          )}
        </div>
        <button
          type="submit"
          className={`${styles["submitBtn"]} defaultBtnStyle`}
        >
          Sign Up
        </button>
        <button
          className={styles["changeTypeBtn"]}
          type="button"
          onClick={goToLogin}
        >
          Already have an account? <span>Login</span>
        </button>
      </form>
    </main>
  );
};

export default Register;
