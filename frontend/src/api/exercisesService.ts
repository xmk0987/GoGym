import axios from "axios";
import { BASE_URL } from "./apiConfig";

export const exercisesService = {
  getExercises: async () => {
    const response = await axios.get(`${BASE_URL}/exercises`, {
      headers: {
        "Content-Type": "application/json",
      },
      withCredentials: true,
    });
    return response.data;
  },
};
