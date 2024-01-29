package org.agency.views.user;

import org.agency.Main;
import org.agency.controllers.UserController;
import org.agency.entities.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DetailsView extends Component {
    private static final int FRAME_WIDTH = 400;
    private static final int FRAME_HEIGHT = 300;
    private static final Dimension LABEL_DIMENSION = new Dimension(100, 30);
    private static final Dimension FIELD_DIMENSION = new Dimension(200, 30);
    private JFrame frame = new JFrame("User");
    private User user;
    private UserController userController = Main.getUserController();

    private String mode;

    private JButton exitButton;
    private JButton saveButton;

    public DetailsView(User model) {
        this.user = model;

        frame.setTitle(user.getId() == 0 ? "New User" : "User: " + user.getName());

        mode = user.getId() == 0 ? "new" : "edit";

        configureFrame(frame);
        frame.setVisible(true);
    }

    public DetailsView() {
        this(new User());
    }

    private void configureFrame(JFrame frame) {
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setIconImage(new ImageIcon("src/main/resources/icon.png").getImage());
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 1));

        mainPanel.add(namePanel());
        mainPanel.add(usernamePanel());
        mainPanel.add(passwordPanel());
        mainPanel.add(emailPanel());
        mainPanel.add(rolePanel());

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());

        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSave(e);
            }
        });

        saveButton.setText(mode.equals("new") ? "Create" : "Save");
        saveButton.setPreferredSize(new Dimension(100, 30));
        buttonsPanel.add(saveButton);

        exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleExit();
            }
        });

        exitButton.setText("Exit");
        exitButton.setPreferredSize(new Dimension(100, 30));
        buttonsPanel.add(exitButton);

        mainPanel.add(buttonsPanel);
        frame.add(mainPanel);
    }

    private void handleSave(Object e) {
        if (mode.equals("new")) {
            userController.registerUser(user);
            // SHOW MESSAGE
            JOptionPane.showMessageDialog(null, "User created successfully!");
        } else if (mode.equals("edit")) {
            userController.update(user);
            // SHOW MESSAGE
            JOptionPane.showMessageDialog(this, "User updated successfully!");
        }
    }

    private void handleExit() {
        frame.dispose();
    }

    private JPanel namePanel() {
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new FlowLayout());
        JLabel nameLabel = new JLabel("Name");
        nameLabel.setPreferredSize(LABEL_DIMENSION);
        JTextField nameField = new JTextField(user.getName(), 20);
        nameField.setPreferredSize(FIELD_DIMENSION);

        if (user.getName() != null) {
            nameField.setText(user.getName());
        }

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
                user.setName(nameField.getText());
                System.out.println(user.getName());
            }
        });

        namePanel.add(nameLabel);
        namePanel.add(nameField);

        return namePanel;
    }

    private JPanel usernamePanel() {
        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new FlowLayout());
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setPreferredSize(LABEL_DIMENSION);
        JTextField usernameField = new JTextField(user.getUsername(), 20);
        usernameField.setPreferredSize(FIELD_DIMENSION);

        if (user.getUsername() != null) {
            usernameField.setText(user.getUsername());
        }
        usernameField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
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
                user.setUsername(usernameField.getText());
                System.out.println(user.getUsername());
            }
        });

        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);

        return usernamePanel;
    }

    private JPanel passwordPanel() {
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new FlowLayout());
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setPreferredSize(LABEL_DIMENSION);
        JPasswordField passwordField = new JPasswordField(user.getPassword(), 20);
        passwordField.setPreferredSize(FIELD_DIMENSION);

        if (user.getPassword() != null) {
            passwordField.setText(user.getPassword());
        }

        passwordField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
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
                user.setPassword(String.valueOf(passwordField.getPassword()));
                System.out.println(user.getPassword());
            }
        });

        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        return passwordPanel;
    }

    private JPanel emailPanel() {
        JPanel emailPanel = new JPanel();
        emailPanel.setLayout(new FlowLayout());
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setPreferredSize(LABEL_DIMENSION);
        JTextField emailField = new JTextField(user.getEmail(), 20);
        emailField.setPreferredSize(FIELD_DIMENSION);

        if (user.getEmail() != null) {
            emailField.setText(user.getEmail());
        }

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
                user.setEmail(emailField.getText());
                System.out.println(user.getEmail());
            }
        });

        emailPanel.add(emailLabel);
        emailPanel.add(emailField);

        return emailPanel;
    }

    private JPanel rolePanel() {
        JPanel rolePanel = new JPanel();
        rolePanel.setLayout(new FlowLayout());
        JLabel roleLabel = new JLabel("Role");
        roleLabel.setPreferredSize(LABEL_DIMENSION);
        JComboBox<String> roleComboBox = new JComboBox<>(new String[] { "AGENT", "ADMIN" });
        roleComboBox.setPreferredSize(FIELD_DIMENSION);

        if (user.getRole() != null) {
            roleComboBox.setSelectedItem(user.getRole());
        } else {
            roleComboBox.setSelectedItem("AGENT");
            user.setRole("AGENT");
        }

        roleComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                user.setRole((String) roleComboBox.getSelectedItem());
            }
        });

        rolePanel.add(roleLabel);
        rolePanel.add(roleComboBox);

        return rolePanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DetailsView::new);
    }

}
