package models.interfaces;

import models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
        JLabel userLabel = new JLabel("Username or Staff Code");
        JLabel passwordLabel = new JLabel("Password");

        userInputField = new JTextField();
        passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");

        frame.setLayout(new GridLayout(3, 2));

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
                List<User> userList = store.getUserList();

                // Check if any user matches the input
                boolean loginSuccessful = false;
                for (User user : userList) {
                    if (userInput.equals(user.getName()) || userInput.equals(user.getStaffCode())) {
                        if (password.equals(user.getPwd())) {
                            loginSuccessful = true;
                            break;
                        }
                    }
                }

                if (loginSuccessful) {
                    User loggedInUser = findLoggedInUser(userInput);
                    if (loggedInUser instanceof BusinessOwner) {
                        frame.dispose();
                        new OwnerGUI((BusinessOwner) loggedInUser);
                    } else if(loggedInUser instanceof InventoryManager){
                        frame.dispose();
                        new InventoryManagerGUI((InventoryManager) loggedInUser);
                    } else if (loggedInUser instanceof SalesStaff){
                        frame.dispose();
                        new SalesStaffGUI((SalesStaff) loggedInUser);
                    } 
                    else {
                        JOptionPane.showMessageDialog(frame, "You are not authorized to access staff interfaces");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                }


                // Clear the password field after login attempt
                passwordField.setText("");
            }
        });

        // Center the frame on the screen
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }


    private User findLoggedInUser(String userInput) {
        for (User user : store.getUserList()) {
            if (userInput.equals(user.getName()) || userInput.equals(user.getStaffCode())) {
                return user;
            }
        }
        return null;
    }



}
