package org.agency.controllers;

import org.agency.core.PaginatedResult;
import org.agency.dao.UserDao;
import org.agency.entities.User;
import java.util.List;
public class UserController {

    private UserDao userDao;

    public UserController() {
        userDao = new UserDao();
    }

    public boolean registerUser(User user) {
        // Perform additional validation if needed
        return userDao.registerUser(user);
    }

    public User loginUser(String username, String password) {
        // Perform authentication logic
        return userDao.loginUser(username, password);
    }

    public User getById(int userId) {
        return userDao.getById(userId);
    }

    public User getByUsername(String username) {
        return userDao.getByUsername(username);
    }

    public void update(User user) {
        userDao.update(user);
    }

    public void delete(User user) {
        userDao.delete(user);
    }

    public void destroy(User user) {
        userDao.destroy(user);    }

    // Additions
    public PaginatedResult<User> paginate(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        int limit = offset + pageSize;
        return userDao.paginate(offset, limit);
    }

    public PaginatedResult<User> paginate(int page, int pageSize, String query) {
        int offset = (page - 1) * pageSize;
        int limit = offset + pageSize;
        return userDao.paginate(offset, limit, query);
    }

}