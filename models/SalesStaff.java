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
        //System.out.println("Stock level before recordsales: " + merchandise.getStockLevel());
        
        //System.out.println("Stock level after recordsales: " + merchandise.getStockLevel());

        this.store.sold(quantity,merchandise);

        List<Merchandise> ml = getAllMerchandise(this.store);
        // Print header
        System.out.println("recordSales");
        System.out.printf("%-20s%-20s%-20s%-20s\n", "Merchandise", "Stock Level", "Unit Price", "Unit Cost");
    
        // Print each merchandise item
        for (Merchandise m : ml) {
            System.out.printf("%-20s%-20d%-20.2f%-20.2f\n",
                    m.getName(), m.getStockLevel(), m.getUnitPrice(), m.getUnitCost());
        }
        this.store.updateTransactions(transaction);
        this.store.saveTransactionList();
    }

    public List<Transaction> getAllTransactions(){
        return this.store.getAllTransactions();
    }
    
    public Merchandise findMerchandise( String merchandiseName){
        return this.store.findMerchandise(merchandiseName);
    }

    public List<Merchandise> getAllMerchandise(Store s){
        return s.getMerchandiseList();
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

