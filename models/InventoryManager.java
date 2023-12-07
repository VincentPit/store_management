package models;
import java.io.*;
import java.util.*;
import java.time.*;
public class InventoryManager extends User implements Serializable{
    public InventoryManager(String name, String staffCode, String passWord,Store store, LocalDate date) {
        // Automatically setting the type to "BusinessOwner"
        super(name, staffCode, passWord, store,"InventoryManager", date);
        this.setSalary(5000);
    }

    //buyProduct update stockLevel and unitCost, the new purchase price could be different
    //if the price is different, it re-calculate average unitCost, and update quanity
    public void buyProducts(String name, double unitCost, int quantity) {
        Merchandise tempMerchandise = store.findMerchandise(name);
        double tempCost = tempMerchandise.getUnitCost();
        int tempStockLevel =  tempMerchandise.getStockLevel();

        int newStockLevel = tempMerchandise.getStockLevel() + quantity;
        double newCost = ((unitCost * quantity) + (tempCost * tempStockLevel))/newStockLevel;
        tempMerchandise.setStockLevel(newStockLevel);
        tempMerchandise.setUnitCost(newCost);

    }

    public void restock(String merchandiseName, int additionalStock){
        this.store.restock(merchandiseName, additionalStock);

    }

    //get the list with all Merchandise
    public void viewAll() {
        List<Merchandise> tempMerchandiseList = store.getMerchandiseList();
    }
}