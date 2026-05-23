package Gui;

import javax.swing.*;
import java.awt.*;

public class SearchFilterPanel extends JPanel {

    public JTextField      txtSearch, txtMin, txtMax;
    public JComboBox<String> comboCat;
    public JButton         btnApply, btnReset;

    public SearchFilterPanel() {
        setLayout(new GridLayout(0, 1, 5, 5));
        setBackground(new Color(235, 240, 255));
        setPreferredSize(new Dimension(160, 0));

        add(new JLabel("Search by name"));
        txtSearch = new JTextField();
        add(txtSearch);

        add(new JLabel("Category"));
        comboCat = new JComboBox<>(
                new String[]{"All", "Tablet", "Capsule", "Syrup", "Injection"});
        add(comboCat);

        add(new JLabel("Min Price"));
        txtMin = new JTextField();
        add(txtMin);

        add(new JLabel("Max Price"));
        txtMax = new JTextField();
        add(txtMax);

        btnApply = new JButton("Apply Filters");
        btnReset = new JButton("Reset");
        add(btnApply);
        add(btnReset);
    }

    public void resetFields() {
        txtSearch.setText("");
        comboCat.setSelectedIndex(0);
        txtMin.setText("");
        txtMax.setText("");
    }
}
