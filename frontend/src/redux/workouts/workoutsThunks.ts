import { createAsyncThunk } from "@reduxjs/toolkit";
import { handleError } from "../../utils/errors";
import { setMessage } from "../notification/notificationSlice";
import { Workout, Workouts } from "../../types/Workouts";
import { workoutsService } from "../../api/workoutsService";
import { UpdateWorkoutRequest } from "../../types/Requests";

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

export const deleteWorkout = createAsyncThunk<
  number,
  { workoutId: number; userId: number }
>(
  "workouts/deleteWorkout",
  async ({ workoutId, userId }, { dispatch, rejectWithValue }) => {
    try {
      await workoutsService.deleteWorkout({
        workoutId,
        userId,
      });

      return workoutId;
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

export const updateWorkout = createAsyncThunk<Workout, UpdateWorkoutRequest>(
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
  { workout: Workout },
  {
    workoutId: number;
    sets: number;
    reps: number | null;
    weight: number;
    exerciseId: number;
    userId: number;
  }
>(
  "workouts/addExercise",
  async (
    { workoutId, sets, reps, weight, exerciseId, userId },
    { dispatch, rejectWithValue }
  ) => {
    try {
      const response = await workoutsService.addExercise({
        workoutId,
        sets,
        reps,
        weight,
        exerciseId,
        userId,
      });

      return { workout: response };
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

export const changeSetsProgress = createAsyncThunk<
  { exerciseId: number; increase: boolean },
  {
    exerciseId: number;
    increase: boolean;
  }
>(
  "workouts/changeSetsProgress",
  async ({ exerciseId, increase }, { dispatch, rejectWithValue }) => {
    try {
      await workoutsService.changeSetsProgress({
        exerciseId,
        increase,
      });

      return { exerciseId, increase };
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
