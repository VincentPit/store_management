package interfaces;
import models.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class InventoryManagerInterface1 {

    private JFrame frame;
    private JTextArea displayArea;
    private JScrollPane scrollPane;
    private Store store;

    public InventoryManagerInterface1(Store store) {
        this.store = store;

        // Create the frame
        frame = new JFrame("Inventory Manager");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        displayArea = new JTextArea();
        displayArea.setEditable(false); // Make the display area read-only
        scrollPane = new JScrollPane(displayArea); // Add displayArea to the scroll pane

        JButton addButton = new JButton("Add");
        JButton exitButton = new JButton("Exit");

        // Set layout manager
        frame.setLayout(new BorderLayout());

        // Add components to the frame
        frame.add(scrollPane, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(exitButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners to buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add logic to handle adding new items
                addItem();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the frame
            }
        });

        // Center the frame on the screen
        frame.setLocationRelativeTo(null);

        // Make the frame visible
        frame.setVisible(true);
    }

    private void addItem() {
        // Add code to handle the addition of new items to the inventory
        // This might involve opening a new dialog where details of the new item can be entered
    }

    public static void main(String[] args) {
        // Create a store and some sample data
        Store store = new Store();// modify as needed

        // Add sample items to the store for demonstration

        // Create the inventory interface
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InventoryManagerInterface1(store);
            }
        });
    }
}
