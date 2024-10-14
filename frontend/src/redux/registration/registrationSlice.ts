import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import { registrationService } from "../../api/registrationService";
import { User } from "../../types/User";
import { setMessage } from "../notification/notificationSlice";
import { handleError } from "../../utils/errors";

// Async thunk for registering the user
export const registerUser = createAsyncThunk(
  "registration/registerUser",
  async (userData: User, { dispatch, rejectWithValue }) => {
    try {
      const response = await registrationService.registerUser(userData);

      dispatch(
        setMessage({
          message:
            "Registration successful! Please confirm your email before logging in.",
          error: false,
        })
      );

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

const registrationSlice = createSlice({
  name: "registration",
  initialState: {
    loading: false,
    success: false,
  },
  reducers: {
    resetState: (state) => {
      state.loading = false;
      state.success = false;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(registerUser.pending, (state) => {
        state.loading = true;
        state.success = false;
      })
      .addCase(registerUser.fulfilled, (state) => {
        state.loading = false;
        state.success = true;
      })
      .addCase(registerUser.rejected, (state) => {
        state.loading = false;
        state.success = false;
      });
  },
});

export const { resetState } = registrationSlice.actions;

export default registrationSlice.reducer;
