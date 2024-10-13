import React from "react";
import { Routes, Route } from "react-router-dom";
import ProtectedRoutes from "../utils/ProtectedRoutes";
import Home from "../modules/Home/Home";
import Workout from "../modules/Workout/Workout";

const AppRoutes = () => {
  return (
    <Routes>
      <Route path="/" element={<main>Frontpage</main>} />
      <Route path="/login" element={<main>Login</main>} />
      <Route element={<ProtectedRoutes />}>
        <Route path="/home" element={<Home />} />
        <Route path="/workout" element={<Workout />} />
      </Route>
      <Route path="/*" element={<main>Frontpage</main>} />
    </Routes>
  );
};

export default AppRoutes;
