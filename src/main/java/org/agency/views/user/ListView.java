package org.agency.views.user;

import org.agency.controllers.UserController;
import org.agency.entities.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.agency.core.PaginatedResult;

public class ListView {

    private UserController userController = new UserController();
    private JPanel mainPanel;

    // Buttons
    private JButton createButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton detailsButton;
    private String[] tableColumnNames = {"ID", "Username", "Role", "Email"};

    private Object[][] tableData = userController.getAll().stream().map(user -> new Object[]{
            user.getId(),
            user.getUsername(),
            user.getRole(),
            user.getEmail()
    }).toArray(Object[][]::new);

    private DefaultTableModel model;
    private static JTable table;


    //pagination

    private JButton backButton;
    private JButton nextButton;
    private int currentPage = 1;
    private int totalPages = 1;
    private int totalItems = 0;

    private int itemsPerPage = 10;

    private String searchTerm = "";

    private String paginationText = "Page %d of %d";

    private JLabel pageNumberLabel;


    public void updateTableData(ArrayList<User> newData) {
        Object[][] tableData = newData.stream().map(user -> new Object[]{
                user.getId(),
                user.getUsername(),
                user.getRole(),
                user.getEmail()
        }).toArray(Object[][]::new);

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setDataVector(tableData, tableColumnNames);
        model.fireTableDataChanged();
    }


    // Constructor
    public ListView() {
        JFrame frame = new JFrame("Users");
        configureFrame(frame);
        mainPanel = new JPanel();
        placeComponents(mainPanel);
        frame.add(mainPanel);
        paginationButtonRefresh();
        frame.setVisible(true);
    }

    private void configureFrame(JFrame frame) {
        frame.setSize(getHalfScreenSize());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(new ImageIcon("src/main/resources/icon.png").getImage());
        frame.setLocationRelativeTo(null);
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

        model = new DefaultTableModel(tableData, tableColumnNames);
        table = new JTable(model);
        table.setRowHeight(30);
        table.setDefaultEditor(Object.class, null);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Implementation of table row selection
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // Display selected row data or perform some action
                    System.out.println("Selected Row: " + selectedRow + "mouse event type: " + e);
                }
            }
        });
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane);
        return tablePanel;
    }

    private void  doSearch(String searchTerm) {
        //Reset pagination
        currentPage = 1;
        this.searchTerm = searchTerm;
        // Perform search based on the searchTerm
        try {
            PaginatedResult<User> result = userController.paginate(currentPage, itemsPerPage, searchTerm);
            updateTableData((ArrayList<User>) result.getData());
            totalItems = result.getTotal();
            totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);
        } catch (Exception e) {


        }
    }


    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(Color.white);
        JPanel searchFieldPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchFieldPanel.setBackground(Color.white);
        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(300, 50));
        searchField.setMaximumSize(new Dimension(600, 50));
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setBackground(Color.getHSBColor(0, 0, 0.95f));
        searchFieldPanel.add(searchField);
        JPanel searchButtonPanel = new JPanel();
        JButton searchButton = new JButton("Search");
        searchButton.setPreferredSize(new Dimension(100, 50));
        searchButton.setFont(new Font("Arial", Font.PLAIN, 14));
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implementation of search functionality
                doSearch(searchField.getText());
            }
        });

        //make search area work
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





        searchButtonPanel.setBackground(Color.white);
        searchButtonPanel.add(searchButton);
        searchPanel.add(searchFieldPanel, BorderLayout.CENTER);
        searchPanel.add(searchButtonPanel, BorderLayout.EAST);
        return searchPanel;
    }

    private JPanel createButtonPanel() {
        createButton = new JButton("Create");
        createButton.setPreferredSize(new Dimension(100, 50));
        createButton.setFont(new Font("Arial", Font.PLAIN, 14));
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implementation of create button functionality
                System.out.println("Create button clicked");
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.white);
        buttonPanel.add(createButton);
        return buttonPanel;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(Color.white);
        JPanel leftFooterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftFooterPanel.setBackground(Color.white);
        JPanel rightFooterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightFooterPanel.setBackground(Color.white);

        //pagination
        pageNumberLabel = new JLabel(String.format(paginationText, currentPage, totalPages));
        pageNumberLabel.setPreferredSize(new Dimension(100, 20));
        pageNumberLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        leftFooterPanel.add(pageNumberLabel);

        //pagination
        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100, 20));
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implementation of back button functionality
                paginationButtonRefresh();
                System.out.println("Back button clicked");
            }
        });
        leftFooterPanel.add(backButton);

        nextButton = new JButton("Next");
        nextButton.setPreferredSize(new Dimension(100, 20));
        nextButton.setFont(new Font("Arial", Font.PLAIN, 14));
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implementation of next/refresh button functionality
                paginationButtonRefresh();
                System.out.println("Next/Refresh button clicked");
            }
        });
        leftFooterPanel.add(nextButton);


        detailsButton = new JButton("Details");
        detailsButton.setPreferredSize(new Dimension(100, 20));
        detailsButton.setFont(new Font("Arial", Font.PLAIN, 14));
        detailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implementation of details button functionality
                System.out.println("Details button clicked");
            }
        });
        rightFooterPanel.add(detailsButton);
        editButton = new JButton("Delete");
        editButton.setPreferredSize(new Dimension(100, 20));
        editButton.setFont(new Font("Arial", Font.PLAIN, 14));
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implementation of delete button functionality
                System.out.println("Delete button clicked");
            }
        });
        rightFooterPanel.add(editButton);
        deleteButton = new JButton("Edit");
        deleteButton.setPreferredSize(new Dimension(100, 20));
        deleteButton.setFont(new Font("Arial", Font.PLAIN, 14));
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implementation of edit button functionality
                System.out.println("Edit button clicked");
            }
        });
        rightFooterPanel.add(deleteButton);
        footerPanel.add(leftFooterPanel, BorderLayout.WEST);
        footerPanel.add(rightFooterPanel, BorderLayout.EAST);
        return footerPanel;
    }

    private void paginationButtonRefresh()
    {
        if(currentPage == 1)
        {
            backButton.setEnabled(false);
        }
        else
        {
            backButton.setEnabled(true);
        }
        if(currentPage == totalPages)
        {
            nextButton.setEnabled(false);
        }
        else
        {
            nextButton.setEnabled(true);
        }

        pageNumberLabel.setText(String.format(paginationText, currentPage, totalPages));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ListView::new);
    }
}


