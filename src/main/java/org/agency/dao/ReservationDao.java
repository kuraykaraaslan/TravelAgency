package org.agency.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.agency.entities.Reservation;
import org.agency.core.Database;

public class ReservationDao {

    private Connection connection;

    public ReservationDao() {
        connection = Database.getInstance();
        if (connection == null) {
            throw new RuntimeException("Failed to connect to the database.");
        }
    }

    public void insert(Reservation reservation) {
        String query = "INSERT INTO reservations (guest_citizen_id, guest_full_name, guest_email, guest_phone, " +
                "check_in, check_out, adult_count, child_count, price, created_at, updated_at, deleted_at, " +
                "created_by, updated_by, deleted_by, hotel_id, room_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            setParameters(preparedStatement, reservation);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Inserting reservation failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    reservation.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Inserting reservation failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public Reservation getById(int reservationId) {
        String query = "SELECT * FROM reservations WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, reservationId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToReservation(resultSet);
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return null;
    }

    public List<Reservation> getAll() {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservations";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                reservations.add(mapResultSetToReservation(resultSet));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return reservations;
    }

    private void setParameters(PreparedStatement preparedStatement, Reservation reservation) throws SQLException {
        preparedStatement.setString(1, reservation.getGuestCitizenId());
        preparedStatement.setString(2, reservation.getGuestFullName());
        preparedStatement.setString(3, reservation.getGuestEmail());
        preparedStatement.setString(4, reservation.getGuestPhone());
        preparedStatement.setDate(5, new java.sql.Date(reservation.getCheckIn().getTime()));
        preparedStatement.setDate(6, new java.sql.Date(reservation.getCheckOut().getTime()));
        preparedStatement.setInt(7, reservation.getAdultCount());
        preparedStatement.setInt(8, reservation.getChildCount());
        preparedStatement.setDouble(9, reservation.getPrice());
        preparedStatement.setDate(10, new java.sql.Date(reservation.getCreatedAt().getTime()));
        preparedStatement.setDate(11, new java.sql.Date(reservation.getUpdatedAt().getTime()));
        preparedStatement.setDate(12, reservation.getDeletedAt() != null ? new java.sql.Date(reservation.getDeletedAt().getTime()) : null);
        preparedStatement.setInt(13, reservation.getCreatedBy());
        preparedStatement.setInt(14, reservation.getUpdatedBy());
        preparedStatement.setInt(15, reservation.getDeletedBy() != null ? reservation.getDeletedBy() : 0);
        preparedStatement.setInt(16, reservation.getHotelId());
        preparedStatement.setInt(17, reservation.getRoomId());
    }

    private Reservation mapResultSetToReservation(ResultSet resultSet) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setId(resultSet.getInt("id"));
        reservation.setGuestCitizenId(resultSet.getString("guest_citizen_id"));
        reservation.setGuestFullName(resultSet.getString("guest_full_name"));
        reservation.setGuestEmail(resultSet.getString("guest_email"));
        reservation.setGuestPhone(resultSet.getString("guest_phone"));
        reservation.setCheckIn(resultSet.getDate("check_in"));
        reservation.setCheckOut(resultSet.getDate("check_out"));
        reservation.setAdultCount(resultSet.getInt("adult_count"));
        reservation.setChildCount(resultSet.getInt("child_count"));
        reservation.setPrice(resultSet.getDouble("price"));
        reservation.setCreatedAt(resultSet.getDate("created_at"));
        reservation.setUpdatedAt(resultSet.getDate("updated_at"));
        reservation.setDeletedAt(resultSet.getDate("deleted_at"));
        reservation.setCreatedBy(resultSet.getInt("created_by"));
        reservation.setUpdatedBy(resultSet.getInt("updated_by"));
        reservation.setDeletedBy(resultSet.getInt("deleted_by"));
        reservation.setHotelId(resultSet.getInt("hotel_id"));
        reservation.setRoomId(resultSet.getInt("room_id"));

        return reservation;
    }

    private void handleSQLException(SQLException e) {
        e.printStackTrace();
        // Handle exceptions as needed
    }
}
