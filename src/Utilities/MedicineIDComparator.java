package Utilities;

import java.util.Comparator;
import Model.Medicine;

public class MedicineIDComparator implements Comparator<Medicine> {
    public int compare(Medicine a, Medicine b) {
        return Integer.compare(a.getId(), b.getId());
    }
}
