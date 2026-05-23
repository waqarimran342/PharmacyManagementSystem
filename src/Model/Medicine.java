package Model;

import java.io.Serializable;
import java.util.Date;

public class Medicine implements Serializable, Cloneable {

    private int id;
    private String name;
    private String category;
    private double price;
    private int quantity;
    private Date expiryDate;

    public Medicine(int id, String name, String category,
                    double price, int quantity, Date expiryDate) {
        this.id         = id;
        this.name       = name;
        this.category   = category;
        this.price      = price;
        this.quantity   = quantity;
        this.expiryDate = expiryDate;
    }

    // SHALLOW COPY
    public Medicine shallowCopy() throws CloneNotSupportedException {
        return (Medicine) super.clone();
    }

    // DEEP COPY
    public Medicine deepCopy() {
        return new Medicine(id, name, category, price, quantity,
                new Date(expiryDate.getTime()));
    }

    public boolean isLowStock() {
        return quantity < 5;
    }

    public boolean isExpiredSoon(int days) {
        long diff     = expiryDate.getTime() - System.currentTimeMillis();
        long daysLeft = diff / (1000 * 60 * 60 * 24);
        return daysLeft <= days;
    }

    // Getters
    public int    getId()         { return id; }
    public String getName()       { return name; }
    public String getCategory()   { return category; }
    public double getPrice()      { return price; }
    public int    getQuantity()   { return quantity; }
    public Date   getExpiryDate() { return expiryDate; }

    // Setters
    public void setName(String n)       { this.name = n; }
    public void setCategory(String c)   { this.category = c; }
    public void setPrice(double p)      { this.price = p; }
    public void setQuantity(int q)      { this.quantity = q; }
    public void setExpiryDate(Date d)   { this.expiryDate = d; }

    @Override
    public String toString() { return this.name; }
}
