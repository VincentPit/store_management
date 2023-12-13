package models.interfaces;
import java.time.*;
import models.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.concurrent.Flow;


public class SalesStaffGUI {

    private SalesStaff salesStaff;
    private JFrame frame;
    private Store store; 

    public SalesStaffGUI(SalesStaff salesStaff) {

        frame = new JFrame("Sales Staff Dashboard");
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

        SwingUtilities.invokeLater(() -> new SalesStaffGUI_Transaction(salesStaff));

        frame.dispose();
    }

    private void openSalesStaffMerchandiseGUI() {

        SwingUtilities.invokeLater(() -> new SalesStaffGUI_Merchandises(salesStaff));

        frame.dispose();
    }

    public static void main(String[] args) {
        Store store = new Store();
        LocalDate date = LocalDate.now();
        User user = store.getUserList().get(0);


        SalesStaff salesStaff = new SalesStaff(user.getName(), user.getStaffCode(), store, user.getPwd(), date);

        SwingUtilities.invokeLater(() -> new SalesStaffGUI(salesStaff));
    }
}
