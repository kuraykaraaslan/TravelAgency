package org.agency.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.agency.core.PaginatedResult;
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
        System.out.println("Inserting reservation...");
        System.out.println(reservation);
        String query = "INSERT INTO reservations (guest_citizen_id, guest_full_name, guest_email, guest_phone, " +
                "check_in, check_out, adult_count, child_count, price, created_at, updated_at, deleted_at, " +
                "created_by, updated_by, deleted_by, hotel_id, room_id, pansion_id, status, season_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS)) {
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

    // getByRoomId
    public ArrayList<Reservation> getByRoomId(int roomId) {
        ArrayList<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservations WHERE room_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, roomId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    reservations.add(mapResultSetToReservation(resultSet));
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return reservations;
    }

    public ArrayList<Reservation> getAll() {
        ArrayList<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservations ORDER BY id ASC";

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
        preparedStatement.setDate(12,
                reservation.getDeletedAt() != null ? new java.sql.Date(reservation.getDeletedAt().getTime()) : null);
        preparedStatement.setInt(13, reservation.getCreatedBy());
        preparedStatement.setInt(14, reservation.getUpdatedBy());
        preparedStatement.setInt(15, reservation.getDeletedBy() != null ? reservation.getDeletedBy() : 0);
        preparedStatement.setInt(16, reservation.getHotelId());
        preparedStatement.setInt(17, reservation.getRoomId());
        preparedStatement.setInt(18, reservation.getPansionId());
        preparedStatement.setString(19, reservation.getStatus());
        preparedStatement.setInt(20, reservation.getSeasonId());
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
        reservation.setPansionId(resultSet.getInt("pansion_id"));
        reservation.setStatus(resultSet.getString("status"));
        reservation.setSeasonId(resultSet.getInt("season_id"));

        return reservation;
    }

    private void handleSQLException(SQLException e) {
        e.printStackTrace();
        // Handle exceptions as needed
    }

    public void update(Reservation reservation) {
        String query = "UPDATE reservations SET guest_citizen_id = ?, guest_full_name = ?, guest_email = ?, guest_phone = ?, "
                +
                "check_in = ?, check_out = ?, adult_count = ?, child_count = ?, price = ?, created_at = ?, updated_at = ?, deleted_at = ?, "
                +
                "created_by = ?, updated_by = ?, deleted_by = ?, hotel_id = ?, room_id = ?, pansion_id = ?, status = ?, season_id = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setParameters(preparedStatement, reservation);
            preparedStatement.setInt(21, reservation.getId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating reservation failed, no rows affected.");
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public void delete(Reservation reservation) {
        String query = "DELETE FROM reservations WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, reservation.getId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Deleting reservation failed, no rows affected.");
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public ArrayList<Reservation> getByFilters(HashMap<String, Object> filters) {
        ArrayList<Reservation> reservations = new ArrayList<>();
        String fullQuery = "SELECT * FROM reservations WHERE hotel_id = ? AND room_id = ? AND check_in >= ? AND check_out <= ? AND adult_count >= ? AND child_count >= ? AND price >= ? AND created_at >= ? AND updated_at >= ? AND deleted_at >= ? AND created_by >= ? AND updated_by >= ? AND deleted_by >= ?";

        String query = "SELECT * FROM reservations WHERE ";

        if (filters.containsKey("hotel_id")) {
            query += "hotel_id = " + filters.get("hotel_id") + " AND ";
        }

        if (filters.containsKey("room_id")) {
            query += "room_id = " + filters.get("room_id") + " AND ";
        }

        if (filters.containsKey("check_in")) {
            // org.postgresql.util.PSQLException: ERROR: operator does not exist: date >=
            // integer
            query += "check_in > " + filters.get("check_in") + " AND ";
        }

        if (filters.containsKey("check_out")) {
            query += "check_out < " + filters.get("check_out") + " AND ";
        }

        if (filters.containsKey("guest_citizen_id")) {
            query += "guest_citizen_id = ? AND ";
        }

        if (filters.containsKey("guest_full_name")) {
            query += "guest_full_name = ? AND ";
        }

        if (filters.containsKey("guest_email")) {
            query += "guest_email = ? AND ";
        }

        if (filters.containsKey("guest_phone")) {
            query += "guest_phone = ? AND ";
        }

        if (filters.containsKey("status")) {
            query += "status = ? AND ";
        }
        // if there is no filter, return all reservations

        if (query.equals("SELECT * FROM reservations WHERE ")) {
            return getAll();
        }

        // remove the last AND from the query

        // remove the last AND
        query = query.substring(0, query.length() - 5);

        System.out.println(query);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    reservations.add(mapResultSetToReservation(resultSet));
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return reservations;
    }

    // Additions
    public PaginatedResult<Reservation> paginate(int offset, int limit, String keyword) {
        ArrayList<Reservation> reservations = new ArrayList<>();

        // Handle null keyword
        if (keyword == null) {
            keyword = "";
        }

        // Handle negative offset
        if (offset < 0) {
            offset = 0;
        }

        String query = "SELECT * FROM reservations WHERE (guest_citizen_id LIKE ? OR guest_full_name LIKE ? OR guest_email LIKE ?) AND deleted_at IS NULL LIMIT ? OFFSET ?";
        String countQuery = "SELECT COUNT(*) FROM reservations WHERE (guest_citizen_id LIKE ? OR guest_full_name LIKE ? OR guest_email LIKE ?) AND deleted_at IS NULL";

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                PreparedStatement countStatement = connection.prepareStatement(countQuery)) {
            preparedStatement.setString(1, "%" + keyword + "%");
            preparedStatement.setString(2, "%" + keyword + "%");
            preparedStatement.setString(3, "%" + keyword + "%");
            preparedStatement.setInt(4, limit);
            preparedStatement.setInt(5, offset);

            countStatement.setString(1, "%" + keyword + "%");
            countStatement.setString(2, "%" + keyword + "%");
            countStatement.setString(3, "%" + keyword + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    if (resultSet.getDate("deleted_at") != null) {
                        continue;
                    }
                    reservations.add(mapResultSetToReservation(resultSet));
                }
            }

            // Calculate total count
            int total = 0;
            try (ResultSet countResultSet = countStatement.executeQuery()) {
                if (countResultSet.next()) {
                    total = countResultSet.getInt(1);
                }
            }

            return new PaginatedResult<>(reservations, total);

        } catch (SQLException e) {
            handleSQLException(e);
            // Handle the exception or rethrow it based on your application's logic
        }

        return null;
    }

    // GET BY HOTEL ID
    public ArrayList<Reservation> getByHotelId(int hotelId) {
        ArrayList<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservations WHERE hotel_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, hotelId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    reservations.add(mapResultSetToReservation(resultSet));
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return reservations;
    }



    public PaginatedResult<Reservation> paginate(int offset, int limit) {
        return paginate(offset, limit, null);
    }
}
