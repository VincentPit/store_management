package models;

import java.util.List;

public interface Sales_Staff {
    void recordSales(int quantity, Merchandise merchandise);
    List<Merchandise> getMerchandiseList();
    Merchandise findMerchandise(String name);
    List<Transaction> getAllTransactions();

}
