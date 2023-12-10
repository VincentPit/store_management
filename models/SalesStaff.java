package models;
import java.io.*;
import java.util.*;
import java.time.*;

public class SalesStaff extends User implements Serializable {

    public SalesStaff(String name, String staffCode,Store store, String passWord, LocalDate date) {
        // Automatically setting the type to "BusinessOwner"
        super(name, staffCode, passWord, store, "SalesStaff", date);
        this.setSalary(1000);
    }

    public void recordSales(int quantity, Merchandise merchandise) {
        LocalTime time = LocalTime.now();
        LocalDate date = LocalDate.now();
        Transaction transaction = new Transaction(time, quantity, merchandise, date);
        merchandise.
        this.store.updateTransactions(transaction);
    }

    public List<Transaction> getAllTransactions(){
        return this.store.getAllTransactions();
    }
    
    public Merchandise findMerchandise( String merchandiseName){
        return this.store.findMerchandise(merchandiseName);
    }

    public List<Merchandise> getAllMerchandise(){
        return this.store.getMerchandiseList();
    }

    public List<Transaction> searchSaleHistoryByDate(LocalDate date) {
        return this.store.searchTransactionsByDate(date);

        }

    public List<Transaction> searchSaleHistoryByTime(LocalTime time) {
        return this.store.searchTransactionsByTime(time);

        }
    
    public List<Transaction> searchSaleHistoryByMerchandise(String m) {
        return this.store.searchTransactionsByMerchandise(m);

        }

}

