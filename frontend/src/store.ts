// src/store.ts
import { configureStore } from "@reduxjs/toolkit";
import workoutsReducer from "./redux/workouts/workoutsSlice";
import registrationReducer from "./redux/registration/registrationSlice";
import messageReducer from "./redux/notification/notificationSlice";
import authReducer from "./redux/auth/authSlice";
import exercisesReducer from "./redux/exercises/exercisesSlice";

// Configure the store with the reducer
export const store = configureStore({
  reducer: {
    workouts: workoutsReducer,
    registration: registrationReducer,
    message: messageReducer,
    auth: authReducer,
    exercises: exercisesReducer,
  },
});

// Infer the `RootState` and `AppDispatch` types from the store itself
export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
