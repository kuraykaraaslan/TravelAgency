package org.agency.views.auth;

import org.agency.Main;
import org.agency.core.Session;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

// Import statements...

public class LoginView {

    // UI components
    private JPanel mainPanel;
    private JFrame frame;
    private JLabel logoLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginView() {
        // Create a new JFrame for the login view
        frame = new JFrame("Travel Agency Login");
        frame.setSize(450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setAlwaysOnTop(true);
        frame.setIconImage(loadImageIcon("icon.png").getImage());

        // Create the main panel and add it to the frame
        mainPanel = new JPanel();
        frame.add(mainPanel);

        // Setup and configure the GUI components
        setupGUI();

        // Make the frame visible
        frame.setVisible(true);
    }

    public LoginView(String username, String password) {
        this();
        usernameField.setText(username);
        passwordField.setText(password);
        loginButton.doClick();
    }

    private void setupGUI() {
        // Set the layout and background color of the main panel
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Create and add the logo panel to the main panel
        JPanel logoPanel = createLogoPanel();
        mainPanel.add(logoPanel, BorderLayout.NORTH);

        // Create and add the login panel to the main panel
        JPanel loginPanel = createLoginPanel();
        mainPanel.add(loginPanel, BorderLayout.CENTER);

        // Create and add the footer panel to the main panel
        JPanel footerPanel = createFooterPanel();
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
    }

    private JPanel createLogoPanel() {
        // Create a panel for the logo
        JPanel logoPanel = new JPanel();
        logoLabel = new JLabel(loadImageIcon("logo.jpg"));
        logoPanel.add(logoLabel);
        logoPanel.setBackground(Color.WHITE);
        return logoPanel;
    }

    private JPanel createLoginPanel() {
        // Create a panel for login inputs
        JPanel loginPanel = new JPanel(new GridLayout(4, 1));
        loginPanel.setBackground(Color.WHITE);

        // Create input panels for username and password fields
        JPanel usernamePanel = createInputPanel("Username", usernameField = new JTextField(20));
        JPanel passwordPanel = createInputPanel("Password", passwordField = new JPasswordField(20));
        JPanel loginButtonPanel = createLoginButtonPanel();

        // Add input panels to the login panel
        loginPanel.add(usernamePanel);
        loginPanel.add(passwordPanel);
        loginPanel.add(loginButtonPanel);

        return loginPanel;
    }

    private JPanel createInputPanel(String label, JComponent inputComponent) {
        // Create a panel for a single login input (label and input field)
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel labelComponent = new JLabel(label);
        panel.add(labelComponent);
        panel.add(inputComponent);
        panel.setBackground(Color.WHITE);
        return panel;
    }

    private JPanel createLoginButtonPanel() {
        // Create a panel for the login button
        JPanel panel = new JPanel();
        loginButton = new JButton("Login");
        loginButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLoginButtonClick();
            }
        });
        panel.add(loginButton);
        panel.setBackground(Color.WHITE);
        return panel;
    }

    private JPanel createFooterPanel() {
        // Create a panel for the footer text
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(Color.WHITE);
        JLabel footerLabel = new JLabel("Travel Agency 2024 - kuray.dev");
        footerPanel.add(footerLabel);
        return footerPanel;
    }

    private void handleLoginButtonClick() {
        // Get the entered username and password
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Get the session object from the Main class
        Session session = Main.getSession();

        // Attempt to login with the entered credentials
        if (session.login(username, password)) {
            // If login is successful, close the login view
            frame.dispose();
        } else {
            // If login fails, show an error message
            JOptionPane.showMessageDialog(frame, "Invalid username or password.", "Login failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private ImageIcon loadImageIcon(String fileName) {
        try {
            // Load and return an ImageIcon from the given file name
            return new ImageIcon(getClass().getResource("/" + fileName));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginView::new);
    }
}
