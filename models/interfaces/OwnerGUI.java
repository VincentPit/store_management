package models.interfaces;

import models.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class OwnerGUI {
    private BusinessOwner businessOwner;
    private DefaultTableModel model; // Declare model here
    private JTable table; //Declare table


    public OwnerGUI(BusinessOwner businessOwner) {
        this.businessOwner = businessOwner;
        createAndShowGUI();
    }
    private void createAndShowGUI() {
        // Create and set up the window
        JFrame frame = new JFrame("Business Owner Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Set layout manager for the frame
        frame.setLayout(new BorderLayout(10, 10));

        // Greeting label
        JLabel greetingLabel = new JLabel("Hi, " + businessOwner.getName());
        greetingLabel.setFont(new Font("Arial", Font.BOLD, 16));
        greetingLabel.setHorizontalAlignment(JLabel.LEFT);
        greetingLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding around label

        // Create a panel for buttons with a GridBagLayout for flexibility
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // Padding around buttons

        // Creating buttons
        JButton viewMerchButton = new JButton("View Merchandise");
        JButton addSalesStaffButton = new JButton("View Personel");
        JButton addInventoryManagerButton = new JButton("Add Inventory Manager");
        JButton deleteStaffButton = new JButton("Delete Staff");
        JButton setDiscountButton = new JButton("Set Discount");
        JButton setPriceButton = new JButton("Set Price");

        // Adding buttons to the panel
        buttonPanel.add(viewMerchButton, gbc);
        buttonPanel.add(addSalesStaffButton, gbc);
        buttonPanel.add(addInventoryManagerButton, gbc);
        buttonPanel.add(deleteStaffButton, gbc);
        buttonPanel.add(setDiscountButton, gbc);
        buttonPanel.add(setPriceButton, gbc);

        // Adding components to the frame
        frame.add(greetingLabel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);

//        // Optional: status bar or additional information at the bottom
//        JLabel statusLabel = new JLabel("Ready", JLabel.CENTER);
//        frame.add(statusLabel, BorderLayout.SOUTH);


        // Center the frame on the screen and make it visible
        viewMerchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayMerchandise();
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    private void displayMerchandise() {
        // Create a new frame to display merchandise
        JFrame merchandiseFrame = new JFrame("Merchandise List");
        merchandiseFrame.setSize(500, 300);

        // Table to display merchandise
        String[] columns = {"Name", "Unit Cost", "Unit Price", "Stock Level"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        refreshMerchandiseTable();

        //Load merchandise data into the table
//         for (Merchandise m : businessOwner.getStore().getMerchandiseList()) {
//             model.addRow(new Object[]{m.getName(), m.getUnitCost(), m.getUnitPrice(), m.getStockLevel()});
//         }

        // Buttons for add, delete, search
        JButton addMerchButton = new JButton("Add New");
        JButton deleteMerchButton = new JButton("Delete");
        JButton searchMerchButton = new JButton("Search");

        // Add action listeners to these buttons
        addMerchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMerchandise();
            }
        });

        deleteMerchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    // Assuming the first column contains the name of the merchandise
                    String merchandiseName = model.getValueAt(selectedRow, 0).toString();

                    // Remove merchandise from the store
                    businessOwner.getStore().deleteMerchandise(merchandiseName);

                    // Refresh the merchandise table
                    refreshMerchandiseTable();
                } else {
                    JOptionPane.showMessageDialog(merchandiseFrame, "Please select a merchandise to delete.");
                }
            }
        });

        searchMerchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSearchDialog(merchandiseFrame);
            }
        });

        // Layout for buttons
        JPanel merchButtonPanel = new JPanel();
        merchButtonPanel.add(addMerchButton);
        merchButtonPanel.add(deleteMerchButton);
        merchButtonPanel.add(searchMerchButton);

        // Add components to the frame
        merchandiseFrame.add(new JScrollPane(table), BorderLayout.CENTER);
        merchandiseFrame.add(merchButtonPanel, BorderLayout.SOUTH);

        // Display the frame
        merchandiseFrame.setLocationRelativeTo(null);
        merchandiseFrame.setVisible(true);
    }

    private void refreshMerchandiseTable() {
        model.setRowCount(0); // Clear existing data
        // Load merchandise data
        List<Merchandise> merchandiseList = businessOwner.getStore().getMerchandiseList();
        for (Merchandise m : merchandiseList) {
            model.addRow(new Object[]{m.getName(), m.getUnitCost(), m.getUnitPrice(), m.getStockLevel()});
        }
    }

    private void addMerchandise() {
        // Create a new frame for adding new merchandise
        JFrame addFrame = new JFrame("Add New Merchandise");
        addFrame.setSize(300, 200);

        // Panel for form fields
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5)); // 5 rows, 2 columns

        // Form fields
        formPanel.add(new JLabel("Name:"));
        JTextField nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Unit Cost:"));
        JTextField unitCostField = new JTextField();
        formPanel.add(unitCostField);

        formPanel.add(new JLabel("Unit Price:"));
        JTextField unitPriceField = new JTextField();
        formPanel.add(unitPriceField);

        formPanel.add(new JLabel("Stock Level:"));
        JTextField stockLevelField = new JTextField();
        formPanel.add(stockLevelField);

        // Submit button
        JButton submitButton = new JButton("Add Merchandise");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Parse input fields and add new merchandise
                String name = nameField.getText();
                double unitCost = Double.parseDouble(unitCostField.getText());
                int unitPrice = Integer.parseInt(unitPriceField.getText());
                int stockLevel = Integer.parseInt(stockLevelField.getText());

                // Add new merchandise to the store
                businessOwner.getStore().addNewMerchandise(name, unitCost, unitPrice, stockLevel);

                refreshMerchandiseTable();

                // Close the add frame
                addFrame.dispose();

                // Optionally, refresh the merchandise display
                // displayMerchandise();
            }
        });


        // Add components to the frame
        addFrame.setLayout(new BorderLayout());
        addFrame.add(formPanel, BorderLayout.CENTER);
        addFrame.add(submitButton, BorderLayout.SOUTH);

        // Display the frame
        addFrame.setLocationRelativeTo(null);
        addFrame.setVisible(true);
    }

    private void showSearchDialog(JFrame frame) {
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
                if (!searchAndHighlightMerchandise(searchText)) {
                    JOptionPane.showMessageDialog(searchDialog, "Merchandise not found.");
                }
                searchDialog.dispose();
            }
        });

        searchDialog.setLocationRelativeTo(frame);
        searchDialog.setVisible(true);
    }


    private boolean searchAndHighlightMerchandise(String searchText) {
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




    public static void main(String[] args) {
        // Here, you should create an instance of Store and BusinessOwner
        // For example:
        Store store = new Store();
        BusinessOwner owner = new BusinessOwner("Angel", "123", "123", store);
        store.loadMerchandiseList();
        new OwnerGUI(owner);
    }
}

