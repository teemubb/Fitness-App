package com.FitnessApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

public class AddWorkoutView extends JPanel {
    private UserInterface ui;
    private JComboBox<String> exerciseTypeDropdown;
    private JTextField durationField;
    private JButton addButton;
    private List<ExerciseListener> exerciseListeners = new ArrayList<>();

    public AddWorkoutView(UserInterface ui) {
        this.ui = ui;
        setupUI();
    }

    private void setupUI() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel workoutLabel = new JLabel("Add a Workout");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(workoutLabel, gbc);

        setupExerciseTypeDropdown(gbc);
        setupDurationField(gbc);
        setupAddButton(gbc);
    }

    private void setupExerciseTypeDropdown(GridBagConstraints gbc) {
        String[] exerciseTypes = {"Running", "Swimming", "Cycling", "Weightlifting"};
        exerciseTypeDropdown = new JComboBox<>(exerciseTypes);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        this.add(new JLabel("Exercise Type:"), gbc);
        gbc.gridy = 2;
        this.add(exerciseTypeDropdown, gbc);
    }

    private void setupDurationField(GridBagConstraints gbc) {
        durationField = new JTextField(10);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        this.add(new JLabel("Duration (minutes):"), gbc);
        gbc.gridx = 1;
        this.add(durationField, gbc);
    }

    private void setupAddButton(GridBagConstraints gbc) {
        addButton = new JButton("Add Workout");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        this.add(addButton, gbc);
        addButton.addActionListener(e -> addWorkout());
    }

    private void addWorkout() {
        try {
            String exerciseType = (String) exerciseTypeDropdown.getSelectedItem();
            double duration = Double.parseDouble(durationField.getText());

            if (duration <= 0) {
                JOptionPane.showMessageDialog(this, "Please enter a positive value for duration.");
            }
            else{
            Exercise workout = new Exercise(exerciseType, "nice", exerciseType, duration);
            ui.getUser().addWorkout(workout);
            fireExerciseAdded(workout);
            JOptionPane.showMessageDialog(this, "Workout added successfully!");
            ui.switchView(ui.getMainView());

            }
        } catch (NumberFormatException err) {
            JOptionPane.showMessageDialog(this,"You cannot add a workout with negative values!");
        } catch (Exception err) {
            JOptionPane.showMessageDialog(this,"An error occurred. Please try again.");
        }
    }

    public void addExerciseListener(ExerciseListener listener) {
        exerciseListeners.add(listener);
    }

    public void removeExerciseListener(ExerciseListener listener) {
        exerciseListeners.remove(listener);
    }

    private void fireExerciseAdded(Exercise exercise) {
        ExerciseEvent event = new ExerciseEvent(this, exercise);
        for (ExerciseListener listener : exerciseListeners) {
            try {
                listener.exerciseAdded(event.getExercise());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}