import React from "react";
import styles from "./Header.module.css";

const Header = () => {
  return (
    <header className={styles["headerContainer"]}>
      <p>Welcome back!</p>
      <h2>Onni Vitikainen</h2>
    </header>
  );
};

export default Header;
