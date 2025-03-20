import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

public class UserMenuSavingsPage implements ActionListener {

    SavingAccount savingAccount;

    JFrame frame = new JFrame();
    JLabel welcomeLabel;
    JLabel totalBalanceLabel;

    JPanel up = new JPanel();
    JPanel down = new JPanel();
    JPanel left = new JPanel();
    JPanel right = new JPanel();
    JPanel centre = new JPanel();
    JPanel centreUp = new JPanel();
    JPanel centreDown = new JPanel();
    JPanel centreLeft = new JPanel();
    JPanel centreRight = new JPanel();
    JPanel centreInner = new JPanel();

    JComboBox<String> money_choice;

    DataBaseUpdateDelete dataBaseUpdateDelete;
    DataBaseOp dataBaseOp;
    DataBaseMoney dataBaseMoney;

    JButton depositButton = new JButton("Deposit");
    JButton withdrawButton = new JButton("Withdraw");
    JButton personalInfoButton = new JButton("See Personal Info");
    JButton exitButton = new JButton("Exit");
    JButton accountDataButton = new JButton("Account Data");
    JButton addSavingsAccountButton = new JButton("Add Savings Account");

    ArrayList<String> opted_currencies;
    JLabel[] ref = new JLabel[1];
    JComboBox<String>[] combobox = new JComboBox[1];

    /// reflect modifications for balance

