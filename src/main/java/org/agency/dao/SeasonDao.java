package org.agency.dao;

import java.util.HashMap;
import java.util.List;

import org.agency.entities.Room;
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
        String query = "INSERT INTO seasons (name, start_date, end_date, hotel_id) " +
                "VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS)) {
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

    public ArrayList<Season> getAll() {
        ArrayList<Season> seasons = new ArrayList<>();
        String query = "SELECT * FROM seasons ORDER BY id ASC";

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
        preparedStatement.setDate(2, Date.valueOf(season.getStartDateLocalDate()));
        preparedStatement.setDate(3, Date.valueOf(season.getEndDateLocalDate()));
        preparedStatement.setInt(4, season.getHotelId());
    }

    private Season mapResultSetToSeason(ResultSet resultSet) throws SQLException {
        Season season = new Season();
        if (resultSet.findColumn("id") != -1) {
            season.setId(resultSet.getInt("id"));
        }
        if (resultSet.findColumn("name") != -1) {
            season.setName(resultSet.getString("name"));
        }
        if (resultSet.findColumn("start_date") != -1) {
            season.setStartDate(resultSet.getDate("start_date"));
        }
        if (resultSet.findColumn("end_date") != -1) {
            season.setEndDate(resultSet.getDate("end_date"));
        }
        if (resultSet.findColumn("hotel_id") != -1) {
            season.setHotelId(resultSet.getInt("hotel_id"));
        }

        return season;
    }

    private void handleSQLException(SQLException e) {
        e.printStackTrace();
        // Handle exceptions as needed
    }

    public void delete(Season season) {
        String query = "DELETE FROM seasons WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, season.getId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Deleting season failed, no rows affected.");
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public void update(Season season) {
        String query = "UPDATE seasons SET name = ?, start_date = ?, end_date = ?, hotel_id = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setParameters(preparedStatement, season);
            preparedStatement.setInt(5, season.getId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating season failed, no rows affected.");
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    // getAllByHotelId
    public ArrayList<Season> getAllByHotelId(int hotelId) {
        ArrayList<Season> seasons = new ArrayList<>();
        String query = "SELECT * FROM seasons WHERE hotel_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, hotelId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    seasons.add(mapResultSetToSeason(resultSet));
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return seasons;
    }

    public ArrayList<Season> getByFilters(HashMap<String, Object> filters) {
        ArrayList<Season> seasons = new ArrayList<>();
        String fullQuery = "SELECT * FROM seasons WHERE hotel_id = ? AND start_date >= ? AND end_date <= ?";

        String query = "SELECT * FROM seasons WHERE ";

        if (filters.containsKey("hotel_id")) {
            System.out.println("hotel_id: " + filters.get("hotel_id"));
            query += "hotel_id = " + filters.get("hotel_id") + " AND ";
        }

        if (filters.containsKey("start_date")) {
            query += "start_date >= " + filters.get("start_date") + " AND ";
        }

        if (filters.containsKey("end_date")) {
            query += "end_date <= " + filters.get("end_date") + " AND ";
        }

        query = query.substring(0, query.length() - 5);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    seasons.add(mapResultSetToSeason(resultSet));
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return seasons;
    }
}