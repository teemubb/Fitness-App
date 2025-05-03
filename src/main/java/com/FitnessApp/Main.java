package com.FitnessApp;
import com.Database.Postgre;
import com.FitnessApp.UI.UserInterface;
import java.sql.SQLException;



public class Main{
    public static void main(String[] args) {
        Postgre db = Postgre.getInstance();
        try {
            db.open("fitnessapp"); // Open the connection and create tables
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);  // Handle connection failure gracefully
        }

        try {
            UserInterface ui = new UserInterface(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        } catch (NumberFormatException nbe) {
            UserInterface ui = new UserInterface(480, 640);
        } catch (Exception e) {
            UserInterface ui = new UserInterface(480, 640);
        }
    }
}