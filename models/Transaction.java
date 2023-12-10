package models;
import java.io.*;
import java.util.*;
import java.time.*;


public class Transaction implements Serializable {
    private LocalTime time;
    private LocalDate date;
    private double amount;
    private Merchandise merchandise;
    private int quantity;


    public Transaction(LocalTime time, int q, Merchandise merchandise, LocalDate date) {

        this.time = LocalTime.now();
        this.quantity = q;
        this.merchandise = merchandise;
        this.date = LocalDate.now();
    }

    // Getters for time, amount, and merchandise, date
    public LocalTime getTime(){
        return this.time;
    }

    public Merchandise getMerchandise(){
        return this.merchandise;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public LocalDate getDate(){
        return this.date;
    }

    
}