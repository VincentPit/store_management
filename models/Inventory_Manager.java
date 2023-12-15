package models;

import java.util.List;

public interface Inventory_Manager {
    void restock(String merchandiseName, int additionalStock);
    List<Merchandise> getMerchandiseList();
    List<Transaction> getAllTransactions();

}
