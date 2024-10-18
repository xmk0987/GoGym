import { axiosInstance } from "./authService";

export const exercisesService = {
  getExercises: async () => {
    const response = await axiosInstance.get(`/exercises`)
    return response.data;
  },
};
