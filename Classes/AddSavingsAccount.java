
import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AddSavingsAccount extends JDialog implements ActionListener {

    String full_name;
    String username;
    JTextField balance;
    JLabel balaceLabel;
    JComboBox<String> list;
    JButton addAccountButton;
    DataBaseOp dataBaseOp;
    JComboBox<String>[] combobox;

    AddSavingsAccount(JFrame parent, DataBaseOp dataBaseOp, String full_name, String username,JComboBox<String>[] combobox) {

        super(parent, "Add Savings Account", true); /// modal JDialog panel

        this.combobox=combobox;

        this.dataBaseOp = dataBaseOp;
        this.full_name = full_name;
        this.username = username;
        ArrayList<String> arr = dataBaseOp.get_currency(full_name);

        String[] select = new String[]{"USD", "EUR", "RON"};
        if (arr.isEmpty()) {
            list = new JComboBox<>(select);
        } else {
            ArrayList<String> aux = new ArrayList<>(List.of(select));
            for (String it : arr) {
                if (aux.contains(it)) {
                    aux.remove(it);
                }
            }
            String[] arr2 = aux.toArray(new String[arr.size()]);
            list = new JComboBox<>(arr2); /// create account with available currencies
        }

        list.setAlignmentX(Component.CENTER_ALIGNMENT);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        list.addActionListener(this);
        addAccountButton = new JButton("Add Account");

        list.setPreferredSize(new Dimension(150, 25));
        list.setMaximumSize(new Dimension(150, 25));

        Font labelFont = new Font(null, Font.PLAIN, 16);

        balaceLabel = new JLabel("Add starting balance (minimum 100) " + list.getSelectedItem().toString());
        balaceLabel.setFont(labelFont);
        balaceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


        balance = new JTextField();
        balance.setMaximumSize(new Dimension(200, 30));
        balance.setAlignmentX(Component.CENTER_ALIGNMENT);

        DocumentFilter filter = new NoSpaceDocumentFilter();
        ((AbstractDocument) balance.getDocument()).setDocumentFilter(filter);

        addAccountButton.setPreferredSize(new Dimension(150, 50));
        addAccountButton.setFocusable(false);
        addAccountButton.addActionListener(this);
        addAccountButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(list);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(balaceLabel);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(balance);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(addAccountButton);

        setMinimumSize(new Dimension(300, 300));
        setSize(400, 400);
        setLocationRelativeTo(parent);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == list) {
            balaceLabel.setText("Add starting balance (minimum 100) " + list.getSelectedItem().toString());
        }
        if (e.getSource() == addAccountButton) {
            if (validateBalanceFromKeyboard(balance.getText())) {
                boolean ok=false;
                ok=dataBaseOp.insertSavingAccount(username, full_name,Long.parseLong(balance.getText())*1000000,list.getSelectedItem().toString(), 2.0, "daily");
                if(combobox!= null && ok) {
                    combobox[0].addItem(list.getSelectedItem().toString());
                }
                dispose();
            }
        }

    }

    private boolean validateBalanceFromKeyboard(String balance) {

        if (balance.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Blank Balance Field");
            return false;
        }

        if (balance.contains(".") || balance.contains(",")) {
            JOptionPane.showMessageDialog(null, "Fractional values are not allowed.");
            return false;
        }

        if (!balance.matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "Balance must only contain digits.");
            return false;
        }

        long rez = Long.parseLong(balance);
        if (rez > 100000 || rez < 100) {
            JOptionPane.showMessageDialog(null, "Balance must be less than or equal to 100,000 and higher than 100");
            return false;
        }

        return true;
    }

}
