import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

public class UserMenuPage implements ActionListener {

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

    String username;
    String full_name;
    JComboBox<String> money_choice=new JComboBox<>();

    private DataBaseUpdateDelete dataBaseUpdateDelete;
    private DataBaseOp dataBaseOp;
    private DataBaseMoney dataBaseMoney;
    private Account account;

    JButton depositButton = new JButton("Deposit");
    JButton withdrawButton = new JButton("Withdraw");
    JButton transferButton = new JButton("Transfer");
    JButton pastTransfersButton = new JButton("Past Transfers ");
    JButton personalInfoButton = new JButton("See Personal Info");
    JButton savingsAccountButton = new JButton("Savings Account");
    JButton exitButton = new JButton("Exit");
    JButton addFriend = new JButton("Add Friend");
    JButton viewFriendsButton = new JButton("View Friends");
    JButton accountDataButton = new JButton("Account Data");
    JLabel[] ref = new JLabel[1];

    UserMenuPage(String username) {

        dataBaseUpdateDelete = new DataBaseUpdateDelete();
        dataBaseOp = new DataBaseOp();
        dataBaseMoney = new DataBaseMoney();

        this.username = username;
        full_name=dataBaseMoney.getFullNameByUsername(username);

        account = new Account(full_name,username, dataBaseOp, dataBaseUpdateDelete, dataBaseMoney); /// create current account helper

        ///////////////////////////////////////////////////////////////////////////////////////////////

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

        centreUp.setBackground(Color.lightGray);
        centreDown.setBackground(Color.lightGray);
        centreLeft.setBackground(Color.lightGray);
        centreRight.setBackground(Color.lightGray);

        ////////////////////////////////// UP
        welcomeLabel = new JLabel("Welcome to your account , " + account.getFull_name());
        welcomeLabel.setFont(new Font(null, Font.BOLD, 20));
        welcomeLabel.setForeground(Color.BLACK);
        up.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        up.add(welcomeLabel);

        //////////////////////////////// Center

        centreDown.add(new JLabel("Select Account:"));
        money_choice = new JComboBox<>(new String[]{"RON", "USD", "EUR"});
        money_choice.addActionListener(this);
        centreDown.add(money_choice);

        totalBalanceLabel = new JLabel("Total Balance: " + dataBaseMoney.balanceFormatted(account.getAmount_RON()) + " RON");
         ref[0]= totalBalanceLabel;

        totalBalanceLabel.setFont(new Font(null, Font.BOLD, 16));
        totalBalanceLabel.setForeground(Color.BLACK);
        centreUp.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        centreUp.add(totalBalanceLabel);

        centreInner.setLayout(new GridLayout(2, 3, 10, 10));
        centreInner.setMaximumSize(new Dimension(200, 100));

        depositButton.setFocusable(false);
        withdrawButton.setFocusable(false);
        transferButton.setFocusable(false);
        pastTransfersButton.setFocusable(false);
        personalInfoButton.setFocusable(false);
        savingsAccountButton.setFocusable(false);
        exitButton.setFocusable(false);
        addFriend.setFocusable(false);
        viewFriendsButton.setFocusable(false);
        accountDataButton.setFocusable(false);

        depositButton.addActionListener(this);
        withdrawButton.addActionListener(this);
        transferButton.addActionListener(this);
        pastTransfersButton.addActionListener(this);
        personalInfoButton.addActionListener(this);
        savingsAccountButton.addActionListener(this);
        exitButton.addActionListener(this);
        addFriend.addActionListener(this);
        viewFriendsButton.addActionListener(this);
        accountDataButton.addActionListener(this);

        centreInner.add(depositButton);
        centreInner.add(withdrawButton);
        centreInner.add(transferButton);
        centreInner.add(pastTransfersButton);
        centreInner.add(personalInfoButton);
        centreInner.add(accountDataButton);

        centre.setLayout(new BorderLayout());
        centre.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        centre.add(centreUp, BorderLayout.NORTH);
        centre.add(centreDown, BorderLayout.SOUTH);
        centre.add(centreLeft, BorderLayout.WEST);
        centre.add(centreRight, BorderLayout.EAST);
        centre.add(centreInner, BorderLayout.CENTER);

        //////////////////// Down
        down.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        down.add(savingsAccountButton);
        down.add(exitButton);
        down.add(addFriend);
        down.add(viewFriendsButton);

        centreInner.setBackground(Color.CYAN);
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
        frame.setTitle("Account Menu");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String choice = money_choice.getSelectedItem().toString();

        if (e.getSource() == depositButton) {
            CustomDialog dialog = new CustomDialog(frame,"Deposit",choice,false,account,ref);
            dialog.setVisible(true);
        }
        else if (e.getSource() == withdrawButton) {
            CustomDialog dialog = new CustomDialog(frame,"Withdraw",choice,false,account,ref);
            dialog.setVisible(true);
        }
        else if (e.getSource() == transferButton) {
            CustomDialog dialog = new CustomDialog(frame,"Transfer",choice,true,account,ref);
            dialog.setVisible(true);
        }
        else if (e.getSource() == pastTransfersButton) {
            ArrayList<TransferClass> list = dataBaseUpdateDelete.getPastTransfersSent(account.getUsername(),money_choice.getSelectedItem().toString());
            if(list.isEmpty()){
                JOptionPane.showMessageDialog(frame,"Past transfers not available");
            }
            else{
                PastTransactions pastTransactions = new PastTransactions(frame,"Past Transfers",list);
                pastTransactions.setVisible(true);
            }
        }
        else if (e.getSource() == personalInfoButton) {
            Map<String, String> userData = dataBaseUpdateDelete.getUserData(account.getFull_name(), "CUSTOMER");
            if (userData != null) {
                ViewDataDialog viewDataDialog = new ViewDataDialog(frame, "User Data", userData);
                viewDataDialog.setVisible(true);
            }
            else{
                JOptionPane.showMessageDialog(frame, "Error");
            }

        }
        else if(e.getSource() == accountDataButton){
            Map<String, String> accountData = dataBaseUpdateDelete.getAccountData(account.getFull_name(), money_choice.getSelectedItem().toString());
            if (accountData != null) {
                ViewDataDialog viewDataDialog = new ViewDataDialog(frame, "Current Account Data", accountData);
                viewDataDialog.setVisible(true);
            }
            else{
                JOptionPane.showMessageDialog(null, "No Account Data Found");
            }
        }
        else if(e.getSource()==money_choice){
            if(money_choice.getSelectedItem().toString().equals("USD")){
                totalBalanceLabel.setText("Total Balance: " + dataBaseMoney.balanceFormatted(account.getAmount_USD()) + " USD");
            }
            else if(money_choice.getSelectedItem().toString().equals("EUR")){
                totalBalanceLabel.setText("Total Balance: " + dataBaseMoney.balanceFormatted(account.getAmount_EUR()) + " EUR");
            }
            else if(money_choice.getSelectedItem().toString().equals("RON")){
                totalBalanceLabel.setText("Total Balance: " + dataBaseMoney.balanceFormatted(account.getAmount_RON()) + " RON");
            }
        }
        else if (e.getSource() == savingsAccountButton) {
            ArrayList<String> currencies;
            currencies = dataBaseUpdateDelete.get_currency(account.getFull_name());
            if (currencies.isEmpty()) { /// no account made
                JOptionPane.showMessageDialog(frame, "No saving account!"+"\nLets add one");
                AddSavingsAccount addSavingsAccount = new AddSavingsAccount(frame,dataBaseOp,account.getFull_name(),account.getUsername(),null);
                addSavingsAccount.setVisible(true);
            }
            else{ /// enter savings account frame
                UserMenuSavingsPage userMenuSavingsPage=new UserMenuSavingsPage(account.getFull_name(),account.getUsername(),dataBaseOp,dataBaseMoney,dataBaseUpdateDelete);
            }
        }
        else if(e.getSource() == exitButton){
            JOptionPane.showMessageDialog(frame,"Exited!");
            frame.dispose();
        }
        else if(e.getSource() == addFriend){
            String name=JOptionPane.showInputDialog(frame,"Enter your friend username");
            int id1=dataBaseMoney.get_user_id(account.getUsername());
            int id2=dataBaseMoney.get_customer_user_id(name);
            if(id2==-1)
            {
                JOptionPane.showMessageDialog(frame,"User not found!");
            }
            else{
                ArrayList<String> check = dataBaseMoney.getFriendUsernames(id1);
                if(check.contains(name))
                {
                    JOptionPane.showMessageDialog(frame,"Friend already exists!");
                }
                else {
                    /// A will be friend with B and B will also be friend with A
                    boolean ok;
                    ok=dataBaseMoney.insertFriends(id1, id2) && dataBaseMoney.insertFriends(id2, id1);
                    if(ok){
                        JOptionPane.showMessageDialog(frame,"Friend added!");
                    }
                    else{
                        JOptionPane.showMessageDialog(frame,"Error!");
                    }
                }
            }
        }
        else if(e.getSource()==viewFriendsButton){
                ArrayList<String> arr = dataBaseMoney.getAllFriends(dataBaseMoney.get_user_id(account.getUsername()));
                if(arr.isEmpty()){
                    JOptionPane.showMessageDialog(frame,"No friends found!");
                }
                else{
                    ViewFriends view = new ViewFriends(frame,"View Friends",arr);
                    view.setVisible(true);
                }
        }

    }

}