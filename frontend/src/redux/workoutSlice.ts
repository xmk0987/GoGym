// src/redux/workoutSlice.ts
import { createSlice } from "@reduxjs/toolkit";

interface WorkoutState {
  chosenWorkout: string | null;
  editMode: boolean;
  popup: boolean;
}

const initialState: WorkoutState = {
  chosenWorkout: null,
  editMode: false,
  popup: false,
};

const workoutSlice = createSlice({
  name: "workout",
  initialState,
  reducers: {
    chooseWorkout: (state, action) => {
      state.chosenWorkout = action.payload;
    },
    toggleEdit: (state) => {
      state.editMode = !state.editMode;
    },
    togglePopup: (state) => {
      state.popup = !state.popup;
    },
  },
});

// Export actions and reducer
export const { chooseWorkout, toggleEdit, togglePopup } = workoutSlice.actions;
export default workoutSlice.reducer;
