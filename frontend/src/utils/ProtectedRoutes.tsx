import { Outlet, Navigate } from "react-router-dom";
import { Provider } from "react-redux";
import { store } from "../store";

const ProtectedRoutes = () => {
  const user = true;

  return user ? (
    <Provider store={store}>
      <Outlet />
    </Provider>
  ) : (
    <Navigate to="/login" />
  );
};

export default ProtectedRoutes;
