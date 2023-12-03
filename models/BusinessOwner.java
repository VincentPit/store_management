package models;
import java.io.*;
import models.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
class BusinessOwner extends User implements Serializable {
    Store store;
    public BusinessOwner(String name, String staffCode, String passWord, Store store) {
        // Automatically setting the type to "BusinessOwner"
        super(name, staffCode, passWord, "BusinessOwner");
        this.store = store;
    }

    public void addNewMerchandise(String name, double unitCost, int unitPrice, int stockLevel) {
        Merchandise newMerchandise = new Merchandise(name, unitCost, unitPrice, stockLevel);
        store.addMerchandise(newMerchandise);
    }

    public void addSalesStaff(String name, String staffCode, String passWord)
    {   //if none of the user share the same staffCode then allow add user using this staffCode
        if(!store.findUserByStaffCode(staffCode))
        {
            SalesStaff newSalesStaff = new SalesStaff(name, staffCode, passWord);
            store.addUser(newSalesStaff);
        }

    }

    public void addInventoryManager(String name, String staffCode, String passWord)
    {   //if none of the user share the same staffCode then allow add user using this staffCode
        if(!store.findUserByStaffCode(staffCode))
        {
            SalesStaff newInventoryManager = new SalesStaff(name, staffCode, passWord);
            store.addUser(newInventoryManager);
        }
    }

    public void deleteStaff(String staffCode)
    {
        store.deleteUser(staffCode);
    }

    //discount is percentage in double
    public void setDiscount(String name, double discount) {
        //this check if Merchandise exist in store, if yes then set new discount price
        if(store.findMerchandise(name))
        {
            store.setDiscount(name, discount);
        }
    }

    public void setPrice(String name, double price) {
        if(store.findMerchandise(name))
        {
            store.setPrice(name, price);
        }
    }

}
