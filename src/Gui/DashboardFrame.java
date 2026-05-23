package Gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import Model.PharmacyInventory;
import Utilities.FileManager;

public class DashboardFrame extends JFrame {

    private final Color BG   = new Color(245, 248, 255);
    private final Color BLUE = new Color(40,  90,  170);
    private final Color BTN  = new Color(70,  130, 210);

    private PharmacyInventory inventory;
    private MainFrame inventoryFrameInstance = null;

    public DashboardFrame() {
        inventory = FileManager.loadInventory();

        setTitle("My Pharmacy - Dashboard");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BG);

        // ── Header ────────────────────────────────────────────────────────────
        JLabel title = new JLabel("MY PHARMACY", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(BLUE);
        title.setBorder(new EmptyBorder(20, 0, 10, 0));

        JLabel subtitle = new JLabel("Select Operation Mode", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(Color.GRAY);

        JPanel top = new JPanel(new GridLayout(2, 1));
        top.setBackground(BG);
        top.add(title);
        top.add(subtitle);
        add(top, BorderLayout.NORTH);

        // ── Buttons ───────────────────────────────────────────────────────────
        JButton btnInventory = createButton("Inventory Management");
        JButton btnBilling   = createButton("Customer / New Sale");

        JPanel center = new JPanel();
        center.setBackground(BG);
        center.setBorder(new EmptyBorder(30, 20, 30, 20));
        center.add(btnInventory);
        center.add(Box.createHorizontalStrut(20));
        center.add(btnBilling);
        add(center, BorderLayout.CENTER);

        btnInventory.addActionListener(e -> openInventory());
        btnBilling.addActionListener(e   -> openBilling());

        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(BTN);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(200, 50));
        return btn;
    }

    private void openInventory() {
        if (inventoryFrameInstance == null) {
            inventoryFrameInstance = new MainFrame(this, inventory);
        }
        inventoryFrameInstance.setVisible(true);
        this.setVisible(false);
    }

    private void openBilling() {
        if (inventoryFrameInstance == null) {
            inventoryFrameInstance = new MainFrame(this, inventory);
        }
        new BillingFrame(inventoryFrameInstance, inventory).setVisible(true);
    }
}
