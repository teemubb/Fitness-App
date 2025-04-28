package com.FitnessApp.UI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

class QuickMenuView extends JPanel {
    private UserInterface ui;

    public QuickMenuView(UserInterface ui) {
        this.ui = ui;
        setupUI();
    }

    private void setupUI() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST; // Ensuring all buttons align to the left

        // Menu button to return to the main view
        addButton("≡", gbc, 0, e -> ui.switchView(ui.getMainView()));

        // Button to open plan selection view
        addButton("Plan Selection", gbc, 1, e -> ui.switchView(ui.getPlanSelectionView()));
        addButton("Workouts", gbc, 2, e -> ui.switchView(ui.getWorkoutView()));

        // Button to open summary view
        addButton("Summary", gbc, 3, e -> ui.switchView(ui.getSummaryView()));

        // Button to open add meal view
        addButton("Add Meal", gbc, 5, e -> ui.switchView(ui.getAddView()));

        addButton("Add workout", gbc, 6, e -> ui.switchView(ui.getAddWorkoutView()));

        // Button to open settings view
        addButton("Settings", gbc, 7, e -> ui.switchView(ui.getSettingsView()));

        // Button to exit the application
        addButton("Exit", gbc, 8, e -> System.exit(0));
    }

    private void addButton(String text, GridBagConstraints gbc, int gridy, ActionListener action) {
        JButton button = new JButton(text);
        gbc.gridx = 0;
        gbc.gridy = gridy;
        button.addActionListener(action);
        this.add(button, gbc);
    }
}
