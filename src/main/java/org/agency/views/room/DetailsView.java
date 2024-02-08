package org.agency.views.room;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.Array;
import java.text.BreakIterator;
import java.util.ArrayList;

import org.agency.controllers.RoomController;
import org.agency.controllers.SeasonController;
import org.agency.core.Session;
import org.agency.entities.Hotel;
import org.agency.entities.Pansion;
import org.agency.entities.Room;

import org.agency.controllers.HotelController;
import org.agency.entities.Season;

import org.agency.controllers.PansionController;

public class DetailsView {

    private JFrame frame;
    private Room room;

    private Dimension labelDimension = new Dimension(100, 30);
    private Dimension fieldDimension = new Dimension(200, 30);

    private HotelController hotelController = new HotelController();
    private SeasonController seasonController = new SeasonController();

    private PansionController pansionController = new PansionController();

    private RoomController roomController = new RoomController();

    private DefaultComboBoxModel<Pansion> pansionModel;

    private DefaultComboBoxModel<Hotel> hotelModel;
    private DefaultComboBoxModel<Season> seasonModel;

    private JComboBox<Season> seasonComboBox;

    public DetailsView(Room room) {
        this.room = room;

        frame = new JFrame("Room Details");
        configureFrame(frame);

        if (room.getId() == 0) {
            frame.setTitle("New Room");
        } else {
            frame.setTitle("Room: " + room.getRoomNumber());
        }

        frame.setVisible(true);
        frame.setResizable(false);
    }

    public DetailsView() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void configureFrame(JFrame frame) {
        frame.setSize(400, 600);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(18, 1));


        JPanel roomNumberPanel = new JPanel();
        JLabel roomNumberlabel = new JLabel("Room Number");
        roomNumberlabel.setPreferredSize(labelDimension);
        JTextField roomNumberfield = new JTextField(room.getRoomNumber(), 20);
        roomNumberfield.setPreferredSize(fieldDimension);
        roomNumberfield.addActionListener((ActionEvent evt) -> {
            System.out.println("Room number changed");
            room.setRoomNumber(roomNumberfield.getText());
        });
        roomNumberPanel.add(roomNumberlabel);
        roomNumberPanel.add(roomNumberfield);

        mainPanel.add(roomNumberPanel);

        //TYPE COMBOBOX
        JPanel typePanel = new JPanel();
        JLabel typeLabel = new JLabel("Type");
        typeLabel.setPreferredSize(labelDimension);
        JComboBox<String> typeComboBox = new JComboBox<>();
        typeComboBox.setPreferredSize(fieldDimension);
        typeComboBox.addItem("SINGLE");
        typeComboBox.addItem("DOUBLE");
        typeComboBox.addItem("JUNIOR");
        typeComboBox.addItem("SUITE");
        typeComboBox.addActionListener((ActionEvent evt) -> {
            room.setType((String) typeComboBox.getSelectedItem());
        });

        if (room.getType() != null) {
            typeComboBox.setSelectedItem(room.getType());
        }

        typePanel.add(typeLabel);
        typePanel.add(typeComboBox);
        mainPanel.add(typePanel);

        //DOUBLE BED COUNT COMBOBOX
        JPanel doubleBedCountPanel = new JPanel();
        JLabel doubleBedCountLabel = new JLabel("Double Bed Count");
        doubleBedCountLabel.setPreferredSize(labelDimension);
        JComboBox<Integer> doubleBedCountComboBox = new JComboBox<>();
        doubleBedCountComboBox.setPreferredSize(fieldDimension);
        for (int i = 0; i < 10; i++) {
            doubleBedCountComboBox.addItem(i);
        }

        doubleBedCountComboBox.addActionListener((ActionEvent evt) -> {
            room.setDoubleBedCount((int) doubleBedCountComboBox.getSelectedItem());
        });

        if (room.getDoubleBedCount() != 0) {
            doubleBedCountComboBox.setSelectedItem(room.getDoubleBedCount());
        }

        doubleBedCountPanel.add(doubleBedCountLabel);
        doubleBedCountPanel.add(doubleBedCountComboBox);
        mainPanel.add(doubleBedCountPanel);

