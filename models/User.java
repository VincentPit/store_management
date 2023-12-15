package models;

import java.io.*;
import java.time.*;

public class User implements Serializable {
    private String name;
    private String staffCode;
    private String passWord;

    //three types: SalesStaff, BusinessOwner, InventoryManager
    private String type;
    private double salary;
    private LocalDate dateOfEnrolment;


    public User(){}

    public User(String name, String staffCode, String passWord,String type, LocalDate now) {
        this.name = name;
        this.staffCode = staffCode;
        this.passWord = passWord;
        this.type = type;
        this.dateOfEnrolment= now;
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


    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name)
    {
        this.name = name;
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







}