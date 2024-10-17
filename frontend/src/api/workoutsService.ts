import axios from "axios";
import { BASE_URL } from "./apiConfig";

export const workoutsService = {
  getWorkouts: async (userId: number) => {
    const response = await axios.get(`${BASE_URL}/workouts/${userId}`, {
      headers: {
        "Content-Type": "application/json",
      },
      withCredentials: true,
    });
    return response.data;
  },
  getWorkout: async (workoutId: number, userId: number) => {
    const response = await axios.get(
      `${BASE_URL}/workouts/${userId}/${workoutId}`,
      {
        headers: {
          "Content-Type": "application/json",
        },
        withCredentials: true,
      }
    );
    return response.data;
  },
  createWorkout: async ({
    name,
    timeOfWorkout,
    dayOfWorkout,
    userId,
  }: {
    name: string;
    timeOfWorkout: string;
    dayOfWorkout: string;
    userId: number;
  }) => {
    const response = await axios.post(
      `${BASE_URL}/workouts`,
      {
        name,
        timeOfWorkout,
        dayOfWorkout,
        userId,
      },
      {
        headers: {
          "Content-Type": "application/json",
        },
        withCredentials: true,
      }
    );
    return response.data;
  },
  updateWorkout: async ({
    id,
    name,
    timeOfWorkout,
    dayOfWorkout,
    userId,
  }: {
    id: number;
    name: string;
    timeOfWorkout: string;
    dayOfWorkout: string;
    userId: number;
  }) => {
    const response = await axios.put(
      `${BASE_URL}/workouts`,
      {
        id,
        name,
        timeOfWorkout,
        dayOfWorkout,
        userId,
      },
      {
        headers: {
          "Content-Type": "application/json",
        },
        withCredentials: true,
      }
    );
    return response.data;
  },
  addExercise: async ({
    workoutId,
    sets,
    reps,
    isFailure,
    weight,
    exerciseId,
  }: {
    workoutId: number;
    sets: number;
    reps: number | null;
    isFailure: boolean;
    weight: number;
    exerciseId: number;
  }) => {
    const response = await axios.post(
      `${BASE_URL}/workouts/${workoutId}/exercise`,
      {
        sets,
        reps,
        isFailure,
        weight,
        exerciseId,
      },
      {
        headers: {
          "Content-Type": "application/json",
        },
        withCredentials: true,
      }
    );
    return response.data;
  },
};
