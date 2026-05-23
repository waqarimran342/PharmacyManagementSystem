package Gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import Model.*;
import Utilities.*;

public class BillingFrame extends JFrame {

    private final MainFrame         parent;
    private final PharmacyInventory inventory;

    private JTable             cartTable;
    private DefaultTableModel  cartModel;
    private final ArrayList<SaleItem> cart = new ArrayList<>();

    private JComboBox<Medicine> comboMed;
    private JTextField          txtQuantity;
    private JLabel              lblTotal;

    private final Color BG_LIGHT  = new Color(245, 248, 255);
    private final Color BUTTON_BG = new Color(90,  140, 220);

    public BillingFrame(MainFrame parent, PharmacyInventory inventory) {
        this.parent    = parent;
        this.inventory = inventory;

        setTitle("New Sale");
        setSize(700, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BG_LIGHT);

        // ── Top: medicine selector ────────────────────────────────────────────
        JPanel topPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(BG_LIGHT);

        comboMed = new JComboBox<>();
        for (Medicine m : inventory.getList()) comboMed.addItem(m);

        txtQuantity = new JTextField("1");
        JButton btnAdd = styledButton("Add to Cart");

        topPanel.add(new JLabel("Select Medicine"));
        topPanel.add(comboMed);
        topPanel.add(new JLabel());
        topPanel.add(new JLabel("Quantity"));
        topPanel.add(txtQuantity);
        topPanel.add(btnAdd);
        add(topPanel, BorderLayout.NORTH);

        // ── Center: cart table ────────────────────────────────────────────────
        cartModel = new DefaultTableModel(
                new Object[]{"ID", "Name", "Price", "Qty", "Total"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        cartTable = new JTable(cartModel);
        cartTable.setRowHeight(24);
        add(new JScrollPane(cartTable), BorderLayout.CENTER);

        // ── Bottom: total + checkout ──────────────────────────────────────────
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setBackground(BG_LIGHT);
        bottom.setBorder(new EmptyBorder(5, 10, 10, 10));

        lblTotal = new JLabel("Total: PKR 0.00");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 15));

        JButton btnCheckout = styledButton("Checkout");
        bottom.add(lblTotal);
        bottom.add(Box.createHorizontalStrut(20));
        bottom.add(btnCheckout);
        add(bottom, BorderLayout.SOUTH);

        btnAdd.addActionListener(e      -> addToCart());
        btnCheckout.addActionListener(e -> checkout());
    }

    private JButton styledButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(BUTTON_BG);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        return btn;
    }

    private void addToCart() {
        Medicine med = (Medicine) comboMed.getSelectedItem();
        if (med == null) return;
        try {
            int qty = Integer.parseInt(txtQuantity.getText().trim());
            if (qty <= 0) throw new NumberFormatException();
            if (qty > med.getQuantity()) {
                JOptionPane.showMessageDialog(this,
                        "Not enough stock! Available: " + med.getQuantity());
                return;
            }
            cart.add(new SaleItem(med, qty));
            refreshCart();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter a valid positive quantity.");
        }
    }

    private void refreshCart() {
        cartModel.setRowCount(0);
        double total = 0;
        for (SaleItem s : cart) {
            cartModel.addRow(new Object[]{
                    s.getMedicine().getId(),
                    s.getMedicine().getName(),
                    String.format("%.2f", s.getMedicine().getPrice()),
                    s.getQuantity(),
                    String.format("%.2f", s.getTotal())
            });
            total += s.getTotal();
        }
        lblTotal.setText(String.format("Total: PKR %.2f", total));
    }

    private void checkout() {
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cart is empty!");
            return;
        }
        Bill bill = new Bill();
        for (SaleItem s : cart) {
            bill.addItem(s.getMedicine(), s.getQuantity());
            inventory.reduceStock(s.getMedicine().getId(), s.getQuantity());
        }
        SalesManager.saveBill(bill);
        JOptionPane.showMessageDialog(this,
                "Bill #" + bill.getBillId() + " saved!\nTotal: PKR " +
                String.format("%.2f", bill.getTotalAmount()));
        parent.refreshAfterSale();
        dispose();
    }
}
