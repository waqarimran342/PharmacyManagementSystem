package Utilities;

import java.io.*;
import Model.PharmacyInventory;

public class FileManager {

    private static final String FILE = "inventory.dat";

    public static void saveInventory(PharmacyInventory inv) {
        try (FileOutputStream  fos = new FileOutputStream(FILE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(inv);
        } catch (Exception e) {
            System.err.println("Error saving inventory:");
            e.printStackTrace();
        }
    }

    public static PharmacyInventory loadInventory() {
        try (FileInputStream   fis = new FileInputStream(FILE);
             ObjectInputStream  ois = new ObjectInputStream(fis)) {
            return (PharmacyInventory) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No inventory file found. Starting fresh.");
            return new PharmacyInventory();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading inventory. Starting fresh.");
            e.printStackTrace();
            return new PharmacyInventory();
        }
    }
}
