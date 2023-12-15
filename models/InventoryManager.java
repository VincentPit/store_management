package models;
import java.io.*;
import java.util.*;
import java.time.*;
public class InventoryManager extends User implements Serializable, Inventory_Manager{
    private Inventory_Manager storeAccess;
    public InventoryManager(String name, String staffCode, String passWord, LocalDate date, Inventory_Manager storeAccess) {
        super(name, staffCode, passWord, "InventoryManager", date);
        this.storeAccess = storeAccess;
        this.setSalary(5000);
    }
    public void restock(String merchandiseName, int additionalStock){
        storeAccess.restock(merchandiseName, additionalStock);
    }

    public List<Transaction> getAllTransactions(){
        return storeAccess.getAllTransactions();
    }

    public List<Merchandise> getMerchandiseList(){
        return storeAccess.getMerchandiseList();
    }

    public Store getStoreAccess() {
        return (Store) this.storeAccess;
    }

}