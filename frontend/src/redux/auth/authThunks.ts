import { createAsyncThunk } from "@reduxjs/toolkit";
import { authService } from "../../api/authService";
import { handleError } from "../../utils/errors";
import { setMessage } from "../notification/notificationSlice";
import { AuthUser } from "../../types/User";

// Login Thunk
export const loginUser = createAsyncThunk<
  AuthUser,
  { username: string; password: string }
>(
  "auth/login",
  async ({ username, password }, { dispatch, rejectWithValue }) => {
    try {
      const response = await authService.loginUser(username, password);
      return response as AuthUser;
    } catch (error) {
      const errorMessage = handleError(error);

      // Dispatch error message to the notification slice
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

// Check authentication status Thunk
export const checkAuthStatus = createAsyncThunk<AuthUser | null>(
  "auth/checkAuthStatus",
  async (_, { dispatch, rejectWithValue }) => {
    try {
      const response = await authService.checkAuthStatus();
      console.log("Check status", response);
      return response;
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

// Logout Thunk
export const logoutUser = createAsyncThunk(
  "auth/logout",
  async (_, { dispatch }) => {
    try {
      await authService.logout();

      dispatch(
        setMessage({ message: "Logged out successfully", error: false })
      );
    } catch (error) {
      const errorMessage = handleError(error);

      dispatch(
        setMessage({
          message: errorMessage,
          error: true,
        })
      );
    }
  }
);
