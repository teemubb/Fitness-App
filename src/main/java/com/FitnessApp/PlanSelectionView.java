package com.FitnessApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlanSelectionView extends JPanel {
    private UserInterface ui;
    private JComboBox<DietPlan> planComboBox;
    private JProgressBar calorieBar, carbsBar, fatBar, proteinBar;
    private User user;
    private DietPlan selectedPlan;
    private DietPlan[] dietPlans = {DietPlan.DEFAULT, DietPlan.COMPETITION_PREPARATION, DietPlan.WEIGHT_GAIN, DietPlan.WEIGHT_LOSS, DietPlan.CUSTOM};

    public enum DietPlan {
        DEFAULT("Default", 2500, 200, 60, 150),
        COMPETITION_PREPARATION("Competition Preparation", 3000, 300, 80, 150),
        WEIGHT_LOSS("Weight loss", 2000, 200, 60, 180),
        WEIGHT_GAIN("Weight gain", 3400, 300, 150, 200),
        CUSTOM("Custom", 2700, 80, 40, 200);

        private final String name;
        private final int calories;
        private final int carbs;
        private final int fat;
        private final int protein;

        DietPlan(String name, int calories, int carbs, int fat, int protein) {
            this.name = name;
            this.calories = calories;
            this.carbs = carbs;
            this.fat = fat;
            this.protein = protein;
        }

        public String getName() {
            return name;
        }

        public int getCalories() {
            return calories;
        }

        public int getCarbs() {
            return carbs;
        }

        public int getFat() {
            return fat;
        }

        public int getProtein() {
            return protein;
        }

        @Override
        public String toString() {
            return name;
        }
    }
    public PlanSelectionView(UserInterface ui) {
        this.ui = ui;
    }
    public PlanSelectionView(UserInterface ui, User user) {
        this.ui = ui;
        this.user = user;
        setupUI(dietPlans);
        this.selectedPlan = DietPlan.DEFAULT;
    }

    private void setupUI(DietPlan[] dietPlans) {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        addButton("Save and Return", gbc, 0, 0, e -> ui.switchView(ui.getQuickMenuView()));
        addLabel("Select Plan:", gbc, 0, 1);
        planComboBox = new JComboBox<>(dietPlans);
        planComboBox.addActionListener(e -> planSelected());
        addComponent(planComboBox, gbc, 0, 2);

        addLabel("Caloric target", gbc, 0, 3);
        calorieBar = createProgressBar("2500 Kcal", 2500);
        addComponent(calorieBar, gbc, 0, 4);

        addLabel("Nutritional Targets", gbc, 0, 5);
        carbsBar = createProgressBar("Carbs: 0%", 0);
        addComponent(carbsBar, gbc, 0, 6);
        fatBar = createProgressBar("Fat: 0%", 0);
        addComponent(fatBar, gbc, 0, 7);
        proteinBar = createProgressBar("Protein: 0%", 0);
        addComponent(proteinBar, gbc, 0, 8);

        updateNutritionalValues();
    }


    private void addButton(String text, GridBagConstraints gbc, int x, int y, ActionListener action) {
        JButton button = new JButton(text);
        gbc.gridx = x;
        gbc.gridy = y;
        this.add(button, gbc);
        button.addActionListener(action);
    }

    private void addLabel(String text, GridBagConstraints gbc, int x, int y) {
        JLabel label = new JLabel(text);
        gbc.gridx = x;
        gbc.gridy = y;
        this.add(label, gbc);
    }

    private void addComponent(JComponent component, GridBagConstraints gbc, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        this.add(component, gbc);
    }

    private JProgressBar createProgressBar(String initialText, int initialValue) {
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue(initialValue);
        progressBar.setStringPainted(true);
        progressBar.setString(initialText);
        return progressBar;
    }

    private void planSelected() {
        selectedPlan = (DietPlan) planComboBox.getSelectedItem();
        System.out.println("Plan selected: " + selectedPlan);
        updateNutritionalValues();
    }

    private void updateNutritionalValues() {
        if(selectedPlan == null){
            System.out.println("this is a bug.");
            return;
        }
        double totalCalories = selectedPlan.getCalories();
        calorieBar.setValue((int) totalCalories);
        calorieBar.setString("Calories: " + (int) totalCalories + " Kcal");

        double totalCarbs = selectedPlan.getCarbs();
        double totalFat = selectedPlan.getFat();
        double totalProtein = selectedPlan.getProtein();
        double totalNutrients = totalCarbs + totalFat + totalProtein;

        carbsBar.setValue((int) ((totalCarbs / totalNutrients) * 100));
        carbsBar.setString("Carbs: " + (int) ((totalCarbs / totalNutrients) * 100) + "%");

        fatBar.setValue((int) ((totalFat / totalNutrients) * 100));
        fatBar.setString("Fat: " + (int) ((totalFat / totalNutrients) * 100) + "%");

        proteinBar.setValue((int) ((totalProtein / totalNutrients) * 100));
        proteinBar.setString("Protein: " + (int) ((totalProtein / totalNutrients) * 100) + "%");
    }

    public double getTotalCalories() {
        return selectedPlan.getCalories();
    }

    public double getTotalCarbs() {
        return selectedPlan.getCarbs();
    }

    public double getTotalFat() {
        return selectedPlan.getFat();
    }

    public double getTotalProtein() {
        return selectedPlan.getProtein();
    }
}
