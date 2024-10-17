import { Routes, Route, Navigate } from "react-router-dom";
import ProtectedRoutes from "./ProtectedRoutes";
import Home from "../modules/Home/Home";
import Login from "../modules/Frontpage/Login";
import Register from "../modules/Frontpage/Register";
import Workouts from "../modules/Workouts/Workouts";
import Workout from "../modules/Workouts/components/Workout/Workout";

const AppRoutes = () => {
  
  return (
    <Routes>
      <Route path="/" element={<Navigate to="/login" />} />
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />

      <Route element={<ProtectedRoutes />}>
        <Route path="/home" element={<Home />} />
        <Route path="/workouts" element={<Workouts />} />
        <Route path="/workouts/:workoutId" element={<Workout />} />
      </Route>
      <Route path="/*" element={<Navigate to="/login" />} />
    </Routes>
  );
};

export default AppRoutes;
