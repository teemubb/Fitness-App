package com.FitnessApp.Obj;

public class Exercise extends Item {
    private String description;
    private String exerciseType;
    private double caloriesBurned;
    private double duration;

    public Exercise(String name, String description, String exerciseType, double duration) {
        super(name);
        if(description != null && !description.isEmpty()){
            this.description = description;
        }
        if(exerciseType != null && !exerciseType.isEmpty()){
            this.exerciseType = exerciseType;
        }
        if(duration > 0){
            this.duration = duration;
        }
        caloriesBurned = 10;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        if(description != null && !description.isEmpty()){
            this.description = description;
        }
    }
    public String getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(String exerciseType) {
        if(exerciseType != null && !exerciseType.isEmpty()){
            this.exerciseType = exerciseType;
        }
    }
    public double getCaloriesBurned() {
        return caloriesBurned;
    }
    public void setCaloriesBurned(double caloriesBurned) {
        if(caloriesBurned >  0){
            this.caloriesBurned = caloriesBurned;
        }
    }
    public double getDuration() {
        return duration;
    }
    public void setDuration(double duration) {
        if(duration > 0){
            this.duration = duration;
        }
    }

    public String toString(){
        return "Name: " + getName() +
                "\nTimestamp: " + getTimestamp() +
                "\nDescription: " + getDescription() +
                "\nExercise Type: " + getExerciseType() +
                "\nCalories Burned: " + getCaloriesBurned()+
                "\nDuration: " + getDuration();
    }



}
