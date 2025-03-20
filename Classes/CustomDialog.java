import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomDialog extends JDialog implements ActionListener {

    private JLabel currentBalanceLabel;
    private JTextField amountField;
    private JTextField recipientField;
    private JButton actionButton;
    private String action;
    GeneralAccount generalAccount;
    String currentBalance;

    private JComboBox<String> currencyToComboBox;
    private JComboBox<String> currencyWithComboBox;

    DataBaseMoney dataBaseMoney;
    JLabel[] balanceLabel;

    public CustomDialog(JFrame parent, String action, String currentBalance, boolean isTransfer, GeneralAccount generalAccount, JLabel[] ref) {
        super(parent, "Account Operation", true);

        dataBaseMoney = new DataBaseMoney();
        this.action = action;
        this.generalAccount = generalAccount;
        this.currentBalance = currentBalance;
        balanceLabel = ref;

        if(ref.length==0){
            System.out.println("0");
            return;
        }

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        Font labelFont = new Font(null, Font.PLAIN, 16);

        currentBalanceLabel = new JLabel(currentBalance);
        currentBalanceLabel.setFont(labelFont);
        currentBalanceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createRigidArea(new Dimension(0, 10)));
        update_UI();
        add(currentBalanceLabel);

        DocumentFilter filter = new NoSpaceDocumentFilter();
        add(Box.createRigidArea(new Dimension(0, 10)));
        JLabel enterAmountLabel = new JLabel("Enter Amount from " + currentBalance + " (1 to 100.000) " + "\nNo fractions allowed");
        enterAmountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(enterAmountLabel);

        amountField = new JTextField();
        amountField.setMaximumSize(new Dimension(200, 30));
        amountField.setAlignmentX(Component.CENTER_ALIGNMENT);
        ((AbstractDocument) amountField.getDocument()).setDocumentFilter(filter);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(amountField);

        if (isTransfer) { /// add components if transfer window invoked
            add(Box.createRigidArea(new Dimension(0, 15)));
            JPanel currencyToPanel = new JPanel();
            currencyToPanel.setMaximumSize(new Dimension(300, 90));
            currencyToPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            JLabel currencyToLabel = new JLabel("In which account to be stored: ");
            currencyToComboBox = new JComboBox<>(new String[]{"RON", "USD", "EUR"});
            currencyToPanel.add(currencyToLabel);
            currencyToPanel.add(currencyToComboBox);

            JPanel currencyWithPanel = new JPanel();
            currencyWithPanel.setMaximumSize(new Dimension(300, 50));
            currencyWithPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            JLabel currencyWithLabel = new JLabel("Send payment in: ");
            currencyWithComboBox = new JComboBox<>(new String[]{"RON", "USD", "EUR"});
            currencyWithPanel.add(currencyWithLabel);
            currencyWithPanel.add(currencyWithComboBox);

            add(currencyWithPanel);
            add(currencyToPanel);

            JLabel toWhomLabel = new JLabel("To Whom: ");
            toWhomLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(Box.createRigidArea(new Dimension(0, 10)));
            add(toWhomLabel);

            recipientField = new JTextField();
            recipientField.setMaximumSize(new Dimension(200, 30));
            ((AbstractDocument) recipientField.getDocument()).setDocumentFilter(filter);
            add(Box.createRigidArea(new Dimension(0, 5)));
            add(recipientField);
        }

        actionButton = new JButton(action);
        actionButton.setPreferredSize(new Dimension(150, 50));
        actionButton.setFocusable(false);
        actionButton.addActionListener(this);
        actionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(actionButton);

        setSize(400, 500);
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

            if (e.getSource() == actionButton && action.equals("Transfer")) {

                if (generalAccount.getUsername().equals(recipientField.getText())) {
                    JOptionPane.showMessageDialog(null, "Cannot transfer to same account!");
                    amountField.setText("");
                    recipientField.setText("");
                    return;
                }
                int id = dataBaseMoney.get_user_id(recipientField.getText());
                if (id == -1) {
                    JOptionPane.showMessageDialog(this, "Recipient not found/Has been deleted");
                    amountField.setText("");
                    recipientField.setText("");
                } else {

                    if (dataBaseMoney.account_is_blocked(recipientField.getText())) {
                        JOptionPane.showMessageDialog(null, "Recipient account is blocked");
                        amountField.setText("");
                        recipientField.setText("");
                        return;
                    }

                    String currencyTo = (String) currencyToComboBox.getSelectedItem();
                    String currencyWith = (String) currencyWithComboBox.getSelectedItem();

                    if (generalAccount.validateBalanceFromKeyboard(amountField.getText())) {
                        String name = dataBaseMoney.getFullNameByUsername(recipientField.getText());

                        boolean ok = generalAccount.transfer(name, Long.parseLong(amountField.getText()) * 1000000, currentBalance, currencyWith, currencyTo); /// transfer

                        if (ok) { /// transfer succeeded
                            TransferClass transferClass = new TransferClass(Long.parseLong(amountField.getText()) * 1000000, generalAccount.getUsername(), recipientField.getText(), currentBalance, currencyWith, currencyTo);
                            dataBaseMoney.insertPastTransfers(transferClass); /// insert into past transfer table
                        }

                        amountField.setText("");
                        recipientField.setText(""); /// clear fields/update text
                        update_UI();

                    }
                }
                /// *100 because if we want to send 67 eur , its representation in the DB is 6700 for math operations
            }
            else if (e.getSource() == actionButton && action.equals("Withdraw")) {

                if (generalAccount.validateBalanceFromKeyboard(amountField.getText())) {
                    generalAccount.withdraw(Long.parseLong(amountField.getText()) * 1000000, currentBalance);
                    amountField.setText("");
                    update_UI();
                }
            }
            else if (e.getSource() == actionButton && action.equals("Deposit")) {
                if (generalAccount.validateBalanceFromKeyboard(amountField.getText())) { /// validate data from user
                    generalAccount.deposit(Long.parseLong(amountField.getText()) * 1000000, currentBalance); /// convert to long rep.
                    amountField.setText("");
                    JOptionPane.showMessageDialog(null, "Deposited!");
                    update_UI();
                }
            }

    }

    private void update_UI(){
        currentBalanceLabel.setText("Total Balance: " + dataBaseMoney.balanceFormatted(generalAccount.retrieve_balance(currentBalance)) + " " + currentBalance);
        balanceLabel[0].setText("Total Balance: " + dataBaseMoney.balanceFormatted(generalAccount.retrieve_balance(currentBalance)) + " " + currentBalance);
    }
}
