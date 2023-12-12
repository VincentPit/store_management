package models;
import java.io.*;
import java.util.*;
public class Merchandise implements Serializable {
    private String name;
    private double unitCost;
    private double unitPrice;
    private int stockLevel;
    private double qtySold;

    

    public Merchandise(String name, double unitCost, double unitPrice, int stockLevel ) {
        this.name = name;
        this.unitCost = unitCost;
        this.unitPrice = unitPrice;
        this.stockLevel = stockLevel;
    }

    public void setPrice(double price){
        this.unitPrice = price;

    }

    public String getName(){
        return this.name;

    }

    public double getUnitPrice(){
        return this.unitPrice;
    }

    public double getUnitCost(){
        return this.unitCost; 
    }

    public int getStockLevel(){
        return this.stockLevel;
    }

    public void setStockLevel(int stock){
        this.stockLevel = stock;
    }

    public void setUnitCost(double cost){
        this.unitCost = cost;
    }

    public double getQtySold() {
        return qtySold;
    }

    public void setQtySold(double qtySold) {
        this.qtySold = qtySold;
    }


    public void increaseStock(int amount){
        this.stockLevel += amount;
    }

    public void reduceStock( int amount){
        System.out.println("Stock level before reduction: " + this.stockLevel);
        this.stockLevel = this.stockLevel - amount;
        System.out.println("Stock level after reduction: " + this.stockLevel);

    }

}