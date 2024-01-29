package org.agency.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.agency.core.PaginatedResult;
import org.agency.entities.Hotel;
import org.agency.core.Database;

// Import statement for User entity, but it is not used

public class HotelDao {

    private Connection connection;

    public HotelDao() {
        connection = Database.getInstance();
        if (connection == null) {
            throw new RuntimeException("Failed to connect to the database.");
        }
    }

    // Insert a hotel into the database
    public void insert(Hotel hotel) {
        String query = "INSERT INTO hotels (name, address_full, address_district, address_city, address_country, " +
                "star_rating, has_car_park, has_internet, has_pool, has_conciege, has_spa, has_room_service, " +
                "phone, website, email) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS)) {
            setParameters(preparedStatement, hotel);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Inserting hotel failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    hotel.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Inserting hotel failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    // Update an existing hotel in the database
    public void update(Hotel hotel) {
        String query = "UPDATE hotels SET name = ?, address_full = ?, address_district = ?, address_city = ?, " +
                "address_country = ?, star_rating = ?, has_car_park = ?, has_internet = ?, has_pool = ?, " +
                "has_conciege = ?, has_spa = ?, has_room_service = ?, phone = ?, website = ?, email = ? " +
                "WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setParameters(preparedStatement, hotel);
            preparedStatement.setInt(16, hotel.getId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating hotel failed, no rows affected.");
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    // Delete a hotel from the database
    public void delete(Hotel hotel) {
        String query = "UPDATE hotels SET deleted_at = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDate(1, new java.sql.Date(System.currentTimeMillis()));
            preparedStatement.setInt(2, hotel.getId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Deleting hotel failed, no rows affected.");
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    // Get a specific hotel by ID from the database
    public Hotel getById(int hotelId) {
        String query = "SELECT * FROM hotels WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, hotelId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToHotel(resultSet);
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return null;
    }

    // Get all hotels from the database
    public ArrayList<Hotel> getAll() {
        ArrayList<Hotel> hotels = new ArrayList<>();
        String query = "SELECT * FROM hotels ORDER BY id ASC";

        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                hotels.add(mapResultSetToHotel(resultSet));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return hotels;
    }

    // Set the prepared statement parameters for a hotel object
    private void setParameters(PreparedStatement preparedStatement, Hotel hotel) throws SQLException {
        preparedStatement.setString(1, hotel.getName());
        preparedStatement.setString(2, hotel.getAddressFull());
        preparedStatement.setString(3, hotel.getAddressDistrict());
        preparedStatement.setString(4, hotel.getAddressCity());
        preparedStatement.setString(5, hotel.getAddressCountry());
        preparedStatement.setInt(6, hotel.getStarRating());
        preparedStatement.setBoolean(7, hotel.isHasCarPark());
        preparedStatement.setBoolean(8, hotel.isHasInternet());
        preparedStatement.setBoolean(9, hotel.isHasPool());
        preparedStatement.setBoolean(10, hotel.isHasConcierge());
        preparedStatement.setBoolean(11, hotel.isHasSpa());
        preparedStatement.setBoolean(12, hotel.isHasRoomService());
        preparedStatement.setString(13, hotel.getPhone());
        preparedStatement.setString(14, hotel.getWebsite());
        preparedStatement.setString(15, hotel.getEmail());
    }

    private Hotel mapResultSetToHotel(ResultSet resultSet) throws SQLException {
        Hotel hotel = new Hotel();
        hotel.setId(resultSet.getInt("id"));
        hotel.setName(resultSet.getString("name"));
        hotel.setAddressFull(resultSet.getString("address_full"));
        hotel.setAddressDistrict(resultSet.getString("address_district"));
        hotel.setAddressCity(resultSet.getString("address_city"));
        hotel.setAddressCountry(resultSet.getString("address_country"));
        hotel.setStarRating(resultSet.getInt("star_rating"));
        hotel.setHasCarPark(resultSet.getBoolean("has_car_park"));
        hotel.setHasInternet(resultSet.getBoolean("has_internet"));
        hotel.setHasPool(resultSet.getBoolean("has_pool"));
        hotel.setHasConcierge(resultSet.getBoolean("has_conciege")); // Update the column name here
        hotel.setHasSpa(resultSet.getBoolean("has_spa"));
        hotel.setHasRoomService(resultSet.getBoolean("has_room_service"));
        hotel.setPhone(resultSet.getString("phone"));
        hotel.setWebsite(resultSet.getString("website"));
        hotel.setEmail(resultSet.getString("email"));
        return hotel;
    }

    private void handleSQLException(SQLException e) {
        e.printStackTrace();
        Logger.getGlobal().severe("Something went wrong while accessing the database.");
    }

    public PaginatedResult<Hotel> paginate(int offset, int limit, String keyword) {
        ArrayList<Hotel> hotels = new ArrayList<>();

        // Handle null keyword
        if (keyword == null) {
            keyword = "";
        }

        // Handle negative offset
        if (offset < 0) {
            offset = 0;
        }

        String query = "SELECT * FROM hotels WHERE name LIKE ? OR address_full LIKE ? OR address_district LIKE ? OR address_city LIKE ? OR address_country LIKE ? LIMIT ? OFFSET ?";
        String countQuery = "SELECT COUNT(*) FROM hotels WHERE name LIKE ? OR address_full LIKE ? OR address_district LIKE ? OR address_city LIKE ? OR address_country LIKE ?";

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                PreparedStatement countStatement = connection.prepareStatement(countQuery)) {
            preparedStatement.setString(1, "%" + keyword + "%");
            preparedStatement.setString(2, "%" + keyword + "%");
            preparedStatement.setString(3, "%" + keyword + "%");
            preparedStatement.setString(4, "%" + keyword + "%");
            preparedStatement.setString(5, "%" + keyword + "%");
            preparedStatement.setInt(6, limit);
            preparedStatement.setInt(7, offset);

            countStatement.setString(1, "%" + keyword + "%");
            countStatement.setString(2, "%" + keyword + "%");
            countStatement.setString(3, "%" + keyword + "%");
            countStatement.setString(4, "%" + keyword + "%");
            countStatement.setString(5, "%" + keyword + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    if (resultSet.getDate("deleted_at") != null) {
                        continue;
                    }
                    hotels.add(mapResultSetToHotel(resultSet));
                }
            }

            // Calculate total count
            int total = 0;
            try (ResultSet countResultSet = countStatement.executeQuery()) {
                if (countResultSet.next()) {
                    total = countResultSet.getInt(1);
                }
            }

            return new PaginatedResult<>(hotels, total);

        } catch (SQLException e) {
            handleSQLException(e);
            // Alert the user that something went wrong
            Logger.getGlobal().severe("Something went wrong while paginating hotels.");
        }

        return null;
    }

    public PaginatedResult<Hotel> paginate(int offset, int limit) {
        return paginate(offset, limit, null);
    }
}
