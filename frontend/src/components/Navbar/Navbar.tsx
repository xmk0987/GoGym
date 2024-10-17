import { useLocation, useNavigate } from "react-router-dom";
import styles from "./Navbar.module.css";
import ExerciseIcon from "../../assets/icons/ExerciseIcon";
import HomeIcon from "../../assets/icons/HomeIcon";

const Navbar = () => {
  const location = useLocation(); // Get the current location
  const navigate = useNavigate();

  const handlePathing = (path: string) => {
    navigate(`/${path}`);
  };

  // Function to check if the current route matches
  const isActive = (path: string) => location.pathname.startsWith(path);

  return (
    <nav className={styles["navbarContainer"]}>
      <div className={styles["navbarItem"]}>
        <span
          className={`${isActive("/home") ? styles["active"] : ""} `}
        ></span>
        <button onClick={() => handlePathing("home")}>
          <HomeIcon />
        </button>
      </div>
      <div className={styles["navbarItem"]}>
        <span
          className={`${isActive("/workouts") ? styles["active"] : ""}`}
        ></span>
        <button onClick={() => handlePathing("workouts")}>
          <ExerciseIcon />
        </button>
      </div>
    </nav>
  );
};

export default Navbar;
