export type Workouts = Workout[];

export interface Workout {
  id: number;
  userId: number;
  name: string;
  dayOfWorkout: string;
  timeOfWorkout: string;
  progress: WorkoutProgress;
}

export interface WorkoutProgress {
  id: number;
  userId: number;
  completed: boolean;
  date: Date;
  exercises: WorkoutProgressExercise[];
}

export interface WorkoutProgressExercise {
  exercise: ExerciseWorkout;
  id: number;
  repsDone: number;
  setsDone: number;
  weightUsed: number;
}

export interface ExerciseWorkout {
  sets: number;
  reps: number;
  id: number;
  failure: boolean;
  weight: number;
  exercise: Exercise;
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
