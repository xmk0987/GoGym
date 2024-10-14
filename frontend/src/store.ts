// src/store.ts
import { configureStore } from "@reduxjs/toolkit";
import workoutReducer from "./redux/workoutSlice";
import registrationReducer from "./redux/registration/registrationSlice";
import messageReducer from "./redux/notification/notificationSlice";
import authReducer from "./redux/auth/authSlice";

// Configure the store with the reducer
export const store = configureStore({
  reducer: {
    workout: workoutReducer,
    registration: registrationReducer,
    message: messageReducer,
    auth: authReducer,
  },
});

// Infer the `RootState` and `AppDispatch` types from the store itself
export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
