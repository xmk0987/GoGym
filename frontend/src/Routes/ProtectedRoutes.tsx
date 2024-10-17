import { useEffect, useState } from "react";
import { Outlet, Navigate, useNavigate } from "react-router-dom";
import { AppDispatch } from "../store";
import { useDispatch } from "react-redux";
import { checkAuthStatus } from "../redux/auth/authThunks";

const ProtectedRoutes = () => {
  const [authenticated, setAuthenticated] = useState(
    localStorage.getItem("authenticated")
  );

  const navigate = useNavigate();

  const dispatch: AppDispatch = useDispatch();

  useEffect(() => {
    dispatch(checkAuthStatus())
      .unwrap()
      .then((response) => {
        if (response) {
          localStorage.setItem("authenticated", "true");
          setAuthenticated("true");
        }
      })
      .catch(() => {
        localStorage.removeItem("authenticated");
        setAuthenticated(null);
        navigate("/login");
      });
  }, [dispatch, navigate]);

  return authenticated ? <Outlet /> : <Navigate to="/login" />;
};

export default ProtectedRoutes;
