package com.FitnessApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

public class SettingsView extends JPanel {
    private UserInterface ui;
    private User user;

    public SettingsView(UserInterface ui, User user) {
        this.ui = ui;
        this.user = user;
        setupUI();
    }

    private void setupUI() {
        this.setLayout(new BorderLayout());

        // Top panel with main menu button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton mainMenuButton = new JButton("≡ Main Menu");
        mainMenuButton.setPreferredSize(new Dimension(200, 50));
        mainMenuButton.setFont(new Font("Arial", Font.BOLD, 20));
        mainMenuButton.addActionListener(e -> ui.switchView(ui.getQuickMenuView()));
        topPanel.add(mainMenuButton);
        this.add(topPanel, BorderLayout.NORTH);

        // Settings panel
        JPanel settingsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        // Buttons for settings adjustments
        addButton("Select Units", e -> displayUnitsOptions(), settingsPanel, gbc);
        addButton("Select Sex", e -> displaySexOptions(), settingsPanel, gbc);
        addButton("Change Height", e -> displayHeightPopup(), settingsPanel, gbc);
        addButton("Change Weight", e -> displayWeightPopup(), settingsPanel, gbc);
        addButton("Add sport", e -> displayAddSportDialog(), settingsPanel, gbc);

        this.add(settingsPanel, BorderLayout.CENTER);
    }

    private void addButton(String label, ActionListener listener, JPanel panel, GridBagConstraints gbc) {
        JButton button = new JButton(label);
        button.setPreferredSize(new Dimension(200, 50));
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.addActionListener(listener);
        panel.add(button, gbc);
        gbc.gridy++;
    }

    private void displayUnitsOptions() {
        JRadioButton metricButton = new JRadioButton("Metric");
        JRadioButton imperialButton = new JRadioButton("Imperial");

        ButtonGroup group = new ButtonGroup();
        group.add(metricButton);
        group.add(imperialButton);

        if (user.getUnits() == User.Units.METRIC) {
            metricButton.setSelected(true);
        } else {
            imperialButton.setSelected(true);
        }

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(metricButton);
        panel.add(imperialButton);

        int result = JOptionPane.showConfirmDialog(this, panel, "Select Units",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            user.setUnits(metricButton.isSelected() ? User.Units.METRIC : User.Units.IMPERIAL);
        }
    }

    private void displaySexOptions() {
        // Generate options array dynamically from the enum
        User.Sex[] sexes = User.Sex.values();
        String[] options = new String[sexes.length];
        for (int i = 0; i < sexes.length; i++) {
            options[i] = sexes[i].toString();
        }

        // Determine the current selection based on the user's current sex
        User.Sex currentSex = user.getUserSex();
        int currentSelection = -1;
        for (int i = 0; i < options.length; i++) {
            if (options[i].equalsIgnoreCase(currentSex.toString())) {
                currentSelection = i;
                break;
            }
        }

        // Show option dialog to select sex
        int response = JOptionPane.showOptionDialog(
                this,
                "Select Sex:",
                "Sex",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                currentSelection >= 0 ? options[currentSelection] : null
        );

        // Update user sex if a valid selection was made
        if (response >= 0 && response < options.length) {
            user.setUserSex(sexes[response]);
        }
    }

    private void displayHeightPopup() {
        JTextField heightField = new JTextField(Double.toString(user.getUserHeight()), 10);
        displayInputPopup("Enter new height:", heightField, value -> user.setUserHeight(value));
    }

    private void displayWeightPopup() {
        JTextField weightField = new JTextField(Double.toString(user.getUserWeight()), 10);
        displayInputPopup("Enter new weight:", weightField, value -> user.setUserWeight(value));
    }

    private void displayInputPopup(String message, JTextField inputField, Consumer<Double> saveAction) {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel(message));
        panel.add(inputField);

        JButton confirmButton = new JButton("Confirm");
        JButton backButton = new JButton("Back");

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(confirmButton);
        buttonsPanel.add(backButton);
        panel.add(buttonsPanel);

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Modify Value", true);
        dialog.setLayout(new BorderLayout());
        dialog.add(panel, BorderLayout.CENTER);

        confirmButton.addActionListener(e -> {
            try {
                double value = Double.parseDouble(inputField.getText());
                saveAction.accept(value);
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Value updated successfully.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number.");
            }
        });

        backButton.addActionListener(e -> dialog.dispose());

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    private void displayAddSportDialog() {
        // Input field for the sport
        JTextField sportField = new JTextField(10);

        // Panel to hold the label and text field
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Enter Sport:"));
        panel.add(sportField);

        // Buttons for the dialog
        JButton addButton = new JButton("Add");
        JButton cancelButton = new JButton("Cancel");

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(addButton);
        buttonsPanel.add(cancelButton);
        panel.add(buttonsPanel);

        // Create the dialog
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add Sport", true);
        dialog.setLayout(new BorderLayout());
        dialog.add(panel, BorderLayout.CENTER);

        // Event handling
        addButton.addActionListener(e -> {
            String sport = sportField.getText().trim();
            if (!sport.isEmpty()) {
                user.setSport(sport);
                JOptionPane.showMessageDialog(this, "Sport updated to: " + user.getSport());
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a sport name.");
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}