    UserMenuSavingsPage(String full_name, String username, DataBaseOp dataBaseOp, DataBaseMoney dataBaseMoney, DataBaseUpdateDelete dataBaseUpdateDelete) {


        this.dataBaseUpdateDelete = dataBaseUpdateDelete;
        this.dataBaseOp = dataBaseOp;
        this.dataBaseMoney = dataBaseMoney;

        opted_currencies = dataBaseUpdateDelete.get_currency(full_name);

        savingAccount = new SavingAccount(full_name, username, opted_currencies.getFirst(), dataBaseOp, dataBaseUpdateDelete, dataBaseMoney); /// create saving account helper class

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

        frame.setLayout(new BorderLayout(10, 10));

        up.setPreferredSize(new Dimension(100, 80));
        down.setPreferredSize(new Dimension(100, 80));
        left.setPreferredSize(new Dimension(50, 200));
        right.setPreferredSize(new Dimension(50, 200));
        centre.setPreferredSize(new Dimension(200, 100));

        up.setBackground(new Color(204, 153, 255));
        down.setBackground(new Color(102, 255, 102));

        centreUp.setPreferredSize(new Dimension(200, 50));
        centreDown.setPreferredSize(new Dimension(200, 50));
        centreLeft.setPreferredSize(new Dimension(50, 200));
        centreRight.setPreferredSize(new Dimension(50, 200));
        centreInner.setBackground(Color.CYAN);

        centreUp.setBackground(Color.lightGray);
        centreDown.setBackground(Color.lightGray);
        centreLeft.setBackground(Color.lightGray);
        centreRight.setBackground(Color.lightGray);

        ////////////////////////////////// UP

        welcomeLabel = new JLabel("Welcome to your savings account , " + savingAccount.getFull_name());
        welcomeLabel.setFont(new Font(null, Font.BOLD, 20));
        welcomeLabel.setForeground(Color.BLUE);
        up.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        up.add(welcomeLabel);

        //////////////////////////////// Center

        centreDown.add(new JLabel("Select Account:"));

        String[] arr2 = opted_currencies.toArray(new String[opted_currencies.size()]);
        money_choice = new JComboBox<>(arr2);
        money_choice.addActionListener(this);
        centreDown.add(money_choice); /// switch between different , existent , saving accounts
        combobox[0] = money_choice;

        totalBalanceLabel = new JLabel("Total Balance: " + dataBaseMoney.balanceFormatted(savingAccount.getBalance()) + money_choice.getSelectedItem().toString());

        ref[0] = totalBalanceLabel;

        totalBalanceLabel.setFont(new Font(null, Font.BOLD, 16));
        totalBalanceLabel.setForeground(Color.BLACK);
        centreUp.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        centreUp.add(totalBalanceLabel);

        centreInner.setLayout(new GridLayout(3, 2, 10, 10));
        centreInner.setMaximumSize(new Dimension(200, 100));

        depositButton.setFocusable(false);
        withdrawButton.setFocusable(false);
        personalInfoButton.setFocusable(false);
        exitButton.setFocusable(false);
        accountDataButton.setFocusable(false);

        depositButton.addActionListener(this);
        withdrawButton.addActionListener(this);
        personalInfoButton.addActionListener(this);
        exitButton.addActionListener(this);
        accountDataButton.addActionListener(this);

        addSavingsAccountButton.addActionListener(this);
        addSavingsAccountButton.setFocusable(false);

        centreInner.add(depositButton);
        centreInner.add(withdrawButton);
        centreInner.add(personalInfoButton);
        centreInner.add(accountDataButton);
        centreInner.add(addSavingsAccountButton);

        centre.setLayout(new BorderLayout());
        centre.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        centre.add(centreUp, BorderLayout.NORTH);
        centre.add(centreDown, BorderLayout.SOUTH);
        centre.add(centreLeft, BorderLayout.WEST);
        centre.add(centreRight, BorderLayout.EAST);
        centre.add(centreInner, BorderLayout.CENTER);

        //////////////////// Down
        down.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        down.add(exitButton);


        /////////////////// Frame
        frame.add(up, BorderLayout.NORTH);
        frame.add(down, BorderLayout.SOUTH);
        frame.add(left, BorderLayout.WEST);
        frame.add(right, BorderLayout.EAST);
        frame.add(centre, BorderLayout.CENTER);

        frame.setMinimumSize(new Dimension(700, 800));
        frame.getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 900);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Savings Account Menu");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == depositButton) {
            String choice = money_choice.getSelectedItem().toString();
            CustomDialog dialog = new CustomDialog(frame, "Deposit", choice, false, savingAccount, ref);
            dialog.setVisible(true);
        } else if (e.getSource() == withdrawButton) {
            String choice = money_choice.getSelectedItem().toString();
            CustomDialog dialog = new CustomDialog(frame, "Withdraw", choice, false, savingAccount, ref);
            dialog.setVisible(true);
        } else if (e.getSource() == personalInfoButton) {
            Map<String, String> userData = dataBaseUpdateDelete.getUserData(savingAccount.getFull_name(), "CUSTOMER");
            if (userData != null) {
                ViewDataDialog viewDataDialog = new ViewDataDialog(frame, "User Data", userData);
                viewDataDialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(frame, "Error");
            }

        } else if (e.getSource() == accountDataButton) {
            String choice = money_choice.getSelectedItem().toString();
            Map<String, String> accountData = dataBaseUpdateDelete.getSavingsAccountData(savingAccount.getFull_name(), choice);
            if (accountData != null) {
                ViewDataDialog viewDataDialog = new ViewDataDialog(frame, "Current Savings Account Data", accountData);
                viewDataDialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "No Account Data Found");
            }
        } else if (e.getSource() == money_choice) {
            /// update UI
            String choice = money_choice.getSelectedItem().toString();
            savingAccount.setCurrency_type(choice);
            savingAccount.setBalance(dataBaseMoney.getSavingsBalanceBy(savingAccount.getUsername(), choice));
            totalBalanceLabel.setText("Total Balance: " + dataBaseMoney.balanceFormatted(savingAccount.getBalance()) + choice);
        } else if (e.getSource() == addSavingsAccountButton) {

            opted_currencies = dataBaseUpdateDelete.get_currency(savingAccount.getFull_name());

            if (opted_currencies.size() == 3) {
                String[] select = new String[]{"USD", "EUR", "RON"};
                boolean allEqual = true;
                for (String currency : select) {
                    if (!opted_currencies.contains(currency)) {
                        allEqual = false;
                        break;
                    }
                }
                if (allEqual) {
                    JOptionPane.showMessageDialog(null, "You have all possible saving accounts!");
                }
            } else {
                AddSavingsAccount addSavingsAccount = new AddSavingsAccount(frame, dataBaseOp, savingAccount.getFull_name(), savingAccount.getUsername(), combobox);
                addSavingsAccount.setVisible(true);
            }

        } else if (e.getSource() == exitButton) {
            JOptionPane.showMessageDialog(frame, "Exited!");
            frame.dispose();
        }

    }

}