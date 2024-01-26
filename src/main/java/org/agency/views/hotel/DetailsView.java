package org.agency.views.hotel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.agency.entities.Hotel;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class DetailsView extends Component {
    private JPanel mainPanel;
    private DefaultTableModel model;
    private static JTabbedPane tabbedPane;

    private Hotel hotel;

    private void configureFrame(JFrame frame) {
        frame.setSize(getHalfScreenSize());
        frame.setBackground(Color.white);
        frame.setMinimumSize(new Dimension(1000, 600));
        frame.setIconImage(new ImageIcon("src/main/resources/icon.png").getImage());
        frame.setLocationRelativeTo(null);
    }

    private Dimension getHalfScreenSize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return new Dimension(screenSize.width / 2, screenSize.height / 2);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.white);
        headerPanel.setPreferredSize(new Dimension(-1, 80));  // Use `setPreferredSize` instead of `setMaximumSize`
        headerPanel.add(createLogoPanel(), BorderLayout.WEST);
        headerPanel.add(createSummaryPanel(), BorderLayout.CENTER);
        //headerPanel.add(createSearchPanel(), BorderLayout.CENTER);
        //headerPanel.add(createButtonPanel(), BorderLayout.EAST);
        return headerPanel;
    }


    private JTabbedPane createTabbedPane() {
        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(Color.white);
        tabbedPane.addTab("Details", createDetailsPanel());
        tabbedPane.addTab("Rooms", createRoomsPanel());
        tabbedPane.addTab("Seasons", createSeasonsPanel());

        return tabbedPane;
    }

    private JPanel createDetailsPanel() {
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridLayout(0, 2));

        // Name
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.LINE_AXIS));
        JLabel nameLabel = new JLabel("Name");
        nameLabel.setPreferredSize(new Dimension(100, 20));
        JTextField nameField = new JTextField("Hotel Name");

        namePanel.add(nameLabel);
        namePanel.add(nameField);

        // Address
        JPanel addressFullPanel = new JPanel();
        addressFullPanel.setLayout(new BoxLayout(addressFullPanel, BoxLayout.LINE_AXIS));
        JLabel addressFullLabel = new JLabel("Full");
        addressFullLabel.setPreferredSize(new Dimension(100, 20));
        JTextField addressFullField = new JTextField("Hotel Address Full");

        addressFullPanel.add(addressFullLabel);
        addressFullPanel.add(addressFullField);

        JPanel addressDistrictPanel = new JPanel();
        addressDistrictPanel.setLayout(new FlowLayout());
        JLabel addressDistrictLabel = new JLabel("District");
        addressDistrictLabel.setPreferredSize(new Dimension(100, 20));
        JTextField addressDistrictField = new JTextField("Hotel Address District");
        addressDistrictPanel.add(addressDistrictLabel);
        addressDistrictPanel.add(addressDistrictField);

        JPanel addressCityPanel = new JPanel();
        addressCityPanel.setLayout(new GridLayout(0, 2));
        addressCityPanel.setPreferredSize(new Dimension(200, 50)); // Set your preferred width and height

        JLabel addressCityLabel = new JLabel("City");
        addressCityLabel.setPreferredSize(new Dimension(100, 20));

        JTextField addressCityField = new JTextField("Hotel Address City");
        addressCityField.setPreferredSize(new Dimension(100, 20));

        addressCityPanel.add(addressCityLabel);
        addressCityPanel.add(addressCityField);

        detailsPanel.add(namePanel);
        detailsPanel.add(addressFullPanel);
        detailsPanel.add(addressDistrictPanel);
        detailsPanel.add(addressCityPanel);

        return detailsPanel;
    }

    private JPanel createRoomsPanel() {
        JPanel roomsPanel = new JPanel();
        roomsPanel.setLayout(new GridLayout(0, 2));


        return roomsPanel;
    }

    private JPanel createSeasonsPanel() {
        JPanel seasonsPanel = new JPanel();
        seasonsPanel.setLayout(new GridLayout(0, 2));

        JLabel nameLabel = new JLabel("Name");
        JTextField nameField = new JTextField("Hotel Name");
        nameField.setEditable(false);
        seasonsPanel.add(nameLabel);
        seasonsPanel.add(nameField);

        return seasonsPanel;
    }

    private JPanel createLogoPanel() {
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(Color.white);
        JLabel logoLabel = new JLabel();
        try {
            File imageFile = new File("src/main/resources/logo.jpg");
            Image originalImage = ImageIO.read(imageFile);
            int scaledWidth = 200;
            int scaledHeight = 50;
            double aspectRatio = (double) originalImage.getWidth(null) / originalImage.getHeight(null);
            scaledHeight = (int) (scaledWidth / aspectRatio);
            Image scaledImage = originalImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
            ImageIcon logoIcon = new ImageIcon(scaledImage);
            logoLabel.setIcon(logoIcon);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logoPanel.add(logoLabel);
        return logoPanel;
    }

    private JPanel createSummaryPanel() {
        JPanel summaryPanel = new JPanel();
        summaryPanel.setBackground(Color.white);
        summaryPanel.setLayout(new BoxLayout(summaryPanel, BoxLayout.Y_AXIS));
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        JLabel nameLabel = new JLabel("Name:");
        JLabel nameField = new JLabel("Hotel Name");
        nameField.setFont(new Font("Serif", Font.PLAIN, 10));
        summaryPanel.add(nameLabel);
        summaryPanel.add(nameField);

        JLabel addressLabel = new JLabel("Address");
        JLabel addressField = new JLabel("Hotel Address");
        addressField.setFont(new Font("Serif", Font.PLAIN, 10));
        summaryPanel.add(addressLabel);
        summaryPanel.add(addressField);


        return summaryPanel;
    }

    public DetailsView() {
        JFrame frame = new JFrame("Hotel:");
        configureFrame(frame);
        mainPanel = new JPanel();
        placeComponents(mainPanel);
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    public DetailsView(Hotel hotel) {
        JFrame frame = new JFrame("Hotels");
        configureFrame(frame);
        mainPanel = new JPanel();
        placeComponents(mainPanel);
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void placeComponents(JPanel mainPanel) {
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        mainPanel.add(createTabbedPane(), BorderLayout.CENTER);
    }

    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DetailsView::new);
    }
}
