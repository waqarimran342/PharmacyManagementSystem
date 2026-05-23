package Gui;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import Model.Medicine;
import Utilities.Utils;

public class TableModelMedicine extends AbstractTableModel {

    private final List<Medicine> list;
    private final String[] col = {"ID", "Name", "Category", "Price", "Qty", "Expiry", "Status"};

    public TableModelMedicine(List<Medicine> list) {
        this.list = list;
    }

    public int    getRowCount()          { return list.size(); }
    public int    getColumnCount()       { return col.length; }
    public String getColumnName(int c)   { return col[c]; }
    public boolean isCellEditable(int r, int c) { return false; }

    public Object getValueAt(int r, int c) {
        Medicine m = list.get(r);
        return switch (c) {
            case 0 -> m.getId();
            case 1 -> m.getName();
            case 2 -> m.getCategory();
            case 3 -> String.format("%.2f", m.getPrice());
            case 4 -> m.getQuantity();
            case 5 -> Utils.formatDate(m.getExpiryDate());
            case 6 -> {
                if (m.isLowStock() && m.isExpiredSoon(30)) yield "⚠ Low & Expiring";
                if (m.isLowStock())      yield "⚠ Low Stock";
                if (m.isExpiredSoon(30)) yield "⚠ Expiring Soon";
                yield "OK";
            }
            default -> null;
        };
    }
}
