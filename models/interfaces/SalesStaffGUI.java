package models.interfaces;
import java.time.*;
import models.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;


public class SalesStaffGUI {

    private SalesStaff salesStaff;
    private JFrame frame;
    private Store store; 

    public SalesStaffGUI(SalesStaff salesStaff) {
        this.salesStaff = salesStaff;
        this.store = salesStaff.getStore();

        frame = new JFrame("SalesStaff Main Menu");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel helloLabel = new JLabel("Hello " + salesStaff.getName());
        helloLabel.setHorizontalAlignment(JLabel.CENTER);

        JButton goToTransactionButton = new JButton("Go to Transaction");
        goToTransactionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSalesStaffTransactionGUI();
            }
        });

        JButton goToMerchandiseButton = new JButton("Go to Merchandise");
        goToMerchandiseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSalesStaffMerchandiseGUI();
            }
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.add(goToTransactionButton);
        buttonsPanel.add(goToMerchandiseButton);
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(helloLabel, BorderLayout.NORTH);
        mainPanel.add(buttonsPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
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
