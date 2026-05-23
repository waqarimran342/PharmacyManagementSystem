package Utilities;

import java.io.FileWriter;
import java.util.List;
import Model.Medicine;

public class ReportGenerator {

    public static void generateTextReport(List<Medicine> list) {
        try (FileWriter fw = new FileWriter("report.txt")) {
            fw.write("=== Pharmacy Inventory Report ===\n\n");
            for (Medicine m : list) {
                fw.write("ID       : " + m.getId()               + "\n");
                fw.write("Name     : " + m.getName()             + "\n");
                fw.write("Category : " + m.getCategory()         + "\n");
                fw.write("Price    : " + m.getPrice()            + "\n");
                fw.write("Quantity : " + m.getQuantity()         + "\n");
                fw.write("Expiry   : " + Utils.formatDate(m.getExpiryDate()) + "\n");
                if (m.isLowStock())        fw.write("*** LOW STOCK ***\n");
                if (m.isExpiredSoon(30))   fw.write("*** EXPIRING SOON ***\n");
                fw.write("------------------------------------\n\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
