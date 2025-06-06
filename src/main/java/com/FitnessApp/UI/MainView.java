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
    private DefaultListModel<Meal> mealListModel = new DefaultListModel<>();
    private JList<Meal> mealList;

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

        // Meal display setup
        mealListModel = new DefaultListModel<>();
        mealList = new JList<>(mealListModel);
        mealList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //mealList.setLayoutOrientation(JList.VERTICAL);
        mealList.setVisibleRowCount(-1);

        mealList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                //default renderer
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                // in case of meal,
                if (value instanceof Meal meal) { //check if element in mealList is meal object
                    setText(meal.mainString()); //use mainString for correct string builder
                }

                return this;
            }
        });

        JScrollPane scrollPane = new JScrollPane(mealList);

        mealList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {  // Check for double-click
                    int index = mealList.locationToIndex(e.getPoint());
                    Meal selectedMeal = mealList.getModel().getElementAt(index);
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

    private void openMealPopup(Meal selectedMeal) {
        String message = "Selected meal:\n" + selectedMeal.toString();

        Object[] options = { "Edit", "Close" };
        int choice = JOptionPane.showOptionDialog(
                this,
                message,
                "Meal Details",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[1]
        );

        if (choice == JOptionPane.YES_OPTION) {
            openMealEdit(selectedMeal); // Open edit window
        }
    }

    private void openMealEdit(Meal meal) {
        JTextField nameField = new JTextField(meal.getName());
        JTextField caloriesField = new JTextField(String.valueOf(meal.getCalories()));
        JTextField fatField = new JTextField(String.valueOf(meal.getFat()));
        JTextField proteinField = new JTextField(String.valueOf(meal.getProtein()));
        JTextField carbsField = new JTextField(String.valueOf(meal.getCarbohydrates()));
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Calories:"));
        panel.add(caloriesField);
        panel.add(new JLabel("Fat:"));
        panel.add(fatField);
        panel.add(new JLabel("Protein:"));
        panel.add(proteinField);
        panel.add(new JLabel("Carbohydrates:"));
        panel.add(carbsField);

        int selection = JOptionPane.showConfirmDialog(this, panel,
                "Edit Meal", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (selection == JOptionPane.OK_OPTION) {
            // Update the meal object with new values
            meal.setName(nameField.getText());
            meal.setCalories(Double.parseDouble(caloriesField.getText()));
            meal.setFat(Double.parseDouble(fatField.getText()));
            meal.setProtein(Double.parseDouble(proteinField.getText()));
            meal.setCarbohydrates(Double.parseDouble(carbsField.getText()));

            db.updateMeal(meal);
        }
    }


    private void updateFoodTextArea(Meal meal) {
        mealListModel.add(0, meal);
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
                mealListModel.addElement(meal); // Add meals to the list model
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
