// src/store.ts
import { configureStore } from "@reduxjs/toolkit";
import workoutReducer from "./redux/workoutSlice";

// Configure the store with the reducer
export const store = configureStore({
  reducer: {
    workout: workoutReducer,
  },
});

// Infer the `RootState` and `AppDispatch` types from the store itself
export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
