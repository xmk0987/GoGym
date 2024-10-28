import { AxiosError } from "axios";

// Utility function to handle errors
export const handleError = (error: unknown): string => {
  let errorMessage = "Something went wrong!";

  if (error instanceof AxiosError) {
    // Handle Axios errors with either string message or object data
    if (typeof error.response?.data === "string") {
      errorMessage = error.response.data;
    } else if (error.response?.data && typeof error.response.data === "object") {
      errorMessage = error.response.data.message || JSON.stringify(error.response.data);
    } else {
      errorMessage = "An error occurred with the request.";
    }
  } else if (error instanceof Error) {
    // Handle generic JavaScript errors
    errorMessage = error.message || "An unexpected error occurred.";
  }

  console.log("Handled error message:", errorMessage);
  return errorMessage;
};