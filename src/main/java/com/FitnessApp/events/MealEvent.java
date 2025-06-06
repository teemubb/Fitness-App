package com.FitnessApp.events;
import com.FitnessApp.Obj.Meal;

import java.util.EventObject;

public class MealEvent extends EventObject{
    private Meal meal;
    public MealEvent(Object source, Meal meal) {
        super(source);
        this.meal = meal;
    }

    public Meal getMeal() {
        return meal;
    }
}
