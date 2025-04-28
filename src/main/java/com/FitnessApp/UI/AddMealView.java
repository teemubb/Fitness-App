package com.FitnessApp.UI;

import com.Database.Postgre;
import com.FitnessApp.events.MealEvent;
import com.FitnessApp.listeners.MealListener;
import com.FitnessApp.Obj.Meal;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class AddMealView extends JPanel {
    private UserInterface ui;
    private JTextField nameField, calField, fatField, proteinField, carbsField;
    private JButton addButton;
    private List<MealListener> mealListeners = new ArrayList<>();
    private Postgre db;

    public AddMealView(UserInterface ui) {
        this.ui = ui;
        this.db = Postgre.getInstance();
        setupUI();
    }

    private void setupUI() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Top Label
        JLabel label = new JLabel("Add Meal");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(label, gbc);

        // Setup input fields
        setupInputFields(gbc);

        // Setup Add Button
        setupAddButton(gbc);
    }

    private void setupInputFields(GridBagConstraints gbc) {
        String[] fieldNames = {"Meal Name", "Calories", "Fat", "Protein", "Carbohydrates"};
        JTextField[] fields = new JTextField[fieldNames.length];

        for (int i = 0; i < fields.length; i++) {
            fields[i] = new JTextField(10);
            gbc.gridx = 0;
            gbc.gridy = i + 1;
            gbc.gridwidth = 1;
            this.add(new JLabel(fieldNames[i] + ":"), gbc);
            gbc.gridx = 1;
            this.add(fields[i], gbc);
        }

        nameField = fields[0];
        calField = fields[1];
        fatField = fields[2];
        proteinField = fields[3];
        carbsField = fields[4];
    }

    private void setupAddButton(GridBagConstraints gbc) {
        addButton = new JButton("Add Meal");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        this.add(addButton, gbc);
        addButton.addActionListener(e -> addMeal());
    }

    private void addMeal() {
        try {
            String name = nameField.getText();
            double calories = Double.parseDouble(calField.getText());
            double fat = Double.parseDouble(fatField.getText());
            double protein = Double.parseDouble(proteinField.getText());
            double carbs = Double.parseDouble(carbsField.getText());

            if (calories < 0 || fat < 0 || protein < 0 || carbs < 0) {
                JOptionPane.showMessageDialog(this, "Please enter non-negative values.");
            } else {
                Meal meal = new Meal(name, calories, fat, protein, carbs);
                //ui.getUser().addMeal(meal); // useless
                System.out.println("test1");
                db.addMeal(name, calories, fat, protein, carbs);
                System.out.println("test2");

                fireMealAdded(meal);
                JOptionPane.showMessageDialog(this, "Meal added successfully!");
                ui.switchView(ui.getMainView()); // Return to main view after adding
            }
        } catch (NumberFormatException err) {
            JOptionPane.showMessageDialog(this, "Please input only numbers in the fields.");
        } catch (Exception err) {
            JOptionPane.showMessageDialog(this, "An error occurred. Please try again.");
        }
    }
    public void addMealListener(MealListener listener) {
        mealListeners.add(listener);
    }

    // Method to remove listeners
    public void removeMealListener(MealListener listener) {
        mealListeners.remove(listener);
    }

    // Notify all listeners about a new meal
    private void fireMealAdded(Meal meal) {
        MealEvent event = new MealEvent(this, meal);
        for (MealListener listener : mealListeners) {
            listener.mealAdded(event.getMeal());
        }
    }
}
