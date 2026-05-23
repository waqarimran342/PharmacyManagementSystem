package Utilities;

import java.util.Comparator;
import Model.Medicine;

public class MedicineExpiryComparator implements Comparator<Medicine> {
    public int compare(Medicine a, Medicine b) {
        return a.getExpiryDate().compareTo(b.getExpiryDate());
    }
}
