import axios from "axios";
import { BASE_URL } from "./apiConfig";

export const authService = {
  loginUser: async (username: string, password: string) => {
    const response = await axios.post(
      `${BASE_URL}/auth/login`,
      { username, password },
      {
        headers: {
          "Content-Type": "application/json",
        },
        withCredentials: true,
      }
    );
    return response.data;
  },
  checkAuthStatus: async () => {
    const response = await axios.get(`${BASE_URL}/auth/me`, {
      withCredentials: true,
    });
    return response.data;
  },
};
