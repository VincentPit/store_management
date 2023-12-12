package models.interfaces;

import models.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.List;
import java.util.*;

public class OwnerGUI {
    private BusinessOwner businessOwner;
    //private DefaultTableModel model; // Declare model here
    //private JTable table; //Declare table


    public OwnerGUI(BusinessOwner businessOwner) {
        this.businessOwner = businessOwner;
        createAndShowGUI();
    }


    private void createAndShowGUI() {
        // Create and set up the window
        JFrame frame = new JFrame("Business Owner Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(430, 280);

        // Set layout manager for the frame
        frame.setLayout(new BorderLayout(10, 10));

        // Greeting label
        JLabel greetingLabel = new JLabel("Hi, " + businessOwner.getName());
        greetingLabel.setFont(new Font("Arial", Font.BOLD, 20));
        greetingLabel.setHorizontalAlignment(JLabel.LEFT);
        greetingLabel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8)); // Padding around label

        // Create a panel for buttons with a GridBagLayout for flexibility
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // Padding around buttons

        // Creating buttons
        JButton viewMerchButton = new JButton("View Merchandise");
        JButton viewPersonnelButton = new JButton("View Personnel");
        JButton exitButton = new JButton("Exit"); // Exit button

        // Adding buttons to the panel
        buttonPanel.add(viewMerchButton, gbc);
        buttonPanel.add(viewPersonnelButton, gbc);
        buttonPanel.add(exitButton, gbc); // Add the exit button

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

        viewPersonnelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPersonnel();
            }
        });

        // Action listener for the exit button
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the current window
                new LoginInterface(businessOwner.getStore());
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
        String[] columns = {"Name", "Unit Cost", "Unit Price", "Stock Level", "Qty Sold"};
        //model = new DefaultTableModel(columns, 0);
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // This will make all cells in the table non-editable
                return false;
            }
        };
        JTable table = new JTable(model);
        refreshMerchandiseTable(model);

        //Load merchandise data into the table
