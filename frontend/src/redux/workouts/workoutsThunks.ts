import { createAsyncThunk } from "@reduxjs/toolkit";
import { handleError } from "../../utils/errors";
import { setMessage } from "../notification/notificationSlice";
import {
  Workout,
  WorkoutProgressExercise,
  Workouts,
} from "../../types/Workouts";
import { workoutsService } from "../../api/workoutsService";

export const getWorkouts = createAsyncThunk<Workouts, number>(
  "workouts/getWorkouts",
  async (userId: number, { dispatch, rejectWithValue }) => {
    try {
      const response = await workoutsService.getWorkouts(userId);

      return response as Workouts;
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

export const getWorkout = createAsyncThunk<
  Workout,
  { workoutId: number; userId: number }
>(
  "workouts/getWorkout",
  async ({ workoutId, userId }, { dispatch, rejectWithValue }) => {
    try {
      const response = await workoutsService.getWorkout(workoutId, userId);

      return response as Workout;
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

export const createWorkout = createAsyncThunk<
  Workout,
  { name: string; dayOfWorkout: string; timeOfWorkout: string; userId: number }
>(
  "workouts/createWorkout",
  async (
    { name, timeOfWorkout, dayOfWorkout, userId },
    { dispatch, rejectWithValue }
  ) => {
    try {
      const response = await workoutsService.createWorkout({
        name,
        timeOfWorkout,
        dayOfWorkout,
        userId,
      });

      return response as Workout;
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

export const updateWorkout = createAsyncThunk<Workout, Workout>(
  "workouts/updateWorkout",
  async (
    { name, timeOfWorkout, dayOfWorkout, userId, id },
    { dispatch, rejectWithValue }
  ) => {
    try {
      const response = await workoutsService.updateWorkout({
        id,
        name,
        timeOfWorkout,
        dayOfWorkout,
        userId,
      });

      return response as Workout;
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

export const addExercise = createAsyncThunk<
  { exercise: WorkoutProgressExercise; workoutId: number },
  {
    workoutId: number;
    sets: number;
    reps: number | null;
    isFailure: boolean;
    weight: number;
    exerciseId: number;
  }
>(
  "workouts/addExercise",
  async (
    { workoutId, sets, reps, isFailure, weight, exerciseId },
    { dispatch, rejectWithValue }
  ) => {
    try {
      const response = await workoutsService.addExercise({
        workoutId,
        sets,
        reps,
        isFailure,
        weight,
        exerciseId,
      });

      return { exercise: response, workoutId };
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
