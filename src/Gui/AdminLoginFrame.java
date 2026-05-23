package Gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import Model.Admin;

public class AdminLoginFrame extends JFrame {

    private JTextField    txtUser;
    private JPasswordField txtPass;

    private final Admin admin = new Admin("Waqar", "admin");

    private final Color BG   = new Color(245, 248, 255);
    private final Color BLUE = new Color(40,  90,  170);
    private final Color BTN  = new Color(70,  130, 210);

    public AdminLoginFrame() {
        setTitle("My Pharmacy - Admin Login");
        setSize(420, 360);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(BG);
        setLayout(new GridBagLayout());

        // ── Card panel ───────────────────────────────────────────────────────
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(25, 30, 30, 30));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("MY PHARMACY");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(BLUE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Welcome to My Pharmacy");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(Color.GRAY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtUser = new JTextField();
        txtUser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUser.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        txtUser.setBorder(BorderFactory.createTitledBorder("Username"));

        txtPass = new JPasswordField();
        txtPass.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPass.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        txtPass.setBorder(BorderFactory.createTitledBorder("Password"));

        JButton btnLogin = new JButton("LOGIN");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setBackground(BTN);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorder(new RoundedBorder(15));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.setMaximumSize(new Dimension(140, 40));

        card.add(title);
        card.add(Box.createVerticalStrut(5));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(25));
        card.add(txtUser);
        card.add(Box.createVerticalStrut(12));
        card.add(txtPass);
        card.add(Box.createVerticalStrut(25));
        card.add(btnLogin);

        add(card);

        btnLogin.addActionListener(e -> login());
        // Allow pressing Enter to login
        txtPass.addActionListener(e -> login());

        setVisible(true);
    }

    private void login() {
        String user = txtUser.getText();
        String pass = new String(txtPass.getPassword());

        if (admin.login(user, pass)) {
            JOptionPane.showMessageDialog(this, "Login Successful!");
            dispose();
            new DashboardFrame();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Username or Password",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
            );
            txtPass.setText("");
        }
    }

    // ── Rounded border helper ─────────────────────────────────────────────────
    private static class RoundedBorder implements javax.swing.border.Border {
        private final int radius;
        RoundedBorder(int r) { radius = r; }
        public Insets getBorderInsets(Component c) {
            return new Insets(radius + 2, radius + 2, radius + 2, radius + 2);
        }
        public boolean isBorderOpaque() { return false; }
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            g.drawRoundRect(x, y, w - 1, h - 1, radius, radius);
        }
    }
}
