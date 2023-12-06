package models.interfaces;

import models.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.List;



public class InventoryManagerGUI {
    private InventoryManager inventoryManager;
    private DefaultTableModel model; // Declare model here
    private JTable table; //Declare table


    public InventoryManagerGUI(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
        createAndShowGUI();
    }


    private void createAndShowGUI() {
        // Create and set up the window
        JFrame frame = new JFrame("Inventory Manager Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);


        // Set layout manager for the frame
        frame.setLayout(new BorderLayout(10, 10));


        // Greeting label
        JLabel greetingLabel = new JLabel("Welcome, " + inventoryManager.getName());
        greetingLabel.setFont(new Font("Arial", Font.BOLD, 16));
        greetingLabel.setHorizontalAlignment(JLabel.LEFT);
        greetingLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding around label


        frame.add(greetingLabel, BorderLayout.NORTH);


        // Table to display merchandise
        String[] columns = {"Name", "Unit Cost", "Unit Price", "Stock Level"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        refreshMerchandiseTable();


        // Buttons for add and search
        JButton addMerchButton = new JButton("Restock");
        JButton searchMerchButton = new JButton("Search");


        // Add action listeners to these buttons
        addMerchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restock();
            }
        });




        // Layout for buttons
        JPanel merchButtonPanel = new JPanel();
        merchButtonPanel.add(addMerchButton);
        merchButtonPanel.add(searchMerchButton);


        // Add components to the frame
        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.add(merchButtonPanel, BorderLayout.SOUTH);


        // Display the frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    private void refreshMerchandiseTable() {
        model.setRowCount(0); // Clear existing data
        // Load merchandise data
        List<Merchandise> merchandiseList = inventoryManager.getStore().getMerchandiseList(); // Access via instance
        for (Merchandise m : merchandiseList) {
            model.addRow(new Object[]{m.getName(), m.getUnitCost(), m.getUnitPrice(), m.getStockLevel()});
        }
    }


    private void restock() {
        // Get the selected row index
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a merchandise item to restock.");
            return;
        }


        // Get the name of the merchandise from the selected row
        String merchandiseName = model.getValueAt(selectedRow, 0).toString();


        // Input dialog to get additional stock amount
        String additionalStockStr = JOptionPane.showInputDialog("Enter additional stock for " + merchandiseName + ":");
        if (additionalStockStr != null) {
            try {
                int additionalStock = Integer.parseInt(additionalStockStr);
                if (additionalStock < 0) {
                    JOptionPane.showMessageDialog(null, "Invalid stock number. Please enter a positive integer.");
                    return;
                }


                // Updating stock in the store
                inventoryManager.restock(merchandiseName, additionalStock);


                // Refreshing the table
                refreshMerchandiseTable();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.");
            }
        }
    }






}

