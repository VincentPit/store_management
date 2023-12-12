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

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("merchandiseList.ser"))) {
            List<Merchandise> loadedList = (List<Merchandise>) ois.readObject();
            merchandiseList = loadedList;
        } catch (FileNotFoundException e) {
            // File not found, create a new merchandise list
            this.merchandiseList = new ArrayList<Merchandise>();
            Merchandise m1 = new Merchandise("Laptop", 1000.0, 1200.0, 10);
            Merchandise m2 = new Merchandise("Phone", 500.0, 600.0, 20);
            this.merchandiseList.add(m1);
            this.merchandiseList.add(m2);
            saveMerchandiseList(); // Optionally save the new empty list to the file
        } catch (IOException | ClassNotFoundException e) {
            // Handle other exceptions (e.g., IOException, ClassNotFoundException)
            e.printStackTrace();
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("userList.ser"))) {
            List<User> loadedList = (List<User>) ois.readObject();
            this.users = loadedList;
        } catch (FileNotFoundException e) {
            // File not found, create a new user list
            this.users = new ArrayList<User>();
            BusinessOwner owner = new BusinessOwner("Angel", "123", "123", this, LocalDate.now());
            this.users.add(owner);
            InventoryManager inventoryManager = new InventoryManager("Aaron","234", "234",this,LocalDate.now());
            this.users.add(inventoryManager);
            SalesStaff salesStaff = new SalesStaff("Stephen", "345", this, "345",  LocalDate.now());
            this.users.add(salesStaff);
            System.out.println(users);
            saveUserList(); // Optionally save the new empty list to the file
        } catch (IOException | ClassNotFoundException e) {
            // Handle other exceptions (e.g., IOException, ClassNotFoundException)
            e.printStackTrace();
        }


        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("transactions.ser"))) {
            List<Transaction> loadedList = (List<Transaction>) ois.readObject();
            this.transactions = loadedList;
        } catch (FileNotFoundException e) {
            // File not found, create a new user list
            this.transactions = new ArrayList<Transaction>();
            saveTransactionList(); // Optionally save the new empty list to the file
        } catch (IOException | ClassNotFoundException e) {
            // Handle other exceptions (e.g., IOException, ClassNotFoundException)
            e.printStackTrace();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("userList.ser"))) {
            List<Merchandise> loadedList = (List<Merchandise>) ois.readObject();
            this.merchandiseList = loadedList;
        } catch (FileNotFoundException e) {
            // File not found, create a new user list
            this.merchandiseList = new ArrayList<Merchandise>();
            saveMerchandiseList(); // Optionally save the new empty list to the file
        } catch (IOException | ClassNotFoundException e) {
            // Handle other exceptions (e.g., IOException, ClassNotFoundException)
            e.printStackTrace();
        }
        

        //for testing
        // Add some sample transactions
        //transactions.add(new Transaction(LocalTime.now(), 2, m1, LocalDate.now()));
        //transactions.add(new Transaction(LocalTime.now(), 1, m2, LocalDate.now()));
        //Delete after testing


    }

    

    public void addUser(User user) {


        if (user.getType() == "SalesStaff"){
            SalesStaff new_user = new SalesStaff(user.getName(), user.getStaffCode(), user.getStore(), user.getPwd(), user.getDateOfEnrolment());
            this.users.add(new_user);
        } else if (user.getType() == "BusinessOwner"){
            BusinessOwner new_user = new BusinessOwner(user.getName(), user.getStaffCode(),  user.getPwd(),user.getStore(), user.getDateOfEnrolment());
            this.users.add(new_user);
        } else {
            InventoryManager new_user = new InventoryManager(user.getName(), user.getStaffCode(),  user.getPwd(),user.getStore(), user.getDateOfEnrolment());
            this.users.add(new_user);
        }

        
        saveUserList();
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


    public String deleteUser(String staffCode) {
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getStaffCode().equals(staffCode)) {
                iterator.remove();
                // Add logic here to save/update the user list to external storage if needed
                users.remove(user);
                this.saveUserList();
                return "User with staff code " + staffCode + " deleted successfully.";
            }
        }
        return "User with staff code " + staffCode + " not found.";
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
        this.loadUserList();
        return this.users;
    }

    public void updateTransactions(Transaction transaction) {
        if (transactions == null){
            this.transactions = new ArrayList<Transaction>();
        }
        this.transactions.add(transaction);
        saveTransactionList();
        
    }

    public List<Transaction> getAllTransactions(){
        loadTransactionList();
        return this.transactions;
    }

    public void saveMerchandiseList() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("merchandiseList.ser"))) {
            oos.writeObject(this.getMerchandiseList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void replaceMerchandiseList(List<Merchandise> ml) {
        this.merchandiseList = ml;
    }

    public void loadMerchandiseList() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("merchandiseList.ser"))) {
            List<Merchandise> loadedList = (List<Merchandise>) ois.readObject();
            merchandiseList = loadedList;
        } catch (FileNotFoundException e) {
            // File not found, create a new merchandise list
            merchandiseList = new ArrayList<Merchandise>();
            saveMerchandiseList(); // Optionally save the new empty list to the file
        } catch (IOException | ClassNotFoundException e) {
            // Handle other exceptions (e.g., IOException, ClassNotFoundException)
            e.printStackTrace();
        }
    }

     public void loadTransactionList() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("transactions.ser"))) {
            List<Transaction> loadedList = (List<Transaction>) ois.readObject();
            this.transactions = loadedList;
        } catch (FileNotFoundException e) {
            // File not found, create a new user list
            this.transactions = new ArrayList<Transaction>();
            saveTransactionList(); // Optionally save the new empty list to the file
        } catch (IOException | ClassNotFoundException e) {
            // Handle other exceptions (e.g., IOException, ClassNotFoundException)
            e.printStackTrace();
        }
    }
    
    
    public void sold(int quantity, Merchandise merchandise){
        for (Merchandise m : this.merchandiseList) {
            if (m.getName().equals(merchandise.getName())){
                m.reduceStock(quantity);
            };
        }

        saveMerchandiseList();
        System.out.println("Stroe");
        System.out.printf("%-20s%-20s%-20s%-20s\n", "Merchandise", "Stock Level", "Unit Price", "Unit Cost");
        
        // Print each merchandise item
        for (Merchandise m : merchandiseList) {
            System.out.printf("%-20s%-20d%-20.2f%-20.2f\n",
                    m.getName(), m.getStockLevel(), m.getUnitPrice(), m.getUnitCost());
        }

    }

    public void saveTransactionList() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("transactions.ser"))) {
            oos.writeObject(this.transactions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveUserList() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("userList.ser"))) {
            oos.writeObject(this.getUserList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void loadUserList() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("userList.ser"))) {
            List<User> loadedList = (List<User>) ois.readObject();
            users = loadedList;
        } catch (FileNotFoundException e) {
            // File not found, create a new user list
            users = new ArrayList<>();
            users.add(new BusinessOwner("Angel", "123", "123", this, null));
            saveUserList(); // Optionally save the new empty list to the file
        } catch (IOException | ClassNotFoundException e) {
            // Handle other exceptions (e.g., IOException, ClassNotFoundException)
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