package org.agency.views.room;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.Array;
import java.text.BreakIterator;
import java.util.ArrayList;

import org.agency.controllers.SeasonController;
import org.agency.core.Session;
import org.agency.entities.Hotel;
import org.agency.entities.Room;

import org.agency.controllers.HotelController;
import org.agency.entities.Season;

public class DetailsView {

    private JFrame frame;
    private Room room;

    private Dimension labelDimension = new Dimension(100, 30);
    private Dimension fieldDimension = new Dimension(200, 30);

    private HotelController hotelController = new HotelController();
    private SeasonController seasonController = new SeasonController();

    private DefaultComboBoxModel<Hotel> hotelModel;
    private DefaultComboBoxModel<Season> seasonModel;

    private JComboBox<Hotel> hotelComboBox;
    private JComboBox<Season> seasonComboBox;


    public DetailsView(Room model) {
        this.room = model;

        frame = new JFrame("Room Details");
        configureFrame(frame);

        if (model.getId() == 0) {
            frame.setTitle("New Room");
        } else {
            frame.setTitle("Room: " + model.getRoomNumber());
        }

        frame.setVisible(true);
        frame.setResizable(false);
    }

    public DetailsView() {
        DetailsView view = new DetailsView(new Room());
    }

    private void configureFrame(JFrame frame) {
        frame.setSize(400, 600);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(17, 1));

        mainPanel.add(roomNumberPanel());
        mainPanel.add(typePanel());
        mainPanel.add(doubleBedCountPanel());
        mainPanel.add(singleBedCountPanel());
        mainPanel.add(adultPricePanel());
        mainPanel.add(childPricePanel());
        mainPanel.add(squareMetersPanel());
        mainPanel.add(televisionPanel());
        mainPanel.add(balconyPanel());
        mainPanel.add(airConditioningPanel());
        mainPanel.add(minibarPanel());
        mainPanel.add(valuablesSafePanel());
        mainPanel.add(gamingConsolePanel());
        mainPanel.add(projectorPanel());

        //add hotelId and seasonId

        JPanel hotelIdPanel = new JPanel();
        JLabel hotelIdLabel = new JLabel("Hotel Id");
        hotelIdLabel.setPreferredSize(labelDimension);

        ArrayList<Hotel> hotels = (ArrayList<Hotel>) hotelController.getAll();
        // model
        hotelModel = new DefaultComboBoxModel<>();

        for (Hotel hotel : hotels) {
            hotelModel.addElement(hotel);
        }

