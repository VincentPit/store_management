package models;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
public class Store implements Serializable, Inventory_Manager, Business_Owner, Sales_Staff {
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
            BusinessOwner owner = new BusinessOwner("Angel", "123", "123",  LocalDate.now(), this);
            this.users.add(owner);
            InventoryManager inventoryManager = new InventoryManager("Aaron","234", "234",LocalDate.now(), this);
            this.users.add(inventoryManager);
            SalesStaff salesStaff = new SalesStaff("Stephen", "345", "345",  LocalDate.now(), this);
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

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("merchandiseList.ser"))) {
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
            SalesStaff new_user = new SalesStaff(user.getName(), user.getStaffCode(), user.getPwd(), user.getDateOfEnrolment(), this);
            this.users.add(new_user);
        } else if (user.getType() == "BusinessOwner"){
            BusinessOwner new_user = new BusinessOwner(user.getName(), user.getStaffCode(),  user.getPwd(), user.getDateOfEnrolment(), this);
            this.users.add(new_user);
        } else {
            InventoryManager new_user = new InventoryManager(user.getName(), user.getStaffCode(),  user.getPwd(), user.getDateOfEnrolment(), this);
            this.users.add(new_user);
        }
        
        saveUserList();
    }


    public Merchandise findMerchandise(String name) {
        for (Merchandise merchandise : merchandiseList) {
            if (merchandise.getName().equals(name)) {
                return merchandise;
            }
        }
        return null;
    }

    public void addNewMerchandise(String name, double unitCost, double unitPrice, int stockLevel) {
        Merchandise m = new Merchandise(name, unitCost, unitPrice, stockLevel);
        merchandiseList.add(m);
        saveMerchandiseList();
    }

    public void editMerchandise(Merchandise m, String name, double unitPrice){
        m.setName(name);
        m.setUnitPrice(unitPrice);
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
                users.remove(user);
                this.saveUserList();
                return "User with staff code " + staffCode + " deleted successfully.";
            }
        }
        return "User with staff code " + staffCode + " not found.";
    }






    public void restock(String merchandiseName, int additionalStock) {
        Merchandise m = findMerchandise(merchandiseName);
        int currentStock = m.getStockLevel();
        m.setStockLevel(currentStock + additionalStock);
        saveMerchandiseList();
        //System.out.println("Merchandise not found: " + merchandiseName);
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


    public void loadMerchandiseList() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("merchandiseList.ser"))) {
            List<Merchandise> loadedList = (List<Merchandise>) ois.readObject();
            merchandiseList = loadedList;
        } catch (FileNotFoundException e) {
            // File not found, create a new merchandise list
            merchandiseList = new ArrayList<Merchandise>();
            saveMerchandiseList();
        } catch (EOFException e) {
            //e.printStackTrace();
        }
        catch (IOException | ClassNotFoundException e) {
            // Handle other exceptions (e.g., IOException, ClassNotFoundException)
            //e.printStackTrace();
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

    public void saveTransactionList() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("transactions.ser"))) {
            oos.writeObject(this.transactions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveUserList() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("userList.ser"))) {
            oos.writeObject(this.users);
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
            users.add(new BusinessOwner("Angel", "123", "123", LocalDate.now(), this));
            saveUserList(); // Optionally save the new empty list to the file
        } catch (IOException | ClassNotFoundException e) {
            // Handle other exceptions (e.g., IOException, ClassNotFoundException)
            e.printStackTrace();
        }
    }


    public void recordSales(int quantity, Merchandise merchandise) {
        LocalDateTime time = LocalDateTime.now();
        Transaction transaction = new Transaction(time, quantity, merchandise);

        for (Merchandise m : this.merchandiseList) {
            if (m.getName().equals(merchandise.getName())){
                m.setStockLevel(m.getStockLevel()-quantity);
                m.setQtySold(quantity);
            }
        }
        saveMerchandiseList();
        this.updateTransactions(transaction);
        this.saveTransactionList();
    }


    public void editUser(User u, String name, double salary, String type){
        u.setName(name);
        u.setSalary(salary);
        u.setType(type);
        this.saveUserList();
    }

    public User findUser(String staffCode){
        for (User user : this.getUserList()) {
            if (user.getStaffCode().equals(staffCode)) {
                return user; // User found
            }
        }
        return null; // User not found
    }

    public boolean checkStaffCodeUnique(String staffCode) {
        for (User user : this.getUserList()) {
            if (user.getStaffCode().equals(staffCode)) {
                return false;
            }
        }
        return true; // No matching staff code found
    }

    public String generateStaffCode(String jobType) {
        StringBuilder code = new StringBuilder();
        Random random = new Random();

        if ("InventoryManager".equals(jobType)) {
            code.append("2");
        } else if ("BusinessOwner".equals(jobType)) {
            code.append("1");
        } else if ("SalesStaff".equals(jobType)) {
            code.append("3");
        }
        do {
            while (code.length() < 3) {
                int nextChar = random.nextInt(26); // 26 letters
                // note the GUI added user's staff code will have 2 characters followed by 1 digit
                // the staff code of users added directly in the code will be made of 3 digits
                if (nextChar < 26) {
                    code.append((char) ('A' + nextChar));
                }
            }
        } while(!checkStaffCodeUnique(code.toString()));

        return code.toString();
    }

    public double calculateSalaryForJobType(String jobType) {
        if (jobType.equals("SalesStaff")) {
            return 1000.0;
        } else if (jobType.equals("InventoryManager")) {
            return 5000.0;
        }
        return 10000.0;
    }


    // Helper method to convert Date to LocalDate for transactions (used by transactionViewer)
    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }






}