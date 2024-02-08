package org.agency.views.pansion;

import org.agency.controllers.HotelController;
import org.agency.controllers.PansionController;
import org.agency.controllers.RoomController;
import org.agency.entities.Pansion;
import org.agency.entities.Hotel;
import org.agency.entities.Pansion;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class DetailsView {

    public Integer state = -1;
    public JFrame frame = new JFrame("Pansion");
    public Pansion pansion;

    public Dimension labelDimension = new Dimension(100, 30);
    public Dimension fieldDimension = new Dimension(300, 30);

    public HotelController hotelController = new HotelController();
    public PansionController pansionController = new PansionController();

    public DetailsView(Pansion model) {
        this.pansion = (Pansion) model;

        if (model.getId() == 0) {
            frame.setTitle("New Pansion");
        } else {
            frame.setTitle("Pansion: " + model.getName());
        }

        configureFrame(this.frame);
        this.frame.setVisible(true);
    }

    public DetailsView() {
        DetailsView DetailsView = new DetailsView(new Pansion());
    }

    private void configureFrame(JFrame frame) {
        this.frame.setSize(350, 300);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setIconImage(new ImageIcon("src/main/resources/icon.png").getImage());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 1));

        // Panels

        /*
         * private boolean breakfast;
         * private boolean lunch;
         * private boolean dinner;
         * private boolean midnightSnack;
         * private boolean softDrinks;
         * private boolean alcoholicDrinks;
         * private boolean snacks;
         * 
         * checkboxs
         */

        JPanel hotelIdPanel = new JPanel();
        hotelIdPanel.setLayout(new FlowLayout());

        JLabel hotelIdLabel = new JLabel("Hotel Name");
        hotelIdLabel.setPreferredSize(new Dimension(100, 30));

        // get hotels from database
        List<Hotel> hotels = hotelController.getAll();

        // create model and add hotels to it
        DefaultComboBoxModel<Hotel> model = new DefaultComboBoxModel<>();

        for (Hotel hotel : hotels) {
            model.addElement(hotel.getId() == 0 ? new Hotel() : hotel);
        }

        // custom renderer
        ListCellRenderer<? super Hotel> renderer = new DefaultListCellRenderer() {
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
        };

        JComboBox<Hotel> hotelIdField = new JComboBox<>(model);

        hotelIdField.setRenderer(renderer);

        if (pansion.getId() == 0) {
            hotelIdField.setSelectedIndex(0);
        } else {
            hotelIdField.setSelectedItem(hotelController.getById(pansion.getHotelId()));
        }

        hotelIdField.setPreferredSize(new Dimension(200, 30));

        hotelIdField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                JComboBox<?> cb = (JComboBox<?>) evt.getSource();
                Hotel hotel = (Hotel) cb.getSelectedItem();
                if (hotel != null) {
                    pansion.setHotelId(hotel.getId());
                    System.out.println(pansion.getHotelId());
                }
            }
        });

        hotelIdPanel.add(hotelIdLabel);
        hotelIdPanel.add(hotelIdField);
        //mainPanel.add(hotelIdPanel);

        // name
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new FlowLayout());
        JLabel nameLabel = new JLabel("Name");
        nameLabel.setPreferredSize(labelDimension);
        // JTextField nameField = new JTextField(pansion.getName(), 20);

        // ('BED_AND_BREAKFAST', 'HALF_BOARD', 'FULL_BOARD', 'ALL_INCLUSIVE',
        // 'ULTRA_ALL_INCLUSIVE', 'BED_ONLY' , 'ALL_INCLUSIVE_NO_ALCOHOL'

        JComboBox nameField = new JComboBox(new String[] { "BED_AND_BREAKFAST", "HALF_BOARD", "FULL_BOARD",
                "ALL_INCLUSIVE", "ULTRA_ALL_INCLUSIVE", "BED_ONLY", "ALL_INCLUSIVE_NO_ALCOHOL" });

        // select first
        nameField.setSelectedIndex(0);
        nameField.setPreferredSize(new Dimension(200, 30));
        nameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                JComboBox cb = (JComboBox) evt.getSource();
                String name = (String) cb.getSelectedItem();
                System.out.println(name);
                pansion.setName(name);
            }
        });

        if (pansion.getId() != 0) {
            nameField.setSelectedItem(pansion.getName());
        }

        namePanel.add(nameLabel);
        namePanel.add(nameField);

        mainPanel.add(namePanel);

        JPanel CheckBoxPanel = new JPanel();
        CheckBoxPanel.setLayout(new GridLayout(0, 2));
        CheckBoxPanel.setBorder(BorderFactory.createTitledBorder("Pansion"));
        CheckBoxPanel.setPreferredSize(new Dimension(400, 30));

        // breakfast
        JPanel breakfastPanel = new JPanel();
        JLabel breakfastLabel = new JLabel("Breakfast");
        breakfastLabel.setPreferredSize(labelDimension);
        JCheckBox breakfastField = new JCheckBox();
        breakfastField.setPreferredSize(fieldDimension);
        breakfastField.setSelected(pansion.isBreakfast());
        breakfastField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                pansion.setBreakfast(breakfastField.isSelected());
            }
        });
        breakfastPanel.setLayout(new BoxLayout(breakfastPanel, BoxLayout.X_AXIS));
        breakfastPanel.setPreferredSize(new Dimension(300, 30));
        breakfastPanel.setMaximumSize(new Dimension(300, 30));
        breakfastPanel.add(breakfastLabel);
        breakfastPanel.add(breakfastField);
        CheckBoxPanel.add(breakfastPanel);

        // lunch
        JPanel lunchPanel = new JPanel();
        JLabel lunchLabel = new JLabel("Lunch");
        lunchLabel.setPreferredSize(labelDimension);
        JCheckBox lunchField = new JCheckBox();
        lunchField.setPreferredSize(fieldDimension);
        lunchField.setSelected(pansion.isLunch());
        lunchField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                pansion.setLunch(lunchField.isSelected());
            }
        });

        lunchPanel.setLayout(new BoxLayout(lunchPanel, BoxLayout.X_AXIS));
        lunchPanel.setPreferredSize(new Dimension(300, 30));
        lunchPanel.setMaximumSize(new Dimension(300, 30));
        lunchPanel.add(lunchLabel);
        lunchPanel.add(lunchField);
        CheckBoxPanel.add(lunchPanel);

        // dinner

        JPanel dinnerPanel = new JPanel();
        JLabel dinnerLabel = new JLabel("Dinner");
        dinnerLabel.setPreferredSize(labelDimension);
        JCheckBox dinnerField = new JCheckBox();
        dinnerField.setPreferredSize(fieldDimension);
        dinnerField.setSelected(pansion.isDinner());
        dinnerField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                pansion.setDinner(dinnerField.isSelected());
            }
        });

        dinnerPanel.setLayout(new BoxLayout(dinnerPanel, BoxLayout.X_AXIS));
        dinnerPanel.setPreferredSize(new Dimension(300, 30));
        dinnerPanel.setMaximumSize(new Dimension(300, 30));
        dinnerPanel.add(dinnerLabel);
        dinnerPanel.add(dinnerField);

        CheckBoxPanel.add(dinnerPanel);

        // midnightSnack

        JPanel midnightSnackPanel = new JPanel();
        JLabel midnightSnackLabel = new JLabel("Midnight Snack");
        midnightSnackLabel.setPreferredSize(labelDimension);
        JCheckBox midnightSnackField = new JCheckBox();
        midnightSnackField.setPreferredSize(fieldDimension);
        midnightSnackField.setSelected(pansion.isMidnightSnack());
        midnightSnackField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                pansion.setMidnightSnack(midnightSnackField.isSelected());
            }
        });

        midnightSnackPanel.setLayout(new BoxLayout(midnightSnackPanel, BoxLayout.X_AXIS));
        midnightSnackPanel.setPreferredSize(new Dimension(300, 30));
        midnightSnackPanel.setMaximumSize(new Dimension(300, 30));
        midnightSnackPanel.add(midnightSnackLabel);
        midnightSnackPanel.add(midnightSnackField);

        CheckBoxPanel.add(midnightSnackPanel);

        // softDrinks

        JPanel softDrinksPanel = new JPanel();
        JLabel softDrinksLabel = new JLabel("Soft Drinks");
        softDrinksLabel.setPreferredSize(labelDimension);
        JCheckBox softDrinksField = new JCheckBox();
        softDrinksField.setPreferredSize(fieldDimension);
        softDrinksField.setSelected(pansion.isSoftDrinks());
        softDrinksField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                pansion.setSoftDrinks(softDrinksField.isSelected());
            }
        });

        softDrinksPanel.setLayout(new BoxLayout(softDrinksPanel, BoxLayout.X_AXIS));
        softDrinksPanel.setPreferredSize(new Dimension(300, 30));
        softDrinksPanel.setMaximumSize(new Dimension(300, 30));
        softDrinksPanel.add(softDrinksLabel);
        softDrinksPanel.add(softDrinksField);

        CheckBoxPanel.add(softDrinksPanel);

        // alcoholicDrinks

        JPanel alcoholicDrinksPanel = new JPanel();
        JLabel alcoholicDrinksLabel = new JLabel("Alcoholic Drinks");
        alcoholicDrinksLabel.setPreferredSize(labelDimension);
        JCheckBox alcoholicDrinksField = new JCheckBox();
        alcoholicDrinksField.setPreferredSize(fieldDimension);
        alcoholicDrinksField.setSelected(pansion.isAlcoholicDrinks());
        alcoholicDrinksField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                pansion.setAlcoholicDrinks(alcoholicDrinksField.isSelected());
            }
        });

        alcoholicDrinksPanel.setLayout(new BoxLayout(alcoholicDrinksPanel, BoxLayout.X_AXIS));
        alcoholicDrinksPanel.setPreferredSize(new Dimension(300, 30));
        alcoholicDrinksPanel.setMaximumSize(new Dimension(300, 30));
        alcoholicDrinksPanel.add(alcoholicDrinksLabel);
        alcoholicDrinksPanel.add(alcoholicDrinksField);

        CheckBoxPanel.add(alcoholicDrinksPanel);

        // snacks

        JPanel snacksPanel = new JPanel();
        JLabel snacksLabel = new JLabel("Snacks");
        snacksLabel.setPreferredSize(labelDimension);
        JCheckBox snacksField = new JCheckBox();
        snacksField.setPreferredSize(fieldDimension);
        snacksField.setSelected(pansion.isSnacks());
        snacksField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                pansion.setSnacks(snacksField.isSelected());
            }
        });

        snacksPanel.setLayout(new BoxLayout(snacksPanel, BoxLayout.X_AXIS));
        snacksPanel.setPreferredSize(new Dimension(300, 30));
        snacksPanel.setMaximumSize(new Dimension(300, 30));

        snacksPanel.add(snacksLabel);
        snacksPanel.add(snacksField);

        CheckBoxPanel.add(snacksPanel);

        // end checkboxs

        mainPanel.add(CheckBoxPanel);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        JButton saveButton = new JButton("Save");
        JButton exitButton = new JButton("Exit");

        buttonsPanel.add(saveButton);
        buttonsPanel.add(exitButton);

        saveButton.addActionListener(new java.awt.event.ActionListener() {

            //check input

            public void actionPerformed(ActionEvent evt) {

                pansion.setName((String) nameField.getSelectedItem());

                if (nameField.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(null, "Name is required");
                    return;
                }

                if (hotelIdField.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(null, "Hotel is required");
                    return;
                }

                if (pansion.getHotelId() == 0) {
                    JOptionPane.showMessageDialog(null, "Hotel is required");
                    return;
                }

                if (pansion.getId() == 0) {
                    pansionController.create(pansion);
                } else {
                    pansionController.update(pansion);
                }
                frame.dispose();
            }
        });

        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                frame.dispose();
            }
        });


        mainPanel.add(buttonsPanel);

        frame.add(mainPanel);
    }


    public static void main(String[] args) {
        DetailsView viewPansion = new DetailsView();
    }
}