        //SINGLE BED COUNT COMBOBOX
        JPanel singleBedCountPanel = new JPanel();
        JLabel singleBedCountLabel = new JLabel("Single Bed Count");
        singleBedCountLabel.setPreferredSize(labelDimension);
        JComboBox<Integer> singleBedCountComboBox = new JComboBox<>();
        singleBedCountComboBox.setPreferredSize(fieldDimension);
        for (int i = 0; i < 10; i++) {
            singleBedCountComboBox.addItem(i);
        }

        singleBedCountComboBox.addActionListener((ActionEvent evt) -> {
            room.setSingleBedCount((int) singleBedCountComboBox.getSelectedItem());
        });

        if (room.getSingleBedCount() != 0) {
            singleBedCountComboBox.setSelectedItem(room.getSingleBedCount());
        }

        singleBedCountPanel.add(singleBedCountLabel);
        singleBedCountPanel.add(singleBedCountComboBox);
        mainPanel.add(singleBedCountPanel);

        //ADULT PRICE COMBOBOX
        JPanel adultPricePanel = new JPanel();
        JLabel adultPriceLabel = new JLabel("Adult Price");
        adultPriceLabel.setPreferredSize(labelDimension);

        JTextField adultPriceField = new JTextField(String.valueOf(room.getAdultPrice()), 20);
        adultPriceField.setPreferredSize(fieldDimension);
        adultPriceField.addActionListener((ActionEvent evt) -> {
            room.setAdultPrice(Double.parseDouble(adultPriceField.getText()));
        });

        adultPricePanel.add(adultPriceLabel);
        adultPricePanel.add(adultPriceField);
        mainPanel.add(adultPricePanel);

        //CHILD PRICE COMBOBOX
        JPanel childPricePanel = new JPanel();
        JLabel childPriceLabel = new JLabel("Child Price");
        childPriceLabel.setPreferredSize(labelDimension);
        JTextField childPriceField = new JTextField(String.valueOf(room.getChildPrice()), 20);

        childPriceField.setPreferredSize(fieldDimension);
        childPriceField.addActionListener((ActionEvent evt) -> {
            room.setChildPrice(Double.parseDouble(childPriceField.getText()));
        });

        childPricePanel.add(childPriceLabel);
        childPricePanel.add(childPriceField);
        mainPanel.add(childPricePanel);

        //SQUARE METERS COMBOBOX
        JPanel squareMetersPanel = new JPanel();
        JLabel squareMetersLabel = new JLabel("Square Meters");
        squareMetersLabel.setPreferredSize(labelDimension);
        JComboBox<Integer> squareMetersComboBox = new JComboBox<>();
        squareMetersComboBox.setPreferredSize(fieldDimension);
        for (int i = 0; i < 100; i++) {
            squareMetersComboBox.addItem(i);
        }

        squareMetersComboBox.addActionListener((ActionEvent evt) -> {
            room.setSquareMeters((int) squareMetersComboBox.getSelectedItem());
        });

        if (room.getSquareMeters() != 0) {
            squareMetersComboBox.setSelectedItem(room.getSquareMeters());
        }

        squareMetersPanel.add(squareMetersLabel);
        squareMetersPanel.add(squareMetersComboBox);
        mainPanel.add(squareMetersPanel);

        //TELEVISION CHECKBOX
        JPanel televisionPanel = new JPanel();
        JLabel televisionLabel = new JLabel("Television");
        televisionLabel.setPreferredSize(labelDimension);
        JCheckBox televisionCheckBox = new JCheckBox();
        televisionCheckBox.setPreferredSize(fieldDimension);
        televisionCheckBox.setSelected(room.isHasTelevision());
        televisionCheckBox.addActionListener((ActionEvent evt) -> {
            room.setHasTelevision(televisionCheckBox.isSelected());
        });
        televisionPanel.add(televisionLabel);
        televisionPanel.add(televisionCheckBox);
        mainPanel.add(televisionPanel);

        //BALCONY CHECKBOX
        JPanel balconyPanel = new JPanel();
        JLabel balconyLabel = new JLabel("Balcony");
        balconyLabel.setPreferredSize(labelDimension);
        JCheckBox balconyCheckBox = new JCheckBox();
        balconyCheckBox.setPreferredSize(fieldDimension);
        balconyCheckBox.setSelected(room.isHasBalcony());
        balconyCheckBox.addActionListener((ActionEvent evt) -> {
            room.setHasBalcony(balconyCheckBox.isSelected());
        });
        balconyPanel.add(balconyLabel);
        balconyPanel.add(balconyCheckBox);
        mainPanel.add(balconyPanel);

