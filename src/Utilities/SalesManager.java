package Utilities;

import java.io.*;
import java.util.ArrayList;
import Model.Bill;

public class SalesManager {

    private static final String FILE = "sales.dat";

    public static void saveBill(Bill bill) {
        ArrayList<Bill> list = loadBills();
        list.add(bill);
        try (FileOutputStream  fos = new FileOutputStream(FILE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(list);
        } catch (Exception e) {
            System.err.println("Error saving bill:");
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Bill> loadBills() {
        try (FileInputStream  fis = new FileInputStream(FILE);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (ArrayList<Bill>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading bills. Returning empty list.");
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
