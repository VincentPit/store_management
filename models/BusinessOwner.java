package models;
import java.io.*;
import models.*;
import java.util.*;
import java.time.*;
public class BusinessOwner extends User implements Serializable {
    Store store;
    public BusinessOwner(String name, String staffCode, String passWord, Store store, LocalDate date) {
        // Automatically setting the type to "BusinessOwner"
        super(name, staffCode, passWord,store, "BusinessOwner", date);
        this.setSalary(10000);
        this.store = store;
    }

    public List<Merchandise> getMerchandiseList(){
        return this.store.getMerchandiseList();
    }

    public void addNewMerchandise(String name, Double unitCost, int unitPrice, int stockLevel){

        this.store.addNewMerchandise(name, unitCost, unitPrice, stockLevel);

    }

    public List<User> getUserList(){
        return this.store.getUserList();
    }

    public void deleteMerchandise(String merchandiseName){
        this.store.deleteMerchandise(merchandiseName);
    }

    public void addSalesStaff(String name, String staffCode, String passWord, LocalDate date)
    {   //if none of the user share the same staffCode then allow add user using this staffCode
        if(!store.findUserByStaffCode(staffCode))
        {
            SalesStaff newSalesStaff = new SalesStaff(name, staffCode, store, passWord, date);
            store.addUser(newSalesStaff);
        }

    }

    public void addInventoryManager(String name, String staffCode, String passWord, LocalDate date)
    {   //if none of the user share the same staffCode then allow add user using this staffCode
        if(!store.findUserByStaffCode(staffCode))
        {
            SalesStaff newInventoryManager = new SalesStaff(name, staffCode, store, passWord, date);
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
        if(store.findMerchandise(name) != null)
        {
            store.setDiscount(name, discount);
        }
    }

    public void setPrice(String name, double price) {
        if(store.findMerchandise(name) != null)
        {
            store.setPrice(name, price);
        }
    }

    public Store getStore(){
        return this.store;
    }

}