        //AIR CONDITIONING CHECKBOX
        JPanel airConditioningPanel = new JPanel();
        JLabel airConditioningLabel = new JLabel("Air Conditioning");
        airConditioningLabel.setPreferredSize(labelDimension);
        JCheckBox airConditioningCheckBox = new JCheckBox();
        airConditioningCheckBox.setPreferredSize(fieldDimension);
        airConditioningCheckBox.setSelected(room.isHasAirConditioning());
        airConditioningCheckBox.addActionListener((ActionEvent evt) -> {
            room.setHasAirConditioning(airConditioningCheckBox.isSelected());
        });
        airConditioningPanel.add(airConditioningLabel);
        airConditioningPanel.add(airConditioningCheckBox);
        mainPanel.add(airConditioningPanel);

        //MINIBAR CHECKBOX
        JPanel minibarPanel = new JPanel();
        JLabel minibarLabel = new JLabel("Minibar");
        minibarLabel.setPreferredSize(labelDimension);
        JCheckBox minibarCheckBox = new JCheckBox();
        minibarCheckBox.setPreferredSize(fieldDimension);
        minibarCheckBox.setSelected(room.isHasMinibar());
        minibarCheckBox.addActionListener((ActionEvent evt) -> {
            room.setHasMinibar(minibarCheckBox.isSelected());
        });
        minibarPanel.add(minibarLabel);
        minibarPanel.add(minibarCheckBox);
        mainPanel.add(minibarPanel);

        //VALUABLES SAFE CHECKBOX
        JPanel valuablesSafePanel = new JPanel();
        JLabel valuablesSafeLabel = new JLabel("Valuables Safe");
        valuablesSafeLabel.setPreferredSize(labelDimension);
        JCheckBox valuablesSafeCheckBox = new JCheckBox();
        valuablesSafeCheckBox.setPreferredSize(fieldDimension);
        valuablesSafeCheckBox.setSelected(room.isHasValuablesSafe());
        valuablesSafeCheckBox.addActionListener((ActionEvent evt) -> {
            room.setHasValuablesSafe(valuablesSafeCheckBox.isSelected());
        });
        valuablesSafePanel.add(valuablesSafeLabel);
        valuablesSafePanel.add(valuablesSafeCheckBox);
        mainPanel.add(valuablesSafePanel);

        //GAMING CONSOLE CHECKBOX
        JPanel gamingConsolePanel = new JPanel();
        JLabel gamingConsoleLabel = new JLabel("Gaming Console");
        gamingConsoleLabel.setPreferredSize(labelDimension);
        JCheckBox gamingConsoleCheckBox = new JCheckBox();
        gamingConsoleCheckBox.setPreferredSize(fieldDimension);
        gamingConsoleCheckBox.setSelected(room.isHasGamingConsole());
        gamingConsoleCheckBox.addActionListener((ActionEvent evt) -> {
            room.setHasGamingConsole(gamingConsoleCheckBox.isSelected());
        });
        gamingConsolePanel.add(gamingConsoleLabel);
        gamingConsolePanel.add(gamingConsoleCheckBox);
        mainPanel.add(gamingConsolePanel);

        //PROJECTOR CHECKBOX
        JPanel projectorPanel = new JPanel();
        JLabel projectorLabel = new JLabel("Projector");
        projectorLabel.setPreferredSize(labelDimension);
        JCheckBox projectorCheckBox = new JCheckBox();
        projectorCheckBox.setPreferredSize(fieldDimension);
        projectorCheckBox.setSelected(room.isHasProjector());
        projectorCheckBox.addActionListener((ActionEvent evt) -> {
            room.setHasProjector(projectorCheckBox.isSelected());
        });
        projectorPanel.add(projectorLabel);
        projectorPanel.add(projectorCheckBox);
        mainPanel.add(projectorPanel);

        //HOTEL ComboBox






        // seasonId ComboBox

        JPanel seasonIdPanel = new JPanel();
        JLabel seasonIdLabel = new JLabel("Season");
        seasonIdLabel.setPreferredSize(labelDimension);

        ArrayList<Season> seasons = (ArrayList<Season>) seasonController.getAllByHotelId(room.getHotelId());
        // model
        seasonModel = new DefaultComboBoxModel<>();



        for (Season season : seasons) {
            seasonModel.addElement(season);
        }


