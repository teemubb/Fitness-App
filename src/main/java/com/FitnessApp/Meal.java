package com.FitnessApp;

public class Meal extends Item {
    private double calories;
    private double fat;
    private double protein;
    private double carbohydrates;

    public Meal() {
        super("default name");
        calories = 0;
        fat = 0;
        protein = 0;
        carbohydrates = 0;
    }
    public Meal(String n, double c, double f, double p, double ch){
        super(n);
        if(c > 0){
            calories = c;
        }
        if(f > 0){
            fat = f;
        }
        if(p > 0){
            protein = p;
        }
        if(ch > 0){
            carbohydrates = ch;
        }

    }

    public double getCalories() {
        return calories;
    }
    public void setCalories(double calories){
        if(calories > 0){
            this.calories = calories;
        }
    }
    public double getFat(){
        return fat;
    }

    public void setFat(double fat){
        if(fat > 0){
            this.fat = fat;
        }
    }

    public double getProtein(){
        return protein;
    }

    public void setProtein(double protein){
        if(protein > 0){
            this.protein = protein;
        }
    }
    public double getCarbohydrates(){
        return carbohydrates;
    }
    public void setCarbohydrates(double carbohydrates){
        if(carbohydrates > 0){
            this.carbohydrates = carbohydrates;
        }
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(getName())
                .append("\nCalories: ").append(calories)
                .append("\nFat: ").append(fat)
                .append("\nProtein: ").append(protein)
                .append("\nCarbohydrates: ").append(carbohydrates)
                .append("\nTimestamp:").append(getTimestamp())
                .append("\n");
        return sb.toString();
    }
    public String mainString(){
        StringBuilder sb = new StringBuilder();
        sb.append(getFormattedTimestamp())
                .append(" " + getName())
                .append(" ").append(calories + " kCal");
        return sb.toString();
    }

}
