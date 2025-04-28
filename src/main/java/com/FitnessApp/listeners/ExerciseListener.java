package com.FitnessApp.listeners;

import com.FitnessApp.Obj.Exercise;

import java.util.EventListener;

public interface ExerciseListener extends EventListener {
    void exerciseAdded(Exercise exercise);
}
