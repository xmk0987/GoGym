import { User } from "../types/User";
import { axiosInstance } from "./authService";

export const registrationService = {
  registerUser: async (userData: User) => {
    const response = await axiosInstance.post(`/registration`, userData);
    return response.data;
  },
};
