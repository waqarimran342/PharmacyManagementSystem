package Utilities;

import java.util.Comparator;
import Model.Medicine;

public class MedicinePriceComparator implements Comparator<Medicine> {
    public int compare(Medicine a, Medicine b) {
        return Double.compare(a.getPrice(), b.getPrice());
    }
}
