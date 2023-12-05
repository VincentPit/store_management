package models;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalesStaff extends User implements Serializable {

    public SalesStaff(String name, String staffCode,Store store, String passWord) {
        // Automatically setting the type to "BusinessOwner"
        super(name, staffCode, passWord, store, "SalesStaff");
    }

    public void recordSales(int quantity, String name) {
    }

    public void searchSale(String inventory) {
    }

    public void searchHistory(String merchandise) {
    }
}
