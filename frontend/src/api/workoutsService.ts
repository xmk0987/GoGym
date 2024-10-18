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
  }: {
    id: number;
    name: string;
    timeOfWorkout: string;
    dayOfWorkout: string;
    userId: number;
  }) => {
    const response = await axiosInstance.put(`/workouts`, {
      id,
      name,
      timeOfWorkout,
      dayOfWorkout,
      userId,
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
  }: {
    workoutId: number;
    sets: number;
    reps: number | null;
    isFailure: boolean;
    weight: number;
    exerciseId: number;
  }) => {
    const response = await axiosInstance.post(
      `/workouts/${workoutId}/exercise`,
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
};
