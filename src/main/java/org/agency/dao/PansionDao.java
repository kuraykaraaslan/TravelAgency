package org.agency.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.agency.entities.Pansion;
import org.agency.core.Database;
public class PansionDao {

    private Connection connection;

    public PansionDao() {
        connection = Database.getInstance();
        if (connection == null) {
            throw new RuntimeException("Failed to connect to the database.");
        }
    }

    public void insert(Pansion pansion) {
        String query = "INSERT INTO pansions (name, breakfast, lunch, dinner, midnight_snack, " +
                "soft_drinks, alcoholic_drinks, snacks, created_at, updated_at, deleted_at, " +
                "created_by, updated_by, deleted_by, hotel_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            setPansionParameters(preparedStatement, pansion);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Inserting pansion failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
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
        String query = "SELECT * FROM pansions WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, pansionId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToPansion(resultSet);
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return null;
    }

    public List<Pansion> getAll() {
        List<Pansion> pansions = new ArrayList<>();
        String query = "SELECT * FROM pansions";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                pansions.add(mapResultSetToPansion(resultSet));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return pansions;
    }

    private void setPansionParameters(PreparedStatement preparedStatement, Pansion pansion) throws SQLException {
        preparedStatement.setString(1, pansion.getName());
        preparedStatement.setBoolean(2, pansion.isBreakfast());
        preparedStatement.setBoolean(3, pansion.isLunch());
        preparedStatement.setBoolean(4, pansion.isDinner());
        preparedStatement.setBoolean(5, pansion.isMidnightSnack());
        preparedStatement.setBoolean(6, pansion.isSoftDrinks());
        preparedStatement.setBoolean(7, pansion.isAlcoholicDrinks());
        preparedStatement.setBoolean(8, pansion.isSnacks());
        preparedStatement.setDate(9, new java.sql.Date(pansion.getCreatedAt().getTime()));
        preparedStatement.setDate(10, new java.sql.Date(pansion.getUpdatedAt().getTime()));
        preparedStatement.setDate(11, pansion.getDeletedAt() != null ? new java.sql.Date(pansion.getDeletedAt().getTime()) : null);
        preparedStatement.setInt(12, pansion.getCreatedBy());
        preparedStatement.setInt(13, pansion.getUpdatedBy());
        preparedStatement.setInt(14, pansion.getDeletedBy() != null ? pansion.getDeletedBy() : 0);
        preparedStatement.setInt(15, pansion.getHotelId());
    }

    private Pansion mapResultSetToPansion(ResultSet resultSet) throws SQLException {
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
        pansion.setCreatedAt(resultSet.getDate("created_at"));
        pansion.setUpdatedAt(resultSet.getDate("updated_at"));
        pansion.setDeletedAt(resultSet.getDate("deleted_at"));
        pansion.setCreatedBy(resultSet.getInt("created_by"));
        pansion.setUpdatedBy(resultSet.getInt("updated_by"));
        pansion.setDeletedBy(resultSet.getInt("deleted_by"));
        pansion.setHotelId(resultSet.getInt("hotel_id"));

        return pansion;
    }

    private void handleSQLException(SQLException e) {
        e.printStackTrace();
        // Handle exceptions as needed
    }
}
