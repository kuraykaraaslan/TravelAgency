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

// Import statements...

public class LoginView {

    private JPanel mainPanel;
    private JFrame frame;

    private JLabel logoLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginView() {

        frame = new JFrame("Travel Agency Login");
        frame.setSize(450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setAlwaysOnTop(true);
        frame.setIconImage(loadImageIcon("icon.png").getImage());

        mainPanel = new JPanel();
        frame.add(mainPanel);
        setupGUI();

        frame.setVisible(true);
    }

    public LoginView(String username, String password) {
        this();
        usernameField.setText(username);
        passwordField.setText(password);
        loginButton.doClick();
    }
    private void setupGUI() {
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        JPanel logoPanel = createLogoPanel();
        mainPanel.add(logoPanel, BorderLayout.NORTH);

        JPanel loginPanel = createLoginPanel();
        mainPanel.add(loginPanel, BorderLayout.CENTER);

        JPanel footerPanel = createFooterPanel();
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

    }

    private JPanel createLogoPanel() {
        JPanel logoPanel = new JPanel();
        logoLabel = new JLabel(loadImageIcon("logo.jpg"));
        logoPanel.add(logoLabel);
        logoPanel.setBackground(Color.WHITE);
        return logoPanel;
    }

    private JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel(new GridLayout(4, 1));
        loginPanel.setBackground(Color.WHITE);

        JPanel usernamePanel = createInputPanel("Username", usernameField = new JTextField(20));
        JPanel passwordPanel = createInputPanel("Password", passwordField = new JPasswordField(20));
        JPanel loginButtonPanel = createLoginButtonPanel();

        loginPanel.add(usernamePanel);
        loginPanel.add(passwordPanel);
        loginPanel.add(loginButtonPanel);

        return loginPanel;
    }

    private JPanel createInputPanel(String label, JComponent inputComponent) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel labelComponent = new JLabel(label);
        panel.add(labelComponent);
        panel.add(inputComponent);
        panel.setBackground(Color.WHITE);
        return panel;
    }

    private JPanel createLoginButtonPanel() {
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
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(Color.WHITE);
        JLabel footerLabel = new JLabel("Travel Agency 2024 - kuray.dev");
        footerPanel.add(footerLabel);
        return footerPanel;
    }

    private void handleLoginButtonClick() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        Session session = Main.getSession();
        if (session.login(username, password)) {
            frame.dispose();
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid username or password.", "Login failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private ImageIcon loadImageIcon(String fileName) {
        try {
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
