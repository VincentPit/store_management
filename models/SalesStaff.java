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

        Transaction transaction = new Transaction(time, quantity, merchandise);
        this.store.updateTransactions(transaction);
    }

    public void searchSaleHistory(String inventory) {
        List<Transaction> transactions = this.store.;


    }

}

