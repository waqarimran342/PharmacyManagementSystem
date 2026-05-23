package Utilities;

import java.util.Comparator;
import Model.Medicine;

public class MedicineNameComparator implements Comparator<Medicine> {
    public int compare(Medicine a, Medicine b) {
        return a.getName().compareToIgnoreCase(b.getName());
    }
}
