package com.FitnessApp.UI;

import com.FitnessApp.listeners.ExerciseListener;
import com.FitnessApp.Obj.Exercise;
import com.FitnessApp.Obj.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.List;

public class WorkoutView extends JPanel implements ExerciseListener {
    private UserInterface ui;
    private User user;
    private JList<String> workoutList;
    private DefaultListModel<String> workoutModel;

    public WorkoutView(UserInterface ui, User user) {
        this.ui = ui;
        this.user = user;
        setupUI();
    }

    private void setupUI() {
        this.setLayout(new BorderLayout());


        JLabel titleLabel = new JLabel("Workouts", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSherif", Font.BOLD, 20)); // Optional: customize font size and style
        this.add(titleLabel, BorderLayout.NORTH);

        // Initialize the model for workout details
        workoutModel = new DefaultListModel<>();
        loadWorkouts();


        // Setup the JList with the model
        workoutList = new JList<>(workoutModel);
        workoutList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(workoutList);
        scrollPane.setPreferredSize(new Dimension(300, 400));
        workoutList.setBackground(new Color(230, 230, 230)); // Todo: colouring through uimanager in userinterface
        workoutList.setForeground(Color.BLACK);

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        JButton backButton = new JButton("← Back");
        backButton.addActionListener(this::backAction);
        buttonPanel.add(backButton);

        // Add components to this panel
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void exerciseAdded(Exercise exercise) {
        loadWorkouts();
    }
    //TODO: TIME FORMAT
    private void loadWorkouts() {
        workoutModel.clear(); // Clear the list before loading
        System.out.println("Exercise count: " + user.getExerciseList().size());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        List<Exercise> exercises = user.getExerciseList();
        for (Exercise ex : exercises) {
            String displayText = String.format("Time: %s, Type: %s, Duration: %s min, Calories: %.0f kcal",
                    ex.getFormattedTimestamp(), ex.getExerciseType(), ex.getDuration(), ex.getCaloriesBurned());
            workoutModel.addElement(displayText);
        }
    }


    private void backAction(ActionEvent e) {
        ui.switchView(ui.getMainView());
    }
}