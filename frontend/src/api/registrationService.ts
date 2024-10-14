import axios from "axios";
import { User } from "../types/User";
import { BASE_URL } from "./apiConfig";

export const registrationService = {
  registerUser: async (userData: User) => {
    console.log("send register", userData);
    const response = await axios.post(`${BASE_URL}/registration`, userData, {
      headers: {
        "Content-Type": "application/json",
      },
      withCredentials: true,
    });
    return response.data;
  },
};
