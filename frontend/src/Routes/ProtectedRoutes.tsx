import { useEffect, useState } from "react";
import { Outlet, Navigate, useNavigate } from "react-router-dom";
import { AppDispatch } from "../store";
import { useDispatch } from "react-redux";
import { checkAuthStatus } from "../redux/auth/authThunks";

const ProtectedRoutes = () => {
  const [checkingAuth, setCheckingAuth] = useState(true);
  const [authenticated, setAuthenticated] = useState(false);
  const navigate = useNavigate();
  const dispatch: AppDispatch = useDispatch();

  useEffect(() => {
    dispatch(checkAuthStatus())
      .unwrap()
      .then((response) => {
        if (response) {
          setAuthenticated(true);
        } else {
          localStorage.removeItem("accessToken");
          navigate("/login");
        }
      })
      .catch(() => {
        localStorage.removeItem("accessToken");
        navigate("/login");
      })
      .finally(() => setCheckingAuth(false));
  }, [dispatch, navigate]);

  if (checkingAuth) {
    return null;
  }

  return authenticated ? <Outlet /> : <Navigate to="/login" />;
};

export default ProtectedRoutes;