//         for (Merchandise m : businessOwner.getStore().getMerchandiseList()) {
//             model.addRow(new Object[]{m.getName(), m.getUnitCost(), m.getUnitPrice(), m.getStockLevel()});
//         }

        // Buttons for add, delete, search
        JButton addMerchButton = new JButton("Add New");
        JButton deleteMerchButton = new JButton("Delete");
        JButton searchMerchButton = new JButton("Search");
        JButton editMerchandiseButton = new JButton("Edit");

        // Add action listeners to these buttons
        addMerchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMerchandise(model);
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
                    businessOwner.deleteMerchandise(merchandiseName);

                    // Refresh the merchandise table
                    refreshMerchandiseTable(model);
                } else {
                    JOptionPane.showMessageDialog(merchandiseFrame, "Please select a merchandise to delete.");
                }
            }
        });

        searchMerchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSearchDialog(merchandiseFrame, table);
            }
        });

        editMerchandiseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    displayEditMerchandiseForm(model, table, selectedRow);
                } else {
                    JOptionPane.showMessageDialog(merchandiseFrame, "Please select a merchandise to edit.");
                }
            }
        });


        // Layout for buttons
        JPanel merchButtonPanel = new JPanel();
        merchButtonPanel.add(addMerchButton);
        merchButtonPanel.add(deleteMerchButton);
        merchButtonPanel.add(searchMerchButton);
        merchButtonPanel.add(editMerchandiseButton);

        // Add components to the frame
        merchandiseFrame.add(new JScrollPane(table), BorderLayout.CENTER);
        merchandiseFrame.add(merchButtonPanel, BorderLayout.SOUTH);

        // Display the frame
        merchandiseFrame.setLocationRelativeTo(null);
        merchandiseFrame.setVisible(true);
    }

    private void refreshMerchandiseTable(DefaultTableModel model) {
        model.setRowCount(0); // Clear existing data
        // Load merchandise data
        List<Merchandise> merchandiseList = businessOwner.getMerchandiseList();
        
        for (Merchandise m : merchandiseList) {
            model.addRow(new Object[]{m.getName(), m.getUnitCost(), m.getUnitPrice(), m.getStockLevel(), m.getQtySold()});
        }
    }

    private void displayEditMerchandiseForm(DefaultTableModel merchandiseModel, JTable merchandiseTable, int selectedRow) {
        JFrame editFrame = new JFrame("Edit Merchandise");
        editFrame.setLayout(new GridLayout(0, 2));
        editFrame.setSize(400, 300);

        // Create and populate fields for merchandise attributes
        JTextField nameField = new JTextField(merchandiseModel.getValueAt(selectedRow, 0).toString());
        JTextField unitPriceField = new JTextField(merchandiseModel.getValueAt(selectedRow, 2).toString());


        // Add fields to the frame
        editFrame.add(new JLabel("Name:")); editFrame.add(nameField);
        editFrame.add(new JLabel("Unit Price:")); editFrame.add(unitPriceField);


        // Save button
        JButton saveButton = new JButton("Save");
        editFrame.add(saveButton);

        JButton resetButton = new JButton("Reset");
        editFrame.add(resetButton);

        // Save button action listener
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Validate and save changes
                // Update the merchandiseModel and possibly the underlying data
                merchandiseModel.setValueAt(nameField.getText(), selectedRow, 0);
                merchandiseModel.setValueAt(Double.parseDouble(unitPriceField.getText()), selectedRow, 2);


                // Close the edit frame
                editFrame.dispose();

                // Optionally, update the underlying data structure and persist changes
            }
        });

        // Store the original values to reset them later
        String originalName = nameField.getText();
        String originalUnitPrice = unitPriceField.getText();

        // Add action listener to the reset button
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Reset the fields to the original values
                nameField.setText(originalName);
                unitPriceField.setText(originalUnitPrice);

            }
        });


        editFrame.setLocationRelativeTo(null);
        editFrame.setVisible(true);
    }


    private void addMerchandise(DefaultTableModel model) {
        // Create a new frame for adding new merchandise
        JFrame addFrame = new JFrame("Add New Merchandise");
        addFrame.setSize(250, 150);
        // Panel for form fields
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 5)); // 5 rows, 2 columns

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
        JPanel buttonPanel = new JPanel(); // Create a panel for buttons
        JButton submitButton = new JButton("Add");
        JButton resetButton = new JButton("Reset");
        buttonPanel.add(submitButton); // Add the submit button to the panel
        buttonPanel.add(resetButton); // Add the reset button to the panel



        // Submit button

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Parse input fields and add new merchandise
                String name = nameField.getText();
                double unitCost = Double.parseDouble(unitCostField.getText());
                int unitPrice = Integer.parseInt(unitPriceField.getText());

                // Add new merchandise to the store
                businessOwner.addNewMerchandise(name, unitCost, unitPrice, 0);

                refreshMerchandiseTable(model);

                // Close the add frame
                addFrame.dispose();

                // Optionally, refresh the merchandise display
                // displayMerchandise();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Parse input fields and add new merchandise
                nameField.setText("");
                unitCostField.setText("");
                unitPriceField.setText("");

            }
        });


        // Add components to the frame
        addFrame.setLayout(new BorderLayout());
        addFrame.add(formPanel, BorderLayout.CENTER);
        //addFrame.add(submitButton);
        addFrame.add(buttonPanel,BorderLayout.SOUTH);

        // Display the frame
        addFrame.setLocationRelativeTo(null);
        addFrame.setVisible(true);
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

    private void displayPersonnel() {
        // Create a new frame
        JFrame personnelFrame = new JFrame("Personnel List");
        personnelFrame.setSize(500, 300);

        // Table model and table for personnel data
        String[] columns = {"StaffCode","Name", "Salary", "Date of Enrolment", "Job Type"};
        //DefaultTableModel personnelModel = new DefaultTableModel(columns, 0);
        DefaultTableModel personnelModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // This will make all cells in the table non-editable
                return false;
            }
        };
        JTable personnelTable = new JTable(personnelModel);

        // Populate the table with personnel data
        refreshPersonnelTable(personnelModel);

        // ComboBox for filtering
        String[] jobTypes = {"All", "SalesStaff", "InventoryManager", "BusinessOwner"};
        JComboBox<String> filterComboBox = new JComboBox<>(jobTypes);
        filterComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedType = (String) filterComboBox.getSelectedItem();
                filterPersonnelTable(personnelModel, selectedType);
            }
        });

        // Layout for ComboBox and table
        JPanel filterPanel = new JPanel();
        filterPanel.add(new JLabel("Filter by Job Type:"));
        filterPanel.add(filterComboBox);

        personnelFrame.add(filterPanel, BorderLayout.NORTH);
        personnelFrame.add(new JScrollPane(personnelTable), BorderLayout.CENTER);

        // Display the frame
        personnelFrame.setLocationRelativeTo(null);
        personnelFrame.setVisible(true);

        // Create "Add" button
        JButton addPersonnelButton = new JButton("Add");
        // Create "Delete" button
        JButton deletePersonnelButton = new JButton("Delete");
        JButton editPersonnelButton = new JButton("Edit");
        JButton searchButton = new JButton("Search");



        // Add the button to the panel or frame
        JPanel personnelButtonPanel = new JPanel();
        personnelButtonPanel.add(addPersonnelButton);
        personnelButtonPanel.add(deletePersonnelButton);
        personnelButtonPanel.add(editPersonnelButton);
        personnelButtonPanel.add(searchButton);


        // Add the panel to the frame
        personnelFrame.add(personnelButtonPanel, BorderLayout.SOUTH);

        addPersonnelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayAddPersonnelForm(personnelModel);
            }
        });

        deletePersonnelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = personnelTable.getSelectedRow();
                if (selectedRow >= 0) {
                    // Assuming staff code is in the first column
                    String staffCode = personnelModel.getValueAt(selectedRow, 0).toString();

                    // Confirm before deletion
                    int confirm = JOptionPane.showConfirmDialog(
                            personnelFrame,
                            "Are you sure you want to delete the personnel with staff code " + staffCode + "?",
                            "Delete Personnel",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (confirm == JOptionPane.YES_OPTION) {
                        // Call the deleteUser method
                        String resultMessage = businessOwner.getStore().deleteUser(staffCode);

                        // Show result message
                        JOptionPane.showMessageDialog(personnelFrame, resultMessage);

                        // Remove the personnel from the table model if deletion was successful
                        if (resultMessage.contains("successfully")) {
                            personnelModel.removeRow(selectedRow);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(personnelFrame, "Please select a personnel to delete.");
                }
            }
        });

        editPersonnelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = personnelTable.getSelectedRow();
                if (selectedRow >= 0) {
                    displayEditPersonnelForm(personnelModel, selectedRow);
                } else {
                    JOptionPane.showMessageDialog(personnelFrame, "Please select a personnel to edit.");
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displaySearchForm(personnelModel, personnelTable);
            }
        });



    }

    private void refreshPersonnelTable(DefaultTableModel model) {

        model.setRowCount(0); // Clear existing data
        // Assuming getPersonnelList() returns a list of Personnel objects
        List<User> personnelList = businessOwner.getUserList();
        for (User p : personnelList) {
            //System.out.println("Adding to table: " + p.getSalary());
            model.addRow(new Object[]{p.getStaffCode(),p.getName(), p.getSalary(), p.getDateOfEnrolment(), p.getType()});
        }
    }

    private void filterPersonnelTable(DefaultTableModel model, String jobType) {
        refreshPersonnelTable(model); // Refresh to show all personnel first
        if (!jobType.equals("All")) {
            for (int i = model.getRowCount() - 1; i >= 0; i--) {
                if (!model.getValueAt(i, 4).equals(jobType)) {
                    model.removeRow(i); // Remove rows that don't match the filter
                }
            }
        }
    }

    private void displayAddPersonnelForm(DefaultTableModel model) {
        JFrame addPersonnelFrame = new JFrame("Add New Personnel");
        addPersonnelFrame.setSize(300, 200);

        // Form fields
        JPanel formPanel = new JPanel(new GridLayout(0, 2)); // 0 rows, 2 columns for labels and fields


        // Name field
        formPanel.add(new JLabel("Name:"));
        JTextField nameField = new JTextField();
        formPanel.add(nameField);

        // Job Type dropdown
        formPanel.add(new JLabel("Job Type:"));
        String[] jobTypes = {"", "SalesStaff", "InventoryManager", "BusinessOwner"};
        JComboBox<String> jobTypeComboBox = new JComboBox<>(jobTypes);
        jobTypeComboBox.setSelectedIndex(0); // Set blank item as selected
        formPanel.add(jobTypeComboBox);

        // Salary field (non-editable, auto-filled)
        formPanel.add(new JLabel("Salary:"));
        JTextField salaryField = new JTextField();
        salaryField.setEditable(true);
        formPanel.add(salaryField);

        // Populate salary based on job type selection
        jobTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedJobType = (String) jobTypeComboBox.getSelectedItem();
                //double salary = calculateSalaryForJobType(selectedJobType);
                //String selectedJobType = (String) jobTypeComboBox.getSelectedItem();
                if (!selectedJobType.isEmpty()) {
                    double recommendedSalary = calculateSalaryForJobType(selectedJobType);
                    salaryField.setText(String.format("%.2f", recommendedSalary)); // Set as a formatted string
                } else {
                    salaryField.setText("");
                }
                //salaryField.setText(String.valueOf(salary));
            }
        });

        // Submit button
        JButton submitButton = new JButton("Add Personnel");
        // Add ActionListener to submitButton here
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String jobType = (String) jobTypeComboBox.getSelectedItem();
                double salary;

                try {
                    salary = Double.parseDouble(salaryField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(addPersonnelFrame, "Invalid salary. Please enter a valid number.");
                    return;
                }
                //System.out.println("Salary: " + salary); // Debugging line
                LocalDate dateOfEnrolment = LocalDate.now();

                // Generate staff code and password
                String staffCode = generateStaffCode(jobType);
                String password = staffCode; // Password is same as staff code

                // Create new Personnel object (adjust constructor as needed)
                User newPersonnel = new User(name, staffCode, password, businessOwner.getStore(), jobType, dateOfEnrolment);
                newPersonnel.setSalary(salary);
                //System.out.println("Before adding new personnel:");
//                for (User p : businessOwner.getStore().getUserList()) {
//                    System.out.println("Existing Personnel - Name: " + p.getName() + ", Salary: " + p.getSalary());
//                }


                // Add to personnel list, save, refresh table, etc.
                businessOwner.getStore().addUser(newPersonnel);
                refreshPersonnelTable(model);
                // ...

                // Display staff code and password
                JOptionPane.showMessageDialog(addPersonnelFrame,
                        "Staff Code: " + staffCode + "\nPassword: " + password +
                                "\n\nPlease share this information with the new personnel.",
                        "Personnel Details", JOptionPane.INFORMATION_MESSAGE);

                // Close the addPersonnelFrame
                addPersonnelFrame.dispose();
            }
        });

        // Add components to the frame
        addPersonnelFrame.add(formPanel, BorderLayout.CENTER);
        addPersonnelFrame.add(submitButton, BorderLayout.SOUTH);

        // Display the frame
        addPersonnelFrame.setLocationRelativeTo(null);
        addPersonnelFrame.setVisible(true);
    }

    private double calculateSalaryForJobType(String jobType) {
        if (jobType.equals("SalesStaff")) {
            return 1000.0;
        } else if (jobType.equals("InventoryManager")) {
            return 5000.0;
        }
        return 10000.0;
    }

    private String generateStaffCode(String jobType) {
        StringBuilder code = new StringBuilder();
        Random random = new Random();

        if ("InventoryManager".equals(jobType)) {
            code.append("2");
        } else if ("BusinessOwner".equals(jobType)) {
            code.append("1");
        } else if ("SalesStaff".equals(jobType)) {
            code.append("3");
        }
        do {
            while (code.length() < 3) {
                int nextChar = random.nextInt(26); // 26 letters
                // note the GUI added user's staff code will have 2 characters followed by 1 digit
                // the staff code of users added directly in the code will be made of 3 digits
                if (nextChar < 26) {
                    code.append((char) ('A' + nextChar));
                }
            }
        } while(!isStaffCodeUnique(code.toString()));

        return code.toString();
    }

    private boolean isStaffCodeUnique(String staffCode) {
        for (User user : businessOwner.getUserList()) {
            if (user.getStaffCode().equals(staffCode)) {
                return false;
            }
        }
        return true; // No matching staff code found
    }

    private void displayEditPersonnelForm(DefaultTableModel personnelModel, int selectedRow) {
        JFrame editFrame = new JFrame("Edit Personnel");
        editFrame.setSize(300, 200);
        editFrame.setLayout(new GridLayout(0, 2));

        String currentName = personnelModel.getValueAt(selectedRow, 1).toString();
        String currentSalary = personnelModel.getValueAt(selectedRow, 2).toString();
        String currentJobType = personnelModel.getValueAt(selectedRow, 4).toString();

        // Create and populate fields
        JTextField nameField = new JTextField(currentName);
        JTextField salaryField = new JTextField(currentSalary);
        JComboBox<String> jobTypeComboBox = new JComboBox<>(new String[]{"SalesStaff", "InventoryManager", "BusinessOwner"});
        jobTypeComboBox.setSelectedItem(currentJobType);

        // Add components to edit frame
        editFrame.add(new JLabel("Name:"));
        editFrame.add(nameField);
        editFrame.add(new JLabel("Salary:"));
        editFrame.add(salaryField);
        editFrame.add(new JLabel("Job Type:"));
        editFrame.add(jobTypeComboBox);

        JButton saveButton = new JButton("Save");
        JButton resetButton = new JButton("Reset");
        editFrame.add(saveButton);
        editFrame.add(resetButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Validate and save changes
                // Update the model and possibly the underlying data
                personnelModel.setValueAt(nameField.getText(), selectedRow, 1);
                personnelModel.setValueAt(salaryField.getText(), selectedRow, 2);
                personnelModel.setValueAt(jobTypeComboBox.getSelectedItem(), selectedRow, 4);

                // Close the edit frame
                editFrame.dispose();

            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameField.setText(currentName);
                salaryField.setText(currentSalary);
                jobTypeComboBox.setSelectedItem(currentJobType);
            }
        });

        editFrame.setLocationRelativeTo(null);
        editFrame.setVisible(true);
    }


    private void displaySearchForm(DefaultTableModel model, JTable table) {
        JFrame searchFrame = new JFrame("Search Personnel");
        searchFrame.setLayout(new GridLayout(0, 2));
        searchFrame.setSize(300, 200);

        // Create input fields for each attribute
        JTextField codeField = new JTextField();
        JTextField nameField = new JTextField();

        searchFrame.add(new JLabel("Staff Code:"));
        searchFrame.add(codeField);
        searchFrame.add(new JLabel("Name:"));
        searchFrame.add(nameField);

        JComboBox<String> jobTypeComboBox = new JComboBox<>();
        jobTypeComboBox.addItem(""); // Blank item
        jobTypeComboBox.addItem("SalesStaff");
        jobTypeComboBox.addItem("InventoryManager");
        jobTypeComboBox.addItem("BusinessOwner");
        jobTypeComboBox.setSelectedItem(""); // Set blank item as default

        searchFrame.add(new JLabel("Job Type:"));
        searchFrame.add(jobTypeComboBox);

        JButton doSearchButton = new JButton("Search");
        searchFrame.add(doSearchButton);
        JButton resetButton = new JButton("Reset");
        searchFrame.add(resetButton);

        doSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String jobType = (String) jobTypeComboBox.getSelectedItem();
                searchAndHighlight(model, table, codeField.getText(), nameField.getText(), jobType);
                searchFrame.dispose(); // Close the search frame
            }
        });


        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameField.setText("");
                codeField.setText("");
                jobTypeComboBox.setSelectedItem("");
                // Reset other fields as needed
            }
        });

        searchFrame.setLocationRelativeTo(null);
        searchFrame.setVisible(true);
    }

    private void searchAndHighlight(DefaultTableModel model, JTable table, String staffCode, String name, String jobType) {
        table.clearSelection();

        for (int i = 0; i < model.getRowCount(); i++) {
            boolean staffCodeMatch = staffCode.isEmpty() || containsIgnoreCase(model.getValueAt(i, 0).toString(), staffCode);
            boolean nameMatch = name.isEmpty() || containsIgnoreCase(model.getValueAt(i, 1).toString(), name);
            boolean jobTypeMatch = jobType.isEmpty() || containsIgnoreCase(model.getValueAt(i, 4).toString(), jobType);

            if (staffCodeMatch && nameMatch && jobTypeMatch) {
                table.addRowSelectionInterval(i, i);
            }
        }

        // Check if any row is selected
        if (table.getSelectedRowCount() == 0) {
            JOptionPane.showMessageDialog(table, "No record matches the search result.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    private boolean containsIgnoreCase(String src, String what) {
        return src.toLowerCase().contains(what.toLowerCase());
    }


}
