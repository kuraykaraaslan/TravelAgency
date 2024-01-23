package org.agency.dao;

import java.util.List;
import org.agency.entities.Room;
import org.agency.core.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDao {

    private Connection connection;

    public RoomDao() {
        connection = Database.getInstance();
        if (connection == null) {
            throw new RuntimeException("Failed to connect to the database.");
        }
    }

    public void insert(Room room) {
        String query = "INSERT INTO rooms (room_number, type, double_bed_count, single_bed_count, adult_price, " +
                "child_price, square_meters, has_television, has_balcony, has_air_conditioning, has_minibar, " +
                "has_valuables_safe, has_gaming_console, has_projector, created_at, updated_at, deleted_at, " +
                "created_by, updated_by, deleted_by, hotel_id, season_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            setParameters(preparedStatement, room);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Inserting room failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    room.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Inserting room failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public Room getById(int roomId) {
        String query = "SELECT * FROM rooms WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, roomId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToRoom(resultSet);
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return null;
    }

    public List<Room> getAll() {
        List<Room> rooms = new ArrayList<>();
        String query = "SELECT * FROM rooms";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                rooms.add(mapResultSetToRoom(resultSet));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return rooms;
    }

    private void setParameters(PreparedStatement preparedStatement, Room room) throws SQLException {
        preparedStatement.setString(1, room.getRoomNumber());
        preparedStatement.setString(2, room.getType());
        preparedStatement.setInt(3, room.getDoubleBedCount());
        preparedStatement.setInt(4, room.getSingleBedCount());
        preparedStatement.setDouble(5, room.getAdultPrice());
        preparedStatement.setDouble(6, room.getChildPrice());
        preparedStatement.setInt(7, room.getSquareMeters());
        preparedStatement.setBoolean(8, room.isHasTelevision());
        preparedStatement.setBoolean(9, room.isHasBalcony());
        preparedStatement.setBoolean(10, room.isHasAirConditioning());
        preparedStatement.setBoolean(11, room.isHasMinibar());
        preparedStatement.setBoolean(12, room.isHasValuablesSafe());
        preparedStatement.setBoolean(13, room.isHasGamingConsole());
        preparedStatement.setBoolean(14, room.isHasProjector());
        preparedStatement.setDate(15, new java.sql.Date(room.getCreatedAt().getTime()));
        preparedStatement.setDate(16, new java.sql.Date(room.getUpdatedAt().getTime()));
        preparedStatement.setDate(17, room.getDeletedAt() != null ? new java.sql.Date(room.getDeletedAt().getTime()) : null);
        preparedStatement.setInt(18, room.getCreatedBy());
        preparedStatement.setInt(19, room.getUpdatedBy());
        preparedStatement.setInt(20, room.getDeletedBy() != null ? room.getDeletedBy() : 0);
        preparedStatement.setInt(21, room.getHotelId());
        preparedStatement.setInt(22, room.getSeasonId());
    }

    private Room mapResultSetToRoom(ResultSet resultSet) throws SQLException {
        Room room = new Room();
        room.setId(resultSet.getInt("id"));
        room.setRoomNumber(resultSet.getString("room_number"));
        room.setType(resultSet.getString("type"));
        room.setDoubleBedCount(resultSet.getInt("double_bed_count"));
        room.setSingleBedCount(resultSet.getInt("single_bed_count"));
        room.setAdultPrice(resultSet.getDouble("adult_price"));
        room.setChildPrice(resultSet.getDouble("child_price"));
        room.setSquareMeters(resultSet.getInt("square_meters"));
        room.setHasTelevision(resultSet.getBoolean("has_television"));
        room.setHasBalcony(resultSet.getBoolean("has_balcony"));
        room.setHasAirConditioning(resultSet.getBoolean("has_air_conditioning"));
        room.setHasMinibar(resultSet.getBoolean("has_minibar"));
        room.setHasValuablesSafe(resultSet.getBoolean("has_valuables_safe"));
        room.setHasGamingConsole(resultSet.getBoolean("has_gaming_console"));
        room.setHasProjector(resultSet.getBoolean("has_projector"));
        room.setCreatedAt(resultSet.getDate("created_at"));
        room.setUpdatedAt(resultSet.getDate("updated_at"));
        room.setDeletedAt(resultSet.getDate("deleted_at"));
        room.setCreatedBy(resultSet.getInt("created_by"));
        room.setUpdatedBy(resultSet.getInt("updated_by"));
        room.setDeletedBy(resultSet.getInt("deleted_by"));
        room.setHotelId(resultSet.getInt("hotel_id"));
        room.setSeasonId(resultSet.getInt("season_id"));

        return room;
    }

    private void handleSQLException(SQLException e) {
        e.printStackTrace();
        // Handle exceptions as needed
    }
}
