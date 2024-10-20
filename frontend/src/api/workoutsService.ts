import { UpdateWorkoutRequest } from "../types/Requests";
import { axiosInstance } from "./authService";

export const workoutsService = {
  getWorkouts: async (userId: number) => {
    const response = await axiosInstance.get(`/workouts/${userId}`);
    return response.data;
  },
  getWorkout: async (workoutId: number, userId: number) => {
    const response = await axiosInstance.get(
      `/workouts/${userId}/${workoutId}`
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
    const response = await axiosInstance.post(`/workouts`, {
      name,
      timeOfWorkout,
      dayOfWorkout,
      userId,
    });
    return response.data;
  },
  updateWorkout: async ({
    id,
    name,
    timeOfWorkout,
    dayOfWorkout,
    userId,
  }: UpdateWorkoutRequest) => {
    const response = await axiosInstance.put(`/workouts/${userId}/${id}`, {
      name,
      timeOfWorkout,
      dayOfWorkout,
    });
    return response.data;
  },
  addExercise: async ({
    workoutId,
    sets,
    reps,
    isFailure,
    weight,
    exerciseId,
    userId,
  }: {
    workoutId: number;
    userId: number;
    sets: number;
    reps: number | null;
    isFailure: boolean;
    weight: number;
    exerciseId: number;
  }) => {
    const response = await axiosInstance.post(
      `/workouts/${userId}/${workoutId}/exercise`,
      {
        sets,
        reps,
        isFailure,
        weight,
        exerciseId,
      }
    );
    return response.data;
  },
  deleteWorkout: async ({
    userId,
    workoutId,
  }: {
    userId: number;
    workoutId: number;
  }) => {
    const response = await axiosInstance.delete(
      `/workouts/${userId}/${workoutId}`
    );
    return response.data;
  },
};
