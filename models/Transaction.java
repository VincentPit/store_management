package models;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class Transaction implements Serializable {
    private long time;
    private double amount;
    private Merchandise merchandise;

    public Transaction(long time, double amount, Merchandise merchandise) {
        this.time = time;
        this.amount = amount;
        this.merchandise = merchandise;
    }

    // Getters for time, amount, and merchandise
}