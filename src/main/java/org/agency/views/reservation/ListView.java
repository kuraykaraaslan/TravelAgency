package org.agency.views.reservation;

import com.github.lgooddatepicker.components.DatePicker;
import org.agency.controllers.HotelController;
import org.agency.controllers.ReservationController;
import org.agency.controllers.RoomController;
import org.agency.controllers.SeasonController;
import org.agency.entities.Hotel;
import org.agency.entities.Reservation;
import org.agency.entities.Room;
import org.agency.entities.Season;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ListView {

    private JFrame frame = new JFrame("Reservations");

    private JPanel mainPanel = new JPanel();

    private HotelController hotelController = new HotelController();

    private ReservationController reservationController = new ReservationController();

    private RoomController roomController = new RoomController();

    private JTable table;

    private JPopupMenu popupMenu;

    private String[] columns = { "ID", "Hotel Name", "Name", "Phone", "Email", "Dates", "Guest Count", "Room Number",
            "Price", "Status" };

    private HashMap<String, Object> filters = new HashMap<>();

    public ListView() {
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.add(render());
        frame.setVisible(true);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.white);
        headerPanel.add(createLogoPanel(), BorderLayout.WEST);
        // headerPanel.add(createSearchPanel(), BorderLayout.CENTER);
        // headerPanel.add(createButtonPanel(), BorderLayout.EAST);
        return headerPanel;
    }

    private JPanel createLogoPanel() {
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(Color.white);
        JLabel logoLabel = new JLabel();
        try {
            File imageFile = new File("src/main/resources/logo.jpg");
            Image originalImage = ImageIO.read(imageFile);
            int scaledWidth = 200;
            int scaledHeight = 50;
            double aspectRatio = (double) originalImage.getWidth(null) / originalImage.getHeight(null);
            scaledHeight = (int) (scaledWidth / aspectRatio);
            Image scaledImage = originalImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
            ImageIcon logoIcon = new ImageIcon(scaledImage);
            logoLabel.setIcon(logoIcon);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logoPanel.add(logoLabel);
        return logoPanel;
    }

    private JPanel headerSearchPanel() {
        JPanel headerSearchPanel = new JPanel();
        headerSearchPanel.setLayout(new FlowLayout());
        headerSearchPanel.setBackground(Color.white);

        /*
         * private Hotel hotel;
         * private String guestCitizenId;
         * private String guestFullName;
         * private String guestEmail;
         * private String guestPhone;
         * private Date checkIn;
         * private Date checkOut;
         * 
         * private String status;
         */

        JLabel hotelLabel = new JLabel("Hotel");
        hotelLabel.setPreferredSize(new Dimension(100, 30));
        hotelLabel.setMaximumSize(new Dimension(100, 30));
        hotelLabel.setMinimumSize(new Dimension(100, 30));
        hotelLabel.setToolTipText("Hotel");
        headerSearchPanel.add(hotelLabel);

        List<Hotel> hotels = hotelController.getAll();

        // JCOMBOBOX WITH HOTELS WITH CUSTOM RENDERER and EMPTY OPTION
        JComboBox<Hotel> hotelField = new JComboBox<>();
        hotelField.setPreferredSize(new Dimension(100, 30));
        hotelField.setMaximumSize(new Dimension(100, 30));
        hotelField.setMinimumSize(new Dimension(100, 30));
        hotelField.setToolTipText("Hotel");
        Hotel emptyHotel = new Hotel();
        emptyHotel.setName("ALL");
        hotelField.addItem(emptyHotel);
        for (Hotel hotel : hotels) {
            hotelField.addItem(hotel);
        }

        hotelField.setRenderer(new DefaultListCellRenderer() {
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

        headerSearchPanel.add(hotelField);

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

        JLabel checkInLabel = new JLabel("Dates");
        checkInLabel.setPreferredSize(new Dimension(50, 30));
        checkInLabel.setMaximumSize(new Dimension(50, 30));
        checkInLabel.setMinimumSize(new Dimension(50, 30));
        checkInLabel.setToolTipText("Check In");
        headerSearchPanel.add(checkInLabel);

        DatePicker checkInField = new DatePicker();
        checkInField.setPreferredSize(new Dimension(160, 30));
        checkInField.setMaximumSize(new Dimension(160, 30));
        checkInField.setMinimumSize(new Dimension(160, 30));
        checkInField.setToolTipText("Check In");
        headerSearchPanel.add(checkInField);

        DatePicker checkOutField = new DatePicker();
        checkOutField.setPreferredSize(new Dimension(160, 30));
        checkOutField.setMaximumSize(new Dimension(160, 30));
        checkOutField.setMinimumSize(new Dimension(160, 30));

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

            if (hotelField.getSelectedIndex() != 0) {
                filters.put("hotel_id", ((Hotel) hotelField.getSelectedItem()).getId());
            }


            if (contactField.getText().length() > 0) {
                filters.put("guest_phone", contactField.getText());
            }

            if (statusField.getSelectedIndex() != 0) {
                filters.put("status", statusField.getSelectedItem());
            }

            if (checkInField.getDate() != null) {
                // YYYY-MM-DD
                filters.put("check_in", checkInField.getDate());
            }

            if (checkOutField.getDate() != null) {
                filters.put("check_out", checkOutField.getDate());
            }

            List<Reservation> reservations = reservationController.getByFilters(filters);
            Object[][] data = new Object[reservations.size()][columns.length];

            for (int i = 0; i < reservations.size(); i++) {
                Reservation reservation = reservations.get(i);
                data[i][0] = reservation.getId();
                data[i][1] = hotelController.getById(reservation.getHotelId()).getName();
                data[i][2] = reservation.getGuestFullName();
                data[i][3] = reservation.getGuestPhone();
                data[i][4] = reservation.getGuestEmail();
                data[i][5] = reservation.getCheckIn() + " - " + reservation.getCheckOut();
                data[i][6] = reservation.getAdultCount() + reservation.getChildCount();
                data[i][7] = roomController.getById(reservation.getRoomId()).getRoomNumber();
                data[i][8] = reservation.getPrice();
                data[i][9] = reservation.getStatus();
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
            Reservation reservation = new Reservation();
            org.agency.views.reservation.DetailsView detailsView = new org.agency.views.reservation.DetailsView(
                    reservation);
        });

        headerSearchPanel.add(createButton);

        return headerSearchPanel;
    }

    public JPanel render() {
        JPanel mainPanel = new JPanel();
        JPanel headerPanel = createHeaderPanel();

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.white);

        List<Reservation> reservations = reservationController.getByFilters(filters);

        Object[][] data = new Object[reservations.size()][columns.length];

        for (int i = 0; i < reservations.size(); i++) {
            Reservation reservation = reservations.get(i);
            data[i][0] = reservation.getId();
            data[i][1] = hotelController.getById(reservation.getHotelId()).getName();
            data[i][2] = reservation.getGuestFullName();
            data[i][3] = reservation.getGuestPhone();
            data[i][4] = reservation.getGuestEmail();
            data[i][5] = reservation.getCheckIn() + " - " + reservation.getCheckOut();
            data[i][6] = reservation.getAdultCount() + reservation.getChildCount();
            data[i][7] = roomController.getById(reservation.getRoomId()).getRoomNumber();
            data[i][8] = reservation.getPrice();
            data[i][9] = reservation.getStatus();
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
                JOptionPane.showMessageDialog(null, "Reservation deleted successfully");
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

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(panel, BorderLayout.CENTER);

        return mainPanel;

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Reservations");
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.add(new ListView().render());
            frame.setVisible(true);
        });
    }
}
