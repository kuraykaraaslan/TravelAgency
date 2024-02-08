package org.agency.views.hotel.Partials;

import com.github.lgooddatepicker.components.DatePicker;
import org.agency.controllers.HotelController;
import org.agency.controllers.ReservationController;
import org.agency.controllers.RoomController;
import org.agency.controllers.SeasonController;
import org.agency.entities.Hotel;
import org.agency.entities.Reservation;
import org.agency.entities.Room;
import org.agency.entities.Season;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class ReservationsPartialView {

    private Hotel hotel;
    private HotelController hotelController = new HotelController();

    private ReservationController reservationController = new ReservationController();

    private RoomController roomController = new RoomController();

    private final Runnable RefreshParent;
    private final Runnable FrameDispose;

    private JTable table;

    private JPopupMenu popupMenu;

    private String[] columns = { "ID", "Name", "Phone", "Email", "Dates", "Guests", "Room Number", "Price", "Status" };

    private HashMap<String, Object> filters = new HashMap<>();

    public ReservationsPartialView(Hotel hotel, Runnable RefreshParent, Runnable FrameDispose) {
        this.hotel = hotel;
        this.RefreshParent = RefreshParent;
        this.FrameDispose = FrameDispose;
        this.filters.put("hotelId", hotel.getId());

    }

    private JPanel headerSearchPanel() {
        JPanel headerSearchPanel = new JPanel();
        headerSearchPanel.setLayout(new FlowLayout());
        headerSearchPanel.setBackground(Color.white);

        /*
         * private String guestCitizenId;
         * private String guestFullName;
         * private String guestEmail;
         * private String guestPhone;
         * private Date checkIn;
         * private Date checkOut;
         * 
         * private String status;
         */

        JLabel citizenIdLabel = new JLabel("Citizen ID");
        citizenIdLabel.setPreferredSize(new Dimension(100, 30));
        citizenIdLabel.setMaximumSize(new Dimension(100, 30));
        citizenIdLabel.setMinimumSize(new Dimension(100, 30));
        citizenIdLabel.setToolTipText("Citizen ID");
        headerSearchPanel.add(citizenIdLabel);

        JTextField citizenIdField = new JTextField();
        citizenIdField.setPreferredSize(new Dimension(100, 30));
        citizenIdField.setMaximumSize(new Dimension(100, 30));
        citizenIdField.setMinimumSize(new Dimension(100, 30));
        citizenIdField.setToolTipText("Citizen ID");
        headerSearchPanel.add(citizenIdField);

        JLabel fullNameLabel = new JLabel("Full Name");
        fullNameLabel.setPreferredSize(new Dimension(100, 30));
        fullNameLabel.setMaximumSize(new Dimension(100, 30));
        fullNameLabel.setMinimumSize(new Dimension(100, 30));
        fullNameLabel.setToolTipText("Full Name");
        headerSearchPanel.add(fullNameLabel);

        JTextField fullNameField = new JTextField();
        fullNameField.setPreferredSize(new Dimension(100, 30));
        fullNameField.setMaximumSize(new Dimension(100, 30));
        fullNameField.setMinimumSize(new Dimension(100, 30));
        fullNameField.setToolTipText("Full Name");
        headerSearchPanel.add(fullNameField);

        JLabel contactLabel = new JLabel("Contact");
        contactLabel.setPreferredSize(new Dimension(100, 30));
        contactLabel.setMaximumSize(new Dimension(100, 30));
        contactLabel.setMinimumSize(new Dimension(100, 30));
        contactLabel.setToolTipText("Contact");
        headerSearchPanel.add(contactLabel);

        JTextField contactField = new JTextField();
        contactField.setPreferredSize(new Dimension(100, 30));
        contactField.setMaximumSize(new Dimension(100, 30));
        contactField.setMinimumSize(new Dimension(100, 30));
        contactField.setToolTipText("Contact");
        headerSearchPanel.add(contactField);

        JLabel statusLabel = new JLabel("Status");
        statusLabel.setPreferredSize(new Dimension(100, 30));
        statusLabel.setMaximumSize(new Dimension(100, 30));
        statusLabel.setMinimumSize(new Dimension(100, 30));
        statusLabel.setToolTipText("Status");
        headerSearchPanel.add(statusLabel);

        JComboBox statusField = new JComboBox(
                new String[] { "ALL", "PENDING", "CONFIRMED", "CHECKED_IN", "CHECKED_OUT", "CANCELLED" });
        statusField.setPreferredSize(new Dimension(100, 30));
        statusField.setMaximumSize(new Dimension(100, 30));
        statusField.setMinimumSize(new Dimension(100, 30));

        headerSearchPanel.add(statusField);

        JLabel checkInLabel = new JLabel("Check In");
        checkInLabel.setPreferredSize(new Dimension(100, 30));
        checkInLabel.setMaximumSize(new Dimension(100, 30));
        checkInLabel.setMinimumSize(new Dimension(100, 30));
        checkInLabel.setToolTipText("Check In");
        headerSearchPanel.add(checkInLabel);

        DatePicker checkInField = new DatePicker();
        checkInField.setPreferredSize(new Dimension(100, 30));
        checkInField.setMaximumSize(new Dimension(100, 30));
        checkInField.setMinimumSize(new Dimension(100, 30));
        checkInField.setToolTipText("Check In");
        headerSearchPanel.add(checkInField);

        JLabel checkOutLabel = new JLabel("Check Out");
        checkOutLabel.setPreferredSize(new Dimension(100, 30));
        checkOutLabel.setMaximumSize(new Dimension(100, 30));
        checkOutLabel.setMinimumSize(new Dimension(100, 30));
        checkOutLabel.setToolTipText("Check Out");

        headerSearchPanel.add(checkOutLabel);

        DatePicker checkOutField = new DatePicker();
        checkOutField.setPreferredSize(new Dimension(100, 30));
        checkOutField.setMaximumSize(new Dimension(100, 30));
        checkOutField.setMinimumSize(new Dimension(100, 30));

        headerSearchPanel.add(checkOutField);

        JButton searchButton = new JButton("Search");
        searchButton.setPreferredSize(new Dimension(100, 30));
        searchButton.setMaximumSize(new Dimension(100, 30));
        searchButton.setMinimumSize(new Dimension(100, 30));
        searchButton.setToolTipText("Search");
        headerSearchPanel.add(searchButton);

        searchButton.addActionListener(e -> {

            // check for valid input
            // if START DATE is after END DATE
            if (checkInField.getDate() != null && checkOutField.getDate() != null) {
                if (checkInField.getDate().isAfter(checkOutField.getDate())) {
                    JOptionPane.showMessageDialog(null, "Start date cannot be after end date");
                    return;
                }
            }

            filters.clear();

            if (citizenIdField.getText().length() > 0) {
                filters.put("guest_citizen_id", citizenIdField.getText());
            }
            if (fullNameField.getText().length() > 0) {
                filters.put("guest_full_name", fullNameField.getText());
            }

            if (contactField.getText().length() > 0) {
                // if contact is a number
                try {
                    Integer.parseInt(contactField.getText());
                    filters.put("guest_phone", contactField.getText());
                } catch (NumberFormatException ex) {
                    filters.put("guest_email", contactField.getText());
                }
            }

            // add hotel id to filters
            filters.put("hotel_id", hotel.getId());

            List<Reservation> reservations = reservationController.getByFilters(filters);
            Object[][] data = new Object[reservations.size()][columns.length];

            for (int i = 0; i < reservations.size(); i++) {
                Reservation reservation = reservations.get(i);
                data[i][0] = reservation.getId();
                data[i][1] = reservation.getGuestFullName();
                data[i][2] = reservation.getGuestPhone();
                data[i][3] = reservation.getGuestEmail();
                data[i][4] = reservation.getCheckIn() + " - " + reservation.getCheckOut();
                data[i][5] = reservation.getAdultCount() + reservation.getChildCount();
                data[i][6] = roomController.getById(reservation.getRoomId()).getRoomNumber();
                data[i][7] = reservation.getPrice();
                data[i][8] = reservation.getStatus();
            }

            table.setModel(new DefaultTableModel(
                    data,
                    columns));

            table.repaint();
        });

        JButton createButton = new JButton("Create");
        createButton.setPreferredSize(new Dimension(100, 30));
        createButton.setMaximumSize(new Dimension(100, 30));
        createButton.setMinimumSize(new Dimension(100, 30));
        createButton.setToolTipText("Create");
        createButton.addActionListener(e -> {
            if (hotel.getRooms().size() == 0) {
                JOptionPane.showMessageDialog(null, "Create a room first to create a reservation");
                return;
            }
            Reservation reservation = new Reservation();
            reservation.setHotelId(hotel.getId());
            org.agency.views.reservation.DetailsView detailsView = new org.agency.views.reservation.DetailsView(
                    reservation);
        });

        headerSearchPanel.add(createButton);

        return headerSearchPanel;
    }

    public JPanel render() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.white);

        List<Reservation> reservations = reservationController.getByHotelId(hotel.getId());

        Object[][] data = new Object[reservations.size()][columns.length];

        for (int i = 0; i < reservations.size(); i++) {
            Reservation reservation = reservations.get(i);
            data[i][0] = reservation.getId();
            data[i][1] = reservation.getGuestFullName();
            data[i][2] = reservation.getGuestPhone();
            data[i][3] = reservation.getGuestEmail();
            data[i][4] = reservation.getCheckIn() + " - " + reservation.getCheckOut();
            data[i][5] = reservation.getAdultCount() + reservation.getChildCount();
            data[i][6] = roomController.getById(reservation.getRoomId()).getRoomNumber();
            data[i][7] = reservation.getPrice();
            data[i][8] = reservation.getStatus();
        }

        table = new JTable(data, columns);

        table.setModel(new DefaultTableModel(
                data,
                columns));

        popupMenu = new JPopupMenu();

        JMenuItem editMenuItem = new JMenuItem("Edit");
        editMenuItem.addActionListener(e -> {
            int row = table.getSelectedRow();
            int id = (int) table.getModel().getValueAt(row, 0);
            Reservation reservation = reservationController.getById(id);
            org.agency.views.reservation.DetailsView detailsView = new org.agency.views.reservation.DetailsView(
                    reservation);
        });

        JMenuItem deleteMenuItem = new JMenuItem("Delete");
        deleteMenuItem.addActionListener(e -> {
            int row = table.getSelectedRow();
            int id = (int) table.getModel().getValueAt(row, 0);
            Reservation reservation = reservationController.getById(id);
            // Confirm delete
            int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this reservation?",
                    "Warning", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                reservationController.delete(reservation);
                RefreshParent.run();
            }
        });

        popupMenu.add(editMenuItem);
        popupMenu.add(deleteMenuItem);

        table.setComponentPopupMenu(popupMenu);

        // disable editing
        table.setDefaultEditor(Object.class, null);

        JScrollPane scrollPane = new JScrollPane(table);

        panel.add(headerSearchPanel(), BorderLayout.NORTH);
        panel.add(scrollPane);

        return panel;
    }

}
