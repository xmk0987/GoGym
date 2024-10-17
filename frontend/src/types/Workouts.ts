export type Workouts = Workout[];

export interface Workout {
  id: number;
  userId: number;
  name: string;
  workoutExercises: ExerciseWorkout[];
  dayOfWorkout: string;
  timeOfWorkout: string;
}

export interface Exercise {
  id: number;
  name: string;
  category: string;
  muscles: string;
  equipment: string;
  difficulty: string;
  instructions: string;
  musclesList: string[];
}

export interface ExerciseWorkout {
  id: number;
  reps: number;
  sets: number;
  failure: boolean;
  exercise: Exercise;
  weight: number;
}
