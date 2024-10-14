import { Outlet, Navigate } from "react-router-dom";

const ProtectedRoutes = () => {
  const authenticated = localStorage.getItem("authenticated");

  return authenticated ? <Outlet /> : <Navigate to="/login" />;
};

export default ProtectedRoutes;
