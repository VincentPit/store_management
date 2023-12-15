package models;
import java.io.*;
import java.util.*;
import java.time.*;

public class SalesStaff extends User implements Serializable, Sales_Staff {
    private Sales_Staff storeAccess;

    public SalesStaff(String name, String staffCode, String passWord, LocalDate date, Sales_Staff storeAccess) {
        super(name, staffCode, passWord, "SalesStaff", date);
        this.setSalary(1000);
        this.storeAccess = storeAccess;
    }

    public void recordSales(int quantity, Merchandise merchandise) {
        storeAccess.recordSales(quantity, merchandise);
    }

    public Store getStoreAccess() {
        return (Store) storeAccess;
    }
    public List<Merchandise> getMerchandiseList(){
        return storeAccess.getMerchandiseList();
    }
    public Merchandise findMerchandise(String name){
        return storeAccess.findMerchandise(name);
    }
    public List<Transaction> getAllTransactions() {
        return this.getAllTransactions();
    }


}

