package org.agency.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import static java.lang.System.exit;

public class Database {
    private static Database instance;
    private static Connection connection;

    private final String url = "jdbc:postgresql://localhost:5432/";
    private final String database = "agency";
    private final String username = "postgres";
    private final String password = "postgres";

    // Private singleton constructor
    private Database() {
        try {
            // Establishing the connection to the PostgreSQL database
            connection = DriverManager.getConnection(url + database, username, password);
        } catch (SQLException e) {
            // If the database does not exist, prompt the user to create it
            if (e.getMessage().contains("database \"" + database + "\" does not exist")) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Database does not exist. Create it? (y/n)");
                String answer = scanner.nextLine();
                if (answer.equals("y")) {
                    createDatabase();
                    try {
                        connection = DriverManager.getConnection(url + database, username, password);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            } else {
                // Print the exception stack trace and exit the program with status code 1
                e.printStackTrace();
                exit(1);
            }
        }
    }

    // Method to create the PostgreSQL database
    private void createDatabase() {
        try {
            // Establishing a connection without specifying the database name
            connection = DriverManager.getConnection(url, username, password);

            // Creating the "agency" database
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE DATABASE agency");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Public static method to get the database connection
    public Connection getConnection() {
        return connection;
    }

    // Public static method to get the Database instance
    public static Connection getInstance() {
        try {
            // If the instance is null or the connection is closed, create a new instance
            if (instance == null || instance.getConnection().isClosed()) {
                instance = new Database();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return instance.getConnection();
    }

    // Method to prepare a SQL statement
    public PreparedStatement prepareStatement(String query) throws SQLException {
        return connection.prepareStatement(query);
    }
}
