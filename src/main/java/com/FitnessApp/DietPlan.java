package com.FitnessApp;

public class DietPlan {
    public static final DietPlan DEFAULT = new DietPlan("Default", 2500, 200, 60, 150);
    public static final DietPlan COMPETITION_PREPARATION = new DietPlan("Competition Preparation", 3000, 300, 80, 150);
    public static final DietPlan WEIGHT_LOSS = new DietPlan("Weight loss", 2000, 200, 60, 180);
    public static final DietPlan WEIGHT_GAIN = new DietPlan("Weight gain", 3400, 300, 150, 200);
    public static final DietPlan CUSTOM = new DietPlan("Custom", 2700, 80, 40, 200);

    private final String displayName;
    private final double calories;
    private final double carbs;
    private final double fat;
    private final double protein;

    private DietPlan(String displayName, double calories, double carbs, double fat, double protein) {
        this.displayName = displayName;
        this.calories = calories;
        this.carbs = carbs;
        this.fat = fat;
        this.protein = protein;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getCalories() {
        return calories;
    }

    public double getCarbs() {
        return carbs;
    }

    public double getFat() {
        return fat;
    }

    public double getProtein() {
        return protein;
    }

    @Override
    public String toString() {
        return displayName;
    }
}