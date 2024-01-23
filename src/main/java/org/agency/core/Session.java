package org.agency.core;

import org.agency.entities.User;
import org.agency.controllers.UserController;

/**
 * Represents the user session in the application.
 */
public class Session {

    private User currentUser;

    /**
     * Creates a new session with no current user.
     */
    public Session() {
        currentUser = null;
    }

    /**
     * Gets the current user of the session.
     *
     * @return The current user or null if no user is logged in.
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the current user of the session.
     *
     * @param user The user to set as the current user.
     */
    public void setCurrentUser(User user) {
        currentUser = user;
    }

    /**
     * Destroys the session by setting the current user to null.
     */
    public void destroy() {
        currentUser = null;
    }

    /**
     * Checks whether a user is logged in.
     *
     * @return True if a user is logged in, false otherwise.
     */
    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public boolean login(String username, String password) {
        UserController userController = new UserController();
        User user = userController.loginUser(username, password);

        if (user != null) {
            currentUser = user;
            return true;
        }

        return false;
    }

    public boolean isAdmin() {
        return currentUser.getRole().equals("ADMIN");
    }

}