        // custom renderer
        DefaultListCellRenderer hotelRenderer = new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                Hotel hotel = (Hotel) value;
                setText(hotel.getName());
                return this;
            }
        };

        hotelComboBox = new JComboBox<>(hotelModel);

        hotelComboBox.setRenderer(hotelRenderer);

        hotelComboBox.addActionListener((ActionEvent evt) -> {
            room.setHotelId(((Hotel) hotelComboBox.getSelectedItem()).getId());
            ArrayList<Season> seasons = (ArrayList<Season>) seasonController.getAllByHotelId(room.getHotelId());
            // model
            seasonModel = new DefaultComboBoxModel<>();

            for (Season season : seasons) {
                seasonModel.addElement(season);
            }

            if (seasons.size() == 0) {
                seasonComboBox.setEnabled(false);
                seasonComboBox.setSelectedIndex(-1);
            } else {
                seasonComboBox.setEnabled(true);
                seasonComboBox.setSelectedIndex(0);
            }
        });

        if (room.getHotelId() != 0) {
            hotelComboBox.setSelectedItem(hotelController.getById(room.getHotelId()));
        }



        hotelIdPanel.add(hotelIdLabel);

        hotelIdPanel.add(hotelComboBox);

        mainPanel.add(hotelIdPanel);

        //seasonId ComboBox

        JPanel seasonIdPanel = new JPanel();
        JLabel seasonIdLabel = new JLabel("Season Id");
        seasonIdLabel.setPreferredSize(labelDimension);

        ArrayList<Season> seasons = (ArrayList<Season>) seasonController.getAllByHotelId(room.getHotelId());
        // model
        seasonModel = new DefaultComboBoxModel<>();

        for (Season season : seasons) {
            seasonModel.addElement(season);
        }

        // custom renderer
        DefaultListCellRenderer seasonRenderer = new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                Season season = (Season) value;
                setText(season.getName());
                return this;
            }
        };

        seasonComboBox = new JComboBox<>(seasonModel);

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



        JButton saveButton = new JButton("Save");
        JButton deleteButton = new JButton("Delete");

        saveButton.addActionListener((ActionEvent evt) -> {
            // Save logic here
            frame.dispose();
        });

        deleteButton.addActionListener((ActionEvent evt) -> {
            // Delete logic here
            frame.dispose();
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.add(saveButton);
        buttonsPanel.add(deleteButton);

        mainPanel.add(buttonsPanel);

        frame.add(mainPanel);
    }

    private JPanel roomNumberPanel() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Room Number");
        label.setPreferredSize(labelDimension);
        JTextField field = new JTextField(room.getRoomNumber(), 20);
        field.setPreferredSize(fieldDimension);
        field.addActionListener((ActionEvent evt) -> {
            room.setRoomNumber(field.getText());
        });
        panel.add(label);
        panel.add(field);
        return panel;
    }

    private JPanel typePanel() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Type");
        label.setPreferredSize(labelDimension);
        JTextField field = new JTextField(room.getType(), 20);
        field.setPreferredSize(fieldDimension);
        field.addActionListener((ActionEvent evt) -> {
            room.setType(field.getText());
        });
        panel.add(label);
        panel.add(field);
        return panel;
    }

    private JPanel doubleBedCountPanel() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Double Bed Count");
        label.setPreferredSize(labelDimension);
        JTextField field = new JTextField(String.valueOf(room.getDoubleBedCount()), 20);
        field.setPreferredSize(fieldDimension);
        field.addActionListener((ActionEvent evt) -> {
            room.setDoubleBedCount(Integer.parseInt(field.getText()));
        });
        panel.add(label);
        panel.add(field);
        return panel;
    }

    private JPanel singleBedCountPanel() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Single Bed Count");
        label.setPreferredSize(labelDimension);
        JTextField field = new JTextField(String.valueOf(room.getSingleBedCount()), 20);
        field.setPreferredSize(fieldDimension);
        field.addActionListener((ActionEvent evt) -> {
            room.setSingleBedCount(Integer.parseInt(field.getText()));
        });
        panel.add(label);
        panel.add(field);
        return panel;
    }

    private JPanel adultPricePanel() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Adult Price");
        label.setPreferredSize(labelDimension);
        JTextField field = new JTextField(String.valueOf(room.getAdultPrice()), 20);
        field.setPreferredSize(fieldDimension);
        field.addActionListener((ActionEvent evt) -> {
            room.setAdultPrice(Double.parseDouble(field.getText()));
        });
        panel.add(label);
        panel.add(field);
        return panel;
    }

    private JPanel childPricePanel() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Child Price");
        label.setPreferredSize(labelDimension);
        JTextField field = new JTextField(String.valueOf(room.getChildPrice()), 20);
        field.setPreferredSize(fieldDimension);
        field.addActionListener((ActionEvent evt) -> {
            room.setChildPrice(Double.parseDouble(field.getText()));
        });
        panel.add(label);
        panel.add(field);
        return panel;
    }

    private JPanel squareMetersPanel() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Square Meters");
        label.setPreferredSize(labelDimension);
        JTextField field = new JTextField(String.valueOf(room.getSquareMeters()), 20);
        field.setPreferredSize(fieldDimension);
        field.addActionListener((ActionEvent evt) -> {
            room.setSquareMeters(Integer.parseInt(field.getText()));
        });
        panel.add(label);
        panel.add(field);
        return panel;
    }


    private JPanel televisionPanel() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Television");
        label.setPreferredSize(labelDimension);
        JCheckBox checkBox = new JCheckBox();
        checkBox.setSelected(room.isHasTelevision());
        checkBox.addActionListener((ActionEvent evt) -> {
            room.setHasTelevision(checkBox.isSelected());
        });
        panel.add(label);
        panel.add(checkBox);
        return panel;
    }

    private JPanel balconyPanel() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Balcony");
        label.setPreferredSize(labelDimension);
        JCheckBox checkBox = new JCheckBox();
        checkBox.setSelected(room.isHasBalcony());
        checkBox.addActionListener((ActionEvent evt) -> {
            room.setHasBalcony(checkBox.isSelected());
        });
        panel.add(label);
        panel.add(checkBox);
        return panel;
    }

    private JPanel airConditioningPanel() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Air Conditioning");
        label.setPreferredSize(labelDimension);
        JCheckBox checkBox = new JCheckBox();
        checkBox.setSelected(room.isHasAirConditioning());
        checkBox.addActionListener((ActionEvent evt) -> {
            room.setHasAirConditioning(checkBox.isSelected());
        });
        panel.add(label);
        panel.add(checkBox);
        return panel;
    }

    private JPanel minibarPanel() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Minibar");
        label.setPreferredSize(labelDimension);
        JCheckBox checkBox = new JCheckBox();
        checkBox.setSelected(room.isHasMinibar());
        checkBox.addActionListener((ActionEvent evt) -> {
            room.setHasMinibar(checkBox.isSelected());
        });
        panel.add(label);
        panel.add(checkBox);
        return panel;
    }

    private JPanel valuablesSafePanel() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Valuables Safe");
        label.setPreferredSize(labelDimension);
        JCheckBox checkBox = new JCheckBox();
        checkBox.setSelected(room.isHasValuablesSafe());
        checkBox.addActionListener((ActionEvent evt) -> {
            room.setHasValuablesSafe(checkBox.isSelected());
        });
        panel.add(label);
        panel.add(checkBox);
        return panel;
    }

    private JPanel gamingConsolePanel() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Gaming Console");
        label.setPreferredSize(labelDimension);
        JCheckBox checkBox = new JCheckBox();
        checkBox.setSelected(room.isHasGamingConsole());
        checkBox.addActionListener((ActionEvent evt) -> {
            room.setHasGamingConsole(checkBox.isSelected());
        });
        panel.add(label);
        panel.add(checkBox);
        return panel;
    }

    private JPanel projectorPanel() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Projector");
        label.setPreferredSize(labelDimension);
        JCheckBox checkBox = new JCheckBox();
        checkBox.setSelected(room.isHasProjector());
        checkBox.addActionListener((ActionEvent evt) -> {
            room.setHasProjector(checkBox.isSelected());
        });
        panel.add(label);
        panel.add(checkBox);
        return panel;
    }



    // Similar methods for other room details (type, double bed count, single bed count, etc.)

    public static void main(String[] args) {
        DetailsView view = new DetailsView();
    }

    // Other methods for creating panels for each property of Room entity
}
