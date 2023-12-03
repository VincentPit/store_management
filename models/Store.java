package models;
import models.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
class Store implements Serializable {
    private List<User> users;
    private List<Transaction> transactions;
    private List<Merchandise> merchandiseList;

    public Store() {
        this.users = new ArrayList<User>();
        this.transactions = new ArrayList<Transaction>();
        this.merchandiseList = new ArrayList<Merchandise>();
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public Boolean findUserByName(String name){
        for (User user : users) {
            if (user.getName().equals(name)) {
                return true; // User found
            }
        }
        return false; // User not found
    }


    public Merchandise findMerchandise(String name) {
        Map<String, Integer> result = new HashMap<>();
    
        for (Merchandise merchandise : merchandiseList) {
            if (merchandise.getName().equals(name)) {
                // Merchandise found, add unit cost and quantity to the result map
                return merchandise;
            }
        }


        // Merchandise not found, return an empty map
        return null;
    }


    public String deleteUser(String staffCode){
        for (User user : users) {
            // Check if the current user has the specified staffNode
            if (user.getStaffcode().equals(staffCode)) {
                // Remove the user from the ArrayList
                users.remove(user);
                return "User with staffNode " + staffCode + " deleted successfully."; // Exit the method once the user is deleted
            }
        }
        return "User with staffNode " + staffCode + " not found.";
    }

    public Boolean findUserByStaffcode(String staffCode){
        for (User user : users) {
            if (user.getStaffcode().equals(staffCode)) {
                return true; // User found
            }
        }
        return false; // User not found
    }

    public User loginByName(String name, String pwd){
        User empt = new User(null,null);
        for (User user : users) {
            if (user.getName().equals(name)) {
                // Assuming User class has a getPassword() method
                if (user.getPassword().equals(pwd)) {
                    return user; // Successful login
                } else {
                    empt.setName("Incorrect password");
                    return empt; // Incorrect password
                }
            }
        }
        empt.setName("User not found");
        return empt; 
    }
    
    public User loginByStaffcode(String staffCode , String pwd){
        User empt = new User(null,null);
        for (User user : users) {
            if (user.getStaffcode().equals(staffCode)) {
                // Assuming User class has a getPassword() method
                if (user.getPassword().equals(pwd)) {
                    return user; // Successful login
                } else {
                    empt.setName("Incorrect password");
                    return empt; // Incorrect password
                }
            }
        }
        empt.setName("User not found");
        return empt;
    }
    
    
    public void setPrice(string name, double price){
        Merchandise merchandise = this.findMerchandise(name);
        if (merchandise.getName() == name){
            merchandise.setPrice(price);
        }
    }

    public void setDiscount(String name, double rate){
        for (Merchandise merchandise : merchandiseList) {
            if (merchandise.getName().equals(name)) {
                // Merchandise found, add unit cost and quantity to the result map
                merchandise.setPrice(rate * merchandise.getPrice());
            }
        }


    }

    public void updateTransactions(Transaction transaction) {
        this.transactions.add(transaction);
    }

    
}
