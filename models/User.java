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

    public User(String name, String staffCode, String passWord, String type) {
        this.name = name;
        this.staffCode = staffCode;
        this.passWord = passWord;
        this.type = type;
    }

    public String getName()
    {
        return this.name;
    }

    public String getStaffCode()
    {
        return this.staffCode;
    }

    public String getPassWord()
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
    
    public void setPassWord(String passWord)
    {
        this.passWord = passWord;
    }
    
}
