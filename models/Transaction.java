package models;
import java.io.*;
import java.util.*;
import java.time.*;


public class Transaction implements Serializable {
    private LocalTime time;
    private double amount;
    private Merchandise merchandise;
    private int quantity;

    public Transaction(LocalTime time, int q, Merchandise merchandise) {
        this.time = time;
        this.quantity = q;
        this.merchandise = merchandise;
    }

    // Getters for time, amount, and merchandise
}