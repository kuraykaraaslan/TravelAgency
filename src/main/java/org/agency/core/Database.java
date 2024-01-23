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
            connection = DriverManager.getConnection(url + database, username, password);
        } catch (SQLException e) {
            if (e.getMessage().contains("database \"" + database + "\" does not exist")) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Database does not exist. Create it? (y/n)");
                String answer = scanner.nextLine();
                if (answer.equals("y"))
                {
                    createDatabase();
                    try {
                        connection = DriverManager.getConnection(url + database, username, password);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            } else {
                e.printStackTrace();
                exit(1);
            }
        }
    }

    private void createDatabase()
    {
        try {
            connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE DATABASE agency");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Public static method to get the instance
    public Connection getConnection() {
        return connection;
    }

    public static Connection getInstance(){
        try {
            if (instance == null || instance.getConnection().isClosed()){
                instance = new Database();
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return instance.getConnection();
    }

    public PreparedStatement prepareStatement(String query) throws SQLException {
        return connection.prepareStatement(query);
    }

}
