import { createSlice } from "@reduxjs/toolkit";

const messageSlice = createSlice({
  name: "message",
  initialState: {
    error: true,
    message: "",
  },
  reducers: {
    resetState: (state) => {
      state.error = false;
      state.message = "";
    },
    setMessage: (state, action) => {
      const { message, error } = action.payload;
      state.error = error;
      state.message = message;
    },
  },
});

export const { resetState, setMessage } = messageSlice.actions;

export default messageSlice.reducer;
