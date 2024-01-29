package org.agency;

import org.agency.controllers.HotelController;
import org.agency.controllers.UserController;
import org.agency.controllers.PansionController;
import org.agency.controllers.RoomController;
import org.agency.controllers.ReservationController;
import org.agency.controllers.SeasonController;

import org.agency.core.Session;
import org.agency.entities.User;
import org.agency.views.MainView;
import org.agency.views.auth.LoginView;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    // Create a logger object to log messages for Main class
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    // Declare Session object to store session information
    private static Session session;

    // Declare controller objects for various entities
    private static UserController userController;
    private static HotelController hotelController;
    private static PansionController pansionController;
    private static RoomController roomController;
    private static ReservationController reservationController;
    private static SeasonController seasonController;

    // Getters for the controller objects and Session
    public static Session getSession() {
        return session;
    }

    public static UserController getUserController() {
        return userController;
    }

    public static HotelController getHotelController() {
        return hotelController;
    }

    public static PansionController getPansionController() {
        return pansionController;
    }

    public static RoomController getRoomController() {
        return roomController;
    }

    public static ReservationController getReservationController() {
        return reservationController;
    }

    public static SeasonController getSeasonController() {
        return seasonController;
    }

    public static void main(String[] args) {
        try {
            // Create a new Session object
            session = new Session();

            // Initiate the login process by creating a LoginView object
            LoginView loginView = new LoginView();

            // Wait until the user is logged in
            while (!session.isLoggedIn()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // Log the interrupted thread exception
                    LOGGER.log(Level.SEVERE, "Thread interrupted: " + e.getMessage(), e);
                }
            }

            // If the user is logged in, create controller objects and initialize the main view
            if (session.isLoggedIn()) {
                userController = new UserController();
                hotelController = new HotelController();
                pansionController = new PansionController();
                roomController = new RoomController();
                reservationController = new ReservationController();
                seasonController = new SeasonController();
                MainView mainView = new MainView();
            }
        } catch (Exception e) {
            // Log any exceptions that occur during the execution of the program
            LOGGER.log(Level.SEVERE, "An error occurred: " + e.getMessage(), e);
        }
    }
}
