import { useDispatch } from "react-redux";
import { AppDispatch } from "../store";
import { setMessage } from "../redux/notification/notificationSlice";

const useSendMessage = () => {
  const dispatch: AppDispatch = useDispatch();

  const sendMessage = (message: string, error: boolean = true) => {
    dispatch(setMessage({ message, error }));
  };

  return sendMessage;
};

export default useSendMessage;
