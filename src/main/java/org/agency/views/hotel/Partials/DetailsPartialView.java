package org.agency.views.hotel.Partials;

import org.agency.controllers.HotelController;
import org.agency.entities.Hotel;

import javax.swing.*;
import java.awt.*;

public class DetailsPartialView {

    private final Runnable RefreshParent;
    private final Runnable FrameDispose;
    private Hotel hotel;
    private HotelController hotelController = new HotelController();

    public DetailsPartialView(Hotel hotel, Runnable refreshParent, Runnable frameDispose) {
        this.hotel = hotel;
        this.RefreshParent = refreshParent;
        this.FrameDispose = frameDispose;
    }

    public JPanel getPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(13, 2));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new GridLayout(1, 2));
        JLabel nameLabel = new JLabel("Name: ");
        nameLabel.setPreferredSize(new Dimension(100, 20));
        JTextField nameField = new JTextField(hotel.getName());
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
                hotel.setName(nameField.getText());
            }
        });

        namePanel.add(nameLabel);
        namePanel.add(nameField);
        panel.add(namePanel);

        JPanel addressPanel = new JPanel();
        addressPanel.setLayout(new GridLayout(1, 2));
        JLabel addressLabel = new JLabel("Address: ");
        addressLabel.setPreferredSize(new Dimension(100, 20));
        JTextField addressField = new JTextField(hotel.getAddressFull());
        addressField.setPreferredSize(new Dimension(200, 20));
        addressField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
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
                hotel.setAddressFull(addressField.getText());
            }
        });
        addressPanel.add(addressLabel);
        addressPanel.add(addressField);
        panel.add(addressPanel);

        JPanel phonePanel = new JPanel();
        phonePanel.setLayout(new GridLayout(1, 2));
        JLabel phoneLabel = new JLabel("Phone: ");
        phoneLabel.setPreferredSize(new Dimension(100, 20));
        JTextField phoneField = new JTextField(hotel.getPhone());
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
                hotel.setPhone(phoneField.getText());
            }
        });
        phonePanel.add(phoneLabel);
        phonePanel.add(phoneField);
        panel.add(phonePanel);

        JPanel websitePanel = new JPanel();
        websitePanel.setLayout(new GridLayout(1, 2));
        JLabel websiteLabel = new JLabel("Website: ");
        websiteLabel.setPreferredSize(new Dimension(100, 20));
        JTextField websiteField = new JTextField(hotel.getWebsite());
        websiteField.setPreferredSize(new Dimension(200, 20));
        websiteField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
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
                hotel.setWebsite(websiteField.getText());
            }
        });
        websitePanel.add(websiteLabel);
        websitePanel.add(websiteField);
        panel.add(websitePanel);

        JPanel emailPanel = new JPanel();
        emailPanel.setLayout(new GridLayout(1, 2));
        JLabel emailLabel = new JLabel("Email: ");
        emailLabel.setPreferredSize(new Dimension(100, 20));
        JTextField emailField = new JTextField(hotel.getEmail());
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
                hotel.setEmail(emailField.getText());
            }
        });
        emailPanel.add(emailLabel);
        emailPanel.add(emailField);
        panel.add(emailPanel);

        //Radio buttons for star rating
        JPanel starRatingPanel = new JPanel();
        starRatingPanel.setLayout(new GridLayout(1, 2));
        JLabel starRatingLabel = new JLabel("Star Rating: ");
        starRatingLabel.setPreferredSize(new Dimension(100, 20));
        ButtonGroup starRatingGroup = new ButtonGroup();
        starRatingPanel.add(starRatingLabel);
        for (int i = 1; i <= 5; i++) {
            JRadioButton starRating = new JRadioButton(Integer.toString(i));
            starRating.addActionListener(e -> {
                hotel.setStarRating(Integer.parseInt(starRating.getText()));
                for (Component component : starRatingPanel.getComponents()) {
                    if (component instanceof JRadioButton && !component.equals(starRating)) {
                        ((JRadioButton) component).setSelected(false);
                    }
                }
            });
            starRatingGroup.add(starRating);
            starRatingPanel.add(starRating);
        }

        panel.add(createCheckBoxPanel("Has Car Park: ", hotel.isHasCarPark()));
        panel.add(createCheckBoxPanel("Has Internet: ", hotel.isHasInternet()));
        panel.add(createCheckBoxPanel("Has Pool: ", hotel.isHasPool()));
        panel.add(createCheckBoxPanel("Has Concierge: ", hotel.isHasConcierge()));
        panel.add(createCheckBoxPanel("Has Spa: ", hotel.isHasSpa()));
        panel.add(createCheckBoxPanel("Has Room Service: ", hotel.isHasRoomService()));

        panel.add(starRatingPanel);


        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            hotelController.update(hotel);
            JOptionPane.showMessageDialog(null, "Hotel updated successfully!");
            RefreshParent.run();

        });

        //delete button
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> {
            // Confirm deletion
            int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this hotel?", "Warning", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                hotelController.delete(hotel);
                JOptionPane.showMessageDialog(null, "Hotel deleted successfully!");
                FrameDispose.run();

            } else {
                JOptionPane.showMessageDialog(null, "Hotel not deleted!");
                RefreshParent.run();
            }
        });

        buttonPanel.add(saveButton);
        buttonPanel.add(deleteButton);

        panel.add(buttonPanel);

        return panel;
    }

    private JPanel createFieldPanel(String label, String value, String field) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        JLabel fieldLabel = new JLabel(label);
        fieldLabel.setPreferredSize(new Dimension(100, 20));
        JTextField textField = new JTextField(value);
        textField.setPreferredSize(new Dimension(200, 20));
        textField.addActionListener(e -> {
            if (field.equals("name")) {
                hotel.setName(textField.getText());
            } else if (field.equals("address")) {
                hotel.setAddressFull(textField.getText());
            } else if (field.equals("phone")) {
                hotel.setPhone(textField.getText());
            } else if (field.equals("website")) {
                hotel.setWebsite(textField.getText());
            } else if (field.equals("email")) {
                hotel.setEmail(textField.getText());
            }
        });
        panel.add(fieldLabel);
        panel.add(textField);
        return panel;
    }

    private JPanel createStarRatingPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        JLabel starRatingLabel = new JLabel("Star Rating: ");
        starRatingLabel.setPreferredSize(new Dimension(100, 20));
        ButtonGroup starRatingGroup = new ButtonGroup();
        panel.add(starRatingLabel);
        for (int i = 1; i <= 5; i++) {
            JCheckBox starRating = new JCheckBox(Integer.toString(i));
            starRating.addActionListener(e -> {
                hotel.setStarRating(Integer.parseInt(starRating.getText()));
                for (Component component : panel.getComponents()) {
                    if (component instanceof JCheckBox && !component.equals(starRating)) {
                        ((JCheckBox) component).setSelected(false);
                    }
                }
            });
            starRatingGroup.add(starRating);
            panel.add(starRating);
        }
        return panel;
    }

    private JPanel createCheckBoxPanel(String label, boolean selected) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        JLabel checkBoxLabel = new JLabel(label);
        JCheckBox checkBox = new JCheckBox();
        checkBox.setSelected(selected);
        checkBox.addActionListener(e -> {
            if (label.equals("Has Car Park: ")) {
                hotel.setHasCarPark(checkBox.isSelected());
            } else if (label.equals("Has Internet: ")) {
                hotel.setHasInternet(checkBox.isSelected());
            } else if (label.equals("Has Pool: ")) {
                hotel.setHasPool(checkBox.isSelected());
            } else if (label.equals("Has Concierge: ")) {
                hotel.setHasConcierge(checkBox.isSelected());
            } else if (label.equals("Has Spa: ")) {
                hotel.setHasSpa(checkBox.isSelected());
            } else if (label.equals("Has Room Service: ")) {
                hotel.setHasRoomService(checkBox.isSelected());
            }
        });
        panel.add(checkBoxLabel);
        panel.add(checkBox);
        return panel;
    }
}