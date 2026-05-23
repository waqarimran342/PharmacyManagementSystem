package Gui;

import javax.swing.*;
import java.awt.*;
import Model.Medicine;
import Utilities.Utils;

public class MedicineFormDialog extends JDialog {

    public JTextField      txtName, txtPrice, txtQty, txtExpiry;
    public JComboBox<String> comboCat;
    public JButton         btnSave, btnCancel;

    private Medicine result = null;

    public Medicine getResult() { return result; }

    public MedicineFormDialog(Frame owner) {
        super(owner, "Add / Edit Medicine", true);
        setSize(320, 320);
        setLocationRelativeTo(owner);
        setLayout(new GridLayout(0, 2, 8, 8));
        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(new JLabel("Name"));
        txtName = new JTextField();
        add(txtName);

        add(new JLabel("Category"));
        comboCat = new JComboBox<>(new String[]{"Tablet", "Capsule", "Syrup", "Injection"});
        add(comboCat);

        add(new JLabel("Price"));
        txtPrice = new JTextField();
        add(txtPrice);

        add(new JLabel("Quantity"));
        txtQty = new JTextField();
        add(txtQty);

        add(new JLabel("Expiry (yyyy-mm-dd)"));
        txtExpiry = new JTextField();
        add(txtExpiry);

        btnSave   = new JButton("Save");
        btnCancel = new JButton("Cancel");
        add(btnSave);
        add(btnCancel);

        btnCancel.addActionListener(e -> dispose());

        btnSave.addActionListener(e -> {
            try {
                result = new Medicine(
                        -1,
                        txtName.getText().trim(),
                        comboCat.getSelectedItem().toString(),
                        Double.parseDouble(txtPrice.getText().trim()),
                        Integer.parseInt(txtQty.getText().trim()),
                        Utils.parseDate(txtExpiry.getText().trim())
                );
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Invalid input!\nCheck price, quantity and date format (yyyy-mm-dd).");
            }
        });
    }

    public void fillForm(Medicine m) {
        txtName.setText(m.getName());
        comboCat.setSelectedItem(m.getCategory());
        txtPrice.setText(String.valueOf(m.getPrice()));
        txtQty.setText(String.valueOf(m.getQuantity()));
        txtExpiry.setText(Utils.formatDate(m.getExpiryDate()));
    }
}
