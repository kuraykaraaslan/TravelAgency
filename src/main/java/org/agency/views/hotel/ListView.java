package org.agency.views.hotel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.agency.controllers.HotelController;
import org.agency.core.PaginatedResult;
import org.agency.entities.Hotel;

public class ListView {
    private HotelController hotelController;
    private JPanel mainPanel;
    private DefaultTableModel model;
    private static JTable table;
    private JButton createButton;
    private JButton deleteButton;
    private JButton detailsButton;
    private JButton backButton;
    private JButton nextButton;
    private int currentPage = 1;
    private int totalPages = 1;
    private int totalItems = 0;
    private int itemsPerPage = 10;
    private String searchTerm = "";
    private String paginationText = "Page %d of %d";
    private JLabel pageNumberLabel;

    public ListView() {
        hotelController = new HotelController();
        JFrame frame = configureFrame();
        mainPanel = new JPanel();
        placeComponents(mainPanel);
        frame.add(mainPanel);
    }

    private JFrame configureFrame() {
        JFrame frame = new JFrame("Hotels");
        frame.setSize(getHalfScreenSize());
        frame.setMinimumSize(new Dimension(1000, 600));
        frame.setIconImage(new ImageIcon("src/main/resources/icon.png").getImage());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        return frame;
    }

    private Dimension getHalfScreenSize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return new Dimension(screenSize.width / 2, screenSize.height / 2);
    }

    private void placeComponents(JPanel mainPanel) {
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        mainPanel.add(createTablePanel(), BorderLayout.CENTER);
        mainPanel.add(createFooterPanel(), BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.white);
        headerPanel.add(createLogoPanel(), BorderLayout.WEST);
        headerPanel.add(createSearchPanel(), BorderLayout.CENTER);
        headerPanel.add(createButtonPanel(), BorderLayout.EAST);
        return headerPanel;
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

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.white);

        model = new DefaultTableModel(getTableData(), getColumnNames());
        table = new JTable(model);
        table.setRowHeight(30);
        table.setDefaultEditor(Object.class, null);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleTableRowSelection();
                if (SwingUtilities.isRightMouseButton(e)) {
                    JPopupMenu menu = handleRightClick();
                    menu.show(table, e.getX(), e.getY());
                }
            }
        });
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane);
        return tablePanel;
    }

    private Object[][] getTableData() {
        return hotelController.paginate(currentPage, itemsPerPage, searchTerm).getData().stream()
                .map(hotel -> new Object[] {
                        hotel.getId(),
                        hotel.getName(),
                        hotel.getPhone(),
                        hotel.getWebsite(),
                        hotel.getStarRating(),
                        hotel.getAddressFull(),
                        hotel.getAddressCity(),
                        hotel.getAddressCountry()
                }).toArray(Object[][]::new);
    }

    private String[] getColumnNames() {
        return new String[] { "ID", "Name", "Phone", "Website", "Rating", "Address", "City", "Country" };
    }

    private void handleTableRowSelection() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            System.out.println("Selected Row: " + selectedRow);
        }
    }

    private void doSearch(String searchTerm) {
        currentPage = 1;
        this.searchTerm = searchTerm;
        try {
            PaginatedResult<Hotel> result = hotelController.paginate(currentPage, itemsPerPage, searchTerm);
            updateTableData((ArrayList<Hotel>) result.getData());
            totalItems = result.getTotal();
            totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);
            paginationButtonRefresh();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception appropriately
        }
    }

    public void refreshTable() {
        doSearch(searchTerm);
    }

    private void updateTableData(ArrayList<Hotel> newData) {
        Object[][] tableData = newData.stream().map(hotel -> new Object[] {
                hotel.getId(),
                hotel.getName(),
                hotel.getPhone(),
                hotel.getWebsite(),
                hotel.getStarRating(),
                hotel.getAddressFull(),
                hotel.getAddressCity(),
                hotel.getAddressCountry()
        }).toArray(Object[][]::new);

        model.setDataVector(tableData, getColumnNames());
        model.fireTableDataChanged();
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(Color.white);
        JTextField searchField = createSearchField();
        // JButton searchButton = createSearchButton(searchField);

        searchPanel.add(createSearchFieldPanel(searchField), BorderLayout.CENTER);
        // searchPanel.add(createSearchButtonPanel(searchButton), BorderLayout.EAST);
        return searchPanel;
    }

    private JTextField createSearchField() {
        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(300, 50));
        searchField.setMaximumSize(new Dimension(600, 50));
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setBackground(Color.getHSBColor(0, 0, 0.95f));

        // Add document listener for live search
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                doSearch(searchField.getText());
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                doSearch(searchField.getText());
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                doSearch(searchField.getText());
            }
        });

        return searchField;
    }

    private JButton createSearchButton(JTextField searchField) {
        JButton searchButton = new JButton("Refresh");
        searchButton.setPreferredSize(new Dimension(100, 50));
        searchButton.setFont(new Font("Arial", Font.PLAIN, 14));
        searchButton.addActionListener(e -> doSearch(searchField.getText()));
        return searchButton;
    }

    private JPanel createSearchFieldPanel(JTextField searchField) {
        JPanel searchFieldPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchFieldPanel.setBackground(Color.white);
        searchFieldPanel.add(searchField);

        JButton searchButton = createSearchButton(searchField);
        searchButton.setPreferredSize(new Dimension(100, 50));
        searchButton.setFont(new Font("Arial", Font.PLAIN, 14));
        searchButton.addActionListener(e -> doSearch(searchField.getText()));
        searchFieldPanel.add(searchButton);
        return searchFieldPanel;
    }

    private JPanel createButtonPanel() {
        createButton = createButton("Create", e -> handleCreateButtonClick());
        JPanel buttonPanel = createButtonPanel(createButton);
        return buttonPanel;
    }

    private JButton createButton(String label, ActionListener actionListener) {
        JButton button = new JButton(label);
        button.setPreferredSize(new Dimension(100, 50));
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.addActionListener(actionListener);
        return button;
    }

    private JPanel createButtonPanel(JButton button) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.white);
        buttonPanel.add(button);
        return buttonPanel;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(Color.white);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel leftFooterPanel = createLeftFooterPanel();
        JPanel rightFooterPanel = createRightFooterPanel();

        footerPanel.add(leftFooterPanel, BorderLayout.WEST);
        footerPanel.add(rightFooterPanel, BorderLayout.EAST);

        return footerPanel;
    }

    private void handleBackButtonClick() {
        if (currentPage > 1) {
            currentPage--;
            doSearch(searchTerm);
        }
    }

    private void handleNextButtonClick() {
        if (currentPage < totalPages) {
            currentPage++;
            doSearch(searchTerm);
        }
    }

    private JPanel createLeftFooterPanel() {
        JPanel leftFooterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftFooterPanel.setBackground(Color.white);

        pageNumberLabel = new JLabel(String.format(paginationText, currentPage, totalPages));
        pageNumberLabel.setPreferredSize(new Dimension(100, 20));
        pageNumberLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        leftFooterPanel.add(pageNumberLabel);

        backButton = createButton("Back", e -> handleBackButtonClick());
        backButton.setPreferredSize(new Dimension(100, 30));
        leftFooterPanel.add(backButton);

        nextButton = createButton("Next", e -> handleNextButtonClick());
        nextButton.setPreferredSize(new Dimension(100, 30));
        leftFooterPanel.add(nextButton);

        // page size combo box
        String[] pageSizes = { "10", "20", "50", "100" };
        JComboBox<String> pageSizeComboBox = new JComboBox<>(pageSizes);
        pageSizeComboBox.setSelectedIndex(0);

        pageSizeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
                itemsPerPage = Integer.parseInt((String) cb.getSelectedItem());
                doSearch(searchTerm);
            }
        });

        leftFooterPanel.add(pageSizeComboBox);

        return leftFooterPanel;
    }

    private void handleCreateButtonClick() {
        // TODO: Open create view
        DetailsView detailsView = new DetailsView();
        detailsView.setVisible(true);

        doSearch(searchTerm);
    }

    private void handleDeleteButtonClick() {
        // GET SELECTED ROW
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int hotelId = (int) table.getValueAt(selectedRow, 0);
            Hotel hotel = hotelController.getById(hotelId);
            // CONFIRMATION
            int dialogResult = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to delete " + hotel.getName() + "?", "Warning", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                hotelController.delete(hotel);
                doSearch(searchTerm);
            }
        }
    }

    private void handleDetailsButtonClick() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int hotelId = (int) table.getValueAt(selectedRow, 0);
            Hotel hotel = hotelController.getById(hotelId);

            // System.out.println(hotel.getName());

            // TODO: Open details view
            DetailsView detailsView = new DetailsView(hotel.getId());
        }
    }

    private JPopupMenu handleRightClick() {
        JPopupMenu menu = new JPopupMenu();
        JMenuItem details = new JMenuItem("Details");

        details.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDetailsButtonClick();
            }
        });

        JMenuItem delete = new JMenuItem("Delete");

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDeleteButtonClick();
            }
        });
        menu.add(details);
        menu.add(delete);
        return menu;
    }

    private JPanel createRightFooterPanel() {
        JPanel rightFooterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightFooterPanel.setBackground(Color.white);

        detailsButton = createButton("Details", e -> handleCreateButtonClick());
        detailsButton.setPreferredSize(new Dimension(100, 30));

        detailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDetailsButtonClick();
            }
        });

        rightFooterPanel.add(detailsButton);

        deleteButton = createButton("Delete", e -> handleDeleteButtonClick());
        deleteButton.setPreferredSize(new Dimension(100, 30));

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDeleteButtonClick();
            }
        });

        rightFooterPanel.add(deleteButton);

        return rightFooterPanel;
    }

    private void paginationButtonRefresh() {
        backButton.setEnabled(currentPage > 1);
        nextButton.setEnabled(currentPage < totalPages);
        pageNumberLabel.setText(String.format(paginationText, currentPage, totalPages));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ListView::new);
    }
}
