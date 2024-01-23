package org.agency;

import org.agency.controllers.UserController;
import org.agency.core.Session;
import org.agency.entities.User;
import org.agency.views.MainView;
import org.agency.views.auth.LoginView;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static Session session;

    private static UserController userController;

    public static Session getSession() {
        return session;
    }

    public static void main(String[] args) {
        try {
            session = new Session();

            LoginView loginView = new LoginView("kuraykaraaslan", "admin");

            while (!session.isLoggedIn()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    LOGGER.log(Level.SEVERE, "Thread interrupted: " + e.getMessage(), e);
                }
            }

            if (session.isLoggedIn()) {
                userController = new UserController();
                MainView mainView = new MainView();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred: " + e.getMessage(), e);
        }
    }
}