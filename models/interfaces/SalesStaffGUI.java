package models.interfaces;
import java.time.*;
import models.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Flow;


public class SalesStaffGUI {

    private SalesStaff salesStaff;
    //private JFrame frame;
    //private Store store;

    public SalesStaffGUI(SalesStaff salesStaff) {
        this.salesStaff = salesStaff;
        //this.store = salesStaff.getStore();

        JFrame frame = new JFrame("Sales Staff Dashboard");
        frame.setSize(430, 280);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        JLabel helloLabel = new JLabel("Hello " + salesStaff.getName());
        helloLabel.setFont(new Font("Arial", Font.BOLD, 20));
        helloLabel.setHorizontalAlignment(JLabel.LEFT);
        helloLabel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8)); // Padding around label

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // Padding around buttons

        JButton goToMerchandiseButton = new JButton("Add Sales Record");
        JButton goToTransactionButton = new JButton("View Transaction History");
        JButton exitButton = new JButton("Exit");

        buttonPanel.add(goToMerchandiseButton, gbc);
        buttonPanel.add(goToTransactionButton, gbc);
        buttonPanel.add(exitButton, gbc);

        frame.add(helloLabel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);


        goToTransactionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSalesStaffTransactionGUI();
            }
        });


        goToMerchandiseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSalesStaffMerchandiseGUI();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the current window
                new LoginInterface(salesStaff.getStore());
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    private void openSalesStaffTransactionGUI() {

//        SwingUtilities.invokeLater(() -> new SalesStaffGUI_Transaction(salesStaff));
//
//        frame.dispose();


        JFrame frame = new JFrame("Transaction History");
        frame.setSize(750, 370);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        String[] columns = {"Date", "Name", "Unit Price", "Qty Sold", "Total Amount ($)"};

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // This will make all cells in the table non-editable
                return false;
            }
        };
        JTable table = new JTable(model);
        refreshTransactionTable(model);

//        resultTextArea = new JTextArea();
//        resultTextArea.setEditable(false);

//        JScrollPane scrollPane = new JScrollPane(resultTextArea);
//        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        //dateTextField = new JTextField("yyyy-MM-dd");
        //timeTextField = new JTextField("HH:mm:ss");
        //merchandiseTextField = new JTextField("Merchandise Name");

        JButton searchByDateButton = new JButton("Search by Date");
        JButton searchByMerchandiseButton = new JButton("Search by Item");

        JPanel panel = new JPanel();
        panel.add(searchByDateButton);
        panel.add(searchByMerchandiseButton);



        searchByDateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //handleSearchByDate();
                showDateSearchDialog(model, frame);
            }
        });


        searchByMerchandiseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showItemSearchDialog(model, frame);
            }
        });



//        frame.setLayout(new BorderLayout());
////        frame.add(scrollPane, BorderLayout.CENTER);
//
//        JPanel searchPanel = new JPanel(new FlowLayout());
//        searchPanel.add(new JLabel("Date:"));
//        //searchPanel.add(dateTextField);
//        searchPanel.add(searchByDateButton);
//
//        searchPanel.add(new JLabel("Time:"));
////        searchPanel.add(timeTextField);
////        searchPanel.add(searchByTimeButton);
//
//        searchPanel.add(new JLabel("Merchandise:"));
////        searchPanel.add(merchandiseTextField);
//        searchPanel.add(searchByMerchandiseButton);
//
//        searchPanel.add(new JLabel("Back"));


        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.add(panel, BorderLayout.SOUTH);

        // Display the frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


        // Initialize and display the template
        //initializeAndDisplayTemplate();
    }

    private void openSalesStaffMerchandiseGUI() {

        JFrame frame = new JFrame("Items for Sale");
        frame.setSize(500, 300);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] columns = {"Name", "Unit Price", "Stock Level"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // This will make all cells in the table non-editable
                return false;
            }
        };
        JTable table = new JTable(model);
        refreshMerchandiseTable(model);

        //merchandiseTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(table);

        //merchandiseComboBox = new JComboBox<>();
        // quantityTextField = new JTextField();
        JButton sellButton = new JButton("Sell");
        JButton searchMerchButton = new JButton("Search");
        //JButton backButton = new JButton("Back"); // Add a back button


        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSellButtonClick(table, frame, model);
            }
        });

        searchMerchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSearchDialog(frame,table);
            }
        });

        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel sellPanel = new JPanel();
        sellPanel.add(sellButton);
        sellPanel.add(searchMerchButton);
//        sellPanel.add(merchandiseComboBox);
//        sellPanel.add(new JLabel("Quantity:"));

        // Set the preferred width of the quantity input box
        //quantityTextField.setPreferredSize(new Dimension(100, 25));
        //sellPanel.add(quantityTextField);