        DefaultListCellRenderer seasonRenderer = new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                Season season = (Season) value;
                if (season == null) {
                    setText("");
                    return this;
                }
                setText(season.getName());
                return this;
            }
        };

        seasonComboBox = new JComboBox<>(seasonModel);
        seasonComboBox.setPreferredSize(fieldDimension);

        seasonComboBox.setRenderer(seasonRenderer);

        seasonComboBox.addActionListener((ActionEvent evt) -> {
            room.setSeasonId(((Season) seasonComboBox.getSelectedItem()).getId());
        });

        if (room.getSeasonId() != 0) {
            seasonComboBox.setSelectedItem(seasonController.getById(room.getSeasonId()));
        }



        seasonIdPanel.add(seasonIdLabel);

        seasonIdPanel.add(seasonComboBox);

        mainPanel.add(seasonIdPanel);

        //Pansion ComboBox

        JPanel pansionPanel = new JPanel();
        JLabel pansionLabel = new JLabel("Pansion");
        pansionLabel.setPreferredSize(labelDimension);

        ArrayList<Pansion> pensions = new ArrayList<>();

        pensions = (ArrayList<Pansion>) pansionController.getByHotelId(room.getHotelId());


        pansionModel = new DefaultComboBoxModel<>();


        for (Pansion pansion : pensions) {
            pansionModel.addElement(pansion);
        }



        DefaultListCellRenderer pansionRenderer = new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                Pansion pansion = (Pansion) value;
                if (pansion == null) {
                    setText("");
                    return this;
                }
                setText(pansion.getName());
                return this;
            }
        };

        JComboBox<Pansion> pansionComboBox = new JComboBox<>(pansionModel);
        pansionComboBox.setPreferredSize(fieldDimension);

        pansionComboBox.setRenderer(pansionRenderer);

        pansionComboBox.addActionListener((ActionEvent evt) -> {
            room.setPansionId(((Pansion) pansionComboBox.getSelectedItem()).getId());
        });

        if (room.getPansionId() != 0) {
            pansionComboBox.setSelectedItem(pansionController.getById(room.getPansionId()));
        }

        pansionPanel.add(pansionLabel);
        pansionPanel.add(pansionComboBox);

        mainPanel.add(pansionPanel);

        JButton saveButton = new JButton("Save");
        JButton deleteButton = new JButton("Delete");

        saveButton.addActionListener((ActionEvent evt) -> {

            if (seasonComboBox.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(null, "Season is required", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (pansionComboBox.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(null, "Pansion is required", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            room.setRoomNumber(roomNumberfield.getText());
            room.setType((String) typeComboBox.getSelectedItem());
            room.setDoubleBedCount((int) doubleBedCountComboBox.getSelectedItem());
            room.setSingleBedCount((int) singleBedCountComboBox.getSelectedItem());
            room.setAdultPrice(Double.parseDouble(adultPriceField.getText()));
            room.setChildPrice(Double.parseDouble(childPriceField.getText()));
            room.setSquareMeters((int) squareMetersComboBox.getSelectedItem());
            room.setHasTelevision(televisionCheckBox.isSelected());
            room.setHasBalcony(balconyCheckBox.isSelected());
            room.setHasAirConditioning(airConditioningCheckBox.isSelected());
            room.setHasMinibar(minibarCheckBox.isSelected());
            room.setHasValuablesSafe(valuablesSafeCheckBox.isSelected());
            room.setHasGamingConsole(gamingConsoleCheckBox.isSelected());
            room.setHasProjector(projectorCheckBox.isSelected());
            room.setPansionId(((Pansion) pansionComboBox.getSelectedItem()).getId());
            room.setSeasonId(((Season) seasonComboBox.getSelectedItem()).getId());




            if (room.getId() == 0) {
                System.out.println("Creating new room");
                roomController.create(room);
            } else {
                System.out.println("Updating room");
                roomController.update(room);
            }


            frame.dispose();
        });

        deleteButton.addActionListener((ActionEvent evt) -> {
            if (room.getId() != 0) {
                // Confirmation dialog
                int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this room?",
                        "Warning", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    roomController.delete(room);
                    frame.dispose();
                }
            } else {
                frame.dispose();
            }
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.add(saveButton);
        buttonsPanel.add(deleteButton);

        mainPanel.add(buttonsPanel);
        frame.add(mainPanel);
    }

    // Similar methods for other room details (type, double bed count, single bed
    // count, etc.)


    // Other methods for creating panels for each property of Room entity
}
