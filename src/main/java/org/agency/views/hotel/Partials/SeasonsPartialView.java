package org.agency.views.hotel.Partials;

import com.github.lgooddatepicker.components.DatePicker;
import org.agency.controllers.HotelController;
import org.agency.controllers.RoomController;
import org.agency.controllers.SeasonController;
import org.agency.entities.Hotel;
import org.agency.entities.Room;
import org.agency.entities.Season;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class SeasonsPartialView {

    private Hotel hotel;
    private HotelController hotelController = new HotelController();

    private SeasonController seasonController = new SeasonController();

    private final Runnable RefreshParent;
    private final Runnable FrameDispose;

    private JTable table;

    private String[] columns = { "ID", "Name", "Start Date", "End Date" };

    private HashMap<String, Object> filters = new HashMap<>();

    private JPopupMenu popupMenu;

    public SeasonsPartialView(Hotel hotel, Runnable RefreshParent, Runnable FrameDispose) {
        this.hotel = hotel;
        this.RefreshParent = RefreshParent;
        this.FrameDispose = FrameDispose;
        this.filters.put("hotelId", hotel.getId());

    }

    private JPanel headerSearchPanel() {
        JPanel headerSearchPanel = new JPanel();
        headerSearchPanel.setLayout(new FlowLayout());
        headerSearchPanel.setBackground(Color.white);

        /*
         * private String name;
         * private Date startDate;
         * private Date endDate;
         */

        // BED_AND_BREAKFAST', 'HALF_BOARD', 'FULL_BOARD', 'ALL_INCLUSIVE',
        // 'ULTRA_ALL_INCLUSIVE', 'BED_ONLY' , 'ALL_INCLUSIVE_NO_ALCOHOL
        JComboBox<String> nameField = new JComboBox<>(new String[] { "ALL", "BED_AND_BREAKFAST", "HALF_BOARD",
                "FULL_BOARD", "ALL_INCLUSIVE", "ULTRA_ALL_INCLUSIVE", "BED_ONLY", "ALL_INCLUSIVE_NO_ALCOHOL" });
        nameField.setPreferredSize(new Dimension(100, 30));
        nameField.setMaximumSize(new Dimension(100, 30));
        nameField.setMinimumSize(new Dimension(100, 30));
        nameField.setToolTipText("Name:");
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setPreferredSize(new Dimension(40, 30));
        nameLabel.setMaximumSize(new Dimension(40, 30));
        nameLabel.setMinimumSize(new Dimension(40, 30));
        //headerSearchPanel.add(nameLabel);
        //headerSearchPanel.add(nameField);

        DatePicker startDateField = new DatePicker();
        startDateField.setPreferredSize(new Dimension(200, 30));
        startDateField.setMaximumSize(new Dimension(200, 30));
        startDateField.setMinimumSize(new Dimension(200, 30));
        startDateField.setToolTipText("Start Date:");
        JLabel startDateLabel = new JLabel("Start Date:");
        startDateLabel.setPreferredSize(new Dimension(40, 30));
        startDateLabel.setMaximumSize(new Dimension(40, 30));
        startDateLabel.setMinimumSize(new Dimension(40, 30));
        //headerSearchPanel.add(startDateLabel);
        //headerSearchPanel.add(startDateField);

        DatePicker endDateField = new DatePicker();
        endDateField.setPreferredSize(new Dimension(200, 30));
        endDateField.setMaximumSize(new Dimension(200, 30));
        endDateField.setMinimumSize(new Dimension(200, 30));
        endDateField.setToolTipText("End Date:");
        JLabel endDateLabel = new JLabel("End Date:");
        endDateLabel.setPreferredSize(new Dimension(40, 30));
        endDateLabel.setMaximumSize(new Dimension(40, 30));
        endDateLabel.setMinimumSize(new Dimension(40, 30));
        //headerSearchPanel.add(endDateLabel);
        //headerSearchPanel.add(endDateField);

        JButton searchButton = new JButton("Refresh");
        searchButton.setPreferredSize(new Dimension(100, 30));
        searchButton.setMaximumSize(new Dimension(100, 30));
        searchButton.setMinimumSize(new Dimension(100, 30));
        searchButton.setToolTipText("Search");
        headerSearchPanel.add(searchButton);

        searchButton.addActionListener(e -> {

            // check for valid input
            // if START DATE is after END DATE
            if (startDateField.getDate() != null && endDateField.getDate() != null) {
                if (startDateField.getDate().toEpochDay() > endDateField.getDate().toEpochDay()) {
                    JOptionPane.showMessageDialog(null, "Start Date must be before End Date", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            filters.clear();

            filters.put("hotel_id", hotel.getId());
            if (nameField.getSelectedIndex() != 0) {
                filters.put("name", nameField.getSelectedItem());
            }
            if (startDateField.getDate() != null) {
                filters.put("start_date", startDateField.getDate());
            }
            if (endDateField.getDate() != null) {
                filters.put("end_date", endDateField.getDate());
            }

            List<Season> seasons = seasonController.getByFilters(filters);
            Object[][] data = new Object[seasons.size()][columns.length];

            for (int i = 0; i < seasons.size(); i++) {
                Season season = seasons.get(i);
                data[i][0] = season.getId();
                data[i][1] = season.getName();
                data[i][2] = season.getStartDate();
                data[i][3] = season.getEndDate();
            }

            table.setModel(new javax.swing.table.DefaultTableModel(
                    data,
                    columns));

            table.repaint();
        });

        JButton createButton = new JButton("Create");
        createButton.setPreferredSize(new Dimension(100, 30));
        createButton.setMaximumSize(new Dimension(100, 30));
        createButton.setMinimumSize(new Dimension(100, 30));
        createButton.setToolTipText("Create");
        createButton.addActionListener(e -> {
            Season season = new Season();
            season.setHotelId(hotel.getId());
            org.agency.views.season.DetailsView detailsView = new org.agency.views.season.DetailsView(season);
        });

        headerSearchPanel.add(createButton);

        return headerSearchPanel;
    }

    public JPanel render() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.white);

        List<Season> seasons = seasonController.getAllByHotelId(hotel.getId());

        Object[][] data = new Object[seasons.size()][columns.length];

        for (int i = 0; i < seasons.size(); i++) {
            Season season = seasons.get(i);
            data[i][0] = season.getId();
            data[i][1] = season.getName();
            data[i][2] = season.getStartDate();
            data[i][3] = season.getEndDate();
        }

        table = new JTable(data, columns);

        popupMenu = new JPopupMenu();
        JMenuItem editItem = new JMenuItem("Details");
        JMenuItem deleteItem = new JMenuItem("Delete");

        editItem.addActionListener(e -> {
            int row = table.getSelectedRow();
            int seasonId = (int) table.getModel().getValueAt(row, 0);
            Season season = seasonController.getById(seasonId);
            org.agency.views.season.DetailsView detailsView = new org.agency.views.season.DetailsView(season);
        });

        deleteItem.addActionListener(e -> {
            int row = table.getSelectedRow();
            int seasonId = (int) table.getModel().getValueAt(row, 0);
            Season season = seasonController.getById(seasonId);
            // Confirm delete
            int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this season?",
                    "Warning", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                seasonController.delete(season);
                RefreshParent.run();
            }
        });

        popupMenu.add(editItem);
        popupMenu.add(deleteItem);

        table.setComponentPopupMenu(popupMenu);

        // Disable editing
        table.setDefaultEditor(Object.class, null);

        JScrollPane scrollPane = new JScrollPane(table);

        panel.add(headerSearchPanel(), BorderLayout.NORTH);
        panel.add(scrollPane);

        return panel;
    }

}
