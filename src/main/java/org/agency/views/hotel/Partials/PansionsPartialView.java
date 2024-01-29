package org.agency.views.hotel.Partials;

import com.github.lgooddatepicker.components.DatePicker;
import org.agency.controllers.HotelController;
import org.agency.controllers.PansionController;
import org.agency.controllers.SeasonController;
import org.agency.entities.Hotel;
import org.agency.entities.Pansion;
import org.agency.entities.Season;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class PansionsPartialView {

    private Hotel hotel;
    private HotelController hotelController = new HotelController();

    private PansionController pansionController = new PansionController();

    private final Runnable RefreshParent;
    private final Runnable FrameDispose;

    private JTable table;

    private String[] columns = { "ID", "Name" };

    private HashMap<String, Object> filters = new HashMap<>();

    private JPopupMenu popupMenu;

    public PansionsPartialView(Hotel hotel, Runnable RefreshParent, Runnable FrameDispose) {
        this.hotel = hotel;
        this.RefreshParent = RefreshParent;
        this.FrameDispose = FrameDispose;
        this.filters.put("hotelId", hotel.getId());

    }

    private JPanel headerSearchPanel() {
        JPanel headerSearchPanel = new JPanel();
        headerSearchPanel.setLayout(new FlowLayout());
        headerSearchPanel.setBackground(Color.white);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setPreferredSize(new Dimension(100, 30));
        refreshButton.setMaximumSize(new Dimension(100, 30));
        refreshButton.setMinimumSize(new Dimension(100, 30));

        refreshButton.setToolTipText("Refresh");

        refreshButton.addActionListener(e -> {
            filters.clear();
            filters.put("hotel_id", hotel.getId());
            List<Pansion> pansions = pansionController.getByHotelId(hotel.getId());

            Object[][] data = new Object[pansions.size()][columns.length];

            for (int i = 0; i < pansions.size(); i++) {
                Pansion pansion = pansions.get(i);
                data[i][0] = pansion.getId();
                data[i][1] = pansion.getName();
            }

            DefaultTableModel model = new DefaultTableModel(data, columns);

            table.setModel(model);
        });

        headerSearchPanel.add(refreshButton);

        JButton createButton = new JButton("Create");
        createButton.setPreferredSize(new Dimension(100, 30));
        createButton.setMaximumSize(new Dimension(100, 30));
        createButton.setMinimumSize(new Dimension(100, 30));
        createButton.setToolTipText("Create");
        createButton.addActionListener(e -> {
            Pansion pansion = new Pansion();
            pansion.setHotelId(hotel.getId());
            org.agency.views.pansion.DetailsView detailsView = new org.agency.views.pansion.DetailsView(pansion);
        });

        headerSearchPanel.add(createButton);

        return headerSearchPanel;
    }

    public JPanel render() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.white);

        List<Pansion> pansions = pansionController.getByHotelId(hotel.getId());

        Object[][] data = new Object[pansions.size()][columns.length];

        for (int i = 0; i < pansions.size(); i++) {
            Pansion pansion = pansions.get(i);
            data[i][0] = pansion.getId();
            data[i][1] = pansion.getName();
        }

        table = new JTable(data, columns);

        popupMenu = new JPopupMenu();
        JMenuItem editItem = new JMenuItem("Details");
        JMenuItem deleteItem = new JMenuItem("Delete");

        editItem.addActionListener(e -> {
            int row = table.getSelectedRow();
            int pansionId = (int) table.getModel().getValueAt(row, 0);
            Pansion pansion = pansionController.getById(pansionId);
            org.agency.views.pansion.DetailsView detailsView = new org.agency.views.pansion.DetailsView(pansion);

        });

        deleteItem.addActionListener(e -> {
            int row = table.getSelectedRow();
            int pansionId = (int) table.getModel().getValueAt(row, 0);
            Pansion pansion = pansionController.getById(pansionId);
            // Confirm dialog
            int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this pansion?",
                    "Warning", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                pansionController.delete(pansion);
                JOptionPane.showMessageDialog(null, "Pansion deleted successfully.");
                FrameDispose.run();
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
