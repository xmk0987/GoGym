import "./App.css";
import AppRoutes from "./Routes/AppRoutes";
import { AppDispatch } from "./store";
import { useDispatch } from "react-redux";
import { checkAuthStatus } from "./redux/auth/authThunks";
import { useEffect } from "react";

function App() {
  const dispatch: AppDispatch = useDispatch();

  useEffect(() => {
    dispatch(checkAuthStatus());
  }, [dispatch]);

  return (
    <>
      <AppRoutes />
    </>
  );
}

export default App;
