package com.FitnessApp;

import java.util.EventObject;

public class ExerciseEvent extends EventObject {
    private Exercise exercise;
    public ExerciseEvent(Object source, Exercise exercise) {
        super(source);
        this.exercise = exercise;
    }
    public Exercise getExercise() {
        return exercise;
    }
}
