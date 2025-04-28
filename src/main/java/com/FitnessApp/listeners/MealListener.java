package com.FitnessApp.listeners;

import com.FitnessApp.Obj.Meal;

import java.util.EventListener;

public interface MealListener extends EventListener {
    void mealAdded(Meal meal);
}
