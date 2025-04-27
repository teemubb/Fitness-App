package com.Database;
import java.sql.*;

public class Postgre {
    private Connection connection;
    public void open(String dbname) throws SQLException{
        //TODO: USE CONFIG FILE
        String url = "jdbc:postgresql://localhost:5432/" + dbname; // database URL
        String user = "postgres";
        String password = "password";
        // Open the database connection
        try{
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to PostgreSQL!");

            createTable(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }


    private void createTable(Connection con){

        String createMealTable = "CREATE TABLE IF NOT EXISTS meals ("
                + "id SERIAL PRIMARY KEY, "
                + "meal_name VARCHAR(100) NOT NULL, "
                + "calories DOUBLE PRECISION NOT NULL, "
                + "fat DOUBLE PRECISION NOT NULL, "
                + "protein DOUBLE PRECISION NOT NULL, "
                + "carbs DOUBLE PRECISION NOT NULL, "
                + "date TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
                + ");";
        try(Statement s = con.createStatement()){
            s.executeUpdate(createMealTable);
            System.out.println("meals table created");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addMeal(Connection con, String mealName, double calories, double fat, double protein, double carbs){
        String query = "INSERT INTO meals (meal_name, calories, fat, protein, carbs)"
                + "VALUES (?, ?, ?, ?, ?)";
        try(PreparedStatement s = con.prepareStatement(query)){
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
}


