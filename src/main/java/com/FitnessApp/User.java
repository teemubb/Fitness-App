package com.FitnessApp;

import java.util.ArrayList;
import java.util.List;

public class User implements MealListener, ExerciseListener{
    private Units units;
    private Sex userSex;
    private double userHeight;
    private double userWeight;
    private List<Meal> mealList;
    private String sport;
    private List<Exercise> exerciseList;
    private PlanSelection currentPlan;
    private double dailyCaloriesIntake = 0;
    private double dailyCarbsIntake = 0;
    private double dailyFatIntake = 0;
    private double dailyProteinIntake = 0;



    public enum Sex{
        MALE("Male"),
        FEMALE("Female"),
        PREFER_NOT_TO_SAY("Prefer not to say");
        private final String description;
        Sex(String description){
            this.description = description;
        }
        public String toString(){
            return this.description;
        }
    }
    public enum Units {
        METRIC("Metric"),
        IMPERIAL("Imperial");;
        private final String description;
        Units(String description){
            this.description = description;
        }
        public String toString(){
            return this.description;
        }
    }

    public User(){
        this.units = Units.METRIC;
        this.userSex = Sex.PREFER_NOT_TO_SAY;
        this.userHeight = 1.0;
        this.userWeight = 1.0;
        this.sport = "no sport";
        mealList = new ArrayList<Meal>();
        exerciseList = new ArrayList<Exercise>();
        currentPlan = new PlanSelection();
    }

    public void consumeCalories(double calories) {
        this.dailyCaloriesIntake += calories;
    }

    public void consumeCarbs(double carbs) {
        this.dailyCarbsIntake += carbs;
    }

    public void consumeFat(double fat) {
        this.dailyFatIntake += fat;
    }

    public void consumeProtein(double protein) {
        this.dailyProteinIntake += protein;
    }

    // Getter methods for the daily intakes
    public double getDailyCaloriesIntake() {
        return dailyCaloriesIntake;
    }

    public double getDailyCarbsIntake() {
        return dailyCarbsIntake;
    }

    public double getDailyFatIntake() {
        return dailyFatIntake;
    }

    public double getDailyProteinIntake() {
        return dailyProteinIntake;
    }

    public void resetDailyIntakes() {
        dailyCaloriesIntake = 0;
        dailyCarbsIntake = 0;
        dailyFatIntake = 0;
        dailyProteinIntake = 0;
    }
    public String getSport(){
        return this.sport;
    }
    public void setSport(String sport){
        if(!sport.isEmpty()){
            this.sport = sport;
        }
    }

    public List<Meal> getMealList() {
        return mealList;
    }
    public List<Exercise> getExerciseList() {
        return exerciseList;
    }

    public Units getUnits() {
        return units;
    }

    public void setUnits(Units units) {
        this.units = units;
    }

    public Sex getUserSex(){
        return userSex;
    }
    public void setUserSex(Sex sex){
        this.userSex = sex;
    }
    public double getUserHeight() {
        return userHeight;
    }

    public void setUserHeight(double userHeight) {
        if(userHeight > 0.0){
            this.userHeight = userHeight;
        }
    }
    public double getUserWeight() {
        return userWeight;
    }

    public void setUserWeight(double userWeight){
        if(userWeight > 0.0){
            this.userWeight = userWeight;
        }
    }
    public void addMeal(Meal meal){
        mealList.add(meal);
    }
    public void addWorkout(Exercise exercise){
        exerciseList.add(exercise);
    }
    public String printMeals(){
        StringBuilder sb = new StringBuilder();
        for(Meal meal : mealList){
            sb.append(meal.toString());
        }
        return sb.toString();
    }
    public String printExercises(){
        StringBuilder sb = new StringBuilder();
        sb.append("DEBUG:");
        for(Exercise exercise : exerciseList){
            sb.append(exercise.toString());
        }
        return sb.toString();
    }
    @Override
    public void mealAdded(Meal meal) {
        addMeal(meal);
    }
    @Override
    public void exerciseAdded(Exercise exercise) {
        addWorkout(exercise);
        printExercises();
    }

}
