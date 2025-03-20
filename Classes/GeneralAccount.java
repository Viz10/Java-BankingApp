import javax.swing.*;

abstract class GeneralAccount {
    private String full_name;
    private String username;

    public DataBaseOp dataBaseOp;
    public DataBaseUpdateDelete dataBaseUpdateDelete;
    public DataBaseMoney dataBaseMoney;

    GeneralAccount(String full_name, String username, DataBaseOp dataBaseOp, DataBaseMoney dataBaseMoney, DataBaseUpdateDelete dataBaseUpdateDelete) {
        this.full_name = full_name;
        this.username = username;
        this.dataBaseOp = dataBaseOp;
        this.dataBaseMoney = dataBaseMoney;
        this.dataBaseUpdateDelete = dataBaseUpdateDelete;

    }

    public String getUsername() {
        return username;
    }
    public String getFull_name() {
        return full_name;
    }
    protected boolean validateBalanceFromKeyboard(String balance) {

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
        if (rez > 100000) {
            JOptionPane.showMessageDialog(null, "Balance must be less than or equal to 100,000.");
            return false;
        }

        return true;
    }

    /// make use of polymorphism between current account and savings account
    abstract boolean transfer(String whatPerson, long balance, String moneyFrom, String moneyWith, String moneyTo);
    abstract void deposit(long balance,String type);
    abstract boolean withdraw(long balance,String type);
    abstract long retrieve_balance(String type);
}
