package org.agency.views;

import org.agency.Main;

import javax.swing.*;
import java.awt.*;

public class MainView {

    private JFrame frame;
    private Box mainBox;

    public MainView() {
        frame = new JFrame("Travel Agency Main");
        frame.setSize(400, 420);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.WHITE); // Set frame background to white

        mainBox = Box.createVerticalBox();
        frame.add(mainBox);

        // Add components to the main box
        addLogo();
        addButtons();

        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void addLogo() {
        JPanel logoPanel = new JPanel();
        JLabel logoLabel = new JLabel(new ImageIcon(getClass().getResource("/logo.jpg")));
        logoPanel.add(logoLabel);
        logoPanel.setBackground(Color.WHITE); // Set logo panel background to white
        mainBox.add(logoPanel);
    }

    private void addButtons() {
        mainBox.add(Box.createVerticalStrut(20)); // Add some vertical space between logo and buttons

        JButton addReservationButton = new JButton("Add Reservation");
        JButton listReservationsButton = new JButton("List Reservations");
        JButton usersButton = new JButton("Users");
        JButton hotelsButton = new JButton("Hotels");
        JButton exitButton = new JButton("Exit");

        // Set a preferred width for the buttons
        Dimension buttonSize = new Dimension(300, 40);
        addReservationButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        listReservationsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        usersButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        hotelsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        addReservationButton.setMaximumSize(buttonSize);
        listReservationsButton.setMaximumSize(buttonSize);
        usersButton.setMaximumSize(buttonSize);
        hotelsButton.setMaximumSize(buttonSize);
        exitButton.setMaximumSize(buttonSize);

        addReservationButton.setPreferredSize(buttonSize);
        listReservationsButton.setPreferredSize(buttonSize);
        usersButton.setPreferredSize(buttonSize);
        hotelsButton.setPreferredSize(buttonSize);
        exitButton.setPreferredSize(buttonSize);

        // Add action listeners to the buttons
        usersButton.addActionListener(e -> new org.agency.views.user.ListView());
        exitButton.addActionListener(e -> System.exit(0));

        //if user is not admin, disable users and hotels buttons
        if(!Main.getSession().isAdmin()){
            usersButton.setEnabled(false);
        }

        mainBox.setBackground(Color.WHITE); // Set main box background to white
        // Add vertical gaps between buttons
        mainBox.add(Box.createVerticalGlue());
        mainBox.add(addReservationButton);
        //
        mainBox.add(Box.createVerticalStrut(10)); // Add vertical gap
        mainBox.add(listReservationsButton);
        //
        mainBox.add(Box.createVerticalStrut(10)); // Add vertical gap
        mainBox.add(hotelsButton);
        //
        mainBox.add(Box.createVerticalStrut(10)); // Add vertical gap
        mainBox.add(usersButton);
        //exits the program
        mainBox.add(Box.createVerticalStrut(10)); // Add vertical gap
        mainBox.add(exitButton);


        mainBox.add(Box.createVerticalGlue());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainView::new);
    }
}
