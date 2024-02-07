package org.agency.views.reservation;

import org.agency.controllers.*;
import org.agency.entities.*;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DetailsView {

    public JFrame frame = new JFrame("Reservation");
    public Reservation reservation;

    public Dimension labelDimension = new Dimension(150, 30);
    public Dimension fieldDimension = new Dimension(150, 30);

    public HotelController hotelController = new HotelController();
    public RoomController roomController = new RoomController();

    public SeasonController seasonController = new SeasonController();

    public PansionController pansionController = new PansionController();

    public ReservationController reservationController = new ReservationController();

    public JComboBox<Hotel> hotelComboBox = new JComboBox<>();

    public JComboBox<Season> seasonComboBox = new JComboBox<>();

    public JComboBox<Room> roomComboBox = new JComboBox<>();

    public JComboBox<String> pansionComboBox = new JComboBox<>();

    public ArrayList<Hotel> hotels = new ArrayList<>();
    private ArrayList<Season> seasons = new ArrayList<>();
    public ArrayList<Pansion> pansions = new ArrayList<>();
    public ArrayList<Room> rooms = new ArrayList<>();

    public DatePicker checkInPicker;

    public DatePicker checkOutPicker;

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
         * private String guestCitizenId;
         * private String guestFullName;
         * private String guestEmail;
         * private String guestPhone;
         * private Date checkIn;
         * private Date checkOut;
         * private int adultCount;
         * private int childCount;
         * private double price;
         * private int hotelId;
         * private int roomId;
         */

        frame.setSize(500, 500);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(14, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new GridLayout(1, 2));
        JLabel nameLabel = new JLabel("Guest Full Name:");
        nameLabel.setPreferredSize(new Dimension(100, 20));
        JTextField nameField = new JTextField(reservation.getGuestFullName());
        nameField.setPreferredSize(new Dimension(200, 20));
        nameField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                update();
            }

            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                update();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                update();
            }

            private void update() {
                reservation.setGuestFullName(nameField.getText());
            }
        });

        namePanel.add(nameLabel);
        namePanel.add(nameField);
        panel.add(namePanel);

        JPanel citizenIdPanel = new JPanel();
        citizenIdPanel.setLayout(new GridLayout(1, 2));
        JLabel citizenIdLabel = new JLabel("Guest Citizen ID:");
        citizenIdLabel.setPreferredSize(new Dimension(100, 20));
        JTextField citizenIdField = new JTextField(reservation.getGuestCitizenId());
        citizenIdField.setPreferredSize(new Dimension(200, 20));
        citizenIdField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                update();
            }

            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                update();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                update();
            }

            private void update() {
                reservation.setGuestCitizenId(citizenIdField.getText());
            }
        });

        citizenIdPanel.add(citizenIdLabel);
        citizenIdPanel.add(citizenIdField);
        panel.add(citizenIdPanel);

        JPanel emailPanel = new JPanel();
        emailPanel.setLayout(new GridLayout(1, 2));
        JLabel emailLabel = new JLabel("Guest Email:");
        emailLabel.setPreferredSize(new Dimension(100, 20));

        JTextField emailField = new JTextField(reservation.getGuestEmail());
        emailField.setPreferredSize(new Dimension(200, 20));

        emailField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                update();
            }

            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                update();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                update();
            }

            private void update() {
                reservation.setGuestEmail(emailField.getText());
            }
        });

        emailPanel.add(emailLabel);
        emailPanel.add(emailField);
        panel.add(emailPanel);

        JPanel phonePanel = new JPanel();
        phonePanel.setLayout(new GridLayout(1, 2));
        JLabel phoneLabel = new JLabel("Guest Phone:");
        phoneLabel.setPreferredSize(new Dimension(100, 20));

        JTextField phoneField = new JTextField(reservation.getGuestPhone());
        phoneField.setPreferredSize(new Dimension(200, 20));

        phoneField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                update();
            }

            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                update();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                update();
            }

            private void update() {
                reservation.setGuestPhone(phoneField.getText());
            }
        });

        phonePanel.add(phoneLabel);
        phonePanel.add(phoneField);

        panel.add(phonePanel);

        JPanel checkInPanel = new JPanel();
        checkInPanel.setLayout(new GridLayout(1, 2));
        JLabel checkInLabel = new JLabel("Check In:");
        checkInLabel.setPreferredSize(new Dimension(100, 20));

        checkInPicker = new DatePicker();
        checkInPicker.setDate(reservation.getCheckInLocalDate());
        checkInPicker.addDateChangeListener(event -> {
            reservation.setCheckIn(java.sql.Date.valueOf(checkInPicker.getDate()));
        });

        checkInPanel.add(checkInLabel);
        checkInPanel.add(checkInPicker);

        panel.add(checkInPanel);

        JPanel checkOutPanel = new JPanel();
        checkOutPanel.setLayout(new GridLayout(1, 2));
        JLabel checkOutLabel = new JLabel("Check Out:");
        checkOutLabel.setPreferredSize(new Dimension(100, 20));

        checkOutPicker = new DatePicker();
        checkOutPicker.setDate(reservation.getCheckOutLocalDate());
        checkOutPicker.addDateChangeListener(event -> {
            reservation.setCheckOut(java.sql.Date.valueOf(checkOutPicker.getDate()));
        });

        checkOutPanel.add(checkOutLabel);
        checkOutPanel.add(checkOutPicker);

        panel.add(checkOutPanel);

        JPanel adultCountPanel = new JPanel();
        adultCountPanel.setLayout(new GridLayout(1, 2));
        JLabel adultCountLabel = new JLabel("Adult Count:");
        adultCountLabel.setPreferredSize(new Dimension(100, 20));

        JComboBox<Integer> adultCountComboBox = new JComboBox<>();
        adultCountComboBox.addItem(1);
        adultCountComboBox.addItem(2);
        adultCountComboBox.addItem(3);
        adultCountComboBox.addItem(4);
        adultCountComboBox.addItem(5);
        adultCountComboBox.addItem(6);

        adultCountComboBox.setSelectedItem(reservation.getAdultCount());

        adultCountComboBox.addActionListener(e -> {
            reservation.setAdultCount((Integer) adultCountComboBox.getSelectedItem());
        });

        adultCountPanel.add(adultCountLabel);

        adultCountPanel.add(adultCountComboBox);

        panel.add(adultCountPanel);

        JPanel childCountPanel = new JPanel();
        childCountPanel.setLayout(new GridLayout(1, 2));
        JLabel childCountLabel = new JLabel("Child Count:");
        childCountLabel.setPreferredSize(new Dimension(100, 20));

        JComboBox<Integer> childCountComboBox = new JComboBox<>();
        childCountComboBox.addItem(0);
        childCountComboBox.addItem(1);
        childCountComboBox.addItem(2);
        childCountComboBox.addItem(3);
        childCountComboBox.addItem(4);
        childCountComboBox.addItem(5);
        childCountComboBox.addItem(6);

        childCountComboBox.setSelectedItem(reservation.getChildCount());

        childCountComboBox.addActionListener(e -> {
            reservation.setChildCount((Integer) childCountComboBox.getSelectedItem());
        });

        childCountPanel.add(childCountLabel);
        childCountPanel.add(childCountComboBox);

        panel.add(childCountPanel);

        // hotel id

        JPanel hotelPanel = new JPanel();
        hotelPanel.setLayout(new GridLayout(1, 2));
        JLabel hotelLabel = new JLabel("Hotel: (click for details)");
        hotelLabel.setPreferredSize(new Dimension(100, 20));

        hotelLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (reservation.getHotelId() != 0) {
                    new org.agency.views.hotel.DetailsView(reservation.getHotelId());
                }
            }
        });


        hotelComboBox = new JComboBox<>();
        ComboBoxModel<Hotel> hotelComboBoxModel = new DefaultComboBoxModel<>(hotels.toArray(new Hotel[0]));
        hotelComboBox.setModel(hotelComboBoxModel);
        hotelComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {

                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (value instanceof Hotel) {
                    Hotel hotel = (Hotel) value;
                    setText(hotel.getName());
                }
                return this;
            }
        });

        hotelPanel.add(hotelLabel);
        hotelPanel.add(hotelComboBox);
        panel.add(hotelPanel);

        // season id

        JPanel seasonPanel = new JPanel();
        seasonPanel.setLayout(new GridLayout(1, 2));
        JLabel seasonLabel = new JLabel("Season: (click for details)");
        seasonLabel.setPreferredSize(new Dimension(100, 20));

        seasonLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (reservation.getSeasonId() != 0) {
                    SeasonController seasonController = new SeasonController();
                    new org.agency.views.season.DetailsView(seasonController.getById(reservation.getSeasonId()));
                }
            }
        });

        seasonComboBox = new JComboBox<>();
        ComboBoxModel<Season> seasonComboBoxModel = new DefaultComboBoxModel<>(seasons.toArray(new Season[0]));
        seasonComboBox.setModel(seasonComboBoxModel);
        seasonComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {

                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (value instanceof Season) {
                    Season season = (Season) value;
                    setText(season.getName());
                }
                return this;
            }
        });

        seasonPanel.add(seasonLabel);
        seasonPanel.add(seasonComboBox);
        panel.add(seasonPanel);

        // room id

        JPanel roomPanel = new JPanel();
        JPanel roomRightPanel = new JPanel();
        roomPanel.setLayout(new GridLayout(1, 2));
        roomRightPanel.setLayout(new GridLayout(1, 2));
        JLabel roomLabel = new JLabel("Room: (click for details)");
        roomLabel.setPreferredSize(new Dimension(100, 20));

        roomLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (reservation.getRoomId() != 0) {
                    RoomController roomController = new RoomController();
                    new org.agency.views.room.DetailsView(roomController.getById(reservation.getRoomId()));
                }
            }
        });

        roomComboBox = new JComboBox<>();
        ComboBoxModel<Room> roomComboBoxModel = new DefaultComboBoxModel<>(rooms.toArray(new Room[0]));
        roomComboBox.setModel(roomComboBoxModel);
        roomComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {

                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (value instanceof Room) {
                    Room room = (Room) value;
                    setText(room.getRoomNumber());
                    if (checkRoomAvailabilitySelectedRoom(room)) {
                        setBackground(Color.GREEN);
                    } else {
                        setBackground(Color.RED);
                    }
                }
                return this;
            }
        });

        JButton checkRoomAvailabilityButton = new JButton("Check");

        checkRoomAvailabilityButton.addActionListener(e -> {
            checkRoomAvailability();
        });

        roomPanel.add(roomLabel);
        roomRightPanel.add(roomComboBox);
        roomRightPanel.add(checkRoomAvailabilityButton);
        roomPanel.add(roomRightPanel);
        panel.add(roomPanel);

        // pansion id

        JPanel pansionPanel = new JPanel();
        pansionPanel.setLayout(new GridLayout(1, 2));
        JLabel pansionLabel = new JLabel("Pansion:");

        JComboBox<Pansion> pansionComboBox = new JComboBox<>();
        ComboBoxModel<Pansion> pansionComboBoxModel = new DefaultComboBoxModel<>(pansions.toArray(new Pansion[0]));

        pansionComboBox.setModel(pansionComboBoxModel);
        pansionComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {

                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (value instanceof Pansion) {
                    Pansion pansion = (Pansion) value;
                    setText(pansion.getName());
                }
                return this;
            }
        });

        pansionPanel.add(pansionLabel);
        pansionPanel.add(pansionComboBox);
        panel.add(pansionPanel);

        // price

        JPanel pricePanel = new JPanel();
        pricePanel.setLayout(new GridLayout(1, 2));
        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setPreferredSize(new Dimension(100, 20));

        JLabel priceField = new JLabel(String.valueOf(reservation.getPrice()));
        priceField.setPreferredSize(new Dimension(200, 20));

        pricePanel.add(priceLabel);
        pricePanel.add(priceField);

        panel.add(pricePanel);

        hotels = hotelController.getAll();
        hotelComboBoxModel = new DefaultComboBoxModel<>(hotels.toArray(new Hotel[0]));
        hotelComboBox.setModel(hotelComboBoxModel);

        if (reservation.getId() != 0) {
            // restore fields from reservation
            System.out.println(reservation);
            hotelComboBox.setSelectedItem(hotelController.getById(reservation.getHotelId()));

            seasons = seasonController.getAllByHotelId(reservation.getHotelId());
            seasonComboBoxModel = new DefaultComboBoxModel<>(seasons.toArray(new Season[0]));
            seasonComboBox.setModel(seasonComboBoxModel);
            seasonComboBox.setSelectedItem(reservation.getSeason());

            pansions = pansionController.getByHotelId(reservation.getHotelId());
            pansionComboBoxModel = new DefaultComboBoxModel<>(pansions.toArray(new Pansion[0]));
            pansionComboBox.setModel(pansionComboBoxModel);
            pansionComboBox.setSelectedItem(reservation.getPansion());

            rooms = roomController.getByHotelAndSeasonIdAndPansionId(reservation.getHotelId(),
                    reservation.getSeasonId(), reservation.getPansionId());
            roomComboBoxModel = new DefaultComboBoxModel<>(rooms.toArray(new Room[0]));
            roomComboBox.setModel(roomComboBoxModel);
            roomComboBox.setSelectedItem(reservation.getRoom());

            adultCountComboBox.setSelectedItem(reservation.getAdultCount());
            childCountComboBox.setSelectedItem(reservation.getChildCount());
            priceField.setText(String.valueOf(reservation.getPrice()));
        }

        hotelComboBox.addActionListener(e -> {
            Hotel hotel = (Hotel) hotelComboBox.getSelectedItem();
            if (hotel == null) {
                return;
            }
            // Clear the other combo boxes
            // seasonComboBox.setEnabled(true);
            // pansionComboBox.setEnabled(true);
            // roomComboBox.setEnabled(true);

            reservation.setHotelId(hotel.getId());
            seasons = seasonController.getAllByHotelId(hotel.getId());
            if (seasons.size() == 0) {
                JOptionPane.showMessageDialog(null, "No seasons found for this hotel.");

                return;
            } else {
                seasonComboBox.setEnabled(true);
                ComboBoxModel<Season> seasonComboBoxModel1 = new DefaultComboBoxModel<>(seasons.toArray(new Season[0]));
                seasonComboBox.setModel(seasonComboBoxModel1);

            }
        });

        seasonComboBox.addActionListener(e -> {
            Season season = (Season) seasonComboBox.getSelectedItem();
            if (season == null) {
                return;
            }
            // Clear the other combo boxes
            pansionComboBox.setModel(new DefaultComboBoxModel<>());
            pansionComboBox.setEnabled(true);
            pansionComboBox.setSelectedIndex(-1);
            roomComboBox.setModel(new DefaultComboBoxModel<>());
            roomComboBox.setEnabled(true);
            roomComboBox.setSelectedIndex(-1);

            reservation.setSeasonId(season.getId());
            pansions = pansionController.getByHotelId(reservation.getHotelId());

            if (pansions.size() == 0) {
                JOptionPane.showMessageDialog(null, "No pansions found for this hotel.");
                pansionComboBox.setModel(new DefaultComboBoxModel<>());
                pansionComboBox.setEnabled(false);
                pansionComboBox.setSelectedIndex(-1);
                roomComboBox.setModel(new DefaultComboBoxModel<>());
                roomComboBox.setEnabled(false);
                roomComboBox.setSelectedIndex(-1);
                return;
            } else {
                pansionComboBox.setEnabled(true);
                ComboBoxModel<Pansion> pansionComboBoxModel1 = new DefaultComboBoxModel<>(
                        pansions.toArray(new Pansion[0]));
                pansionComboBox.setModel(pansionComboBoxModel1);
            }

        });

        pansionComboBox.addActionListener(e -> {
            Pansion pansion = (Pansion) pansionComboBox.getSelectedItem();
            if (pansion == null) {
                return;
            }
            // Clear the other combo boxes
            roomComboBox.setModel(new DefaultComboBoxModel<>());
            roomComboBox.setEnabled(true);
            roomComboBox.setSelectedIndex(-1);

            reservation.setPansionId(pansion.getId());
            rooms = roomController.getByHotelAndSeasonIdAndPansionId(reservation.getHotelId(),
                    reservation.getSeasonId(), reservation.getPansionId());
            if (rooms.size() == 0) {
                JOptionPane.showMessageDialog(null, "No rooms found for this hotel.");
                roomComboBox.setModel(new DefaultComboBoxModel<>());
                roomComboBox.setEnabled(false);
                roomComboBox.setSelectedIndex(-1);
                return;
            } else {
                roomComboBox.setEnabled(true);
                ComboBoxModel<Room> roomComboBoxModel1 = new DefaultComboBoxModel<>(rooms.toArray(new Room[0]));
                roomComboBox.setModel(roomComboBoxModel1);
            }
        });

        roomComboBox.addActionListener(e -> {
            Room room = (Room) roomComboBox.getSelectedItem();
            if (room == null) {
                return;
            }
            reservation.setRoomId(room.getId());
            int price = calculatePrice(reservation, room, reservation.getAdultCount(), reservation.getChildCount(),
                    reservation.getCheckInLocalDate(), reservation.getCheckOutLocalDate());
            reservation.setPrice(price);
            priceField.setText(String.valueOf(price));
        });

        adultCountComboBox.addActionListener(e -> {
            Room room = (Room) roomComboBox.getSelectedItem();
            if (room == null) {
                return;
            }
            reservation.setAdultCount((Integer) adultCountComboBox.getSelectedItem());
            int price = calculatePrice(reservation, room, reservation.getAdultCount(), reservation.getChildCount(), reservation.getCheckInLocalDate(), reservation.getCheckOutLocalDate());
            reservation.setPrice(price);
            priceField.setText(String.valueOf(price));
        });

        childCountComboBox.addActionListener(e -> {
            Room room = (Room) roomComboBox.getSelectedItem();
            if (room == null) {
                return;
            }
            reservation.setChildCount((Integer) childCountComboBox.getSelectedItem());
            int price = calculatePrice(reservation, room, reservation.getAdultCount(), reservation.getChildCount(), reservation.getCheckInLocalDate(), reservation.getCheckOutLocalDate());
            reservation.setPrice(price);
            priceField.setText(String.valueOf(price));
        });

        checkInPicker.addDateChangeListener(event -> {
            Room room = (Room) roomComboBox.getSelectedItem();
            if (room == null) {
                return;
            }
            int price = calculatePrice(reservation, room, reservation.getAdultCount(), reservation.getChildCount(), reservation.getCheckInLocalDate(), reservation.getCheckOutLocalDate());
            reservation.setPrice(price);
            priceField.setText(String.valueOf(price));
        });

        checkOutPicker.addDateChangeListener(event -> {
            Room room = (Room) roomComboBox.getSelectedItem();
            if (room == null) {
                return;
            }
            int price = calculatePrice(reservation, room, reservation.getAdultCount(), reservation.getChildCount(), reservation.getCheckInLocalDate(), reservation.getCheckOutLocalDate());
            reservation.setPrice(price);
            priceField.setText(String.valueOf(price));
        });

        // buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));
        JButton saveButton = new JButton("Save");
        JButton deleteButton = new JButton("Delete");

        saveButton.addActionListener(e -> {

            //check for empty fields
            if (reservation.getGuestFullName().equals("")) {
                JOptionPane.showMessageDialog(null, "Guest Full Name cannot be empty.");
                return;
            }
            if (reservation.getGuestCitizenId().equals("")) {
                JOptionPane.showMessageDialog(null, "Guest Citizen ID cannot be empty.");
                return;
            }

            if (reservation.getGuestEmail().equals("")) {
                JOptionPane.showMessageDialog(null, "Guest Email cannot be empty.");
                return;
            }
            if (reservation.getGuestPhone().equals(""))
            {
                JOptionPane.showMessageDialog(null, "Guest Phone cannot be empty.");
                return;
            }
            if (reservation.getCheckIn() == null) {
                JOptionPane.showMessageDialog(null, "Check In date cannot be empty.");
                return;
            }
            if (reservation.getCheckOut() == null) {
                JOptionPane.showMessageDialog(null, "Check Out date cannot be empty.");
                return;
            }
            if (reservation.getAdultCount() == 0) {
                JOptionPane.showMessageDialog(null, "Adult Count cannot be 0.");
                return;
            }
            if (reservation.getHotelId() == 0) {
                JOptionPane.showMessageDialog(null, "Please select a hotel.");
                return;
            }

            if (reservation.getSeasonId() == 0) {
                JOptionPane.showMessageDialog(null, "Please select a season.");
                return;
            }

            if (reservation.getPansionId() == 0) {
                JOptionPane.showMessageDialog(null, "Please select a pansion.");
                return;
            }

            if (reservation.getRoomId() == 0) {
                JOptionPane.showMessageDialog(null, "Please select a room.");
                return;
            }





            if (reservation.getId() == 0) {
                // Create
                reservation.setCreatedAt(new Date());
                reservation.setCreatedBy(1);
                reservation.setUpdatedAt(new Date());
                reservation.setUpdatedBy(1);
                reservation.setDeletedAt(null);
                reservation.setDeletedBy(null);
                reservationController.create(reservation);
                JOptionPane.showMessageDialog(null, "Reservation created successfully.");
            } else {
                // Update
                reservation.setUpdatedAt(new Date());
                reservation.setUpdatedBy(1);
                reservationController.update(reservation);
                JOptionPane.showMessageDialog(null, "Reservation updated successfully.");
            }
            frame.dispose();
        });

        deleteButton.addActionListener(e -> {
            if (reservation.getId() == 0) {
            } else {
                // Confirm delete
                int dialogResult = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to delete this reservation?", "Warning", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    reservation.setDeletedAt(new Date());
                    reservation.setDeletedBy(1);
                    reservationController.delete(reservation);
                    JOptionPane.showMessageDialog(null, "Reservation deleted successfully.");
                }
            }
            frame.dispose();
        });

        if (reservation.getId() == 0) {
            deleteButton.setEnabled(false);
        }

        if (reservation.getHotelId() == 0) {
            hotelComboBox.setSelectedItem((Hotel) hotelController.getById(reservation.getHotelId()));
            seasons = seasonController.getAllByHotelId(reservation.getHotelId());
            seasonComboBoxModel = new DefaultComboBoxModel<>(seasons.toArray(new Season[0]));
            seasonComboBox.setModel(seasonComboBoxModel);

        }

        // restore the comboboxes from the reservation
        if (reservation.getId() != 0) {
            seasonComboBox.setSelectedItem(seasonController.getById(reservation.getSeasonId()));
            pansionComboBox.setSelectedItem(pansionController.getById(reservation.getPansionId()));
            roomComboBox.setSelectedItem(roomController.getById(reservation.getRoomId()));
        }

        buttonPanel.add(saveButton);
        buttonPanel.add(deleteButton);

        panel.add(hotelPanel);
        panel.add(seasonPanel);
        panel.add(pansionPanel);
        panel.add(roomPanel);
        panel.add(pricePanel);
        panel.add(buttonPanel);

        frame.add(panel, BorderLayout.CENTER);

    }

    public int calculatePrice(Reservation reservation, Room room, int adultCount, int childCount, LocalDate checkIn,
            LocalDate checkOut) {
        int price = 0;
        if (reservation.getRoomId() == 0) {
            return price;
        }
        if (reservation.getSeasonId() == 0) {
            return price;
        }
        if (reservation.getPansionId() == 0) {
            return price;
        }

        if (checkIn == null || checkOut == null) {
            return price;
        }

        if (checkIn.isAfter(checkOut)) {
            // alert
            JOptionPane.showMessageDialog(null, "Check in date cannot be after check out date.");
            return 0;
        }


        int days = (int) (checkOut.toEpochDay() - checkIn.toEpochDay());

        price = (int) (room.getAdultPrice() * adultCount + room.getChildPrice() * childCount) * days;

        return price;

    }

    private void checkRoomAvailability() {
        // check date fits in the season
        LocalDate checkIn = reservation.getCheckInLocalDate();
        LocalDate checkOut = reservation.getCheckOutLocalDate();

        if (checkIn == null || checkOut == null) {
            // alert user to select check in and check out dates
            JOptionPane.showMessageDialog(null, "Please select check in and check out dates.");
            return;
        }

        //check if selected dates fit in the season
        Season season = (Season) seasonComboBox.getSelectedItem();

        if (season == null) {
            JOptionPane.showMessageDialog(null, "Please select a season.");
            return;
        }

        if (checkIn.isBefore(season.getStartDateLocalDate())
                || checkOut.isAfter(season.getEndDateLocalDate())) {
            JOptionPane.showMessageDialog(null, "Selected dates do not fit in the season.");
            return;
        }

        if (checkIn.isAfter(checkOut)) {
            JOptionPane.showMessageDialog(null, "Check in date cannot be after check out date.");
            return;
        }

        // check if room is available
        Room room = (Room) roomComboBox.getSelectedItem();

        if (room == null) {
            JOptionPane.showMessageDialog(null, "Please select a room.");
        }

        // get all reservations for the room
        List<Reservation> reservationsExists = reservationController.getByRoomId(room.getId());

        for (Reservation reservationExists : reservationsExists) {

            if (reservationExists.getId() == reservation.getId()) {
                continue;
            }

            // is there any reservation that star before the new reservation and ends after the new reservation
            if (reservationExists.getCheckInLocalDate().isBefore(checkIn) && reservationExists.getCheckOutLocalDate().isAfter(checkOut)) {
                JOptionPane.showMessageDialog(null, "Room is not available for the selected dates.");
                return;
            }
            // is there any reservation that starts after the new reservation and ends after the new reservation
            if (reservationExists.getCheckInLocalDate().isAfter(checkIn) && reservationExists.getCheckOutLocalDate().isAfter(checkOut)) {
                JOptionPane.showMessageDialog(null, "Room is not available for the selected dates.");
                return;
            }
            // is there any reservation that starts before the new reservation and ends before the new reservation
            if (reservationExists.getCheckInLocalDate().isBefore(checkIn) && reservationExists.getCheckOutLocalDate().isBefore(checkOut)) {
                JOptionPane.showMessageDialog(null, "Room is not available for the selected dates.");
                return;
            }
            // is there any reservation that starts after the new reservation and ends before the new reservation
            if (reservationExists.getCheckInLocalDate().isAfter(checkIn) && reservationExists.getCheckOutLocalDate().isBefore(checkOut)) {
                JOptionPane.showMessageDialog(null, "Room is not available for the selected dates.");
                return;
            }
        }

        // if no reservation is found, the room is available
        JOptionPane.showMessageDialog(null, "Room is available for the selected dates.");
    }

    private Boolean checkRoomAvailabilitySelectedRoom(Room room) {
        // check date fits in the season
        LocalDate checkIn = reservation.getCheckInLocalDate();
        LocalDate checkOut = reservation.getCheckOutLocalDate();

        if (checkIn == null || checkOut == null) {
            // alert user to select check in and check out dates
            return false;
        }

        //check if selected dates fit in the season
        Season season = (Season) seasonComboBox.getSelectedItem();

        if (season == null) {
            return false;
        }

        if (checkIn.isBefore(season.getStartDateLocalDate())
                || checkOut.isAfter(season.getEndDateLocalDate())) {
            return false;

        }

        if (checkIn.isAfter(checkOut)) {
            return false;

        }

        // check if room is available

        // get all reservations for the room
        List<Reservation> reservationsExists = reservationController.getByRoomId(room.getId());

        System.out.println("the size of the reservationsExists is " + reservationsExists.size());

        for (Reservation reservationExists : reservationsExists) {


            if (reservationExists.getId() == reservation.getId()) {
                continue;
            }


            // is there any reservation that star before the new reservation and ends after the new reservation
            if (reservationExists.getCheckInLocalDate().isBefore(checkIn) && reservationExists.getCheckOutLocalDate().isAfter(checkOut)) {
                return false;

            }
            // is there any reservation that starts after the new reservation and ends after the new reservation
            if (reservationExists.getCheckInLocalDate().isAfter(checkIn) && reservationExists.getCheckOutLocalDate().isAfter(checkOut)) {
                return false;

            }
            // is there any reservation that starts before the new reservation and ends before the new reservation
            if (reservationExists.getCheckInLocalDate().isBefore(checkIn) && reservationExists.getCheckOutLocalDate().isBefore(checkOut)) {
                return false;

            }
            // is there any reservation that starts after the new reservation and ends before the new reservation
            if (reservationExists.getCheckInLocalDate().isAfter(checkIn) && reservationExists.getCheckOutLocalDate().isBefore(checkOut)) {
                return false;

            }

        }

        return true;
    }

    private void refreshRoomComboBox() {
        Room selectedRoom = (Room) roomComboBox.getSelectedItem();
        rooms = roomController.getByHotelAndSeasonIdAndPansionId(reservation.getHotelId(), reservation.getSeasonId(),
                reservation.getPansionId());

        DefaultComboBoxModel<Room> roomComboBoxModel = new DefaultComboBoxModel<>(rooms.toArray(new Room[0]));

        for (Room room : rooms) {
            if (checkRoomAvailabilitySelectedRoom(room)) {
                roomComboBoxModel.addElement(room);
            }
        }

        roomComboBox.setModel(roomComboBoxModel);
        roomComboBox.setSelectedItem(selectedRoom);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(DetailsView::new);
    }
}
