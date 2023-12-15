package models;
import java.io.*;

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


    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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



}