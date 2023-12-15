package models.interfaces;

import models.Store;
import models.Transaction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class transactionViewer {

    private Store store;
    private DefaultTableModel model;
    private JTable table;
    private JFrame frame;

    public transactionViewer(Store store) {
        this.store = store;
        this.model = createTableModel();
        this.table = new JTable(model);
        initializeFrame();
    }

    private DefaultTableModel createTableModel() {
        String[] columns = {"Date", "Name", "Unit Price", "Qty Sold", "Total Amount ($)"};
        return new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private void initializeFrame() {
        frame = new JFrame("Transaction History");
        frame.setSize(750, 370);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        JButton searchByDateButton = new JButton("Search by Date");
        JButton searchByMerchandiseButton = new JButton("Search by Item");
        JButton resetButton = new JButton("Reset");

        JPanel panel = new JPanel();
        panel.add(searchByDateButton);
        panel.add(searchByMerchandiseButton);
        panel.add(resetButton);

        searchByDateButton.addActionListener(e -> showDateSearchDialog());
        searchByMerchandiseButton.addActionListener(e -> showItemSearchDialog());
        resetButton.addActionListener(e->refreshTransactionTable());

        frame.add(panel, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }



    public void display() {
        refreshTransactionTable();
        frame.setVisible(true);
    }


    private void showDateSearchDialog() {
        JDialog searchDialog = new JDialog(frame, "Search by Date and Hour Range", true);
        searchDialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add some padding

        // Date Spinner
        JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(dateEditor);

        // Hour Range Spinners
        JSpinner startHourSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 23, 1));
        JSpinner endHourSpinner = new JSpinner(new SpinnerNumberModel(23, 0, 23, 1));

        JButton searchButton = new JButton("Search");

        gbc.gridx = 0;
        gbc.gridy = 0;
        searchDialog.add(new JLabel("Select Date:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        searchDialog.add(dateSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        searchDialog.add(new JLabel("Hour Range: From"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        searchDialog.add(startHourSpinner, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        searchDialog.add(new JLabel("To"), gbc);

        gbc.gridx = 3;
        gbc.gridy = 1;
        searchDialog.add(endHourSpinner, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        searchDialog.add(searchButton, gbc);

        // Search Button Action
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date selectedDate = (Date) dateSpinner.getValue();
                int startHour = (Integer) startHourSpinner.getValue();
                int endHour = (Integer) endHourSpinner.getValue();
                handleDateAndHourSearch(selectedDate, startHour, endHour, model);
                searchDialog.dispose();
            }
        });

        searchDialog.pack(); // Adjusts size to fit components
        searchDialog.setLocationRelativeTo(frame);
        searchDialog.setVisible(true);
    }

    private void handleDateAndHourSearch(Date date, int startHour, int endHour, DefaultTableModel model) {
        LocalDate selectedDate = this.store.convertToLocalDateViaInstant(date);
        LocalTime startTime = LocalTime.of(startHour, 0); // Start at :00 of the startHour
        LocalTime endTime;

        if (endHour == 24) {
            endTime = LocalTime.MAX; // 23:59:59.999999999
        } else {
            endTime = LocalTime.of(endHour, 0); // End at :00 of the endHour, which is exclusive
        }

        model.setRowCount(0);

        java.util.List<Transaction> transactionList = store.getAllTransactions();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (Transaction t : transactionList) {
            LocalDateTime transactionDateTime = t.getTime();
            LocalDate transactionDate = transactionDateTime.toLocalDate();
            LocalTime transactionTime = transactionDateTime.toLocalTime();

            // Check if the transaction date is the selected date
            // and the transaction time is between start time and 1 minute before the end time
            if (transactionDate.equals(selectedDate) &&
                    !transactionTime.isBefore(startTime) &&
                    transactionTime.isBefore(endTime)) {
                String formattedDateTime = transactionDateTime.format(formatter);
                model.addRow(new Object[]{
                        formattedDateTime, t.getMerchandise().getName(), t.getMerchandise().getUnitPrice(),
                        t.getQuantity(), t.getMerchandise().getUnitPrice() * t.getQuantity()
                });
            }
        }

        if (model.getRowCount() == 0) {
            // No transactions found for the given item name
            JOptionPane.showMessageDialog(frame, "No transactions found for this Date");
            refreshTransactionTable();
        }
    }

    private void showItemSearchDialog() {
        JDialog searchDialog = new JDialog(frame, "Search by Item", true);
        searchDialog.setLayout(new FlowLayout());
        searchDialog.setSize(300, 130);

        JTextField itemSearchField = new JTextField(20);
        JButton searchButton = new JButton("Search");

        searchDialog.add(new JLabel("Enter Item Name:"));
        searchDialog.add(itemSearchField);
        searchDialog.add(searchButton);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String itemName = itemSearchField.getText().trim();
                if (!itemName.isEmpty()) {
                    handleItemSearch(itemName, model, frame);
                } else {
                    JOptionPane.showMessageDialog(searchDialog, "Please enter an item name to search.");
                }
                searchDialog.dispose();
            }
        });

        searchDialog.setLocationRelativeTo(frame);
        searchDialog.setVisible(true);
    }

    private void handleItemSearch(String itemName, DefaultTableModel model, JFrame frame) {
        model.setRowCount(0);
        List<Transaction> transactionList = store.getAllTransactions();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Filter transactions by item name
        for (Transaction t : transactionList) {
            if (t.getMerchandise().getName().toLowerCase().contains(itemName.toLowerCase())) {
                String formattedDateTime = t.getTime().format(formatter);
                model.addRow(new Object[]{
                        formattedDateTime, t.getMerchandise().getName(), t.getMerchandise().getUnitPrice(),
                        t.getQuantity(), t.getMerchandise().getUnitPrice() * t.getQuantity()
                });
            }
        }

        if (model.getRowCount() == 0) {
            // No transactions found for the given item name
            JOptionPane.showMessageDialog(frame, "No transactions found for item: " + itemName);
            refreshTransactionTable();
        }
    }



    private void refreshTransactionTable(){
        model.setRowCount(0); // Clear existing data
        List<Transaction> transactionList = store.getAllTransactions();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (Transaction t: transactionList) {
            String formattedDateTime = t.getTime().format(formatter);
            model.addRow(new Object[]{formattedDateTime, t.getMerchandise().getName(), t.getMerchandise().getUnitPrice(), t.getQuantity(), t.getMerchandise().getUnitPrice() *t.getQuantity()});
        }
    }


}
