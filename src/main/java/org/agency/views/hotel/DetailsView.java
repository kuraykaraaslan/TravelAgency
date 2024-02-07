package org.agency.views.hotel;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.agency.controllers.HotelController;
import org.agency.entities.Hotel;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import org.agency.views.hotel.Partials.*;

public class DetailsView extends Component {

    private HotelController hotelController = new HotelController();
    private JPanel mainPanel;
    private static JTabbedPane tabbedPane;
    private Hotel hotel;

    private JPanel detailsPanel;
    private JPanel roomsPanel;
    private JPanel seasonsPanel;

    private JPanel pansionsPanel;

    private JPanel reservationsPanel;


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
        headerPanel.setPreferredSize(new Dimension(-1, 80)); // Use `setPreferredSize` instead of `setMaximumSize`
        headerPanel.add(createLogoPanel(), BorderLayout.WEST);
        headerPanel.add(createSummaryPanel(), BorderLayout.CENTER);
        // headerPanel.add(createSearchPanel(), BorderLayout.CENTER);
        // headerPanel.add(createButtonPanel(), BorderLayout.EAST);
        return headerPanel;
    }

    private JTabbedPane createTabbedPane() {
        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(Color.white);
        createDetailsPanel();
        createRoomsPanel();
        createSeasonsPanel();
        createReservationsPanel();
        createPansionsPanel();
        tabbedPane.addTab("Details", detailsPanel);
        if (hotel.getId() != 0) {
            tabbedPane.addTab("Rooms", roomsPanel);
            tabbedPane.addTab("Seasons", seasonsPanel);
            tabbedPane.addTab("Pansions", pansionsPanel);
            tabbedPane.addTab("Reservations", reservationsPanel);
        }
        return tabbedPane;
    }

    private void createDetailsPanel() {
        DetailsPartialView detailsPartialView = new DetailsPartialView(hotel, this::refresh, this::frameDispose);
        detailsPanel = detailsPartialView.getPanel();
    }

    private void createRoomsPanel() {
        RoomsPartialView roomsPartialView = new RoomsPartialView(hotel, this::refresh, this::frameDispose);
        roomsPanel = roomsPartialView.render();
    }

    private void createSeasonsPanel() {
        SeasonsPartialView seasonsPartialView = new SeasonsPartialView(hotel, this::refresh, this::frameDispose);
        seasonsPanel = seasonsPartialView.render();
    }

    private void createReservationsPanel() {
        ReservationsPartialView reservationsPartialView = new ReservationsPartialView(hotel, this::refresh,
                this::frameDispose);
        reservationsPanel = reservationsPartialView.render();
    }

    private void createPansionsPanel() {
        PansionsPartialView pansionsPartialView = new PansionsPartialView(hotel, this::refresh, this::frameDispose);
        pansionsPanel = pansionsPartialView.render();
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
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 0));
        // add a label with the name
        JLabel nameLabel = new JLabel(hotel.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        // add a label with the address
        JLabel addressLabel = new JLabel(hotel.getAddressFull());
        addressLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        // add a label with the phone
        JLabel phoneLabel = new JLabel(hotel.getPhone());
        phoneLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        summaryPanel.add(nameLabel);
        summaryPanel.add(addressLabel);
        summaryPanel.add(phoneLabel);
        return summaryPanel;
    }

    public DetailsView() {
        hotel = new Hotel();
        JFrame frame = new JFrame("Hotel:");
        configureFrame(frame);
        mainPanel = new JPanel();
        placeComponents(mainPanel);
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    public DetailsView(int hotelId) {
        hotel = hotelController.getById(hotelId);
        JFrame frame = new JFrame("Hotel: " + hotel.getName());
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

    public void refresh() {
        // get hotel from db
        hotel = hotelController.getById(hotel.getId());
        // refresh the view
        mainPanel.removeAll();
        placeComponents(mainPanel);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void frameDispose() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
        frame.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DetailsView::new);
    }
}
