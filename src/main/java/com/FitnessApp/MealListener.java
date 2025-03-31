package com.FitnessApp;

import java.util.EventListener;

public interface MealListener extends EventListener {
    void mealAdded(Meal meal);
}
