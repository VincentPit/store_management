package models;
import models.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
public class Store implements Serializable {
    private List<User> users;
    private List<Transaction> transactions;
    private List<Merchandise> merchandiseList;

    public Store() {

        this.users = new ArrayList<User>();
        BusinessOwner owner = new BusinessOwner("Angel", "123", "123", this, null);
        this.users.add(owner);
        InventoryManager inventoryManager = new InventoryManager("Aaron","124", "124",this,null);
        this.users.add(inventoryManager);
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

    //Angel: 我感觉不需要，而且就正常contains了就行
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

    public void addNewMerchandise(String name, double unitCost, int unitPrice, int stockLevel) {
        Merchandise m = new Merchandise(name, unitCost, unitPrice, stockLevel);
        merchandiseList.add(m);
        saveMerchandiseList();
    }

    public void deleteMerchandise(String merchandiseName) {
        Merchandise toRemove = null;
        for (Merchandise m : merchandiseList) {
            if (m.getName().equals(merchandiseName)) {
                toRemove = m;
                break;
            }
        }
        if (toRemove != null) {
            merchandiseList.remove(toRemove);
        }
        saveMerchandiseList();
    }


    public String deleteUser(String staffCode){
        for (User user : users) {
            // Check if the current user has the specified staffNode
            if (user.getStaffCode().equals(staffCode)) {
                // Remove the user from the ArrayList
                users.remove(user);
                return "User with staffNode " + staffCode + " deleted successfully."; // Exit the method once the user is deleted
            }
        }
        return "User with staffNode " + staffCode + " not found.";
    }

    public Boolean findUserByStaffCode(String staffCode){
        for (User user : users) {
            if (user.getStaffCode().equals(staffCode)) {
                return true; // User found
            }
        }
        return false; // User not found
    }

    public User loginByName(String name, String pwd){
        User empt = new User();
        for (User user : users) {
            if (user.getName().equals(name)) {
                // Assuming User class has a getPassword() method
                if (user.getPwd().equals(pwd)) {
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
    
    public User loginByStaffCode(String staffCode , String pwd){
        User empt = new User();
        for (User user : users) {
            if (user.getStaffCode().equals(staffCode)) {
                // Assuming User class has a getPassword() method
                if (user.getPwd().equals(pwd)) {
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
    
    
    public void setPrice(String name, double price){
        Merchandise merchandise = this.findMerchandise(name);
        if (merchandise.getName() == name){
            merchandise.setPrice(price);
        }
    }

    public void restock(String merchandiseName, int additionalStock) {
        for (Merchandise m : merchandiseList) {
            if (m.getName().equals(merchandiseName)) {
                int currentStock = m.getStockLevel();
                m.setStockLevel(currentStock + additionalStock);
                saveMerchandiseList();
                return; // Exit the function once the merchandise is found and restocked
            }
        }
        System.out.println("Merchandise not found: " + merchandiseName);
    }



    public void setDiscount(String name, double rate){
        for (Merchandise merchandise : merchandiseList) {
            if (merchandise.getName().equals(name)) {
                // Merchandise found, add unit cost and quantity to the result map
                merchandise.setPrice(rate * merchandise.getUnitPrice());
            }
        }
    }

    public List<Merchandise> getMerchandiseList(){
        loadMerchandiseList();
        return this.merchandiseList;
    }

    public List<User> getUserList(){

        return this.users;
    }

    public void updateTransactions(Transaction transaction) {
        this.transactions.add(transaction);
    }

    public List<Transaction> getAllTransactions(){
        return this.transactions;
    }

    public void saveMerchandiseList() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("merchandiseList.ser"))) {
            oos.writeObject(this.getMerchandiseList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void loadMerchandiseList() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("merchandiseList.ser"))) {
            List<Merchandise> loadedList = (List<Merchandise>) ois.readObject();
            merchandiseList = loadedList;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Search transactions by merchandise name
    public List<Transaction> searchTransactionsByMerchandise(String merchandiseName) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getMerchandise().getName().equals(merchandiseName)) {
                result.add(transaction);
            }
        }
        return result;
    }

    // Search transactions by time
    public List<Transaction> searchTransactionsByTime(LocalTime searchTime) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getTime().equals(searchTime)) {
                result.add(transaction);
            }
        }
        return result;
    }

    // Search transactions by date
    public List<Transaction> searchTransactionsByDate(LocalDate searchDate) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getDate().equals(searchDate)) {
                result.add(transaction);
            }
        }
        return result;
    }


}