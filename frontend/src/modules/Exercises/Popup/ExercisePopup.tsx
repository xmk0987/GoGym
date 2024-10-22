import React, { useEffect, useState } from "react";
import styles from "./ExercisePopup.module.css";
import { Exercise } from "../../../types/Workouts";
import { useDispatch, useSelector } from "react-redux";
import { AppDispatch, RootState } from "../../../store";
import { getExercises } from "../../../redux/exercises/exercisesThunks";
import Notification from "../../Notification/Notification";
import useSendMessage from "../../../hooks/useSendMessage";
import { addExercise } from "../../../redux/workouts/workoutsThunks";

interface ExercisePopupProps {
  close: () => void;
  workoutId: number;
}

const ExercisePopup: React.FC<ExercisePopupProps> = ({ close, workoutId }) => {
  const exercises = useSelector(
    (state: RootState) => state.exercises.exercises
  );
  const user = useSelector((state: RootState) => state.auth.user);

  const sendMessage = useSendMessage();

  const dispatch: AppDispatch = useDispatch();

  const [sets, setSets] = useState<number>(3);
  const [reps, setReps] = useState<number | null>(10);
  const [isFailure, setIsFailure] = useState<boolean>(false);
  const [weight, setWeight] = useState<number>(60);
  const [exercise, setExercise] = useState<Exercise | null>(null);
  const [selectedCategory, setSelectedCategory] = useState<string>("");

  useEffect(() => {
    if (exercises.length === 0) {
      dispatch(getExercises());
    }
  }, [dispatch, exercises.length]);

  const uniqueCategories = Array.from(
    new Set(exercises.map((exercise) => exercise.category))
  );

  // Filter exercises based on the selected category, or show all if no category is selected
  const filteredExercises = selectedCategory
    ? exercises.filter((exercise) => exercise.category === selectedCategory)
    : exercises;

  const handleSubmitForm = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (!user) {
      sendMessage("Failed to get user.");
      return;
    }
    if (!exercise) {
      sendMessage("Please select an exercise.");
      return;
    }

    dispatch(
      addExercise({
        workoutId,
        sets,
        reps,
        weight,
        exerciseId: exercise.id,
        userId: user.id,
      })
    )
      .unwrap()
      .then(() => {
        close();
      })
      .catch(() => {
        sendMessage("Failed to add exercise");
      });
  };

  const handleCategoryChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const selectedCategory = e.target.value;
    setSelectedCategory(selectedCategory);
    setExercise(null); // Reset selected exercise when category changes
  };

  const handleExerciseChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const selectedExercise = e.target.value;
    const foundExercise = exercises.find((ex) => ex.name === selectedExercise);
    setExercise(foundExercise || null);
  };

  const handleRepsChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setReps(Number(e.target.value));
  };

  const handleFailureChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const checked = e.target.checked;
    setIsFailure(checked);
    setReps(checked ? null : 10);
  };

  return (
    <div className={styles["container"]}>
      <h1>Add Exercise</h1>
      <Notification />
      <form onSubmit={handleSubmitForm}>
        <div className={styles["formItem"]}>
          <label htmlFor="exerciseFor">Exercise for:</label>
          <select
            name="exerciseFor"
            id="exerciseFor"
            value={selectedCategory}
            onChange={handleCategoryChange}
          >
            <option value="">Select muscle group</option>
            {uniqueCategories.map((category) => (
              <option key={category} value={category}>
                {category}
              </option>
            ))}
          </select>
        </div>
        <div className={styles["formItem"]}>
          <label htmlFor="exercise">Exercise:</label>
          <select
            name="exercise"
            id="exercise"
            value={exercise?.name || ""}
            onChange={handleExerciseChange}
          >
            <option value="">Select exercise</option>
            {filteredExercises.map((ex) => (
              <option key={ex.id} value={ex.name}>
                {ex.name}
              </option>
            ))}
          </select>
        </div>
        <div className={styles["longFormItem"]}>
          <div className={styles["formItem"]}>
            <div className={`${styles["formItem"]} ${styles["reps"]}`}>
              <label htmlFor="reps">Reps:</label>
              <input
                name="reps"
                id="reps"
                type="number"
                min={1}
                value={typeof reps === "number" ? reps : ""}
                onChange={handleRepsChange}
                disabled={isFailure}
              />
            </div>
            <div className={styles["toFailure"]}>
              <label>Failure</label>
              <input
                type="checkbox"
                checked={isFailure}
                onChange={handleFailureChange}
              />
            </div>
          </div>
          <div className={styles["formItem"]}>
            <label htmlFor="sets">Sets:</label>
            <input
              name="sets"
              id="sets"
              type="number"
              min={1}
              value={sets}
              onChange={(e) => setSets(Number(e.target.value))}
            />
          </div>
          <div className={styles["formItem"]}>
            <label htmlFor="weight">Weight:</label>
            <input
              name="weight"
              id="weight"
              type="number"
              min={1}
              value={weight}
              onChange={(e) => setWeight(Number(e.target.value))}
            />
          </div>
        </div>
        <div className={styles["formButtons"]}>
          <button type="button" className="defaultBorderStyle" onClick={close}>
            Cancel
          </button>
          <button className="defaultBtnStyle">Add Exercise</button>
        </div>
      </form>
      <button
        className={`defaultBorderStyle ${styles["close"]}`}
        onClick={close}
      >
        X
      </button>
    </div>
  );
};

export default ExercisePopup;
