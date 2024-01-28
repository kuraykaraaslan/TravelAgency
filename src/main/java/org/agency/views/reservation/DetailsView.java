package org.agency.views.reservation;

import org.agency.controllers.HotelController;
import org.agency.controllers.RoomController;
import org.agency.entities.Hotel;
import org.agency.entities.Room;
import org.agency.entities.Reservation;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.time.LocalDate;
import java.util.Date;

public class DetailsView {

    public JFrame frame = new JFrame("Reservation");
    public Reservation reservation;

    public Dimension labelDimension = new Dimension(150, 30);
    public Dimension fieldDimension = new Dimension(150, 30);

    public HotelController hotelController = new HotelController();

    public RoomController roomController = new RoomController();

    public DetailsView(Reservation reservation) {
        this.reservation = reservation;

        if (reservation.getId() == 0) {

            // Set default values
            this.reservation.setAdultCount(1);
            this.reservation.setChildCount(0);
        }

        configureFrame(this.frame);
        this.frame.setVisible(true);
    }

    public DetailsView() {
        DetailsView detailsView = new DetailsView(new Reservation());
    }

    private void configureFrame(JFrame frame) {

        /*
            private String guestCitizenId;
            private String guestFullName;
            private String guestEmail;
            private String guestPhone;
            private Date checkIn;
            private Date checkOut;
            private int adultCount;
            private int childCount;
            private double price;
            private int hotelId;
            private int roomId;
         */
        frame.setSize(400, 500);
        frame.setIconImage(new ImageIcon("src/main/resources/icon.png").getImage());
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 1));

        // Panels
        // Guest Panel
        JPanel guestPanel = new JPanel();

        // Guest Citizen ID Panel
        JPanel guestCitizenIdPanel = new JPanel();
        guestCitizenIdPanel.setLayout(new FlowLayout(0, 0, FlowLayout.LEFT));
        JLabel guestCitizenIdLabel = new JLabel("Citizen ID");
        guestCitizenIdLabel.setPreferredSize(labelDimension);
        JTextField guestCitizenIdField = new JTextField();
        guestCitizenIdField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // Save button logic
                reservation.setGuestCitizenId(guestCitizenIdField.getText());
            }
        });
        guestCitizenIdField.setPreferredSize(fieldDimension);
        guestCitizenIdPanel.add(guestCitizenIdLabel);
        guestCitizenIdPanel.add(guestCitizenIdField);


        // Guest Full Name Panel
        JPanel guestFullNamePanel = new JPanel();
        guestFullNamePanel.setLayout(new FlowLayout(0, 0, FlowLayout.LEFT));
        JLabel guestFullNameLabel = new JLabel("Full Name");
        guestFullNameLabel.setPreferredSize(labelDimension);
        JTextField guestFullNameField = new JTextField();
        guestFullNameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // Save button logic
                reservation.setGuestFullName(guestFullNameField.getText());
            }
        });

        guestFullNameField.setPreferredSize(fieldDimension);
        guestFullNamePanel.add(guestFullNameLabel);
        guestFullNamePanel.add(guestFullNameField);

        // Guest Email Panel
        JPanel guestEmailPanel = new JPanel();

        guestEmailPanel.setLayout(new FlowLayout(0, 0, FlowLayout.LEFT));
        JLabel guestEmailLabel = new JLabel("Email");
        guestEmailLabel.setPreferredSize(labelDimension);
        JTextField guestEmailField = new JTextField();

        guestEmailField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // Save button logic
                reservation.setGuestEmail(guestEmailField.getText());
            }
        });

        guestEmailField.setPreferredSize(fieldDimension);
        guestEmailPanel.add(guestEmailLabel);

        guestEmailPanel.add(guestEmailField);


        // Guest Phone Panel

        JPanel guestPhonePanel = new JPanel();
        guestPhonePanel.setLayout(new FlowLayout(0, 0, FlowLayout.LEFT));
        JLabel guestPhoneLabel = new JLabel("Phone");
        guestPhoneLabel.setPreferredSize(labelDimension);

        JTextField guestPhoneField = new JTextField();
        guestPhoneField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // Save button logic
                reservation.setGuestPhone(guestPhoneField.getText());
            }
        });
        guestPhoneField.setPreferredSize(fieldDimension);
        guestPhonePanel.add(guestPhoneLabel);
        guestPhonePanel.add(guestPhoneField);



        // Check In Panel
        JPanel checkInPanel = new JPanel();
        checkInPanel.setLayout(new FlowLayout(0, 0, FlowLayout.LEFT));
        JLabel checkInLabel = new JLabel("Check In");
        checkInLabel.setPreferredSize(labelDimension);
        DatePicker checkInDatePicker = new DatePicker();
        checkInDatePicker.setDate(reservation.getCheckInLocalDate());
        checkInDatePicker.addDateChangeListener((dce) -> {
            reservation.setCheckInLocalDate(checkInDatePicker.getDate());
        });

        checkInPanel.add(checkInLabel);

        checkInPanel.add(checkInDatePicker);

        // Check Out Panel
        JPanel checkOutPanel = new JPanel();
        checkOutPanel.setLayout(new FlowLayout(0, 0, FlowLayout.LEFT));
        JLabel checkOutLabel = new JLabel("Check Out");
        checkOutLabel.setPreferredSize(labelDimension);
        DatePicker checkOutDatePicker = new DatePicker();
        checkOutDatePicker.setDate(reservation.getCheckOutLocalDate());
        checkOutDatePicker.addDateChangeListener((dce) -> {
            reservation.setCheckOutLocalDate(checkOutDatePicker.getDate());
        });

        checkOutPanel.add(checkOutLabel);
        checkOutPanel.add(checkOutDatePicker);

        // Hotel Panel
        JPanel hotelPanel = new JPanel();
        hotelPanel.setLayout(new FlowLayout(0, 0, FlowLayout.LEFT));
        JLabel hotelLabel = new JLabel("Hotel");
        hotelLabel.setPreferredSize(labelDimension);
        JComboBox hotelComboBox = new JComboBox();
        for (Hotel hotel : hotelController.getAll()) {
            hotelComboBox.addItem(hotel);
        }
        //CUSTOM RENDERER
        hotelComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                if (value instanceof Hotel) {
                    value = ((Hotel) value).getName();
                }
                return super.getListCellRendererComponent(list, value, index,
                        isSelected, cellHasFocus);
            }
        });

        hotelComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // Save button logic
                reservation.setHotelId(((Hotel) hotelComboBox.getSelectedItem()).getId());
            }
        });

        hotelPanel.add(hotelLabel);

        hotelPanel.add(hotelComboBox);

        // Room Panel

        JPanel roomPanel = new JPanel();
        roomPanel.setLayout(new FlowLayout(0, 0, FlowLayout.LEFT));
        JLabel roomLabel = new JLabel("Room");
        roomLabel.setPreferredSize(labelDimension);
        JComboBox roomComboBox = new JComboBox();
        for (Room room : roomController.getAll()) {
            roomComboBox.addItem(room);
        }




        guestPanel.setLayout(new GridLayout(0, 1));
        guestPanel.add(guestCitizenIdPanel);
        guestPanel.add(guestFullNamePanel);
        guestPanel.add(guestEmailPanel);
        guestPanel.add(guestPhonePanel);
        guestPanel.add(checkInPanel);
        guestPanel.add(checkOutPanel);
        guestPanel.add(hotelPanel);


        mainPanel.add(guestPanel);
        // Buttons Panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        JButton saveButton = new JButton("Save");
        JButton deleteButton = new JButton("Delete");

        buttonsPanel.add(saveButton);
        buttonsPanel.add(deleteButton);

        mainPanel.add(buttonsPanel);

        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // Save button logic
            }
        });

        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // Delete button logic
            }
        });

        frame.add(mainPanel);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(DetailsView::new);
    }
}
