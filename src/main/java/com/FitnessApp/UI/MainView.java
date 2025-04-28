package com.FitnessApp.UI;

import com.Database.Postgre;
import com.FitnessApp.listeners.ExerciseListener;
import com.FitnessApp.listeners.MealListener;
import com.FitnessApp.Obj.DietPlan;
import com.FitnessApp.Obj.Exercise;
import com.FitnessApp.Obj.Meal;
import com.FitnessApp.Obj.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.sql.SQLException;
import java.util.List;




public class MainView extends JPanel implements MealListener, ExerciseListener {
    UserInterface ui;
    private JProgressBar progressBar;
    private User user;
    private PlanSelectionView planSelectionView;
    private DietPlan dietPlan;
    private Postgre db;
    private DefaultListModel<String> mealListModel = new DefaultListModel<>();
    private JList<String> mealList;

    public MainView(UserInterface ui, User user, PlanSelectionView planSelectionView) {
        this.ui = ui;
        this.user = user;
        this.planSelectionView = planSelectionView;
        this.db = Postgre.getInstance();
        setupUI();
        loadMeals();
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

        // Text area setup TODO: add functionality to edit existing meals
        mealListModel = new DefaultListModel<>();
        mealList = new JList<>(mealListModel);
        mealList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //mealList.setLayoutOrientation(JList.VERTICAL);
        mealList.setVisibleRowCount(-1);
        JScrollPane scrollPane = new JScrollPane(mealList);

        mealList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {  // Check for double-click
                    int index = mealList.locationToIndex(e.getPoint());
                    String selectedMeal = mealList.getModel().getElementAt(index);
                    openMealPopup(selectedMeal);
                }
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        this.add(scrollPane, gbc);
    }

    private void openMealPopup(String selectedMeal) {
        JOptionPane.showMessageDialog(this, "You selected: " + selectedMeal, "Meal Details", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateFoodTextArea(Meal meal) {
        mealListModel.add(0, meal.mainString());
    }

    private void updateProgressBar() {
        double totalCalories = GetTotalCalories();
        System.out.println(totalCalories);
        progressBar.setValue((int) totalCalories);
        progressBar.setString(totalCalories + " / 3000 Kcal"); // TODO: fetch from plan selection (needs to be implemented)
    }
    // Load stored meals from db
    private void loadMeals() {
        try {
            List<Meal> meals = db.getMeals();
            for (Meal meal : meals) {
                mealListModel.addElement(meal.mainString()); // Add meals to the list model
            }
            updateProgressBar();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("database error");
        }
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
        //load calories from database
        try {
            List<Meal> mealsFromDb = db.getMeals();
            for (Meal meal : mealsFromDb) {
                System.out.println("Loaded Meal Calories: " + meal.getCalories());
                totalCalories += meal.getCalories();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalCalories;
    }
}
