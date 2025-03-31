package com.FitnessApp;

public class HardcodedFood {
    private Meal spaghetti;
    private Meal peasoup;

    public HardcodedFood() {
        this.spaghetti = new Meal("Spaghetti bolognese", 127, 3.2, 4.8, 19);
        this.peasoup = new Meal("Pea soup", 100, 100, 100, 100);
    }
}
