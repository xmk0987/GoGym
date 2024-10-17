import { createSlice } from "@reduxjs/toolkit";

const messageSlice = createSlice({
  name: "message",
  initialState: {
    error: true,
    message: "",
    dontShowToAll: false,
  },
  reducers: {
    resetState: (state) => {
      state.error = false;
      state.message = "";
    },
    setMessage: (state, action) => {
      const { message, error, dontShowToAll } = action.payload;
      state.error = error;
      state.message = message;
      state.dontShowToAll = dontShowToAll ? dontShowToAll : false;
    },
  },
});

export const { resetState, setMessage } = messageSlice.actions;

export default messageSlice.reducer;
