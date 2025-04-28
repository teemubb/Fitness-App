package com.FitnessApp.UI;

import com.FitnessApp.listeners.MealListener;
import com.FitnessApp.Obj.Meal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SummaryView extends JPanel implements MealListener {
    UserInterface ui;
    private JProgressBar totalCaloriesProgressBar, carbsProgressBar, fatProgressBar, proteinProgressBar;
    private PlanSelectionView planSelectionView;

    public SummaryView(UserInterface ui, PlanSelectionView planSelectionView) {
        this.ui = ui;
        this.planSelectionView = planSelectionView;
        setupUI();
    }

    private void setupUI() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Menu button to return to the quick menu view
        addButton("≡ Main Menu", gbc, 0, 0, e -> ui.switchView(ui.getQuickMenuView()));

        // Label for total daily calories
        addLabel("Total Daily Calories", gbc, 0, 1, 2);

        // Progress bar for total calories
        totalCaloriesProgressBar = new JProgressBar(0, 3000);
        totalCaloriesProgressBar.setStringPainted(true);
        totalCaloriesProgressBar.setString("0 / 3000 Kcal");
        addComponent(totalCaloriesProgressBar, gbc, 0, 2, 2);

        // Label for nutrients
        addLabel("Nutrients", gbc, 0, 3, 2);

        // Progress bars for nutritional values
        carbsProgressBar = createNutrientProgressBar("Carbs: 0%");
        addComponent(carbsProgressBar, gbc, 0, 4, 1);

        fatProgressBar = createNutrientProgressBar("Fat: 0%");
        addComponent(fatProgressBar, gbc, 0, 5, 1);

        proteinProgressBar = createNutrientProgressBar("Protein: 0%");
        addComponent(proteinProgressBar, gbc, 0, 6, 1);

        // Separator for aesthetic division
        JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        this.add(separator, gbc);

        // Labels for target nutrition values
        addLabel("Selected Targets: ", gbc, 0, 8, 2);
        addLabel("Target Calories: 0 Kcal", gbc, 0, 9, 1);
        addLabel("Target Carbs: 0%", gbc, 0, 10, 1);
        addLabel("Target Fat: 0%", gbc, 0, 11, 1);
        addLabel("Target Protein: 0%", gbc, 0, 12, 1);

        // Dummy listener for demonstration purposes
    }
    private void addLabel(String text, GridBagConstraints gbc, int x, int y, int width) {
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        this.add(label, gbc);
    }

    private void addButton(String text, GridBagConstraints gbc, int x, int y, ActionListener action) {
        JButton button = new JButton(text);
        gbc.gridx = x;
        gbc.gridy = y;
        button.addActionListener(action);
        this.add(button, gbc);
    }

    private void addComponent(JComponent component, GridBagConstraints gbc, int x, int y, int width) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        this.add(component, gbc);
    }

    private JProgressBar createNutrientProgressBar(String initialText) {
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setString(initialText);
        return progressBar;
    }

    private void updateProgressBars(Meal meal) {
        // Simulated data; replace with actual data retrieval jostain mealista hakemaan..
        double totalCalories = meal.getCalories();
        double totalCarbs = meal.getCarbohydrates();
        double totalFat = meal.getFat();
        double totalProtein = meal.getProtein();
        double totalNutrients = totalCarbs + totalFat + totalProtein;

        totalCaloriesProgressBar.setValue((int) totalCalories);
        totalCaloriesProgressBar.setString(totalCalories + " / " + planSelectionView.getTotalCalories());

        carbsProgressBar.setValue((int) ((totalCarbs / totalNutrients) * 100));
        carbsProgressBar.setString("Carbs: " + (int) ((totalCarbs / totalNutrients) * 100) + "%");

        fatProgressBar.setValue((int) ((totalFat / totalNutrients) * 100));
        fatProgressBar.setString("Fat: " + (int) ((totalFat / totalNutrients) * 100) + "%");

        proteinProgressBar.setValue((int) ((totalProtein / totalNutrients) * 100));
        proteinProgressBar.setString("Protein: " + (int) ((totalProtein / totalNutrients) * 100) + "%");
    }

    private void updateNutritionalValues() {
        // Retrieving targets from PlanSelectionView
        double targetCalories = planSelectionView.getTotalCalories();
        double targetCarbs = planSelectionView.getTotalCarbs();
        double targetFat = planSelectionView.getTotalFat();
        double targetProtein = planSelectionView.getTotalProtein();
    
        // Updating labels with retrieved target values
        // Target Calories
        JLabel targetCaloriesLabel = (JLabel) this.getComponent(9); // Index 9 corresponds to the label "Target Calories: 0 Kcal"
        targetCaloriesLabel.setText("Target Calories: " + (int) targetCalories + " Kcal");
    
        // Target Carbs
        JLabel targetCarbsLabel = (JLabel) this.getComponent(10); 
        targetCarbsLabel.setText("Target Carbs: " + (int) targetCarbs + "%");
    
        // Target Fat
        JLabel targetFatLabel = (JLabel) this.getComponent(11); 
        targetFatLabel.setText("Target Fat: " + (int) targetFat + "%");
    
        // Target Protein
        JLabel targetProteinLabel = (JLabel) this.getComponent(12); 
        targetProteinLabel.setText("Target Protein: " + (int) targetProtein + "%");
    
    }

    @Override
    public void mealAdded(Meal meal) {
        updateProgressBars(meal);
    }
}
