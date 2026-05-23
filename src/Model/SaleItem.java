package Model;

import java.io.Serializable;

public class SaleItem implements Serializable {

    private Medicine medicine;
    private int quantity;

    public SaleItem(Medicine m, int q) {
        this.medicine = m;
        this.quantity = q;
    }

    public Medicine getMedicine()            { return medicine; }
    public int      getQuantity()            { return quantity; }
    public void     setMedicine(Medicine m)  { this.medicine = m; }
    public void     setQuantity(int q)       { this.quantity = q; }

    public double getTotal() {
        return medicine.getPrice() * quantity;
    }
}