//        sellPanel.add(sellButton);
//        sellPanel.add(backButton);

        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.add(sellPanel, BorderLayout.SOUTH);
        refreshMerchandiseTable(model);

        //updateMerchandiseList();
        //refreshMerchandiseTextArea(salesStaff);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    private void refreshMerchandiseTable(DefaultTableModel model) {
        model.setRowCount(0); // Clear existing data
        // Load merchandise data
        List<Merchandise> merchandiseList = salesStaff.getStore().getMerchandiseList();

        for (Merchandise m : merchandiseList) {
            model.addRow(new Object[]{m.getName(), m.getUnitPrice(), m.getStockLevel()});
        }
    }

    private void refreshTransactionTable(DefaultTableModel model){
        model.setRowCount(0); // Clear existing data
        // Load merchandise data
        List<Transaction> transactionList = salesStaff.getStore().getAllTransactions();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (Transaction t: transactionList) {
            String formattedDateTime = t.getCurrent().format(formatter);
            model.addRow(new Object[]{formattedDateTime, t.getMerchandise().getName(), t.getMerchandise().getUnitPrice(), t.getQuantity(), t.getMerchandise().getUnitPrice() *t.getQuantity()});
        }
    }


    private void handleSellButtonClick(JTable table, JFrame frame,DefaultTableModel model) {
        // Get the selected row index
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select an item to sell.");
            return;
        }

        // Get the name of the selected item (assuming it's in the first column)
        String itemName = (String) table.getValueAt(selectedRow, 0);

        // Show input dialog to get the quantity
        String quantityString = JOptionPane.showInputDialog(frame, "Enter quantity for " + itemName + ":", "Sell Item", JOptionPane.QUESTION_MESSAGE);
        if (quantityString != null && !quantityString.isEmpty()) {
            try {
                int quantity = Integer.parseInt(quantityString);
                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(frame, "Invalid Quantity. Quantity must be greater than 0.");
                    return; // Return early to prevent further processing
                }
                Merchandise m = salesStaff.getStore().findMerchandise(itemName);
                salesStaff.recordSales(quantity, m);
                refreshMerchandiseTable(model);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid quantity. Please enter a valid integer.");
            }
        }
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void showSearchDialog(JFrame frame, JTable table) {
        // Create a dialog for search input
        JDialog searchDialog = new JDialog(frame, "Search Merchandise", true);
        searchDialog.setLayout(new FlowLayout());
        searchDialog.setSize(300, 130);

        JTextField searchTextField = new JTextField(20);
        JButton searchConfirmButton = new JButton("Search");

        searchDialog.add(new JLabel("Enter Merchandise Name:"));
        searchDialog.add(searchTextField);
        searchDialog.add(searchConfirmButton);

        searchConfirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchTextField.getText();
                if (!searchAndHighlightMerchandise(searchText, table)) {
                    JOptionPane.showMessageDialog(searchDialog, "Merchandise not found.");
                }
                searchDialog.dispose();
            }
        });

        searchDialog.setLocationRelativeTo(frame);
        searchDialog.setVisible(true);
    }

    private boolean searchAndHighlightMerchandise(String searchText, JTable table) {
        boolean foundMatch = false;

        // Clear existing selection
        table.clearSelection();

        for (int i = 0; i < table.getRowCount(); i++) {
            if (table.getValueAt(i, 0).toString().toLowerCase().contains(searchText.toLowerCase())) {
                // Highlight the row
                table.addRowSelectionInterval(i, i);
                foundMatch = true;
            }
        }

        // Scroll to the first matched row, if any match found
        if (foundMatch) {
            int firstMatchedRow = table.getSelectedRow();
            table.scrollRectToVisible(new Rectangle(table.getCellRect(firstMatchedRow, 0, true)));
        }

        return foundMatch;
    }
    private void showDateSearchDialog(DefaultTableModel model, JFrame frame) {
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

    private void handleDateSearch(Date date, DefaultTableModel model) {
        LocalDate selectedDate = convertToLocalDateViaInstant(date);
        model.setRowCount(0); // Clear existing data

        List<Transaction> transactionList = salesStaff.getStore().getAllTransactions();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (Transaction t : transactionList) {
            LocalDate transactionDate = t.getCurrent().toLocalDate();
            if (transactionDate.equals(selectedDate)) {
                String formattedDateTime = t.getCurrent().format(formatter);
                model.addRow(new Object[]{
                        formattedDateTime, t.getMerchandise().getName(), t.getMerchandise().getUnitPrice(),
                        t.getQuantity(), t.getMerchandise().getUnitPrice() * t.getQuantity()
                });
            }
        }
    }
    private void handleDateAndHourSearch(Date date, int startHour, int endHour, DefaultTableModel model) {
        LocalDate selectedDate = convertToLocalDateViaInstant(date);
        LocalTime startTime = LocalTime.of(startHour, 0); // Start at :00 of the startHour
        LocalTime endTime;

        if (endHour == 24) {
            endTime = LocalTime.MAX; // 23:59:59.999999999
        } else {
            endTime = LocalTime.of(endHour, 0); // End at :00 ozf the endHour, which is exclusive
        }

        model.setRowCount(0); // Clear existing data

        List<Transaction> transactionList = salesStaff.getStore().getAllTransactions();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (Transaction t : transactionList) {
            LocalDateTime transactionDateTime = t.getCurrent();
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
    }

    private void showItemSearchDialog(DefaultTableModel model, JFrame frame) {
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
        model.setRowCount(0); // Clear existing data
        List<Transaction> transactionList = salesStaff.getStore().getAllTransactions();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Filter transactions by item name
        for (Transaction t : transactionList) {
            if (containsIgnoreCase(t.getMerchandise().getName(), itemName)) {
                String formattedDateTime = t.getCurrent().format(formatter);
                model.addRow(new Object[]{
                        formattedDateTime, t.getMerchandise().getName(), t.getMerchandise().getUnitPrice(),
                        t.getQuantity(), t.getMerchandise().getUnitPrice() * t.getQuantity()
                });
            }
        }

        if (model.getRowCount() == 0) {
            // No transactions found for the given item name
            JOptionPane.showMessageDialog(frame, "No transactions found for item: " + itemName);
        }
    }



    // Helper method to convert Date to LocalDate
    private LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    private boolean containsIgnoreCase(String src, String what) {
        return src.toLowerCase().contains(what.toLowerCase());
    }








}
