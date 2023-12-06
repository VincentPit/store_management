package models;
import models.*;
import java.io.*;
import java.util.*;
import java.time.*;

public class User implements Serializable {
    private String name;
    private String staffCode;
    private String passWord;

    //three type: SalesStaff, BusinessOwner, InventoryManager
    private String type;
    private double salary;
    private LocalDate dateOfEnrolment;

    public Store store;

//感觉不需要这个
    public User(){
//        this.name = null;
//        this.staffCode = null;
//        this.passWord = null;
//        this.type = null;
    }

    public User(String name, String staffCode, String passWord, Store store,String type, LocalDate date) {
        this.name = name;
        this.staffCode = staffCode;
        this.passWord = passWord;
        this.type = type;
        this.store = store;
        this.dateOfEnrolment= date;
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

    public void setSalary(double amt){
        this.salary = amt;
    }

    public double getSalary(){
        return this.salary;
    }

    public LocalDate getDateOfEnrolment() {
        return dateOfEnrolment;
    }


    public Store getStore(){
        return this.store;
    }



}