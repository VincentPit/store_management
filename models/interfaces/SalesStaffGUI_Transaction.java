package models.interfaces;
import models.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class SalesStaffGUI_Transaction {

    private SalesStaff salesStaff;
    private JFrame frame;
    private JTextArea resultTextArea;
    private JTextField dateTextField;
    private JTextField timeTextField;
    private JTextField merchandiseTextField;

    public SalesStaffGUI_Transaction(SalesStaff salesStaff) {
        this.salesStaff = salesStaff;

        frame = new JFrame("SalesStaff Transaction");
        frame.setSize(1200, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        resultTextArea = new JTextArea();
        resultTextArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(resultTextArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        dateTextField = new JTextField("yyyy-MM-dd");
        timeTextField = new JTextField("HH:mm:ss");
        merchandiseTextField = new JTextField("Merchandise Name");

        JButton searchByDateButton = new JButton("Search by Date");
        JButton searchByTimeButton = new JButton("Search by Time");
        JButton searchByMerchandiseButton = new JButton("Search by Merchandise");
        JButton backButton = new JButton("Back"); // Add a back button

        searchByDateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSearchByDate();
            }
        });

        searchByTimeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSearchByTime();
            }
        });

        searchByMerchandiseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSearchByMerchandise();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create and show the SalesStaffGUI
                SwingUtilities.invokeLater(() -> {
                    frame.dispose(); // Close the current frame
                    SalesStaffGUI salesStaffGUI = new SalesStaffGUI(salesStaff);
                    
                });
            }
        });
        

        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Date:"));
        searchPanel.add(dateTextField);
        searchPanel.add(searchByDateButton);

        searchPanel.add(new JLabel("Time:"));
        searchPanel.add(timeTextField);
        searchPanel.add(searchByTimeButton);

        searchPanel.add(new JLabel("Merchandise:"));
        searchPanel.add(merchandiseTextField);
        searchPanel.add(searchByMerchandiseButton);

        searchPanel.add(new JLabel("Back"));


        searchPanel.add(backButton);

        frame.add(searchPanel, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Initialize and display the template
        initializeAndDisplayTemplate();
    }

    private void initializeAndDisplayTemplate() {
        List<Transaction> allTransactions = salesStaff.getAllTransactions();
    
        // Display attribute names as labels
        resultTextArea.setText(String.format("%-25s%-30s%-25s%-30s\n", "Time", "Date", "Merchandise", "Quantity"));
    
        if (!allTransactions.isEmpty()) {
            // Display all transactions
            for (Transaction transaction : allTransactions) {
                displayTransaction(transaction);
            }
        } else {
            resultTextArea.append("No transactions found.");
        }
    }
    
    

    private void displayTransaction(Transaction transaction) {
        resultTextArea.append(String.format(" %-25s%-30s%-25s%-30s\n",
                transaction.getTime(),
                transaction.getDate(),
                transaction.getMerchandise().getName(),
                transaction.getQuantity()));
    }
    

    private void handleSearchByDate() {
        String dateStr = dateTextField.getText();
        LocalDate date = LocalDate.parse(dateStr);

        List<Transaction> result = salesStaff.searchSaleHistoryByDate(date);
        displaySearchResult(result);
    }

    private void handleSearchByTime() {
        String timeStr = timeTextField.getText();
        LocalTime time = LocalTime.parse(timeStr);

        List<Transaction> result = salesStaff.searchSaleHistoryByTime(time);
        displaySearchResult(result);
    }

    private void handleSearchByMerchandise() {
        String merchandiseName = merchandiseTextField.getText();

        List<Transaction> result = salesStaff.searchSaleHistoryByMerchandise(merchandiseName);
        displaySearchResult(result);
    }

    private void displaySearchResult(List<Transaction> result) {
        resultTextArea.setText(""); // Clear previous content
    
        if (result.isEmpty()) {
            resultTextArea.append("No transactions found.");
        } else {
            // Display attribute names as labels
            Transaction sampleTransaction = result.get(0);
            resultTextArea.append(String.format("%-25s%-30s%-25s%-30s\n", "Time", "Date", "Merchandise", "Quantity"));
    
            // Display all transactions
            for (Transaction transaction : result) {
                resultTextArea.append(String.format("%-25s%-30s%-25s%-30s\n",
                        transaction.getTime(),
                        transaction.getDate(),
                        transaction.getMerchandise().getName(),
                        transaction.getQuantity()));
            }
        }
    }
    

    public static void main(String[] args) {
        // Create an instance of Store and SalesStaff
        Store store = new Store();
        LocalDate date = LocalDate.now();
        User user = store.getUserList().get(0);
        SalesStaff salesStaff = new SalesStaff(user.getName(), user.getStaffCode(), store, user.getPwd(), date);

        // Create and show SalesStaffGUI
        SwingUtilities.invokeLater(() -> new SalesStaffGUI_Transaction(salesStaff));
    }
}
