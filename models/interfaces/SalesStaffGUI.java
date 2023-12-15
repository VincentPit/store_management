package models.interfaces;
import models.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;



public class SalesStaffGUI {

    private SalesStaff salesStaff;
    private transactionViewer tv;



    public SalesStaffGUI(SalesStaff salesStaff) {
        this.salesStaff = salesStaff;
        this.tv = new transactionViewer(salesStaff.getStoreAccess());

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
                tv.display();
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
                frame.dispose();
                new LoginInterface(salesStaff.getStoreAccess());
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    private void openSalesStaffMerchandiseGUI() {

        JFrame frame = new JFrame("Items for Sale");
        frame.setSize(500, 300);

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


        JScrollPane scrollPane = new JScrollPane(table);
        JButton sellButton = new JButton("Sell");
        JButton searchMerchButton = new JButton("Search");

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


        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.add(sellPanel, BorderLayout.SOUTH);
        refreshMerchandiseTable(model);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    private void refreshMerchandiseTable(DefaultTableModel model) {
        model.setRowCount(0); // Clear existing data
        List<Merchandise> merchandiseList = salesStaff.getMerchandiseList();

        for (Merchandise m : merchandiseList) {
            model.addRow(new Object[]{m.getName(), m.getUnitPrice(), m.getStockLevel()});
        }
    }


    private void handleSellButtonClick(JTable table, JFrame frame,DefaultTableModel model) {
        // Get the selected row index
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select an item to sell.");
            return;
        }

        String itemName = (String) table.getValueAt(selectedRow, 0);

        // Show input dialog to get the quantity
        String quantityString = JOptionPane.showInputDialog(frame, "Enter quantity for " + itemName + ":", "Sell Item", JOptionPane.QUESTION_MESSAGE);
        if (quantityString != null && !quantityString.isEmpty()) {
            try {
                int quantity = Integer.parseInt(quantityString);
                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(frame, "Invalid Quantity. Quantity must be greater than 0.");
                    return; // Return here to prevent further processing
                }
                Merchandise m = salesStaff.findMerchandise(itemName);
                if (quantity > m.getStockLevel()) {
                    JOptionPane.showMessageDialog(frame, "Insufficient Stock. Order cannot be processed.");
                    return;
                }
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








}
