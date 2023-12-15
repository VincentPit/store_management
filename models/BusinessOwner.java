package models;
import java.io.*;
import models.*;
import java.util.*;
import java.time.*;
public class BusinessOwner extends User implements Serializable, Business_Owner {
    private Business_Owner storeAccess;

    public BusinessOwner(String name, String staffCode, String passWord, LocalDate date, Business_Owner storeAccess) {
        super(name, staffCode, passWord, "BusinessOwner", date);
        this.setSalary(10000);
        this.storeAccess = storeAccess;
    }

    public List<Merchandise> getMerchandiseList(){
        return storeAccess.getMerchandiseList();
    }
    public List<User> getUserList(){
        return storeAccess.getUserList();
    }

    public void addNewMerchandise(String name, double unitCost, double unitPrice, int stockLevel){
        storeAccess.addNewMerchandise(name, unitCost, unitPrice, stockLevel);
    }
    public void deleteMerchandise(String merchandiseName){
        storeAccess.deleteMerchandise(merchandiseName);
    }

    public void editMerchandise(Merchandise m, String name, double unitPrice){
        storeAccess.editMerchandise(m, name, unitPrice);
    }
    public Merchandise findMerchandise(String name) {
        return storeAccess.findMerchandise(name);
    }
    public void editUser(User u, String name, double salary, String type){
        storeAccess.editUser(u, name, salary, type);
    }
    public User findUser(String staffCode) {
        return storeAccess.findUser(staffCode);
    }
    public void addUser(User user){
        storeAccess.addUser(user);
    }
    public String deleteUser(String staffCode) {
        return storeAccess.deleteUser(staffCode);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return storeAccess.getAllTransactions();
    }
    public boolean checkStaffCodeUnique(String staffCode){
        return storeAccess.checkStaffCodeUnique(staffCode);
    }
    public String generateStaffCode(String jobType) {
        return storeAccess.generateStaffCode(jobType);
    }
    public double calculateSalaryForJobType(String jobType){
        return storeAccess.calculateSalaryForJobType(jobType);
    }
    public Store getStoreAccess(){
        return (Store) this.storeAccess;
    }

}