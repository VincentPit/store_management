package models;
import java.io.*;
import models.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
class BusinessOwner extends User implements Serializable {

    public BusinessOwner(String name, String staffCode, String passWord) {
        // Automatically setting the type to "BusinessOwner"
        super(name, staffCode, passWord, "BusinessOwner");
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

    public void setDiscount(String name, double price) {
        store.setPrice(name, price);
    }
    
}
