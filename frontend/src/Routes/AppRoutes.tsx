import React from "react";
import { Routes, Route, Navigate } from "react-router-dom";
import ProtectedRoutes from "./ProtectedRoutes";
import Home from "../modules/Home/Home";
import Workout from "../modules/Workout/Workout";
import Login from "../modules/Frontpage/Login";
import Register from "../modules/Frontpage/Register";

const AppRoutes = () => {
  return (
    <Routes>
      <Route path="/" element={<Navigate to="/login" />} />
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />

      <Route element={<ProtectedRoutes />}>
        <Route path="/home" element={<Home />} />
        <Route path="/workout" element={<Workout />} />
      </Route>
      <Route path="/*" element={<Navigate to="/login" />} />
    </Routes>
  );
};

export default AppRoutes;
