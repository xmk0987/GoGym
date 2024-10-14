import { createSlice } from "@reduxjs/toolkit";
import { loginUser, checkAuthStatus } from "./authThunks";
import { AuthUser } from "../../types/User";

interface AuthState {
  user: AuthUser | null;
  loading: boolean;
  success: boolean;
}

const initialState: AuthState = {
  user: null,
  loading: false,
  success: false,
};

const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(loginUser.pending, (state) => {
        state.loading = true;
        state.success = false;
      })
      .addCase(loginUser.fulfilled, (state, action) => {
        state.loading = false;
        state.success = true;
        state.user = action.payload;
      })
      .addCase(loginUser.rejected, (state) => {
        state.loading = false;
        state.success = false;
        state.user = null;
      })
      .addCase(checkAuthStatus.pending, (state) => {
        state.loading = true;
      })
      .addCase(checkAuthStatus.fulfilled, (state, action) => {
        state.loading = false;
        state.success = true;
        state.user = action.payload;
      })
      .addCase(checkAuthStatus.rejected, (state) => {
        state.loading = false;
        state.success = false;
        state.user = null;
      });
  },
});

export default authSlice.reducer;
