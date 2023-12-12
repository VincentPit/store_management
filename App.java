import models.*;
import models.interfaces.*;
import models.Store;

import java.io.*;

public class App {

    private static final String STORE_FILE_PATH = "store.ser";

    public static void main(String[] args) {
        Store store = readStoreFromFile();

        if (store == null) {
            //If the store is not found, create a new one
            System.out.println("Creating a new store.");

            store = new Store();

            saveStoreToFile(store);
        } else {
            System.out.println("Store loaded from file.");
        }


        LoginInterface login = new LoginInterface(store);
        
        store.saveMerchandiseList();
        store.saveTransactionList();
        store.saveUserList();
        saveStoreToFile(store);
    }

    private static Store readStoreFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(STORE_FILE_PATH))) {
            return (Store) ois.readObject();
        } catch (FileNotFoundException e) {
            //File not found, which is expected if it's the first run
            System.out.println("Store file not found.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void saveStoreToFile(Store store) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(STORE_FILE_PATH))) {
            oos.writeObject(store);
            System.out.println("Store saved to file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
