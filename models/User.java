package models;
import models.*;
import java.io.*;
import java.util.*;

public class User implements Serializable {
    private String name;
    private String staffCode;
    private String passWord;

    //three type: SalesStaff,BusinessOwner, InventoryManager
    private String type;
    public Store store;

    public User(){
//        this.name = null;
//        this.staffCode = null;
//        this.passWord = null;
//        this.type = null;
    }

    public User(String name, String staffCode, String passWord, Store store,String type) {
        this.name = name;
        this.staffCode = staffCode;
        this.passWord = passWord;
        this.type = type;
        this.store = store;

    }

    public String getName()
    {
        return this.name;
    }

    public String getStaffCode()
    {
        return this.staffCode;
    }

    public String getPwd()
    {
        return this.passWord;
    }

    public String getType()
    {
        return this.type;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public void setStaffCode(String staffCode)
    {
        this.staffCode = staffCode;
    }
    
    public void setPwd(String passWord)
    {
        this.passWord = passWord;
    }


    
}
