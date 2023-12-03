package models;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class InventoryManager extends User implements Serializable{
    public InventoryManager(String name, String staffCode, String passWord,Store store) {
        // Automatically setting the type to "BusinessOwner"
        super(name, staffCode, passWord, store,"InventoryManager");
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

    //get the list with all Merchandise
    public void viewAll() {
        List<Merchandise> tempMerchandiseList = store.viewAllMerchandise();
    }
}
