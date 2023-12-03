package interfaces;

import models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LoginInterface {

    private JFrame frame;
    private JTextField userInputField;
    private JPasswordField passwordField;

    private Store store;

    public LoginInterface(Store store) {
        this.store = store;

        // Create the frame
        frame = new JFrame("Login Interface");
        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        JLabel userLabel = new JLabel("Username or Staff Code:");
        JLabel passwordLabel = new JLabel("Password:");

        userInputField = new JTextField();
        passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");

        // Set layout manager
        frame.setLayout(new GridLayout(3, 2));

        // Add components to the frame
        frame.add(userLabel);
        frame.add(userInputField);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(new JLabel()); // Empty label for spacing
        frame.add(loginButton);

        // Add action listener to the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle login logic here
                String userInput = userInputField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);

                // Retrieve the list of users from the store
                List<User> userList = store.viewAllUser();

                // Check if any user matches the input
                boolean loginSuccessful = false;
                for (User user : userList) {
                    if (userInput.equals(user.getName()) || userInput.equals(user.getStaffcode())) {
                        if (password.equals(user.getPassword())) {
                            loginSuccessful = true;
                            break;
                        }
                    }
                }

                // Display appropriate message based on login success
                if (loginSuccessful) {
                    JOptionPane.showMessageDialog(frame, "Login successful!");
                    openNextInterface();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                }

                // Clear the password field after login attempt
                passwordField.setText("");
            }
        });

        // Center the frame on the screen
        frame.setLocationRelativeTo(null);

        // Make the frame visible
        frame.setVisible(true);
    }

    private void openNextInterface() {
        // Add code to switch to the next interface upon successful login
        // For simplicity, let's just close the current frame in this example
        frame.dispose();

        // Create and show the next interface (you can replace this with your actual next interface)
        new NextInterface(store);
    }

    public static void main(String[] args) {
        // Create a store and some sample users
        Store store = new Store();// modify

        store.addUser(new User("admin", "adminStaffCode", "password",store,null));

        // Create the login interface
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginInterface(store);
            }
        });
    }
}
