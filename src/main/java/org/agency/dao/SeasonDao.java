package org.agency.dao;

import java.util.List;
import org.agency.entities.Season;
import org.agency.core.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeasonDao {

    private Connection connection;

    public SeasonDao() {
        connection = Database.getInstance();
        if (connection == null) {
            throw new RuntimeException("Failed to connect to the database.");
        }
    }

    public void insert(Season season) {
        String query = "INSERT INTO seasons (name, start_date, end_date, created_at, updated_at, deleted_at, " +
                "created_by, updated_by, deleted_by, hotel_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            setParameters(preparedStatement, season);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Inserting season failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    season.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Inserting season failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public Season getById(int seasonId) {
        String query = "SELECT * FROM seasons WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, seasonId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToSeason(resultSet);
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return null;
    }

    public List<Season> getAll() {
        List<Season> seasons = new ArrayList<>();
        String query = "SELECT * FROM seasons";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                seasons.add(mapResultSetToSeason(resultSet));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return seasons;
    }

    private void setParameters(PreparedStatement preparedStatement, Season season) throws SQLException {
        preparedStatement.setString(1, season.getName());
        preparedStatement.setDate(2, new java.sql.Date(season.getStartDate().getTime()));
        preparedStatement.setDate(3, new java.sql.Date(season.getEndDate().getTime()));
        preparedStatement.setDate(4, new java.sql.Date(season.getCreatedAt().getTime()));
        preparedStatement.setDate(5, new java.sql.Date(season.getUpdatedAt().getTime()));
        preparedStatement.setDate(6, season.getDeletedAt() != null ? new java.sql.Date(season.getDeletedAt().getTime()) : null);
        preparedStatement.setInt(7, season.getCreatedBy());
        preparedStatement.setInt(8, season.getUpdatedBy());
        preparedStatement.setInt(9, season.getDeletedBy() != null ? season.getDeletedBy() : 0);
        preparedStatement.setInt(10, season.getHotelId());
    }

    private Season mapResultSetToSeason(ResultSet resultSet) throws SQLException {
        Season season = new Season();
        season.setId(resultSet.getInt("id"));
        season.setName(resultSet.getString("name"));
        season.setStartDate(resultSet.getDate("start_date"));
        season.setEndDate(resultSet.getDate("end_date"));
        season.setCreatedAt(resultSet.getDate("created_at"));
        season.setUpdatedAt(resultSet.getDate("updated_at"));
        season.setDeletedAt(resultSet.getDate("deleted_at"));
        season.setCreatedBy(resultSet.getInt("created_by"));
        season.setUpdatedBy(resultSet.getInt("updated_by"));
        season.setDeletedBy(resultSet.getInt("deleted_by"));
        season.setHotelId(resultSet.getInt("hotel_id"));

        return season;
    }

    private void handleSQLException(SQLException e) {
        e.printStackTrace();
        // Handle exceptions as needed
    }
}
