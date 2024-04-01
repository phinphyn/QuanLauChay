package com.example.demo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestConnection {
    public static void main(String[] args) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        if (connection != null) {
            System.out.println("Connection successful!");

            // Retrieve and print all rows from the user_account table
            try {
                Statement statement = connection.createStatement();
                String query = "SELECT * FROM user_account";
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    int id = resultSet.getInt("idUser");
                    String username = resultSet.getString("Username");
                    String password = resultSet.getString("Password");
                    String name = resultSet.getString("Name");
                    String phone = resultSet.getString("Phone");

                    System.out.println("ID: " + id + ", Username: " + username + ", Password: " + password + ", Name: " + name + ", Phone: " + phone);
                }

                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                connection.close(); // Close the connection when done
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Connection failed!");
        }
    }
}