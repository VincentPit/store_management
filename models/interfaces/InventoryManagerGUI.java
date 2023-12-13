package models.interfaces;

import models.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryManagerGUI {
    private InventoryManager inventoryManager;
    private DefaultTableModel model; // Model for inventory
    private DefaultTableModel salesModel; // Model for sales history
    private JTable table; // Table for inventory
    private JTable salesTable; // Table for sales history

    public InventoryManagerGUI(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
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
        salesHistoryButton.addActionListener(e -> openSalesHistory());
        exitButton.addActionListener(e -> {
            frame.dispose();
            new LoginInterface(inventoryManager.getStore()); // Replace with actual call to LoginInterface
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

    private void openSalesHistory() {
        createAndShowSalesHistoryGUI();
    }

    private void createAndShowInventoryGUI() {
        JFrame inventoryFrame = new JFrame("Inventory Management");
        inventoryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        inventoryFrame.setSize(500, 300);
        inventoryFrame.setLayout(new BorderLayout(10, 10));

        String[] columns = {"Name", "Unit Cost", "Unit Price", "Stock Level", "Qty Sold"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // This will make all cells in the table non-editable
                return false;
            }
        };
        table = new JTable(model);
        refreshMerchandiseTable();

        JButton addMerchButton = new JButton("Restock");
        JButton searchMerchButton = new JButton("Search");

        addMerchButton.addActionListener(e -> restock());
        searchMerchButton.addActionListener(e -> showSearchDialog(inventoryFrame));

        JPanel merchButtonPanel = new JPanel();
        merchButtonPanel.add(addMerchButton);
        merchButtonPanel.add(searchMerchButton);

        inventoryFrame.add(new JScrollPane(table), BorderLayout.CENTER);
        inventoryFrame.add(merchButtonPanel, BorderLayout.SOUTH);

        inventoryFrame.setLocationRelativeTo(null);
        inventoryFrame.setVisible(true);
    }

    private void createAndShowSalesHistoryGUI() {
        JFrame salesHistoryFrame = new JFrame("Sales History");
        salesHistoryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        salesHistoryFrame.setSize(600, 400);
        salesHistoryFrame.setLayout(new BorderLayout(10, 10));

        String[] salesColumns = {"Date", "Time", "Merchandise", "Quantity", "Amount"};
        salesModel = new DefaultTableModel(salesColumns, 0);
        salesTable = new JTable(salesModel);
        refreshSalesHistoryTable();

        JPanel buttonPanel = new JPanel();
        JButton searchByNameButton = new JButton("Search by Name");
        JButton searchByDateButton = new JButton("Search by Date");
        JButton searchByTimeButton = new JButton("Search by Time");

        searchByNameButton.addActionListener(e -> searchSalesHistoryByMerchandise(salesHistoryFrame));
        searchByDateButton.addActionListener(e -> searchSalesHistoryByDate(salesHistoryFrame));
        searchByTimeButton.addActionListener(e -> searchSalesHistoryByTime(salesHistoryFrame));

        buttonPanel.add(searchByNameButton);
        buttonPanel.add(searchByDateButton);
        buttonPanel.add(searchByTimeButton);

        salesHistoryFrame.add(new JScrollPane(salesTable), BorderLayout.CENTER);
        salesHistoryFrame.add(buttonPanel, BorderLayout.SOUTH);

        salesHistoryFrame.setLocationRelativeTo(null);
        salesHistoryFrame.setVisible(true);
    }

    private void refreshMerchandiseTable() {
        model.setRowCount(0);
        List<Merchandise> merchandiseList = inventoryManager.getStore().getMerchandiseList();
        for (Merchandise m : merchandiseList) {
            model.addRow(new Object[]{m.getName(), m.getUnitCost(), m.getUnitPrice(), m.getStockLevel(), m.getQtySold()});
        }
    }

    private void restock() {
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
                refreshMerchandiseTable();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.");
            }
        }
    }

    private void showSearchDialog(JFrame frame) {
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
            if (!searchAndHighlightMerchandise(searchText)) {
                JOptionPane.showMessageDialog(searchDialog, "Merchandise not found.");
            }
            searchDialog.dispose();
        });

        searchDialog.setLocationRelativeTo(frame);
        searchDialog.setVisible(true);
    }

    private boolean searchAndHighlightMerchandise(String searchText) {
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
    private void searchSalesHistoryByMerchandise(JFrame frame) {
        String inputName = JOptionPane.showInputDialog(frame, "Enter Merchandise Name:");
        if (inputName != null) {
            final String merchandiseName = inputName.toLowerCase();
            List<Transaction> filteredTransactions = inventoryManager.getAllTransactions().stream()
                    .filter(t -> t.getMerchandise().getName().toLowerCase().contains(merchandiseName))
                    .collect(Collectors.toList());
            updateSalesTableModel(filteredTransactions);
        }
    }

    private void searchSalesHistoryByDate(JFrame frame) {
        String inputDate = JOptionPane.showInputDialog(frame, "Enter Date (yyyy-MM-dd):");
        if (inputDate != null) {
            final String dateString = inputDate;
            List<Transaction> filteredTransactions = inventoryManager.getAllTransactions().stream()
                    .filter(t -> t.getDate().toString().contains(dateString))
                    .collect(Collectors.toList());
            updateSalesTableModel(filteredTransactions);
        }
    }

    private void searchSalesHistoryByTime(JFrame frame) {
        String inputTime = JOptionPane.showInputDialog(frame, "Enter Time (HH:mm):");
        if (inputTime != null) {
            final String timeString = inputTime;
            List<Transaction> filteredTransactions = inventoryManager.getAllTransactions().stream()
                    .filter(t -> t.getTime().toString().contains(timeString))
                    .collect(Collectors.toList());
            updateSalesTableModel(filteredTransactions);
        }
    }


    private void updateSalesTableModel(List<Transaction> transactions) {
        salesModel.setRowCount(0);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        // Check if transactions list is not null
        if (transactions != null) {
            for (Transaction transaction : transactions) {
                salesModel.addRow(new Object[]{
                        transaction.getDate().format(dateFormatter),
                        transaction.getTime().format(timeFormatter),
                        transaction.getMerchandise().getName(),
                        transaction.getQuantity(),
                        transaction.getMerchandise().getUnitPrice() * transaction.getQuantity()
                });
            }
        }
    }


    private void refreshSalesHistoryTable() {
        updateSalesTableModel(inventoryManager.getAllTransactions());
    }


}
