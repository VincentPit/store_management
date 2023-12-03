package models;

import java.io.*;


public class FileManager {

    public static void writeObjectToFile(String filePath, Serializable object) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            outputStream.writeObject(object);
            System.out.println("Object written to file successfully.");
        } catch (IOException e) {
            System.err.println("Error writing object to file: " + e.getMessage());
        }
    }

    public static Object readObjectFromFile(String filePath) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath))) {
            Object object = inputStream.readObject();
            System.out.println("Object read from file successfully.");
            return object;
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filePath);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading object from file: " + e.getMessage());
        }
        return null;
    }

}
