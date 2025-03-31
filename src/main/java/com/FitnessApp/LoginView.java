package com.FitnessApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView extends JPanel {
    private GridBagConstraints gbc;
    private UserInterface ui;

    public LoginView(UserInterface ui) {
        this.ui = ui;
        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();

        // To push the button to the bottom, fill the vertical space with a spacer component
        // This component will push all following components to the bottom
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1; // Assign maximum weight to push components to the bottom
        gbc.fill = GridBagConstraints.VERTICAL; // Fill the space vertically
        add(Box.createVerticalGlue(), gbc); // Invisible component that just takes up space

        // Now add the button at the bottom
        addButton("Login as user", gbc, 1, e -> ui.switchView(ui.getMainView())); // Use gridy = 1 for the actual button
        gbc.gridy = 2; // Increment gridy to place this component just below the button
        gbc.weighty = 0.1; // A smaller weight compared to the top spacer
        add(Box.createVerticalGlue(), gbc); // Adds a bit of space under the button

    }

    private void addButton(String text, GridBagConstraints gbc, int gridy, ActionListener action) {
        JButton button = new JButton(text);
        gbc.gridx = 0;
        gbc.gridy = gridy;
        gbc.weighty = 0; // Reset the vertical weight
        gbc.anchor = GridBagConstraints.SOUTH; // Anchor the button to the south
        gbc.fill = GridBagConstraints.NONE; // No filling needed for the button
        button.addActionListener(action);
        this.add(button, gbc);
    }
}