package com.Database;
import com.FitnessApp.Obj.Meal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Postgre {
    private static Postgre instance = null;
    private Connection connection = null;

    private Postgre(){
    }
    //singleton
    public static Postgre getInstance() {
        if (instance == null) {
            synchronized (Postgre.class) {
                if (instance == null) {
                    instance = new Postgre();
                }
            }
        }
        return instance;
    }

    public void open(String dbname) throws SQLException{
        //TODO: USE CONFIG FILE
        String url = "jdbc:postgresql://localhost:5432/" + dbname; // database URL
        String user = "postgres";
        String password = "password";
        // Open the database connection
        try{
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to PostgreSQL!");

            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTable(){

        String createMealTable = "CREATE TABLE IF NOT EXISTS meals ("
                + "id SERIAL PRIMARY KEY, "
                + "meal_name VARCHAR(100) NOT NULL, "
                + "calories DOUBLE PRECISION NOT NULL, "
                + "fat DOUBLE PRECISION NOT NULL, "
                + "protein DOUBLE PRECISION NOT NULL, "
                + "carbs DOUBLE PRECISION NOT NULL, "
                + "date TIMESTAMP NOT NULL" // Timestamp from item object...
                + ");";
        try(Statement s = connection.createStatement()){
            s.executeUpdate(createMealTable);
            System.out.println("meals table created");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addMeal(Meal meal) {
        String query = "INSERT INTO meals (meal_name, calories, fat, protein, carbs, date) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement s = connection.prepareStatement(query)) {
            s.setString(1, meal.getName());
            s.setDouble(2, meal.getCalories());
            s.setDouble(3, meal.getFat());
            s.setDouble(4, meal.getProtein());
            s.setDouble(5, meal.getCarbohydrates());
            s.setTimestamp(6, meal.getTimestamp()); // Timestamp from item object...
            s.executeUpdate();
            System.out.println("Meal added: " + meal.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateMeal(Meal meal){
        String query = "UPDATE meals SET calories = ?, fat = ?, protein = ?, carbs = ?, meal_name = ? WHERE id = ?";

        try(PreparedStatement s = connection.prepareStatement(query)){
            s.setDouble(1, meal.getCalories());
            s.setDouble(2, meal.getFat());
            s.setDouble(3, meal.getProtein());
            s.setDouble(4, meal.getCarbohydrates());
            s.setString(5, meal.getName());
            s.setInt(6, meal.getId());
            s.executeUpdate();
            System.out.println("Meal updated!");
            System.out.println(meal.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Meal> getMeals() throws SQLException {
        String query = "SELECT * FROM meals ORDER BY date DESC";
        List<Meal> mealList = new ArrayList<>();
        try (Statement s = connection.createStatement();
             ResultSet rs = s.executeQuery(query)) {
            while (rs.next()) {
                //convert to readable format
                Timestamp timestamp = rs.getTimestamp("date");
                long timestampMillis = (timestamp != null) ? timestamp.getTime() : 0;
                Meal meal = new Meal(
                        rs.getString("meal_name"),
                        rs.getDouble("calories"),
                        rs.getDouble("fat"),
                        rs.getDouble("protein"),
                        rs.getDouble("carbs"),
                        rs.getInt("id")
                );
                System.out.println(timestampMillis); //debug stuff
                mealList.add(meal);
            }
        }
        return mealList;
    }

    //TODO: save mealplan&other user data, exercises, logic to reset daily,
}


