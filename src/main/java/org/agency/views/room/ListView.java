package org.agency.views.room;

import com.github.lgooddatepicker.components.DatePicker;
import org.agency.controllers.HotelController;
import org.agency.controllers.RoomController;
import org.agency.controllers.SeasonController;
import org.agency.entities.Hotel;
import org.agency.entities.Reservation;
import org.agency.entities.Room;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class ListView {

    private JFrame frame = new JFrame("Room List");
    private HotelController hotelController = new HotelController();

    private RoomController roomController = new RoomController();

    private LocalDate checkInDate;
    private LocalDate checkOutDate;


    private JTable table;
    private JPopupMenu popupMenu;

    private String[] columns = { "Status", "Room Number", "Type", "Double Bed Count", "Single Bed Count", "Adult Price",
            "Child Price", "Square Meters",  "Has Television", "Has Balcony", "Has Air Conditioning", "Hotel Name", "Star Rating", "Address Full", "Address District", "Address City", "Address Country"};

    private HashMap<String, Object> filters = new HashMap<>();

    public ListView() {
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.add(render());
        frame.setVisible(true);
    }

    private JPanel headerSearchPanel() {
        JPanel headerSearchPanel = new JPanel();
        headerSearchPanel.setLayout(new GridLayout(3, 1));
        headerSearchPanel.setBackground(Color.white);

        JPanel firstRow = new JPanel();
        firstRow.setLayout(new FlowLayout());
        firstRow.setBackground(Color.white);

        JPanel secondRow = new JPanel();
        secondRow.setLayout(new FlowLayout());
        secondRow.setBackground(Color.white);

        JPanel thirdRow = new JPanel();
        thirdRow.setLayout(new FlowLayout());
        thirdRow.setBackground(Color.white);
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

        JLabel firstRowLabel = new JLabel("Room Preferences:");
        firstRowLabel.setPreferredSize(new Dimension(100, 30));
        firstRowLabel.setMaximumSize(new Dimension(100, 30));
        firstRowLabel.setMinimumSize(new Dimension(100, 30));
        firstRow.add(firstRowLabel);

        // SINGLE, DOUBLE, JUNIOR_SUITE, SUITE
        JPanel roomNumberPanel = new JPanel();
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
        roomNumberPanel.add(typeLabel);
        roomNumberPanel.add(typeField);
        firstRow.add(roomNumberPanel);

        JPanel doubleBedCountPanel = new JPanel();
        JTextField doubleBedCountField = new JTextField();
        doubleBedCountField.setPreferredSize(new Dimension(50, 30));
        doubleBedCountField.setMaximumSize(new Dimension(50, 30));
        doubleBedCountField.setMinimumSize(new Dimension(50, 30));
        doubleBedCountField.setToolTipText("Double Bed Count:");
        JLabel doubleBedCountLabel = new JLabel("DBC:");
        doubleBedCountLabel.setPreferredSize(new Dimension(40, 30));
        doubleBedCountLabel.setMaximumSize(new Dimension(40, 30));
        doubleBedCountLabel.setMinimumSize(new Dimension(40, 30));
        doubleBedCountPanel.add(doubleBedCountLabel);
        doubleBedCountPanel.add(doubleBedCountField);
        firstRow.add(doubleBedCountPanel);


        JPanel singleBedCountPanel = new JPanel();
        JTextField singleBedCountField = new JTextField();
        singleBedCountField.setPreferredSize(new Dimension(50, 30));
        singleBedCountField.setMaximumSize(new Dimension(50, 30));
        singleBedCountField.setMinimumSize(new Dimension(50, 30));
        singleBedCountField.setToolTipText("Single Bed Count:");
        JLabel singleBedCountLabel = new JLabel("SBC:");
        singleBedCountLabel.setPreferredSize(new Dimension(40, 30));
        singleBedCountLabel.setMaximumSize(new Dimension(40, 30));
        singleBedCountLabel.setMinimumSize(new Dimension(40, 30));
        singleBedCountPanel.add(singleBedCountLabel);
        singleBedCountPanel.add(singleBedCountField);
        firstRow.add(singleBedCountPanel);

        JPanel adultPricePanel = new JPanel();
        JTextField adultPriceField = new JTextField();
        adultPriceField.setPreferredSize(new Dimension(50, 30));
        adultPriceField.setMaximumSize(new Dimension(50, 30));
        adultPriceField.setMinimumSize(new Dimension(50, 30));
        adultPriceField.setToolTipText("Adult Price:");
        JLabel adultPriceLabel = new JLabel("AP:");
        adultPriceLabel.setPreferredSize(new Dimension(40, 30));
        adultPriceLabel.setMaximumSize(new Dimension(40, 30));
        adultPriceLabel.setMinimumSize(new Dimension(40, 30));
        adultPricePanel.add(adultPriceLabel);
        adultPricePanel.add(adultPriceField);
        firstRow.add(adultPricePanel);

        JPanel childPricePanel = new JPanel();
        JTextField childPriceField = new JTextField();
        childPriceField.setPreferredSize(new Dimension(50, 30));
        childPriceField.setMaximumSize(new Dimension(50, 30));
        childPriceField.setMinimumSize(new Dimension(50, 30));
        childPriceField.setToolTipText("Child Price:");
        JLabel childPriceLabel = new JLabel("CP:");
        childPriceLabel.setPreferredSize(new Dimension(40, 30));
        childPriceLabel.setMaximumSize(new Dimension(40, 30));
        childPriceLabel.setMinimumSize(new Dimension(40, 30));
        childPricePanel.add(childPriceLabel);
        childPricePanel.add(childPriceField);
        firstRow.add(childPricePanel);


        JPanel squareMetersPanel = new JPanel();
        JTextField squareMetersField = new JTextField();
        squareMetersField.setPreferredSize(new Dimension(50, 30));
        squareMetersField.setMaximumSize(new Dimension(50, 30));
        squareMetersField.setMinimumSize(new Dimension(50, 30));
        squareMetersField.setToolTipText("Square Meters:");
        JLabel squareMetersLabel = new JLabel("sqM:");
        squareMetersLabel.setPreferredSize(new Dimension(40, 30));
        squareMetersLabel.setMaximumSize(new Dimension(40, 30));
        squareMetersLabel.setMinimumSize(new Dimension(40, 30));
        squareMetersPanel.add(squareMetersLabel);
        squareMetersPanel.add(squareMetersField);
        firstRow.add(squareMetersPanel);

        JPanel hasTelevisionPanel = new JPanel();
        JCheckBox hasTelevisionField = new JCheckBox();
        hasTelevisionField.setPreferredSize(new Dimension(50, 30));
        hasTelevisionField.setMaximumSize(new Dimension(50, 30));
        hasTelevisionField.setMinimumSize(new Dimension(50, 30));
        hasTelevisionField.setToolTipText("hasTV:");
        JLabel hasTelevisionLabel = new JLabel("TV:");
        hasTelevisionLabel.setPreferredSize(new Dimension(40, 30));
        hasTelevisionLabel.setMaximumSize(new Dimension(40, 30));
        hasTelevisionLabel.setMinimumSize(new Dimension(40, 30));
        hasTelevisionPanel.add(hasTelevisionLabel);
        hasTelevisionPanel.add(hasTelevisionField);
        firstRow.add(hasTelevisionPanel);

        JPanel hasBalconyPanel = new JPanel();
        JCheckBox hasBalconyField = new JCheckBox();
        hasBalconyField.setPreferredSize(new Dimension(50, 30));
        hasBalconyField.setMaximumSize(new Dimension(50, 30));
        hasBalconyField.setMinimumSize(new Dimension(50, 30));
        hasBalconyField.setToolTipText("Balcony:");
        JLabel hasBalconyLabel = new JLabel("B:");
        hasBalconyLabel.setPreferredSize(new Dimension(40, 30));
        hasBalconyLabel.setMaximumSize(new Dimension(40, 30));
        hasBalconyLabel.setMinimumSize(new Dimension(40, 30));
        hasBalconyPanel.add(hasBalconyLabel);
        hasBalconyPanel.add(hasBalconyField);
        firstRow.add(hasBalconyPanel);

        JPanel hasAirConditioningPanel = new JPanel();
        JCheckBox hasAirConditioningField = new JCheckBox();
        hasAirConditioningField.setPreferredSize(new Dimension(50, 30));
        hasAirConditioningField.setMaximumSize(new Dimension(50, 30));
        hasAirConditioningField.setMinimumSize(new Dimension(50, 30));
        hasAirConditioningField.setToolTipText("Air Conditioning:");
        JLabel hasAirConditioningLabel = new JLabel("AC:");
        hasAirConditioningLabel.setPreferredSize(new Dimension(40, 30));
        hasAirConditioningLabel.setMaximumSize(new Dimension(40, 30));
        hasAirConditioningLabel.setMinimumSize(new Dimension(40, 30));
        hasAirConditioningPanel.add(hasAirConditioningLabel);
        hasAirConditioningPanel.add(hasAirConditioningField);
        firstRow.add(hasAirConditioningPanel);

        JPanel hasMinibarPanel = new JPanel();
        JCheckBox hasMinibarField = new JCheckBox();
        hasMinibarField.setPreferredSize(new Dimension(50, 30));
        hasMinibarField.setMaximumSize(new Dimension(50, 30));
        hasMinibarField.setMinimumSize(new Dimension(50, 30));
        hasMinibarField.setToolTipText("Minibar:");
        JLabel hasMinibarLabel = new JLabel("MB:");
        hasMinibarLabel.setPreferredSize(new Dimension(40, 30));
        hasMinibarLabel.setMaximumSize(new Dimension(40, 30));
        hasMinibarLabel.setMinimumSize(new Dimension(40, 30));
        hasMinibarPanel.add(hasMinibarLabel);
        hasMinibarPanel.add(hasMinibarField);
        firstRow.add(hasMinibarPanel);

        JPanel hasValuablesSafePanel = new JPanel();
        JCheckBox hasValuablesSafeField = new JCheckBox();
        hasValuablesSafeField.setPreferredSize(new Dimension(50, 30));
        hasValuablesSafeField.setMaximumSize(new Dimension(50, 30));
        hasValuablesSafeField.setMinimumSize(new Dimension(50, 30));
        hasValuablesSafeField.setToolTipText("Valuables Safe:");
        JLabel hasValuablesSafeLabel = new JLabel("VS:");
        hasValuablesSafeLabel.setPreferredSize(new Dimension(40, 30));
        hasValuablesSafeLabel.setMaximumSize(new Dimension(40, 30));
        hasValuablesSafeLabel.setMinimumSize(new Dimension(40, 30));
        hasValuablesSafePanel.add(hasValuablesSafeLabel);
        hasValuablesSafePanel.add(hasValuablesSafeField);
        firstRow.add(hasValuablesSafePanel);

        // second row

        JLabel secondRowLabel = new JLabel("Hotel Preferences:");
        secondRowLabel.setPreferredSize(new Dimension(100, 30));
        secondRowLabel.setMaximumSize(new Dimension(100, 30));
        secondRowLabel.setMinimumSize(new Dimension(100, 30));
        secondRow.add(secondRowLabel);

        //second row
        JPanel starRatingPanel = new JPanel();
        JComboBox<String> starRatingField = new JComboBox<>(
                new String[] { "ALL", "1", "2", "3", "4", "5" });
        starRatingField.setPreferredSize(new Dimension(100, 30));
        starRatingField.setMaximumSize(new Dimension(100, 30));
        starRatingField.setMinimumSize(new Dimension(100, 30));
        starRatingField.setToolTipText("Star Rating:");
        JLabel starRatingLabel = new JLabel("Star Rating:");
        starRatingLabel.setPreferredSize(new Dimension(40, 30));
        starRatingLabel.setMaximumSize(new Dimension(40, 30));
        starRatingLabel.setMinimumSize(new Dimension(40, 30));
        starRatingPanel.add(starRatingLabel);
        starRatingPanel.add(starRatingField);
        secondRow.add(starRatingPanel);

        // Hotel Name
        JPanel hotelNamePanel = new JPanel();
        JTextField hotelNameField = new JTextField();
        hotelNameField.setPreferredSize(new Dimension(100, 30));
        hotelNameField.setMaximumSize(new Dimension(100, 30));
        hotelNameField.setMinimumSize(new Dimension(100, 30));
        hotelNameField.setToolTipText("Hotel Name:");
        JLabel hotelNameLabel = new JLabel("Hotel Name:");
        hotelNameLabel.setPreferredSize(new Dimension(40, 30));
        hotelNameLabel.setMaximumSize(new Dimension(40, 30));
        hotelNameLabel.setMinimumSize(new Dimension(40, 30));
        hotelNamePanel.add(hotelNameLabel);
        hotelNamePanel.add(hotelNameField);
        secondRow.add(hotelNamePanel);

        // Address Full
        JPanel addressFullPanel = new JPanel();
        JTextField addressFullField = new JTextField();
        addressFullField.setPreferredSize(new Dimension(100, 30));
        addressFullField.setMaximumSize(new Dimension(100, 30));
        addressFullField.setMinimumSize(new Dimension(100, 30));
        addressFullField.setToolTipText("Address Full:");
        JLabel addressFullLabel = new JLabel("Full:");
        addressFullLabel.setPreferredSize(new Dimension(40, 30));
        addressFullLabel.setMaximumSize(new Dimension(40, 30));
        addressFullLabel.setMinimumSize(new Dimension(40, 30));
        addressFullPanel.add(addressFullLabel);
        addressFullPanel.add(addressFullField);
        secondRow.add(addressFullPanel);

        // Address District
        JPanel addressDistrictPanel = new JPanel();
        JTextField addressDistrictField = new JTextField();
        addressDistrictField.setPreferredSize(new Dimension(100, 30));
        addressDistrictField.setMaximumSize(new Dimension(100, 30));
        addressDistrictField.setMinimumSize(new Dimension(100, 30));
        addressDistrictField.setToolTipText("Address District:");
        JLabel addressDistrictLabel = new JLabel("District:");
        addressDistrictLabel.setPreferredSize(new Dimension(40, 30));
        addressDistrictLabel.setMaximumSize(new Dimension(40, 30));
        addressDistrictLabel.setMinimumSize(new Dimension(40, 30));
        addressDistrictPanel.add(addressDistrictLabel);
        addressDistrictPanel.add(addressDistrictField);
        secondRow.add(addressDistrictPanel);

        // Address City
        JPanel addressCityPanel = new JPanel();
        JTextField addressCityField = new JTextField();
        addressCityField.setPreferredSize(new Dimension(100, 30));
        addressCityField.setMaximumSize(new Dimension(100, 30));
        addressCityField.setMinimumSize(new Dimension(100, 30));
        addressCityField.setToolTipText("Address City:");
        JLabel addressCityLabel = new JLabel("City:");
        addressCityLabel.setPreferredSize(new Dimension(40, 30));
        addressCityLabel.setMaximumSize(new Dimension(40, 30));
        addressCityLabel.setMinimumSize(new Dimension(40, 30));
        addressCityPanel.add(addressCityLabel);
        addressCityPanel.add(addressCityField);
        secondRow.add(addressCityPanel);

        // Address Country
        JPanel addressCountryPanel = new JPanel();
        JTextField addressCountryField = new JTextField();
        addressCountryField.setPreferredSize(new Dimension(100, 30));
        addressCountryField.setMaximumSize(new Dimension(100, 30));
        addressCountryField.setMinimumSize(new Dimension(100, 30));
        addressCountryField.setToolTipText("Address Country:");
        JLabel addressCountryLabel = new JLabel("Country:");
        addressCountryLabel.setPreferredSize(new Dimension(40, 30));
        addressCountryLabel.setMaximumSize(new Dimension(40, 30));
        addressCountryLabel.setMinimumSize(new Dimension(40, 30));
        addressCountryPanel.add(addressCountryLabel);
        addressCountryPanel.add(addressCountryField);
        secondRow.add(addressCountryPanel);

        /*
            private boolean hasCarPark;
            private boolean hasInternet;
            private boolean hasPool;
            private boolean hasConcierge;
            private boolean hasSpa;
            private boolean hasRoomService;
         */

        JPanel hasCarParkPanel = new JPanel();
        JCheckBox hasCarParkField = new JCheckBox();
        hasCarParkField.setPreferredSize(new Dimension(50, 30));
        hasCarParkField.setMaximumSize(new Dimension(50, 30));
        hasCarParkField.setMinimumSize(new Dimension(50, 30));
        hasCarParkField.setToolTipText("Car Park:");
        JLabel hasCarParkLabel = new JLabel("CP:");
        hasCarParkLabel.setPreferredSize(new Dimension(40, 30));
        hasCarParkLabel.setMaximumSize(new Dimension(40, 30));
        hasCarParkLabel.setMinimumSize(new Dimension(40, 30));
        hasCarParkPanel.add(hasCarParkLabel);
        hasCarParkPanel.add(hasCarParkField);
        secondRow.add(hasCarParkPanel);

        JPanel hasInternetPanel = new JPanel();
        JCheckBox hasInternetField = new JCheckBox();
        hasInternetField.setPreferredSize(new Dimension(50, 30));
        hasInternetField.setMaximumSize(new Dimension(50, 30));
        hasInternetField.setMinimumSize(new Dimension(50, 30));
        hasInternetField.setToolTipText("Internet:");
        JLabel hasInternetLabel = new JLabel("I:");
        hasInternetLabel.setPreferredSize(new Dimension(40, 30));
        hasInternetLabel.setMaximumSize(new Dimension(40, 30));
        hasInternetLabel.setMinimumSize(new Dimension(40, 30));
        hasInternetPanel.add(hasInternetLabel);
        hasInternetPanel.add(hasInternetField);
        secondRow.add(hasInternetPanel);

        JPanel hasPoolPanel = new JPanel();
        JCheckBox hasPoolField = new JCheckBox();
        hasPoolField.setPreferredSize(new Dimension(50, 30));
        hasPoolField.setMaximumSize(new Dimension(50, 30));
        hasPoolField.setMinimumSize(new Dimension(50, 30));
        hasPoolField.setToolTipText("Pool:");
        JLabel hasPoolLabel = new JLabel("P:");
        hasPoolLabel.setPreferredSize(new Dimension(40, 30));
        hasPoolLabel.setMaximumSize(new Dimension(40, 30));
        hasPoolLabel.setMinimumSize(new Dimension(40, 30));
        hasPoolPanel.add(hasPoolLabel);
        hasPoolPanel.add(hasPoolField);
        secondRow.add(hasPoolPanel);

        JPanel hasConciergePanel = new JPanel();
        JCheckBox hasConciergeField = new JCheckBox();
        hasConciergeField.setPreferredSize(new Dimension(50, 30));
        hasConciergeField.setMaximumSize(new Dimension(50, 30));
        hasConciergeField.setMinimumSize(new Dimension(50, 30));
        hasConciergeField.setToolTipText("Concierge:");
        JLabel hasConciergeLabel = new JLabel("C:");
        hasConciergeLabel.setPreferredSize(new Dimension(40, 30));
        hasConciergeLabel.setMaximumSize(new Dimension(40, 30));
        hasConciergeLabel.setMinimumSize(new Dimension(40, 30));
        hasConciergePanel.add(hasConciergeLabel);
        hasConciergePanel.add(hasConciergeField);
        secondRow.add(hasConciergePanel);

        JPanel hasSpaPanel = new JPanel();
        JCheckBox hasSpaField = new JCheckBox();
        hasSpaField.setPreferredSize(new Dimension(50, 30));
        hasSpaField.setMaximumSize(new Dimension(50, 30));
        hasSpaField.setMinimumSize(new Dimension(50, 30));
        hasSpaField.setToolTipText("Spa:");
        JLabel hasSpaLabel = new JLabel("S:");
        hasSpaLabel.setPreferredSize(new Dimension(40, 30));
        hasSpaLabel.setMaximumSize(new Dimension(40, 30));
        hasSpaLabel.setMinimumSize(new Dimension(40, 30));
        hasSpaPanel.add(hasSpaLabel);
        hasSpaPanel.add(hasSpaField);
        secondRow.add(hasSpaPanel);

        JPanel hasRoomServicePanel = new JPanel();
        JCheckBox hasRoomServiceField = new JCheckBox();
        hasRoomServiceField.setPreferredSize(new Dimension(50, 30));

        hasRoomServiceField.setMaximumSize(new Dimension(50, 30));
        hasRoomServiceField.setMinimumSize(new Dimension(50, 30));
        hasRoomServiceField.setToolTipText("Room Service:");
        JLabel hasRoomServiceLabel = new JLabel("RS:");
        hasRoomServiceLabel.setPreferredSize(new Dimension(40, 30));
        hasRoomServiceLabel.setMaximumSize(new Dimension(40, 30));
        hasRoomServiceLabel.setMinimumSize(new Dimension(40, 30));
        hasRoomServicePanel.add(hasRoomServiceLabel);
        hasRoomServicePanel.add(hasRoomServiceField);
        secondRow.add(hasRoomServicePanel);

        // third row

        JLabel thirdRowLabel = new JLabel("Reservation Preferences:");
        thirdRowLabel.setPreferredSize(new Dimension(100, 30));
        thirdRowLabel.setMaximumSize(new Dimension(100, 30));
        thirdRowLabel.setMinimumSize(new Dimension(100, 30));
        thirdRow.add(thirdRowLabel);

        // Reservation Preferences
        JPanel checkInDatePanel = new JPanel();
        DatePicker checkInDateField = new DatePicker();
        checkInDateField.setPreferredSize(new Dimension(170, 30));
        checkInDateField.setMaximumSize(new Dimension(170, 30));
        checkInDateField.setMinimumSize(new Dimension(170, 30));
        checkInDateField.setToolTipText("Check In Date:");
        JLabel checkInDateLabel = new JLabel("In:");
        checkInDateLabel.setPreferredSize(new Dimension(40, 30));
        checkInDateLabel.setMaximumSize(new Dimension(40, 30));
        checkInDateLabel.setMinimumSize(new Dimension(40, 30));
        checkInDatePanel.add(checkInDateLabel);
        checkInDatePanel.add(checkInDateField);
        thirdRow.add(checkInDatePanel);

        JPanel checkOutDatePanel = new JPanel();
        DatePicker checkOutDateField = new DatePicker();
        checkOutDateField.setPreferredSize(new Dimension(170, 30));
        checkOutDateField.setMaximumSize(new Dimension(170, 30));
        checkOutDateField.setMinimumSize(new Dimension(170, 30));
        checkOutDateField.setToolTipText("Check Out Date:");

        JLabel checkOutDateLabel = new JLabel("Out:");
        checkOutDateLabel.setPreferredSize(new Dimension(40, 30));
        checkOutDateLabel.setMaximumSize(new Dimension(40, 30));
        checkOutDateLabel.setMinimumSize(new Dimension(40, 30));
        checkOutDatePanel.add(checkOutDateLabel);
        checkOutDatePanel.add(checkOutDateField);
        thirdRow.add(checkOutDatePanel);



        JButton searchButton = new JButton("Search");
        searchButton.setPreferredSize(new Dimension(100, 30));
        searchButton.setMaximumSize(new Dimension(100, 30));
        searchButton.setMinimumSize(new Dimension(100, 30));
        searchButton.setToolTipText("Search");
        thirdRow.add(searchButton);

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

            if (!adultPriceField.getText().isEmpty()) {
                try {
                    Double.parseDouble(adultPriceField.getText());
                    filters.put("adult_price", Double.parseDouble(adultPriceField.getText()));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Adult Price must be a number!");
                    return;
                }
            }

            if (!childPriceField.getText().isEmpty()) {
                try {
                    Double.parseDouble(childPriceField.getText());
                    filters.put("child_price", Double.parseDouble(childPriceField.getText()));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Child Price must be a number!");
                    return;
                }
            }

            filters.put("has_television", hasTelevisionField.isSelected());
            filters.put("has_balcony", hasBalconyField.isSelected());
            filters.put("has_air_conditioning", hasAirConditioningField.isSelected());
            filters.put("has_minibar", hasMinibarField.isSelected());
            filters.put("has_valuables_safe", hasValuablesSafeField.isSelected());

            if (starRatingField.getSelectedItem() != "ALL") {
                filters.put("star_rating", Integer.parseInt(starRatingField.getSelectedItem().toString()));
            }

            if (!hotelNameField.getText().isEmpty()) {
                filters.put("hotel_name", hotelNameField.getText());
            }

            if (!addressFullField.getText().isEmpty()) {
                filters.put("address_full", addressFullField.getText());
            }

            if (!addressDistrictField.getText().isEmpty()) {
                filters.put("address_district", addressDistrictField.getText());
            }

            if (!addressCityField.getText().isEmpty()) {
                filters.put("address_city", addressCityField.getText());
            }

            if (!addressCountryField.getText().isEmpty()) {
                filters.put("address_country", addressCountryField.getText());
            }

            filters.put("has_car_park", hasCarParkField.isSelected());
            filters.put("has_internet", hasInternetField.isSelected());
            filters.put("has_pool", hasPoolField.isSelected());
            filters.put("has_concierge", hasConciergeField.isSelected());
            filters.put("has_spa", hasSpaField.isSelected());
            filters.put("has_room_service", hasRoomServiceField.isSelected());

            if (checkInDateField.getDate() != null) {
                checkInDate = checkInDateField.getDate();
            }

            if (checkOutDateField.getDate() != null) {
                checkOutDate = checkOutDateField.getDate();
            }


            List<Room> roomsWithHotel = roomController.getAllHotelJoinedByFilters(filters);

            Object[][] data = new Object[roomsWithHotel.size()][columns.length];

            for (int i = 0; i < roomsWithHotel.size(); i++) {

                if (checkInDate != null && checkOutDate != null) {
                    if (!roomController.isRoomAvailable(roomsWithHotel.get(i).getId(), checkInDate, checkOutDate)) {
                        data[i][0] = "Not Available";
                    } else {
                        data[i][0] = "Available";
                    }
                } else {
                    data[i][0] = "Available";
                }

                if (checkInDate != null && checkOutDate != null) {
                    if (!roomController.isDatesValid(roomsWithHotel.get(i).getId(), checkInDate, checkOutDate)) {
                        data[i][0] = "Not Available";
                    }
                }

                data[i][1] = roomsWithHotel.get(i).getRoomNumber();
                data[i][2] = roomsWithHotel.get(i).getType();
                data[i][3] = roomsWithHotel.get(i).getDoubleBedCount();
                data[i][4] = roomsWithHotel.get(i).getSingleBedCount();
                data[i][5] = roomsWithHotel.get(i).getAdultPrice();
                data[i][6] = roomsWithHotel.get(i).getChildPrice();
                data[i][7] = roomsWithHotel.get(i).getSquareMeters();
                data[i][8] = roomsWithHotel.get(i).isHasTelevision();
                data[i][9] = roomsWithHotel.get(i).isHasBalcony();
                data[i][10] = roomsWithHotel.get(i).isHasAirConditioning();
                Hotel hotel = roomsWithHotel.get(i).getHotel();
                data[i][11] = hotel.getName();
                data[i][12] = hotel.getStarRating();
                data[i][13] = hotel.getAddressFull();
                data[i][14] = hotel.getAddressDistrict();
                data[i][15] = hotel.getAddressCity();
                data[i][16] = hotel.getAddressCountry();
            }

            table.setModel(new javax.swing.table.DefaultTableModel(
                    data,
                    columns));
        });



        headerSearchPanel.add(firstRow);
        headerSearchPanel.add(secondRow);
        headerSearchPanel.add(thirdRow);

        return headerSearchPanel;
    }

    public JPanel render() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.white);

        List<Room> roomsWithHotel = roomController.getAllHotelJoinedByFilters(filters);

        Object[][] data = new Object[roomsWithHotel.size()][columns.length];

        for (int i = 0; i < roomsWithHotel.size(); i++) {
            data[i][0] = "Available";
            data[i][1] = roomsWithHotel.get(i).getRoomNumber();
            data[i][2] = roomsWithHotel.get(i).getType();
            data[i][3] = roomsWithHotel.get(i).getDoubleBedCount();
            data[i][4] = roomsWithHotel.get(i).getSingleBedCount();
            data[i][5] = roomsWithHotel.get(i).getAdultPrice();
            data[i][6] = roomsWithHotel.get(i).getChildPrice();
            data[i][7] = roomsWithHotel.get(i).getSquareMeters();
            data[i][8] = roomsWithHotel.get(i).isHasTelevision();
            data[i][9] = roomsWithHotel.get(i).isHasBalcony();
            data[i][10] = roomsWithHotel.get(i).isHasAirConditioning();
            Hotel hotel = roomsWithHotel.get(i).getHotel();
            data[i][11] = hotel.getName();
            data[i][12] = hotel.getStarRating();
            data[i][13] = hotel.getAddressFull();
            data[i][14] = hotel.getAddressDistrict();
            data[i][15] = hotel.getAddressCity();
            data[i][16] = hotel.getAddressCountry();
        }

        table = new JTable(data, columns);

        popupMenu = new JPopupMenu();

        table.setComponentPopupMenu(popupMenu);

        // Disable editing

        table.setDefaultEditor(Object.class, null);

        //right click menu
        JMenuItem menuItem = new JMenuItem("Reservation");
        menuItem.addActionListener(e -> {
                    int row = table.getSelectedRow();
                    if (row != -1) {
                        Room room = roomsWithHotel.get(row);
                        int roomId = room.getId();
                        Reservation reservation = new Reservation();
                        reservation.setRoomId(roomId);
                        reservation.setSeasonId(room.getSeasonId());
                        reservation.setHotelId(room.getHotelId());
                        if (checkInDate != null && checkOutDate != null) {
                            reservation.setCheckInLocalDate(checkInDate);
                            reservation.setCheckOutLocalDate(checkOutDate);
                        }
                        org.agency.views.reservation.DetailsView detailsView = new org.agency.views.reservation.DetailsView(reservation);
                    }
                }
        );
        JMenuItem menuItem2 = new JMenuItem("Details");
        menuItem2.addActionListener(e -> {
                    int row = table.getSelectedRow();
                    if (row != -1) {
                        Room room = roomsWithHotel.get(row);
                        int roomId = room.getId();
                        org.agency.views.room.DetailsView detailsView = new org.agency.views.room.DetailsView(room);
                    }

                }
        );

        // HOTEL BUTTON
        JMenuItem menuItem3 = new JMenuItem("Hotel");
        menuItem3.addActionListener(e -> {
                    int row = table.getSelectedRow();
                    if (row != -1) {
                        Room room = roomsWithHotel.get(row);
                        int hotelId = room.getHotelId();
                        org.agency.views.hotel.DetailsView detailsView = new org.agency.views.hotel.DetailsView(hotelId);
                    }
                }
        );

        popupMenu.add(menuItem);
        popupMenu.add(menuItem2);
        popupMenu.add(menuItem3);

        table.setComponentPopupMenu(popupMenu);


        JScrollPane scrollPane = new JScrollPane(table);

        panel.add(headerSearchPanel(), BorderLayout.NORTH);
        panel.add(scrollPane);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Rooms");
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.add(new org.agency.views.room.ListView().render());
            frame.setVisible(true);
        });
    }

}