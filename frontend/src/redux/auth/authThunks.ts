import { createAsyncThunk } from "@reduxjs/toolkit";
import { authService } from "../../api/authService";
import { handleError } from "../../utils/errors";
import { setMessage } from "../notification/notificationSlice";
import { AuthUser } from "../../types/User";

export const loginUser = createAsyncThunk<
  AuthUser,
  { username: string; password: string }
>(
  "auth/login",
  async ({ username, password }, { dispatch, rejectWithValue }) => {
    try {
      const response = await authService.loginUser(username, password);

      localStorage.setItem("authenticated", "true");
      return response as AuthUser;
    } catch (error) {
      const errorMessage = handleError(error);

      dispatch(
        setMessage({
          message: errorMessage,
          error: true,
        })
      );

      return rejectWithValue(errorMessage);
    }
  }
);

// Thunk to check if the user is authenticated
export const checkAuthStatus = createAsyncThunk<AuthUser | null>(
  "auth/checkAuthStatus",
  async (_, { dispatch, rejectWithValue }) => {
    try {
      const response = await authService.checkAuthStatus();
      localStorage.setItem("authenticated", "true");

      return response;
    } catch (error) {
      const errorMessage = handleError(error);
      localStorage.removeItem("authenticated");
      dispatch(
        setMessage({
          message: errorMessage,
          error: true,
        })
      );

      return rejectWithValue(errorMessage);
    }
  }
);
