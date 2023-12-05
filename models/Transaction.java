package models;
import java.io.*;
import java.util.*;
public class Transaction implements Serializable {
    private long time;
    private double amount;
    private Merchandise merchandise;
    private int quantity;

    public Transaction(long time, double amount, int q, Merchandise merchandise) {
        this.time = time;
        this.amount = amount;
        this.quantity = q;
        this.merchandise = merchandise;
    }

    // Getters for time, amount, and merchandise
}