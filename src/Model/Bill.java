package Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Bill implements Serializable {

    private static int nextBillId = 1001;

    private int billId;
    private Customer customer;
    private ArrayList<SaleItem> items;
    private LocalDateTime date;

    public Bill() {
        billId   = nextBillId++;
        customer = new Customer(0, "Cash Customer", "N/A");
        items    = new ArrayList<>();
        date     = LocalDateTime.now();
    }

    public void addItem(Medicine m, int qty) {
        items.add(new SaleItem(m, qty));
    }

    public double getTotalAmount() {
        double total = 0;
        for (SaleItem i : items) {
            total += i.getTotal();
        }
        return total;
    }

    public int              getBillId()  { return billId; }
    public LocalDateTime    getDate()    { return date; }
    public ArrayList<SaleItem> getItems(){ return items; }
}
