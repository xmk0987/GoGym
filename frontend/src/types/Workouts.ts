export type Workouts = Workout[];

export interface Workout {
  id: number;
  userId: number;
  name: string;
  dayOfWorkout: string;
  timeOfWorkout: string;
  progress: WorkoutProgress;
  exercises: ExerciseWorkout[];
}

export interface WorkoutProgress {
  completed: boolean;
  date: Date;
}

export interface ExerciseWorkout {
  sets: number;
  reps: number;
  id: number;
  failure: boolean;
  weight: number;
  exercise: Exercise;
  progress: ExerciseProgress;
}

export interface ExerciseProgress {
  setsDone: number;
  weightUsed: number;
  date: string;
  id: number;
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
