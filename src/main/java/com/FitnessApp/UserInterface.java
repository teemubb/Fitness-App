package com.FitnessApp;
import javax.swing.*;
import java.awt.*;

public class UserInterface extends JFrame {
    private JFrame frame;
    private LoginView loginView;
    private MainView mainView;
    private QuickMenuView quickMenuView;
    private SettingsView settingsView;
    private AddMealView addView;
    private SummaryView summaryView;
    private PlanSelectionView planView;
    private AddWorkoutView addWorkoutView;
    private WorkoutView workoutView;
    private DietPlan dietPlan;
    private User user;

    public UserInterface(int width, int height) {
        user = new User();
        frame = new JFrame("Tracker App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null); // Center the window

        loginView = new LoginView(this);
        planView = new PlanSelectionView(this, user);
        mainView = new MainView(this, user, planView);
        quickMenuView = new QuickMenuView(this);
        settingsView = new SettingsView(this, user);
        addView = new AddMealView(this);
        addView.addMealListener(user);
        addView.addMealListener(mainView);
        addWorkoutView = new AddWorkoutView(this);
        addWorkoutView.addExerciseListener(user);
        workoutView = new WorkoutView(this, user);
        summaryView = new SummaryView(this, planView);

        frame.add(loginView, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public void switchView(JPanel view) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(view, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }
    public LoginView getLoginView() {
        return loginView;
    }

    public MainView getMainView(){
        return mainView;
    }

    public QuickMenuView getQuickMenuView(){
        return quickMenuView;
    }

    public SettingsView getSettingsView(){
        return settingsView;
    }
    public AddMealView getAddView(){
        return addView;
    }

    public SummaryView getSummaryView(){
        return summaryView;
    }

    public PlanSelectionView getPlanSelectionView(){
        return planView;
    }
    public User getUser(){
        return user;
    }
    public AddWorkoutView getAddWorkoutView(){
        return addWorkoutView;
    }
    public WorkoutView getWorkoutView(){
        return workoutView;
    }



}