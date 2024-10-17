// src/redux/workouts.ts
import { createSlice } from "@reduxjs/toolkit";
import {} from "./exercisesThunks";
import { Exercise } from "../../types/Workouts";
import { getExercises } from "./exercisesThunks";

interface ExercisesState {
  loading: boolean;
  success: boolean;
  exercises: Exercise[];
}

const initialState: ExercisesState = {
  loading: false,
  success: false,
  exercises: [],
};

const exercises = createSlice({
  name: "exercises",
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(getExercises.pending, (state) => {
        state.loading = true;
        state.success = false;
      })
      .addCase(getExercises.fulfilled, (state, action) => {
        state.loading = false;
        state.success = true;
        state.exercises = action.payload;
      })
      .addCase(getExercises.rejected, (state) => {
        state.loading = false;
        state.success = false;
      });
  },
});

export default exercises.reducer;
