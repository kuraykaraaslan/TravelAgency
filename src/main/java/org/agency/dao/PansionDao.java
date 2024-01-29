package org.agency.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.agency.entities.Pansion;
import org.agency.core.Database;

public class PansionDao {

    private Connection connection;

    public PansionDao() {
        // Establish a connection to the database
        connection = Database.getInstance();
        if (connection == null) {
            // Throw an exception if connection is not successful
            throw new RuntimeException("Failed to connect to the database.");
        }
    }

    public void insert(Pansion pansion) {
        // SQL query to insert a new row into the 'pansions' table
        String query = "INSERT INTO pansions (name, breakfast, lunch, dinner, midnight_snack, " +
                "soft_drinks, alcoholic_drinks, snacks, hotel_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS)) {
            // Set the parameters for the prepared statement
            setPansionParameters(preparedStatement, pansion);

            // Execute the update and get the number of affected rows
            int affectedRows = preparedStatement.executeUpdate();

            // Throw an exception if no rows were affected
            if (affectedRows == 0) {
                throw new SQLException("Inserting pansion failed, no rows affected.");
            }

            // Retrieve the generated keys (auto-incremented ID)
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    // Set the generated ID of the pansion object
                    pansion.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Inserting pansion failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public Pansion getById(int pansionId) {
        // SQL query to retrieve a row from the 'pansions' table by ID
        String query = "SELECT * FROM pansions WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Set the ID parameter for the prepared statement
            preparedStatement.setInt(1, pansionId);

            // Execute the query and get the result set
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Map the result set to a Pansion object
                    return mapResultSetToPansion(resultSet);
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        // Return null if no matching row was found
        return null;
    }

    public ArrayList<Pansion> getAll() {
        ArrayList<Pansion> pansions = new ArrayList<>();
        // SQL query to retrieve all rows from the 'pansions' table in ascending order
        // of ID
        String query = "SELECT * FROM pansions ORDER BY id ASC";

        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                // Map each result set to a Pansion object and add to the list
                pansions.add(mapResultSetToPansion(resultSet));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        // Return the list of Pansion objects
        return pansions;
    }

    private void setPansionParameters(PreparedStatement preparedStatement, Pansion pansion) throws SQLException {
        // Set the parameters in the prepared statement
        preparedStatement.setString(1, pansion.getName());
        preparedStatement.setBoolean(2, pansion.isBreakfast());
        preparedStatement.setBoolean(3, pansion.isLunch());
        preparedStatement.setBoolean(4, pansion.isDinner());
        preparedStatement.setBoolean(5, pansion.isMidnightSnack());
        preparedStatement.setBoolean(6, pansion.isSoftDrinks());
        preparedStatement.setBoolean(7, pansion.isAlcoholicDrinks());
        preparedStatement.setBoolean(8, pansion.isSnacks());
        preparedStatement.setInt(9, pansion.getHotelId());
    }

    private Pansion mapResultSetToPansion(ResultSet resultSet) throws SQLException {
        // Create a new Pansion object and set its properties from the result set
        Pansion pansion = new Pansion();
        pansion.setId(resultSet.getInt("id"));
        pansion.setName(resultSet.getString("name"));
        pansion.setBreakfast(resultSet.getBoolean("breakfast"));
        pansion.setLunch(resultSet.getBoolean("lunch"));
        pansion.setDinner(resultSet.getBoolean("dinner"));
        pansion.setMidnightSnack(resultSet.getBoolean("midnight_snack"));
        pansion.setSoftDrinks(resultSet.getBoolean("soft_drinks"));
        pansion.setAlcoholicDrinks(resultSet.getBoolean("alcoholic_drinks"));
        pansion.setSnacks(resultSet.getBoolean("snacks"));
        pansion.setHotelId(resultSet.getInt("hotel_id"));

        // Return the populated Pansion object
        return pansion;
    }

    private void handleSQLException(SQLException e) {
        e.printStackTrace();
        // Handle exceptions as needed
    }

    public void update(Pansion pansion) {
        String query = "UPDATE pansions SET name = ?, breakfast = ?, lunch = ?, dinner = ?, midnight_snack = ?, " +
                "soft_drinks = ?, alcoholic_drinks = ?, snacks = ?, hotel_id = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setPansionParameters(preparedStatement, pansion);
            preparedStatement.setInt(10, pansion.getId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating pansion failed, no rows affected.");
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public void delete(Pansion pansion) {
        String query = "DELETE FROM pansions WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, pansion.getId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Deleting pansion failed, no rows affected.");
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public ArrayList<Pansion> getByHotelId(int hotelId) {
        ArrayList<Pansion> pansions = new ArrayList<>();
        String query = "SELECT * FROM pansions WHERE hotel_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, hotelId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    pansions.add(mapResultSetToPansion(resultSet));
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return pansions;
    }
}
