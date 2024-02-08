package org.agency.dao;

import java.util.*;

import org.agency.entities.Room;
import org.agency.core.Database;

import java.sql.*;

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
                "has_valuables_safe, has_gaming_console, has_projector, hotel_id, season_id, pansion_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS)) {
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

    public ArrayList<Room> getAll() {
        ArrayList<Room> rooms = new ArrayList<>();
        String query = "SELECT * FROM rooms ORDER BY id ASC";

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
        preparedStatement.setInt(15, room.getHotelId());
        preparedStatement.setInt(16, room.getSeasonId());
        preparedStatement.setInt(17, room.getPansionId());
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
        room.setHotelId(resultSet.getInt("hotel_id"));
        room.setSeasonId(resultSet.getInt("season_id"));
        room.setPansionId(resultSet.getInt("pansion_id"));
        return room;
    }

    private void handleSQLException(SQLException e) {
        e.printStackTrace();
        System.err.println("Error message: " + e.getMessage());
        // Handle exceptions as needed
    }

    public void update(Room room) {
        String query = "UPDATE rooms SET room_number = ?, type = ?, double_bed_count = ?, single_bed_count = ?, " +
                "adult_price = ?, child_price = ?, square_meters = ?, has_television = ?, has_balcony = ?, " +
                "has_air_conditioning = ?, has_minibar = ?, has_valuables_safe = ?, has_gaming_console = ?, " +
                "has_projector = ?, hotel_id = ?, season_id = ?, pansion_id = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setParameters(preparedStatement, room);
            preparedStatement.setInt(18, room.getId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating room failed, no rows affected.");
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public void delete(Room room) {
        String query = "DELETE FROM rooms WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, room.getId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Deleting room failed, no rows affected.");
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public ArrayList<Room> getByHotelId(int hotelId) {
        ArrayList<Room> rooms = new ArrayList<>();
        String query = "SELECT * FROM rooms WHERE hotel_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, hotelId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    rooms.add(mapResultSetToRoom(resultSet));
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return rooms;
    }

    public ArrayList<Room> getByHotelId(int hotelId, String query) {
        ArrayList<Room> rooms = new ArrayList<>();
        String sqlQuery = "SELECT * FROM rooms WHERE hotel_id = ? AND (room_number LIKE ? OR type LIKE ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1, hotelId);
            preparedStatement.setString(2, "%" + query + "%");
            preparedStatement.setString(3, "%" + query + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    rooms.add(mapResultSetToRoom(resultSet));
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return rooms;
    }

    public ArrayList<Room> getByHotelAndSeasonId(int hotelId, int seasonId) {
        ArrayList<Room> rooms = new ArrayList<>();
        String query = "SELECT * FROM rooms WHERE hotel_id = ? AND season_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, hotelId);
            preparedStatement.setInt(2, seasonId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    rooms.add(mapResultSetToRoom(resultSet));
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return rooms;
    }

    public ArrayList<Room> getByHotelAndSeasonIdAndPansionId(int hotelId, int seasonId, int pansionId) {
        ArrayList<Room> rooms = new ArrayList<>();
        String query = "SELECT * FROM rooms WHERE hotel_id = ? AND season_id = ? AND pansion_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, hotelId);
            preparedStatement.setInt(2, seasonId);
            preparedStatement.setInt(3, pansionId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    rooms.add(mapResultSetToRoom(resultSet));
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return rooms;
    }

    public ArrayList<Room> getByFilters(HashMap<String, Object> filters) {
        ArrayList<Room> rooms = new ArrayList<>();
        String fullQuery = "SELECT * FROM rooms WHERE hotel_id = ? AND (room_number LIKE ? OR type LIKE ?) " +
                "AND double_bed_count = ? AND single_bed_count = ? AND adult_price = ? AND child_price = ? " +
                "AND square_meters = ? AND has_television = ? AND has_balcony = ? AND has_air_conditioning = ? " +
                "AND has_minibar = ? AND has_valuables_safe = ? AND has_gaming_console = ? AND has_projector = ?";

        String query = "SELECT * FROM rooms WHERE ";

        if (filters.containsKey("hotel_id")) {
            query += "hotel_id = " + filters.get("hotel_id") + " AND ";
        }
        if (filters.containsKey("room_number")) {
            query += "room_number LIKE '%" + filters.get("room_number") + "%' AND ";
        }
        if (filters.containsKey("type")) {
            query += "type LIKE '%" + filters.get("type") + "%' AND ";
        }
        if (filters.containsKey("double_bed_count")) {
            query += "double_bed_count = " + filters.get("double_bed_count") + " AND ";
        }
        if (filters.containsKey("single_bed_count")) {
            query += "single_bed_count = " + filters.get("single_bed_count") + " AND ";
        }
        if (filters.containsKey("adult_price")) {
            query += "adult_price = " + filters.get("adult_price") + " AND ";
        }
        if (filters.containsKey("child_price")) {
            query += "child_price = " + filters.get("child_price") + " AND ";
        }
        if (filters.containsKey("square_meters")) {
            query += "square_meters = " + filters.get("square_meters") + " AND ";
        }
        // for booleans if the value is true we add the column name to the query
        if (filters.containsKey("has_television") && (boolean) filters.get("has_television")) {
            query += "has_television = true AND ";
        }

        if (filters.containsKey("has_balcony") && (boolean) filters.get("has_balcony")) {
            query += "has_balcony = true AND ";
        }

        if (filters.containsKey("has_air_conditioning") && (boolean) filters.get("has_air_conditioning")) {
            query += "has_air_conditioning = true AND ";
        }

        if (filters.containsKey("has_minibar") && (boolean) filters.get("has_minibar")) {
            query += "has_minibar = true AND ";
        }

        if (filters.containsKey("has_valuables_safe") && (boolean) filters.get("has_valuables_safe")) {
            query += "has_valuables_safe = true AND ";
        }

        if (filters.containsKey("has_gaming_console") && (boolean) filters.get("has_gaming_console")) {
            query += "has_gaming_console = true AND ";
        }

        if (filters.containsKey("has_projector") && (boolean) filters.get("has_projector")) {
            query += "has_projector = true AND ";
        }

        // remove the last AND
        query = query.substring(0, query.length() - 5);

        System.out.println(query);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    rooms.add(mapResultSetToRoom(resultSet));
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return rooms;
    }

    ///getAllWithHotelJoin
    public ArrayList<Room> getAllHotelJoinedByFilters(HashMap<String, Object> filters) {

        ArrayList<Room> rooms = new ArrayList<>();
        String query = "SELECT *  FROM rooms r JOIN hotels h ON r.hotel_id = h.id WHERE ";


        if (filters.containsKey("room_number")) {
            query += "r.room_number LIKE '%" + filters.get("room_number") + "%' AND ";
        }

        if (filters.containsKey("type")) {
            query += "r.type LIKE '%" + filters.get("type") + "%' AND ";
        }

        if (filters.containsKey("double_bed_count")) {
            query += "r.double_bed_count >= " + filters.get("double_bed_count") + " AND ";
        }

        if (filters.containsKey("single_bed_count")) {
            query += "r.single_bed_count >= " + filters.get("single_bed_count") + " AND ";
        }

        if (filters.containsKey("adult_price")) {
            query += "r.adult_price <= " + filters.get("adult_price") + " AND ";
        }

        if (filters.containsKey("child_price")) {
            query += "r.child_price <= " + filters.get("child_price") + " AND ";
        }

        if (filters.containsKey("square_meters")) {
            query += "r.square_meters >= " + filters.get("square_meters") + " AND ";
        }

        if (filters.containsKey("has_television") && (boolean) filters.get("has_television")) {
            query += "r.has_television = true AND ";
        }

        if (filters.containsKey("has_balcony") && (boolean) filters.get("has_balcony")) {
            query += "r.has_balcony = true AND ";
        }

        if (filters.containsKey("has_air_conditioning") && (boolean) filters.get("has_air_conditioning")) {
            query += "r.has_air_conditioning = true AND ";
        }

        if (filters.containsKey("has_minibar") && (boolean) filters.get("has_minibar")) {
            query += "r.has_minibar = true AND ";
        }

        if (filters.containsKey("has_valuables_safe") && (boolean) filters.get("has_valuables_safe")) {
            query += "r.has_valuables_safe = true AND ";
        }

        if (filters.containsKey("has_gaming_console") && (boolean) filters.get("has_gaming_console")) {
            query += "r.has_gaming_console = true AND ";
        }

        if (filters.containsKey("has_projector") && (boolean) filters.get("has_projector")) {
            query += "r.has_projector = true AND ";
        }

        if (filters.containsKey("hotel_name")) {
            query += "h.name LIKE '%" + filters.get("hotel_name") + "%' AND ";
        }

        if (filters.containsKey("star_rating")) {
            query += "h.star_rating >= " + filters.get("star_rating") + " AND ";
        }

        if (filters.containsKey("address_city")) {
            query += "h.address_city LIKE '%" + filters.get("address_city") + "%' AND ";
        }

        if (filters.containsKey("address_country")) {
            query += "h.address_country LIKE '%" + filters.get("address_country") + "%' AND ";
        }

        if (filters.containsKey("address_district")) {
            query += "h.address_district LIKE '%" + filters.get("address_district") + "%' AND ";
        }

        if (filters.containsKey("has_car_park") && (boolean) filters.get("has_car_park")) {
            query += "h.has_car_park = true AND ";
        }

        if (filters.containsKey("has_restaurant") && (boolean) filters.get("has_restaurant")) {
            query += "h.has_restaurant = true AND ";
        }

        if (filters.containsKey("has_pool") && (boolean) filters.get("has_pool")) {
            query += "h.has_pool = true AND ";
        }

        if (filters.containsKey("has_concierge") && (boolean) filters.get("has_concierge")) {
            query += "h.has_concierge = true AND ";
        }

        if (filters.containsKey("has_spa") && (boolean) filters.get("has_spa")) {
            query += "h.has_spa = true AND ";
        }

        if (filters.containsKey("has_room_service") && (boolean) filters.get("has_room_service")) {
            query += "h.has_room_service = true AND ";
        }


        // remove the last AND
        if (query.endsWith("AND ")) {
            query = query.substring(0, query.length() - 5);
        } else {
            query = query.substring(0, query.length() - 6);
        }


        System.out.println(query);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    rooms.add(mapResultSetToRoom(resultSet));
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return rooms;

    }

}







