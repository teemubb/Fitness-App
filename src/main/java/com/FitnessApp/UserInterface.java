package com.FitnessApp;
import com.Database.Postgre;

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
    private Postgre db;

    public UserInterface(int width, int height, Postgre db) {

        try {
            // Nimbus look & feel
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            // Background
            UIManager.put("control", Color.DARK_GRAY);  // Background for components
            UIManager.put("nimbusBase", new Color(0, 98, 255));   // General theme colour
            // Buttons
            UIManager.put("Button.textForeground", Color.BLACK);  // Button text colour
            UIManager.put("Button.font", new Font("SansSherif", Font.BOLD, 14));

            UIManager.put("text", Color.WHITE);        // Text colour

            // Dropdown menu
            UIManager.put("ComboBox:\"ComboBox.listRenderer\"[Selected].background", new Color(0, 98, 255)); // selected background
            UIManager.put("ComboBox:\"ComboBox.listRenderer\".background", new Color(160, 172, 252)); //unselected background
            UIManager.put("ComboBox:\"ComboBox.textField\"[Selected].textForeground", new Color(255, 0, 255));

            // Text field colours
            UIManager.put("TextArea.background", new Color(211, 211, 211));
            UIManager.put("TextArea.foreground", Color.BLACK);  // Black text for contrast
            UIManager.put("TextField.background", new Color(211, 211, 211));  // Lighter grey background for all JTextFields
            UIManager.put("TextField.foreground", Color.BLACK);  // Black text for contrast

           /* UIManager.put("List.background" , new Color(211, 211, 211));
            UIManager.put("List.foreground" , Color.BLACK);*/
            //UIManager.put("List[Selected].textForeground" , Color.BLACK);

        } catch (Exception e) {
            e.printStackTrace();
        }


        user = new User();
        frame = new JFrame("FitApp");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null); // Center the window

        loginView = new LoginView(this);
        planView = new PlanSelectionView(this, user);
        mainView = new MainView(this, user, planView);
        quickMenuView = new QuickMenuView(this);
        settingsView = new SettingsView(this, user);

        this.db = db;
        addView = new AddMealView(this,db);
        System.out.println(db.getConnection());
        addView.addMealListener(user);
        addView.addMealListener(mainView);

        workoutView = new WorkoutView(this, user);
        addWorkoutView = new AddWorkoutView(this);
        addWorkoutView.addExerciseListener(user);
        addWorkoutView.addExerciseListener(workoutView); // tarvii listener?

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