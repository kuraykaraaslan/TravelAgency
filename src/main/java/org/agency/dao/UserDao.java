package org.agency.dao;

import java.util.ArrayList;
import java.util.List;
import org.agency.entities.User;
import org.agency.core.Database;
import org.agency.core.PaginatedResult;

import java.sql.*;

public class UserDao {

    private Connection connection;

    public UserDao() {
        connection = Database.getInstance();
        if (connection == null) {
            throw new RuntimeException("Failed to connect to the database.");
        }
    }

    public boolean registerUser(User user) {
        String query = "INSERT INTO users (name, username, email, password, created_at, updated_at, role) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setRegisterParameters(preparedStatement, user);

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            handleSQLException(e);
            return false;
        }
    }

    public User loginUser(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToUser(resultSet);
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return null;
    }

    private void setRegisterParameters(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getUsername());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getPassword());
        preparedStatement.setDate(5, new java.sql.Date(System.currentTimeMillis()));
        preparedStatement.setDate(6, new java.sql.Date(System.currentTimeMillis()));
        preparedStatement.setString(7, user.getRole());
    }

    private User mapResultSetToUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setEmail(resultSet.getString("email"));
        user.setCreatedAt(resultSet.getDate("created_at"));
        user.setUpdatedAt(resultSet.getDate("updated_at"));
        user.setRole(resultSet.getString("role"));

        return user;
    }

    public User getById(int userId) {
        String query = "SELECT * FROM users WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToUser(resultSet);
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return null;
    }

    public boolean update(User user) {
        String query = "UPDATE users SET name = ?, username = ?, email = ?, password = ?, updated_at = ?, role = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setUpdateParameters(preparedStatement, user);

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            handleSQLException(e);
            return false;
        }
    }

    public boolean delete(User user) {
        String query = "UPDATE users SET deleted_at = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDate(1, new java.sql.Date(System.currentTimeMillis()));
            preparedStatement.setInt(2, user.getId());

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            handleSQLException(e);
            return false;
        }
    }

    public List<User> search(String keyword) {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users WHERE name LIKE ? OR username LIKE ? OR email LIKE ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, "%" + keyword + "%");
            preparedStatement.setString(2, "%" + keyword + "%");
            preparedStatement.setString(3, "%" + keyword + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    users.add(mapResultSetToUser(resultSet));
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return users;
    }

    public boolean restore(User user) {
        String query = "UPDATE users SET deleted_at = NULL WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, user.getId());

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            handleSQLException(e);
            return false;
        }
    }

    public boolean destroy(User user) {
        String query = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, user.getId());

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            handleSQLException(e);
            return false;
        }
    }

    public ArrayList<User> getDeleted() {
        ArrayList<User> users = new ArrayList<>();
        String query = "SELECT * FROM users WHERE deleted_at IS NOT NULL";

        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                users.add(mapResultSetToUser(resultSet));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return users;
    }

    public User getByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToUser(resultSet);
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return null;
    }

    private void setUpdateParameters(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getUsername());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getPassword());
        preparedStatement.setDate(5, new java.sql.Date(System.currentTimeMillis()));
        preparedStatement.setString(6, user.getRole());
        preparedStatement.setInt(7, user.getId());
    }

    private void handleSQLException(SQLException e) {
        e.printStackTrace();
        // Handle exceptions as needed
    }

    // Additions
    public PaginatedResult<User> paginate(int offset, int limit, String keyword) {
        ArrayList<User> users = new ArrayList<>();

        // Handle null keyword
        if (keyword == null) {
            keyword = "";
        }

        // Handle negative offset
        if (offset < 0) {
            offset = 0;
        }

        //String query = "SELECT * FROM users WHERE name LIKE ? OR username LIKE ? OR role LIKE ? OR email LIKE ? LIMIT ? OFFSET ?";
        //String countQuery = "SELECT COUNT(*) FROM users WHERE name LIKE ? OR username LIKE ? OR role LIKE ? OR email LIKE ?";

        String query = "SELECT * FROM users WHERE LOWER(name) LIKE LOWER(?) OR LOWER(username) LIKE LOWER(?) OR LOWER(role) LIKE LOWER(?) OR LOWER(email) LIKE LOWER(?) LIMIT ? OFFSET ?";
        String countQuery = "SELECT COUNT(*) FROM users WHERE LOWER(name) LIKE LOWER(?) OR LOWER(username) LIKE LOWER(?) OR LOWER(role) LIKE LOWER(?) OR LOWER(email) LIKE LOWER(?)";


        try (
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                PreparedStatement countStatement = connection.prepareStatement(countQuery)) {
            preparedStatement.setString(1, "%" + keyword + "%");
            preparedStatement.setString(2, "%" + keyword + "%");
            preparedStatement.setString(3, "%" + keyword + "%");
            preparedStatement.setString(4, "%" + keyword + "%");
            preparedStatement.setInt(5, limit);
            preparedStatement.setInt(6, offset);

            countStatement.setString(1, "%" + keyword + "%");
            countStatement.setString(2, "%" + keyword + "%");
            countStatement.setString(3, "%" + keyword + "%");
            countStatement.setString(4, "%" + keyword + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    if (resultSet.getDate("deleted_at") != null) {
                        continue;
                    }
                    users.add(mapResultSetToUser(resultSet));
                }
            }

            // Calculate total count
            int total = 0;
            try (ResultSet countResultSet = countStatement.executeQuery()) {
                if (countResultSet.next()) {
                    total = countResultSet.getInt(1);
                }
            }

            return new PaginatedResult<>(users, total);

        } catch (SQLException e) {
            handleSQLException(e);
            // Handle the exception or rethrow it based on your application's logic
        }

        return null;
    }

    public PaginatedResult<User> paginate(int offset, int limit) {
        return paginate(offset, limit, null);
    }
}