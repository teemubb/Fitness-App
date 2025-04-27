package com.Database;
import com.FitnessApp.Meal;

import java.sql.*;
import java.text.SimpleDateFormat;
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

    /*public Connection getConnection() {
        return connection;
    }*/


    private void createTable(){

        String createMealTable = "CREATE TABLE IF NOT EXISTS meals ("
                + "id SERIAL PRIMARY KEY, "
                + "meal_name VARCHAR(100) NOT NULL, "
                + "calories DOUBLE PRECISION NOT NULL, "
                + "fat DOUBLE PRECISION NOT NULL, "
                + "protein DOUBLE PRECISION NOT NULL, "
                + "carbs DOUBLE PRECISION NOT NULL, "
                + "date TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
                + ");";
        try(Statement s = connection.createStatement()){
            s.executeUpdate(createMealTable);
            System.out.println("meals table created");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addMeal(String mealName, double calories, double fat, double protein, double carbs){
        String query = "INSERT INTO meals (meal_name, calories, fat, protein, carbs)"
                + "VALUES (?, ?, ?, ?, ?)";
        try(PreparedStatement s = connection.prepareStatement(query)){
            s.setString(1, mealName);
            s.setDouble(2, calories);
            s.setDouble(3, fat);
            s.setDouble(4, protein);
            s.setDouble(5, carbs);
            s.executeUpdate();
            System.out.println("Meal added: " + mealName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Meal> getMeals() throws SQLException {
        String query = "SELECT * FROM meals ORDER BY date ASC";
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
                        timestampMillis
                );
                System.out.println(timestampMillis);
                mealList.add(meal);
            }
        }
        return mealList;
    }
}


