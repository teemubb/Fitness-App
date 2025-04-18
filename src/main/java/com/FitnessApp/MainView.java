package com.FitnessApp;
import javax.swing.*;
import java.awt.*;
public class MainView extends JPanel implements MealListener, ExerciseListener{
    UserInterface ui;
    private JProgressBar progressBar;
    private JTextArea foodTextArea;
    private User user;
    private PlanSelectionView planSelectionView;
    private DietPlan dietPlan;

    public MainView(UserInterface ui, User user, PlanSelectionView planSelectionView) {
        this.ui = ui;
        this.user = user;
        this.planSelectionView = planSelectionView;
        setupUI();
    }

    private void setupUI() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Menu button
        JButton menuButton = new JButton("≡");
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(menuButton, gbc);
        menuButton.addActionListener(e -> ui.switchView(ui.getQuickMenuView()));

        // Add meal button
        JButton addButton = new JButton("Add meal");
        gbc.gridx = 1;
        gbc.gridy = 4;
        //gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.SOUTHEAST; // Align to bottom right
        gbc.fill = GridBagConstraints.NONE; // Don't stretch the button
        this.add(addButton, gbc);
        addButton.addActionListener(e -> ui.switchView(ui.getAddView()));

        // Spacer
        gbc.weightx = 1.0;
        gbc.gridx = 1;
        this.add(new JPanel(), gbc);

        // Daily Calories label
        JLabel caloriesLabel = new JLabel("Daily Calories");
        caloriesLabel.setFont(new Font("SansSherif", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2; // Span across two columns
        gbc.anchor = GridBagConstraints.CENTER; // Align to center
        this.add(caloriesLabel, gbc);

        // Progress bar setup
        progressBar = new JProgressBar(0, (int) planSelectionView.getTotalCalories());
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setString("Start Tracking!");
        progressBar.setPreferredSize(new Dimension(400, 30));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Don't stretch the button
        this.add(progressBar, gbc);

        // Text area setup TODO: change to list / something, add functionality to edit existing meals
        foodTextArea = new JTextArea();
        foodTextArea.setPreferredSize(new Dimension(200, 50));
        JScrollPane scrollPane = new JScrollPane(foodTextArea);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        this.add(scrollPane, gbc);
    }

    public void updateFoodTextArea(Meal meal) {
        String existingText = foodTextArea.getText();
        foodTextArea.setText(existingText + "\n" + meal.mainString());
    }

    public void updateProgressBar() {
        double totalCalories = GetTotalCalories();
        System.out.println(totalCalories);
        progressBar.setValue((int) totalCalories);
        progressBar.setString(totalCalories + " / 3000 Kcal"); // TODO: fetch from plan selection (needs to be implemented)
    }


    @Override
    public void exerciseAdded(Exercise exercise) {

    }

    @Override
    public void mealAdded(Meal meal) {
        updateFoodTextArea(meal);
        updateProgressBar();
    }

    private double GetTotalCalories() {
        double totalCalories = 0;
        for (Meal meal : user.getMealList()) {
            System.out.println(meal.getCalories());
            totalCalories += meal.getCalories();
        }
        return totalCalories;
    }
}
