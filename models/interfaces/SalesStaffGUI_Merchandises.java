package models.interfaces;
import models.*;

import java.time.LocalDate;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SalesStaffGUI_Merchandises {

    private SalesStaff salesStaff;

    private JFrame frame;
    private JTextArea merchandiseTextArea;
    private JComboBox<String> merchandiseComboBox;
    private JTextField quantityTextField;

    public SalesStaffGUI_Merchandises(SalesStaff salesStaff) {
        this.salesStaff = salesStaff;

        frame = new JFrame("SalesStaff Merchandise Management");
        frame.setSize(700, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        merchandiseTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(merchandiseTextArea);

        merchandiseComboBox = new JComboBox<>();
        quantityTextField = new JTextField();
        JButton sellButton = new JButton("Sell");

        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSellButtonClick();
            }
        });

        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel sellPanel = new JPanel();
        sellPanel.add(new JLabel("Merchandise:"));
        sellPanel.add(merchandiseComboBox);
        sellPanel.add(new JLabel("Quantity:"));
        
        // Set the preferred width of the quantity input box
        quantityTextField.setPreferredSize(new Dimension(100, 25));
        sellPanel.add(quantityTextField);
        
        sellPanel.add(sellButton);

        frame.add(sellPanel, BorderLayout.SOUTH);

        updateMerchandiseList();
        refreshMerchandiseTextArea();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void updateMerchandiseList() {
        List<Merchandise> merchandiseList = salesStaff.getStore().getMerchandiseList();
        merchandiseComboBox.removeAllItems();
        for (Merchandise merchandise : merchandiseList) {
            merchandiseComboBox.addItem(merchandise.getName());
        }
    }

    private void handleSellButtonClick() {
        String merchandiseName = (String) merchandiseComboBox.getSelectedItem();
        int quantitySold;
        try {
            quantitySold = Integer.parseInt(quantityTextField.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Quantity should be a valid number.");
            return;
        }

        if (quantitySold <= 0) {
            JOptionPane.showMessageDialog(frame, "Quantity should be a positive number.");
            return;
        }

        Merchandise selectedMerchandise = salesStaff.getStore().findMerchandise(merchandiseName);

        if (selectedMerchandise == null) {
            JOptionPane.showMessageDialog(frame, "Selected merchandise not found.");
            return;
        }

        if (quantitySold > selectedMerchandise.getStockLevel()) {
            JOptionPane.showMessageDialog(frame, "Insufficient stock for the selected merchandise.");
            return;
        }

        // Record the sale and update stock level
        salesStaff.recordSales(quantitySold, selectedMerchandise);
        selectedMerchandise.reduceStock(quantitySold);

        // Refresh UI
        updateMerchandiseList();
        refreshMerchandiseTextArea();

        JOptionPane.showMessageDialog(frame, "Sale recorded successfully.");
    }

    private void refreshMerchandiseTextArea() {
        List<Merchandise> merchandiseList = salesStaff.getStore().getMerchandiseList();
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-20s%-20s%-20s%-20s\n", "Merchandise", "Stock Level", "Unit Price", "Unit Cost"));
        for (Merchandise merchandise : merchandiseList) {
            sb.append(String.format("%-20s%-20d%-20.2f%-20.2f\n",
                    merchandise.getName(), merchandise.getStockLevel(), merchandise.getUnitPrice(), merchandise.getUnitCost()));
        }
        merchandiseTextArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        // Assuming created an instance of SalesStaff
        Store store = new Store();
        LocalDate date = LocalDate.now();
        User user = store.getUserList().get(0);
        SalesStaff salesStaff = new SalesStaff(user.getName(), user.getStaffCode(), store, user.getPwd(), date);

        // Create and show the SalesStaff_Merchandise GUI
        SwingUtilities.invokeLater(() -> new SalesStaffGUI_Merchandises(salesStaff));
    }
}
