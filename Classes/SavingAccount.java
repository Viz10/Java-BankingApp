import javax.swing.*;

public class SavingAccount extends GeneralAccount {

    private long balance;
    private String currency_type;

    public SavingAccount(String full_name,String username,String currency_type, DataBaseOp dataBaseOp, DataBaseUpdateDelete dataBaseUpdateDelete, DataBaseMoney dataBaseMoney) {

        super(full_name,username,dataBaseOp,dataBaseMoney,dataBaseUpdateDelete);
        this.currency_type = currency_type;
        balance=dataBaseMoney.getSavingsBalanceBy(username,currency_type); /// default set balance on the type

    }

    public String getCurrency_type() {
        return currency_type;
    }
    public void setCurrency_type(String currency_type) {
        this.currency_type = currency_type;
    }

    public long getBalance() {
        return balance;
    }
    public void setBalance(long balance) {
        this.balance = balance;
    }

    @Override
    public void deposit(long deposited_balance,String type) {
        long new_balance=balance+deposited_balance;
        dataBaseUpdateDelete.update_savings_balance(getFull_name(),new_balance,getCurrency_type());
        setBalance(new_balance);
    }

    @Override
    public boolean withdraw(long withdraw_balance,String type) {
        long intermediate = balance - withdraw_balance;

        if (intermediate >= 0) {
            dataBaseUpdateDelete.update_savings_balance(getFull_name(), intermediate, getCurrency_type());
            setBalance(intermediate);
            JOptionPane.showMessageDialog(null,"Taken "+dataBaseMoney.balanceFormatted(withdraw_balance)+" from "+getCurrency_type()+" account");
            return true;
        } else {
            JOptionPane.showMessageDialog(null,"Not enough money in"+getCurrency_type()+" account");
            return false;
        }
    }

    @Override
    public boolean transfer(String whatPerson, long balance, String moneyFrom, String moneyWith, String moneyTo){
        System.out.println("Not implemented yet");
        return false;
    }

    @Override
    public long retrieve_balance(String type){
        return dataBaseMoney.getSavingsBalanceBy(getUsername(), type);
    }
}
