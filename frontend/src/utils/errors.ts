import { AxiosError } from "axios";

// Utility function to handle errors
export const handleError = (error: unknown): string => {
  let errorMessage = "Something went wrong!";

  // Check if the error is an AxiosError
  if (error instanceof AxiosError) {
    errorMessage =
      error.response?.data || "An error occurred with the request.";
  } else if (error instanceof Error) {
    // Handle generic JavaScript errors
    errorMessage = error.message || "An unexpected error occurred.";
  }

  return errorMessage;
};
