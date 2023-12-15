package models.interfaces;

import models.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class InventoryManagerGUI {
    private InventoryManager inventoryManager;
    private transactionViewer tv;


    public InventoryManagerGUI(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
        this.tv = new transactionViewer(inventoryManager.getStoreAccess());
        createAndShowInitialGUI();
    }

    private void createAndShowInitialGUI() {
        JFrame frame = new JFrame("Inventory Manager Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(430, 280);
        frame.setLayout(new BorderLayout(10, 10));

        JLabel greetingLabel = new JLabel("Welcome, " + inventoryManager.getName());
        greetingLabel.setFont(new Font("Arial", Font.BOLD, 20));
        greetingLabel.setHorizontalAlignment(JLabel.LEFT);
        greetingLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(greetingLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JButton inventoryButton = new JButton("View Inventory Level");
        JButton salesHistoryButton = new JButton("View Transaction History");
        JButton exitButton = new JButton("Exit");

        inventoryButton.addActionListener(e -> openInventoryManagement());
        salesHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tv.display();
            }
        });
        exitButton.addActionListener(e -> {
            frame.dispose();
            new LoginInterface(inventoryManager.getStoreAccess()); // Replace with actual call to LoginInterface
        });

        buttonPanel.add(inventoryButton, gbc);
        buttonPanel.add(salesHistoryButton, gbc);
        buttonPanel.add(exitButton, gbc);

        frame.add(buttonPanel, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void openInventoryManagement() {
        createAndShowInventoryGUI();
    }


    private void createAndShowInventoryGUI() {
        JFrame inventoryFrame = new JFrame("Inventory Management");
        inventoryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        inventoryFrame.setSize(500, 300);
        inventoryFrame.setLayout(new BorderLayout(10, 10));

        String[] columns = {"Name", "Unit Cost", "Unit Price", "Stock Level", "Qty Sold"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // make all cells in the table non-editable
                return false;
            }
        };
        JTable table = new JTable(model);
        refreshMerchandiseTable(model);

        JButton addMerchButton = new JButton("Restock");
        JButton searchMerchButton = new JButton("Search");

        addMerchButton.addActionListener(e -> restock(model, table));
        searchMerchButton.addActionListener(e -> showSearchDialog(inventoryFrame, table));

        JPanel merchButtonPanel = new JPanel();
        merchButtonPanel.add(addMerchButton);
        merchButtonPanel.add(searchMerchButton);

        inventoryFrame.add(new JScrollPane(table), BorderLayout.CENTER);
        inventoryFrame.add(merchButtonPanel, BorderLayout.SOUTH);

        inventoryFrame.setLocationRelativeTo(null);
        inventoryFrame.setVisible(true);
    }


    private void refreshMerchandiseTable(DefaultTableModel model) {
        model.setRowCount(0);
        List<Merchandise> merchandiseList = inventoryManager.getMerchandiseList();
        for (Merchandise m : merchandiseList) {
            model.addRow(new Object[]{m.getName(), m.getUnitCost(), m.getUnitPrice(), m.getStockLevel(), m.getQtySold()});
        }
    }

    private void restock(DefaultTableModel model, JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a merchandise item to restock.");
            return;
        }
        String merchandiseName = model.getValueAt(selectedRow, 0).toString();
        String additionalStockStr = JOptionPane.showInputDialog("Enter additional stock for " + merchandiseName + ":");
        if (additionalStockStr != null) {
            try {
                int additionalStock = Integer.parseInt(additionalStockStr);
                if (additionalStock < 0) {
                    JOptionPane.showMessageDialog(null, "Invalid stock number. Please enter a positive integer.");
                    return;
                }
                inventoryManager.restock(merchandiseName, additionalStock);
                refreshMerchandiseTable(model);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.");
            }
        }
    }

    private void showSearchDialog(JFrame frame, JTable table) {
        JDialog searchDialog = new JDialog(frame, "Search Merchandise", true);
        searchDialog.setLayout(new FlowLayout());
        searchDialog.setSize(300, 130);

        JTextField searchTextField = new JTextField(20);
        JButton searchConfirmButton = new JButton("Search");

        searchDialog.add(new JLabel("Enter Merchandise Name:"));
        searchDialog.add(searchTextField);
        searchDialog.add(searchConfirmButton);

        searchConfirmButton.addActionListener(e -> {
            String searchText = searchTextField.getText();
            if (!searchAndHighlightMerchandise(searchText, table)) {
                JOptionPane.showMessageDialog(searchDialog, "Merchandise not found.");
            }
            searchDialog.dispose();
        });

        searchDialog.setLocationRelativeTo(frame);
        searchDialog.setVisible(true);
    }

    private boolean searchAndHighlightMerchandise(String searchText, JTable table) {
        boolean foundMatch = false;
        table.clearSelection();
        for (int i = 0; i < table.getRowCount(); i++) {
            if (table.getValueAt(i, 0).toString().toLowerCase().contains(searchText.toLowerCase())) {
                table.addRowSelectionInterval(i, i);
                foundMatch = true;
            }
        }
        if (foundMatch) {
            int firstMatchedRow = table.getSelectedRow();
            table.scrollRectToVisible(new Rectangle(table.getCellRect(firstMatchedRow, 0, true)));
        }
        return foundMatch;
    }



}
