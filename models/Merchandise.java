package models;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
class Merchandise implements Serializable {
    private String name;
    private double unitCost;
    private double unitPrice;
    private int stockLevel;
    

    public Merchandise(String name, double unitCost, double unitPrice, int stockLevel ) {
        this.name = name;
        this.unitCost = unitCost;
        this.unitPrice = unitPrice;
        this.stockLevel = stockLevel;
    }

    // Getters for name, unitCost, and unitPrice
}