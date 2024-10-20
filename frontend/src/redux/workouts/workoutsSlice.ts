// src/redux/workouts.ts
import { createSlice } from "@reduxjs/toolkit";
import {
  addExercise,
  createWorkout,
  deleteWorkout,
  getWorkout,
  getWorkouts,
  updateWorkout,
} from "./workoutsThunks";
import { Workout, Workouts } from "../../types/Workouts";

interface WorkoutsState {
  chosenWorkout: Workout | null;
  edit: boolean;
  popup: boolean;
  workouts: Workouts;
  loading: boolean;
  success: boolean;
  editWorkout: Workout | null;
  allFetched: boolean;
}

const initialState: WorkoutsState = {
  chosenWorkout: null,
  allFetched: false,
  edit: false,
  popup: false,
  workouts: [],
  loading: false,
  success: false,
  editWorkout: null,
};

const workouts = createSlice({
  name: "workouts",
  initialState,
  reducers: {
    chooseWorkout: (state, action) => {
      state.chosenWorkout = action.payload;
    },
    toggleEdit: (state) => {
      state.edit = !state.edit;
    },
    togglePopup: (state) => {
      state.popup = !state.popup;
    },
    handleEditWorkout: (state, action) => {
      state.editWorkout = action.payload;
    },
    setEdit: (state, action) => {
      state.edit = action.payload;
    },
    setPopup: (state, action) => {
      state.popup = action.payload;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(getWorkouts.pending, (state) => {
        state.loading = true;
        state.success = false;
      })
      .addCase(getWorkouts.fulfilled, (state, action) => {
        console.log("get workouts", action.payload);
        state.loading = false;
        state.success = true;
        state.workouts = action.payload;
        state.allFetched = true;
      })
      .addCase(getWorkouts.rejected, (state) => {
        state.loading = false;
        state.success = false;
      })
      .addCase(getWorkout.pending, (state) => {
        state.loading = true;
        state.success = false;
      })
      .addCase(getWorkout.fulfilled, (state, action) => {
        state.loading = false;
        state.success = true;

        const workoutExists = state.workouts.some(
          (workout) => workout.id === action.payload.id
        );

        if (!workoutExists) {
          state.workouts.push(action.payload);
        }
      })
      .addCase(getWorkout.rejected, (state) => {
        state.loading = false;
        state.success = false;
      })
      .addCase(createWorkout.pending, (state) => {
        state.loading = true;
        state.success = false;
      })
      .addCase(createWorkout.fulfilled, (state, action) => {
        console.log(action.payload);
        state.loading = false;
        state.success = true;
        state.workouts.push(action.payload);
      })
      .addCase(createWorkout.rejected, (state) => {
        state.loading = false;
        state.success = false;
      })
      .addCase(deleteWorkout.pending, (state) => {
        state.loading = true;
        state.success = false;
      })
      .addCase(deleteWorkout.fulfilled, (state, action) => {
        state.loading = false;
        state.success = true;
        // Filter out the workout with the given ID and reassign the filtered array
        state.workouts = state.workouts.filter(
          (workout) => workout.id !== action.payload
        );
      })
      .addCase(deleteWorkout.rejected, (state) => {
        state.loading = false;
        state.success = false;
      })
      .addCase(updateWorkout.pending, (state) => {
        state.loading = true;
        state.success = false;
      })
      .addCase(updateWorkout.fulfilled, (state, action) => {
        const { id } = action.payload;
        console.log(action.payload);
        console.log("update workout");
        state.loading = false;
        state.success = true;

        const updatedWorkoutIndex = state.workouts.findIndex(
          (workout) => workout.id === id
        );

        if (updatedWorkoutIndex !== -1) {
          state.workouts[updatedWorkoutIndex] = action.payload;
        } else {
          state.workouts.push(action.payload);
        }
      })
      .addCase(updateWorkout.rejected, (state) => {
        state.loading = false;
        state.success = false;
      })
      .addCase(addExercise.pending, (state) => {
        state.loading = true;
        state.success = false;
      })
      .addCase(addExercise.fulfilled, (state, action) => {
        const { workout } = action.payload;
        const updatedWorkoutIndex = state.workouts.findIndex(
          (workout) => workout.id === workout.id
        );

        if (updatedWorkoutIndex !== -1) {
          state.workouts[updatedWorkoutIndex] = workout;
        } else {
          state.workouts.push(workout);
        }
      })
      .addCase(addExercise.rejected, (state) => {
        state.loading = false;
        state.success = false;
      });
  },
});

// Export actions and reducer
export const {
  chooseWorkout,
  toggleEdit,
  togglePopup,
  handleEditWorkout,
  setEdit,
  setPopup,
} = workouts.actions;
export default workouts.reducer;
