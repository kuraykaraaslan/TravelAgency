package org.agency.views.season;

import com.github.lgooddatepicker.components.DatePicker;
import org.agency.controllers.HotelController;
import org.agency.controllers.SeasonController;
import org.agency.entities.Pansion;
import org.agency.entities.Hotel;
import org.agency.entities.Season;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class DetailsView {

    public JFrame frame = new JFrame("Season");
    public Season season;

    public Dimension labelDimension = new Dimension(100, 30);
    public Dimension fieldDimension = new Dimension(200, 30);

    private HotelController hotelController = new HotelController();
    private SeasonController seasonController = new SeasonController();

    public DetailsView(Season season) {
        this.season = season;

        if (season.getId() != 0) {
            this.frame.setTitle("Season #" + season.getId());
        } else {
            this.frame.setTitle("New Season");
        }

        configureFrame(this.frame);
        this.frame.setVisible(true);
    }

    public DetailsView() {
        DetailsView viewSeason = new DetailsView(new Season());
    }

    private void configureFrame(JFrame frame) {
        this.frame.setSize(400, 230);
        frame.setIconImage(new ImageIcon("src/main/resources/icon.png").getImage());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Panels
        mainPanel.add(namePanel());
        mainPanel.add(startDatePanel());
        mainPanel.add(endDatePanel());
        //mainPanel.add(hotelIdPanel());

        // Buttons Panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.setMaximumSize(new Dimension(400, 50));
        buttonsPanel.setPreferredSize(new Dimension(400, 50));
        buttonsPanel.setMinimumSize(new Dimension(400, 50));

        JButton saveButton = new JButton("Save");
        JButton deleteButton = new JButton("Delete");
        saveButton.setPreferredSize(new Dimension(100, 30));
        deleteButton.setPreferredSize(new Dimension(100, 30));

        buttonsPanel.add(saveButton);
        buttonsPanel.add(deleteButton);

        mainPanel.add(buttonsPanel);

        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                // check inputs
                if (season.getName().equals("")) {
                    JOptionPane.showMessageDialog(null, "Name is required.");
                    return;
                }

                if (season.getStartDateLocalDate() == null) {
                    JOptionPane.showMessageDialog(null, "Start Date is required.");
                    return;
                }

                if (season.getEndDateLocalDate() == null) {
                    JOptionPane.showMessageDialog(null, "End Date is required.");
                    return;
                }

                if (season.getHotelId() == 0) {
                    JOptionPane.showMessageDialog(null, "Hotel is required.");
                    return;
                }

                if (season.getStartDateLocalDate().isAfter(season.getEndDateLocalDate())) {
                    JOptionPane.showMessageDialog(null, "Start Date must be before End Date.");
                    return;
                }
                if (season.getId() != 0) {
                    seasonController.update(season);
                } else {
                    seasonController.create(season);
                }
                frame.dispose();
            }
        });

        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // CONFIRMATION
                JOptionPane confirmDialog = new JOptionPane();
                int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this season?",
                        "Warning", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    seasonController.delete(season);
                    frame.dispose();
                }
            }
        });

        frame.add(mainPanel);
    }

    // Hotel ID Panel
    private JPanel hotelIdPanel() {
        JPanel hotelIdPanel = new JPanel();
        hotelIdPanel.setLayout(new FlowLayout());

        JLabel hotelIdLabel = new JLabel("Hotel Name");
        hotelIdLabel.setPreferredSize(new Dimension(100, 30));

        List<Hotel> hotels = hotelController.getAll();

        JComboBox<Hotel> hotelIdField = new JComboBox<Hotel>();
        hotelIdField.setPreferredSize(new Dimension(200, 30));

        for (Hotel hotel : hotels) {
            hotelIdField.addItem(hotel);
        }

        System.out.println(season);
        hotelIdField.setRenderer(new BasicComboBoxRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value != null) {
                    Hotel item = (Hotel) value;
                    setText(item.getName());
                }
                if (index == -1) {
                    Hotel item = (Hotel) value;
                    setText("" + item.getName());
                }
                return this;
            }
        });

        hotelIdField.setSelectedItem((Hotel) season.getHotel());

        hotelIdField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                season.setHotelId(((Hotel) hotelIdField.getSelectedItem()).getId());
            }
        });

        if (season.getHotelId() != 0) {
            hotelIdField.setSelectedItem(hotelController.getById(season.getHotelId()));
        }

        hotelIdPanel.add(hotelIdLabel);
        hotelIdPanel.add(hotelIdField);

        return hotelIdPanel;
    }

    // name panel
    private JPanel namePanel() {
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new FlowLayout());
        JLabel nameLabel = new JLabel("Name");
        nameLabel.setPreferredSize(labelDimension);

        JTextField nameField = new JTextField(season.getName());
        nameField.setPreferredSize(fieldDimension);

        nameField.setText(season.getName());

        nameField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent evt) {
                season.setName(nameField.getText());
            }

            public void removeUpdate(javax.swing.event.DocumentEvent evt) {
                season.setName(nameField.getText());
            }

            public void insertUpdate(javax.swing.event.DocumentEvent evt) {
                season.setName(nameField.getText());
            }
        });

        namePanel.add(nameLabel);
        namePanel.add(nameField);

        return namePanel;
    }

    private JPanel startDatePanel() {
        JPanel startDatePanel = new JPanel();
        startDatePanel.setLayout(new FlowLayout());
        JLabel startDateLabel = new JLabel("Start Date");
        startDateLabel.setPreferredSize(labelDimension);

        DatePicker startDateField = new DatePicker();
        startDateField.setDate(season.getStartDateLocalDate());
        startDateField.setPreferredSize(fieldDimension);

        startDateField.addDateChangeListener((dce) -> {
            LocalDate startDate = startDateField.getDate();
            season.setStartDateLocalDate(startDate);
            System.out.println(season.getStartDateLocalDate());
        });

        startDatePanel.add(startDateLabel);
        startDatePanel.add(startDateField);

        return startDatePanel;
    }

    private JPanel endDatePanel() {
        JPanel endDatePanel = new JPanel();
        endDatePanel.setLayout(new FlowLayout());
        JLabel endDateLabel = new JLabel("End Date");
        endDateLabel.setPreferredSize(labelDimension);

        DatePicker endDateField = new DatePicker();
        endDateField.setDate(season.getEndDateLocalDate());
        endDateField.setPreferredSize(fieldDimension);

        endDateField.addDateChangeListener((dce) -> {
            LocalDate endDate = endDateField.getDate();
            season.setEndDateLocalDate(endDate);
            System.out.println(season.getEndDateLocalDate());
        });

        endDatePanel.add(endDateLabel);
        endDatePanel.add(endDateField);

        return endDatePanel;
    }

    private String getFormattedDate(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DetailsView::new);
    }
}
