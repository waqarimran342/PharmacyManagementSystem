package Model;

import java.io.Serializable;
import java.util.*;
import Utilities.*;

public class PharmacyInventory implements Serializable {

    private List<Medicine> list   = new ArrayList<>();
    private int            nextId = 1;

    public List<Medicine> getList() { return list; }

    public void add(Medicine m)     { list.add(m); }

    public void delete(int id) {
        list.removeIf(m -> m.getId() == id);
    }

    public Medicine getById(int id) {
        return list.stream()
                   .filter(m -> m.getId() == id)
                   .findFirst()
                   .orElse(null);
    }

    public void reduceStock(int medId, int qty) {
        Medicine m = getById(medId);
        if (m != null) {
            m.setQuantity(m.getQuantity() - qty);
        }
    }

    public int generateId() { return nextId++; }

    public void sortById()     { Collections.sort(list, Comparator.comparing(Medicine::getId)); }
    public void sortByName()   { Collections.sort(list, new MedicineNameComparator()); }
    public void sortByPrice()  { Collections.sort(list, new MedicinePriceComparator()); }
    public void sortByExpiry() { Collections.sort(list, new MedicineExpiryComparator()); }

    public List<Medicine> filter(String name, String category,
                                  Double minP, Double maxP) {
        return list.stream().filter(m -> {
            boolean nameOk = name.isEmpty() ||
                             m.getName().toLowerCase().contains(name.toLowerCase());
            boolean catOk  = category.equals("All") || m.getCategory().equals(category);
            boolean minOk  = minP == null || m.getPrice() >= minP;
            boolean maxOk  = maxP == null || m.getPrice() <= maxP;
            return nameOk && catOk && minOk && maxOk;
        }).toList();
    }
}
