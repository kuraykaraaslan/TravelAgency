package org.agency.views.hotel.Partials;

import org.agency.controllers.HotelController;
import org.agency.controllers.RoomController;
import org.agency.controllers.SeasonController;
import org.agency.entities.Hotel;
import org.agency.entities.Room;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class RoomsPartialView {

    private Hotel hotel;
    private HotelController hotelController = new HotelController();

    private RoomController roomController = new RoomController();

    private final Runnable RefreshParent;
    private final Runnable FrameDispose;

    private JTable table;
    private JPopupMenu popupMenu;

    private String[] columns = { "ID", "Room Number", "Double Bed Count", "Single Bed Count", "Adult Price",
            "Child Price", "Square Meters" };

    private HashMap<String, Object> filters = new HashMap<>();

    public RoomsPartialView(Hotel hotel, Runnable RefreshParent, Runnable FrameDispose) {
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
         * private String roomNumber;
         * private String type;
         * private int doubleBedCount;
         * private int singleBedCount;
         * private int squareMeters;
         * private boolean hasTelevision;
         * private boolean hasBalcony;
         * private boolean hasAirConditioning;
         * private boolean hasMinibar;
         * private boolean hasValuablesSafe;
         * private boolean hasGamingConsole;
         * private boolean hasProjector;
         */

        JTextField roomNumberField = new JTextField();
        roomNumberField.setPreferredSize(new Dimension(50, 30));
        roomNumberField.setMaximumSize(new Dimension(50, 30));
        roomNumberField.setMinimumSize(new Dimension(50, 30));
        roomNumberField.setToolTipText("Room Number:");
        JLabel roomNumberLabel = new JLabel("RN:");
        roomNumberLabel.setPreferredSize(new Dimension(20, 30));
        roomNumberLabel.setMaximumSize(new Dimension(20, 30));
        roomNumberLabel.setMinimumSize(new Dimension(20, 30));
        headerSearchPanel.add(roomNumberLabel);
        headerSearchPanel.add(roomNumberField);

        // SINGLE, DOUBLE, JUNIOR_SUITE, SUITE
        JComboBox<String> typeField = new JComboBox<>(
                new String[] { "ALL", "SINGLE", "DOUBLE", "JUNIOR_SUITE", "SUITE" });
        typeField.setPreferredSize(new Dimension(100, 30));
        typeField.setMaximumSize(new Dimension(100, 30));
        typeField.setMinimumSize(new Dimension(100, 30));
        typeField.setToolTipText("Type:");
        JLabel typeLabel = new JLabel("Type:");
        typeLabel.setPreferredSize(new Dimension(40, 30));
        typeLabel.setMaximumSize(new Dimension(40, 30));
        typeLabel.setMinimumSize(new Dimension(40, 30));
        headerSearchPanel.add(typeLabel);

        headerSearchPanel.add(typeField);

        JTextField doubleBedCountField = new JTextField();
        doubleBedCountField.setPreferredSize(new Dimension(50, 30));
        doubleBedCountField.setMaximumSize(new Dimension(50, 30));
        doubleBedCountField.setMinimumSize(new Dimension(50, 30));
        doubleBedCountField.setToolTipText("Double Bed Count:");
        JLabel doubleBedCountLabel = new JLabel("DBC:");
        doubleBedCountLabel.setPreferredSize(new Dimension(40, 30));
        doubleBedCountLabel.setMaximumSize(new Dimension(40, 30));
        doubleBedCountLabel.setMinimumSize(new Dimension(40, 30));
        headerSearchPanel.add(doubleBedCountLabel);
        headerSearchPanel.add(doubleBedCountField);

        JTextField singleBedCountField = new JTextField();
        singleBedCountField.setPreferredSize(new Dimension(50, 30));
        singleBedCountField.setMaximumSize(new Dimension(50, 30));
        singleBedCountField.setMinimumSize(new Dimension(50, 30));
        singleBedCountField.setToolTipText("Single Bed Count:");
        JLabel singleBedCountLabel = new JLabel("SBC:");
        singleBedCountLabel.setPreferredSize(new Dimension(40, 30));
        singleBedCountLabel.setMaximumSize(new Dimension(40, 30));
        singleBedCountLabel.setMinimumSize(new Dimension(40, 30));
        headerSearchPanel.add(singleBedCountLabel);
        headerSearchPanel.add(singleBedCountField);

        JTextField squareMetersField = new JTextField();
        squareMetersField.setPreferredSize(new Dimension(50, 30));
        squareMetersField.setMaximumSize(new Dimension(50, 30));
        squareMetersField.setMinimumSize(new Dimension(50, 30));
        squareMetersField.setToolTipText("Square Meters:");
        JLabel squareMetersLabel = new JLabel("sqM:");
        squareMetersLabel.setPreferredSize(new Dimension(40, 30));
        squareMetersLabel.setMaximumSize(new Dimension(40, 30));
        squareMetersLabel.setMinimumSize(new Dimension(40, 30));
        headerSearchPanel.add(squareMetersLabel);
        headerSearchPanel.add(squareMetersField);

        JCheckBox hasTelevisionField = new JCheckBox();
        hasTelevisionField.setPreferredSize(new Dimension(50, 30));
        hasTelevisionField.setMaximumSize(new Dimension(50, 30));
        hasTelevisionField.setMinimumSize(new Dimension(50, 30));
        hasTelevisionField.setToolTipText("hasTV:");
        JLabel hasTelevisionLabel = new JLabel("TV:");
        hasTelevisionLabel.setPreferredSize(new Dimension(40, 30));
        hasTelevisionLabel.setMaximumSize(new Dimension(40, 30));
        hasTelevisionLabel.setMinimumSize(new Dimension(40, 30));
        headerSearchPanel.add(hasTelevisionLabel);
        headerSearchPanel.add(hasTelevisionField);

        JCheckBox hasBalconyField = new JCheckBox();
        hasBalconyField.setPreferredSize(new Dimension(50, 30));
        hasBalconyField.setMaximumSize(new Dimension(50, 30));
        hasBalconyField.setMinimumSize(new Dimension(50, 30));
        hasBalconyField.setToolTipText("Balcony:");
        JLabel hasBalconyLabel = new JLabel("B:");
        hasBalconyLabel.setPreferredSize(new Dimension(40, 30));
        hasBalconyLabel.setMaximumSize(new Dimension(40, 30));
        hasBalconyLabel.setMinimumSize(new Dimension(40, 30));
        headerSearchPanel.add(hasBalconyLabel);
        headerSearchPanel.add(hasBalconyField);

        JCheckBox hasAirConditioningField = new JCheckBox();
        hasAirConditioningField.setPreferredSize(new Dimension(50, 30));
        hasAirConditioningField.setMaximumSize(new Dimension(50, 30));
        hasAirConditioningField.setMinimumSize(new Dimension(50, 30));
        hasAirConditioningField.setToolTipText("Air Conditioning:");
        JLabel hasAirConditioningLabel = new JLabel("AC:");
        hasAirConditioningLabel.setPreferredSize(new Dimension(40, 30));
        hasAirConditioningLabel.setMaximumSize(new Dimension(40, 30));
        hasAirConditioningLabel.setMinimumSize(new Dimension(40, 30));
        headerSearchPanel.add(hasAirConditioningLabel);
        headerSearchPanel.add(hasAirConditioningField);

        JCheckBox hasMinibarField = new JCheckBox();
        hasMinibarField.setPreferredSize(new Dimension(50, 30));
        hasMinibarField.setMaximumSize(new Dimension(50, 30));
        hasMinibarField.setMinimumSize(new Dimension(50, 30));
        hasMinibarField.setToolTipText("Minibar:");
        JLabel hasMinibarLabel = new JLabel("MB:");
        hasMinibarLabel.setPreferredSize(new Dimension(40, 30));
        hasMinibarLabel.setMaximumSize(new Dimension(40, 30));
        hasMinibarLabel.setMinimumSize(new Dimension(40, 30));
        headerSearchPanel.add(hasMinibarLabel);
        headerSearchPanel.add(hasMinibarField);

        JCheckBox hasValuablesSafeField = new JCheckBox();
        hasValuablesSafeField.setPreferredSize(new Dimension(50, 30));
        hasValuablesSafeField.setMaximumSize(new Dimension(50, 30));
        hasValuablesSafeField.setMinimumSize(new Dimension(50, 30));
        hasValuablesSafeField.setToolTipText("Valuables Safe:");
        JLabel hasValuablesSafeLabel = new JLabel("VS:");
        hasValuablesSafeLabel.setPreferredSize(new Dimension(40, 30));
        hasValuablesSafeLabel.setMaximumSize(new Dimension(40, 30));
        hasValuablesSafeLabel.setMinimumSize(new Dimension(40, 30));
        headerSearchPanel.add(hasValuablesSafeLabel);
        headerSearchPanel.add(hasValuablesSafeField);

        JButton searchButton = new JButton("Search");
        searchButton.setPreferredSize(new Dimension(100, 30));
        searchButton.setMaximumSize(new Dimension(100, 30));
        searchButton.setMinimumSize(new Dimension(100, 30));
        searchButton.setToolTipText("Search");
        headerSearchPanel.add(searchButton);

        searchButton.addActionListener(e -> {

            // check for valid input
            // if doubleBedCountField.getText() is not a number, show error message
            // if singleBedCountField.getText() is not a number, show error message
            // if squareMetersField.getText() is not a number, show error message

            if (!doubleBedCountField.getText().isEmpty()) {
                try {
                    Integer.parseInt(doubleBedCountField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Double Bed Count must be a number!");
                    return;
                }
            }

            if (!singleBedCountField.getText().isEmpty()) {
                try {
                    Integer.parseInt(singleBedCountField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Single Bed Count must be a number!");
                    return;
                }
            }

            if (!squareMetersField.getText().isEmpty()) {
                try {
                    Integer.parseInt(squareMetersField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Square Meters must be a number!");
                    return;
                }
            }

            filters.clear();

            filters.put("room_number", roomNumberField.getText());
            if (typeField.getSelectedItem() != "ALL") {
                filters.put("type", typeField.getSelectedItem());
            }
            if (!doubleBedCountField.getText().isEmpty()) {
                filters.put("double_bed_count", Integer.parseInt(doubleBedCountField.getText()));
            }
            if (!singleBedCountField.getText().isEmpty()) {
                filters.put("single_bed_count", Integer.parseInt(singleBedCountField.getText()));
            }
            if (!squareMetersField.getText().isEmpty()) {
                filters.put("square_meters", Integer.parseInt(squareMetersField.getText()));
            }
            filters.put("has_television", hasTelevisionField.isSelected());
            filters.put("has_balcony", hasBalconyField.isSelected());
            filters.put("has_air_conditioning", hasAirConditioningField.isSelected());
            filters.put("has_minibar", hasMinibarField.isSelected());
            filters.put("has_valuables_safe", hasValuablesSafeField.isSelected());
            filters.put("hotel_id", hotel.getId());
            List<Room> rooms = roomController.getByFilters(filters);
            Object[][] data = new Object[rooms.size()][columns.length];

            for (int i = 0; i < rooms.size(); i++) {
                Room room = rooms.get(i);
                data[i][0] = room.getId();
                data[i][1] = room.getRoomNumber();
                data[i][2] = room.getDoubleBedCount();
                data[i][3] = room.getSingleBedCount();
                data[i][4] = room.getAdultPrice();
                data[i][5] = room.getChildPrice();
                data[i][6] = room.getSquareMeters();
            }

            table.setModel(new javax.swing.table.DefaultTableModel(
                    data,
                    columns));
        });

        JButton createButton = new JButton("Create");
        createButton.setPreferredSize(new Dimension(100, 30));
        createButton.setMaximumSize(new Dimension(100, 30));
        createButton.setMinimumSize(new Dimension(100, 30));
        createButton.setToolTipText("Create");
        headerSearchPanel.add(createButton);

        createButton.addActionListener(e -> {
            Room room = new Room();
            room.setHotelId(hotel.getId());
            SeasonController seasonController = new SeasonController();
            if (seasonController.getAllByHotelId(hotel.getId()).size() == 0) {
                JOptionPane.showMessageDialog(null, "You must create a season first!");
                return;
            }
            org.agency.views.room.DetailsView detailsView = new org.agency.views.room.DetailsView(room);
        });

        return headerSearchPanel;
    }

    public JPanel render() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.white);

        List<Room> rooms = roomController.getByHotelId(hotel.getId());

        Object[][] data = new Object[rooms.size()][columns.length];

        for (int i = 0; i < rooms.size(); i++) {
            Room room = rooms.get(i);
            data[i][0] = room.getId();
            data[i][1] = room.getRoomNumber();
            data[i][2] = room.getDoubleBedCount();
            data[i][3] = room.getSingleBedCount();
            data[i][4] = room.getAdultPrice();
            data[i][5] = room.getChildPrice();
            data[i][6] = room.getSquareMeters();
        }

        table = new JTable(data, columns);

        popupMenu = new JPopupMenu();

        JMenuItem editMenuItem = new JMenuItem("Edit");
        editMenuItem.addActionListener(e -> {
            int row = table.getSelectedRow();
            int id = (int) table.getModel().getValueAt(row, 0);
            Room room = roomController.getById(id);
            org.agency.views.room.DetailsView detailsView = new org.agency.views.room.DetailsView(room);
        });

        JMenuItem deleteMenuItem = new JMenuItem("Delete");
        deleteMenuItem.addActionListener(e -> {
            int row = table.getSelectedRow();
            int id = (int) table.getModel().getValueAt(row, 0);
            Room room = roomController.getById(id);
            // Confirm delete
            int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this room?",
                    "Warning", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                roomController.delete(room);
                RefreshParent.run();
            }
        });

        popupMenu.add(editMenuItem);
        popupMenu.add(deleteMenuItem);

        table.setComponentPopupMenu(popupMenu);

        // Disable editing

        table.setDefaultEditor(Object.class, null);

        JScrollPane scrollPane = new JScrollPane(table);

        panel.add(headerSearchPanel(), BorderLayout.NORTH);
        panel.add(scrollPane);

        return panel;
    }

}