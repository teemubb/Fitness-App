package com.FitnessApp;

import java.util.EventListener;

public interface ExerciseListener extends EventListener {
    void exerciseAdded(Exercise exercise);
}
