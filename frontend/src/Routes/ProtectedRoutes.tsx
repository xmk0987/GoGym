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
    const accessToken = localStorage.getItem("accessToken");

    if (!accessToken) {
      dispatch(checkAuthStatus())
        .unwrap()
        .then((response) => {
          if (response) {
            setAuthenticated(true);
          } else {
            navigate("/login");
          }
        })
        .catch(() => {
          navigate("/login");
        })
        .finally(() => setCheckingAuth(false));
    } else {
      setAuthenticated(true);
      setCheckingAuth(false);
    }
  }, [dispatch, navigate]);

  if (checkingAuth) {
    return null;
  }

  return authenticated ? <Outlet /> : <Navigate to="/login" />;
};

export default ProtectedRoutes;
