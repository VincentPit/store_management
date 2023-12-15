package models;
import java.io.*;
import java.time.*;


public class Transaction implements Serializable {
    private LocalDateTime time;
    private Merchandise merchandise;
    private int quantity;


    public Transaction(LocalDateTime time, int q, Merchandise merchandise) {
        this.time = time;
        this.quantity = q;
        this.merchandise = merchandise;
    }


    public Merchandise getMerchandise(){
        return this.merchandise;
    }


    public int getQuantity(){
        return this.quantity;
    }


    public LocalDateTime getTime() {
        return this.time;
    }
}