import { createAsyncThunk } from "@reduxjs/toolkit";
import { handleError } from "../../utils/errors";
import { setMessage } from "../notification/notificationSlice";
import { Exercise } from "../../types/Workouts";
import { exercisesService } from "../../api/exercisesService";

export const getExercises = createAsyncThunk<Exercise[], void>(
  "exercises/getExercises",
  async (_, { dispatch, rejectWithValue }) => {
    try {
      const response = await exercisesService.getExercises();

      return response as Exercise[];
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
