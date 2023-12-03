package models;
import models.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class User implements Serializable {
    private String name;
    private String staffCode;
    private String passWord;

    //three type: SalesStaff,BusinessOwner, InventoryManager
    private String type;
    public Store store;

    public User(){
        this.name = null;
        this.staffCode = null;
        this.passWord = null;
        this.type = null;
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

    public String getStaffcode()
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
    
    public void setStaffcode(String staffCode)
    {
        this.staffCode = staffCode;
    }
    
    public void setPassword(String passWord)
    {
        this.passWord = passWord;
    }

    public String getPassword()
    {
        return this.passWord;
    }
    
}
