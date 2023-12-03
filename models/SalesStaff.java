package models;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class SalesStaff extends User implements Serializable {

    public SalesStaff(String name, String staffCode, String passWord) {
        // Automatically setting the type to "BusinessOwner"
        super(name, staffCode, passWord, "SalesStaff");
    }

    public void recordSales(int quantity, String name) {
    }

    public void searchSale(String inventory) {
    }

    public void searchHistory(String merchandise) {
    }
}
