package com.FitnessApp.Obj;

public class Meal extends Item {
    private double calories;
    private double fat;
    private double protein;
    private double carbohydrates;
    private long datemilli;

    public Meal() {
        super("default name");
        calories = 0;
        fat = 0;
        protein = 0;
        carbohydrates = 0;
    }
    //constructor for new meal
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
        this.datemilli = System.currentTimeMillis();
    }

    // constructor for meal from database
    public Meal(String n, double c, double f, double p, double ch, long date){
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
        this.datemilli = date;

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
                .append("\n\uD83D\uDD25 Calories: ").append(calories)
                .append("\n\uD83E\uDD51 Fat: ").append(fat)
                .append("\n\uD83C\uDF57 Protein: ").append(protein)
                .append("\n\uD83C\uDF3E Carbohydrates: ").append(carbohydrates)
                .append("\n\uD83D\uDD52 Timestamp: ").append(getFormattedTimestamp())
                .append("\n");
        return sb.toString();
    }
    public String mainString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\uD83D\uDD52 " + getFormattedTimestamp())
                .append("\n \uD83C\uDF7D " + getName())
                .append(" \uD83D\uDD25").append(calories + " kcal");
        return sb.toString();
    }

    public String getFormattedTimestamp() {
        // Use java.util.Date to convert the timestamp to a human-readable format
        java.util.Date date = new java.util.Date(datemilli);
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(datemilli);
    }
}
