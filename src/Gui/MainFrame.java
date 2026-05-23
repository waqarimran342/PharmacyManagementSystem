package Gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;
import java.awt.*;
import Model.*;
import Utilities.*;

public class MainFrame extends JFrame {

    private JTable            table;
    private PharmacyInventory inventory;
    private TableModelMedicine model;
    private SearchFilterPanel  filterPanel;
    private DashboardFrame     parentDashboard;

    private final Color BG_LIGHT  = new Color(245, 248, 255);
    private final Color BLUE      = new Color(70,  120, 200);
    private final Color BLUE_DARK = new Color(40,  90,  170);
    private final Color BUTTON_BG = new Color(90,  140, 220);
    private final Color BUTTON_TX = Color.WHITE;

    public MainFrame(DashboardFrame parentDashboard, PharmacyInventory sharedInventory) {
        this.parentDashboard = parentDashboard;
        inventory = (sharedInventory != null) ? sharedInventory : FileManager.loadInventory();

        setTitle("Pharmacy Management System - Inventory");
        setSize(950, 620);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BG_LIGHT);

        // ── Save on close & return to dashboard ──────────────────────────────
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                FileManager.saveInventory(inventory);
                if (parentDashboard != null) parentDashboard.setVisible(true);
                dispose();
            }
        });

        // ── Title ─────────────────────────────────────────────────────────────
        JLabel title = new JLabel("Pharmacy Inventory Manager", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(BLUE_DARK);
        title.setBorder(new EmptyBorder(10, 0, 10, 0));

        // ── Sort buttons ──────────────────────────────────────────────────────
        JPanel sortPanel = new JPanel();
        sortPanel.setBackground(BG_LIGHT);
        sortPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JButton btnId    = styledButton("Sort by ID");
        JButton btnName  = styledButton("Sort by Name");
        JButton btnPrice = styledButton("Sort by Price");
        JButton btnExp   = styledButton("Sort by Expiry");

        sortPanel.add(btnId);
        sortPanel.add(btnName);
        sortPanel.add(btnPrice);
        sortPanel.add(btnExp);

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(BG_LIGHT);
        top.add(title,     BorderLayout.NORTH);
        top.add(sortPanel, BorderLayout.SOUTH);
        add(top, BorderLayout.NORTH);

        // ── Filter panel (left sidebar) ───────────────────────────────────────
        filterPanel = new SearchFilterPanel();
        filterPanel.setBorder(new EmptyBorder(15, 10, 15, 10));
        add(filterPanel, BorderLayout.WEST);

        // ── Medicine table ────────────────────────────────────────────────────
        model = new TableModelMedicine(inventory.getList());
        table = new JTable(model);
        table.setRowHeight(26);

        JTableHeader header = table.getTableHeader();
        header.setBackground(BLUE);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));

        add(new JScrollPane(table), BorderLayout.CENTER);

        // ── Bottom CRUD buttons ───────────────────────────────────────────────
        JPanel bottom = new JPanel();
        bottom.setBorder(new EmptyBorder(10, 10, 10, 10));

        JButton btnAdd     = styledButton("Add");
        JButton btnEdit    = styledButton("Edit");
        JButton btnDel     = styledButton("Delete");
        JButton btnBilling = styledButton("New Sale");
        JButton btnReport  = styledButton("Export Report");

        bottom.add(btnAdd);
        bottom.add(btnEdit);
        bottom.add(btnDel);
        bottom.add(btnBilling);
        bottom.add(btnReport);
        add(bottom, BorderLayout.SOUTH);

        // ── Action listeners ──────────────────────────────────────────────────
        btnAdd.addActionListener(e    -> addMedicine());
        btnEdit.addActionListener(e   -> editMedicine());
        btnDel.addActionListener(e    -> deleteMedicine());
        btnBilling.addActionListener(e-> openBilling());
        btnReport.addActionListener(e -> {
            ReportGenerator.generateTextReport(inventory.getList());
            JOptionPane.showMessageDialog(this, "Report saved to report.txt");
        });

        btnId.addActionListener(e    -> { inventory.sortById();     refresh(); });
        btnName.addActionListener(e  -> { inventory.sortByName();   refresh(); });
        btnPrice.addActionListener(e -> { inventory.sortByPrice();  refresh(); });
        btnExp.addActionListener(e   -> { inventory.sortByExpiry(); refresh(); });

        // ── Filter apply / reset ──────────────────────────────────────────────
        filterPanel.btnApply.addActionListener(e -> applyFilter());
        filterPanel.btnReset.addActionListener(e -> { refresh(); filterPanel.resetFields(); });
    }

    private JButton styledButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(BUTTON_BG);
        btn.setForeground(BUTTON_TX);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        return btn;
    }

    public void refresh() {
        model = new TableModelMedicine(inventory.getList());
        table.setModel(model);
    }

    private void applyFilter() {
        String name  = filterPanel.txtSearch.getText();
        String cat   = filterPanel.comboCat.getSelectedItem().toString();
        String minTx = filterPanel.txtMin.getText().trim();
        String maxTx = filterPanel.txtMax.getText().trim();
        Double minP  = minTx.isEmpty() ? null : Double.parseDouble(minTx);
        Double maxP  = maxTx.isEmpty() ? null : Double.parseDouble(maxTx);

        model = new TableModelMedicine(inventory.filter(name, cat, minP, maxP));
        table.setModel(model);
    }

    private void addMedicine() {
        MedicineFormDialog dlg = new MedicineFormDialog(this);
        dlg.setVisible(true);
        Medicine m = dlg.getResult();
        if (m != null) {
            m = new Medicine(
                    inventory.generateId(),
                    m.getName(), m.getCategory(),
                    m.getPrice(), m.getQuantity(), m.getExpiryDate()
            );
            inventory.add(m);
            refresh();
        }
    }

    private void editMedicine() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a medicine to edit.");
            return;
        }
        int      id  = (int) model.getValueAt(row, 0);
        Medicine med = inventory.getById(id);
        if (med == null) return;

        MedicineFormDialog dlg = new MedicineFormDialog(this);
        dlg.fillForm(med);
        dlg.setVisible(true);
        Medicine updated = dlg.getResult();
        if (updated != null) {
            med.setName(updated.getName());
            med.setCategory(updated.getCategory());
            med.setPrice(updated.getPrice());
            med.setQuantity(updated.getQuantity());
            med.setExpiryDate(updated.getExpiryDate());
            refresh();
        }
    }

    private void deleteMedicine() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a medicine to delete.");
            return;
        }
        int id  = (int) model.getValueAt(row, 0);
        int opt = JOptionPane.showConfirmDialog(
                this, "Delete this medicine?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (opt == JOptionPane.YES_OPTION) {
            inventory.delete(id);
            refresh();
        }
    }

    private void openBilling() {
        new BillingFrame(this, inventory).setVisible(true);
    }

    public void refreshAfterSale() { refresh(); }
}
