package models;

import java.util.List;

public interface Business_Owner {
    List<Merchandise> getMerchandiseList();
    void addNewMerchandise(String name, double unitCost, double unitPrice, int stockLevel);
    List<User> getUserList();
    void deleteMerchandise(String merchandiseName);
    void editMerchandise(Merchandise m, String name, double unitPrice);
    void editUser(User u, String name, double salary, String type);
    User findUser(String staffCode);
    void addUser(User user);
    Merchandise findMerchandise(String name);
    String deleteUser(String staffCode);
    List<Transaction> getAllTransactions();
    boolean checkStaffCodeUnique(String staffCode);
    String generateStaffCode(String jobType);
    double calculateSalaryForJobType(String jobType);

}
