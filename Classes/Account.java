import javax.swing.*;

public class Account extends GeneralAccount{

    private long amount_EUR;
    private long amount_USD;
    private long amount_RON;

    public Account(String full_name,String username, DataBaseOp dataBaseOp, DataBaseUpdateDelete dataBaseUpdateDelete, DataBaseMoney dataBaseMoney) {

      super(full_name,username,dataBaseOp,dataBaseMoney,dataBaseUpdateDelete);

        amount_USD = dataBaseMoney.getBalanceBy(username, "USD");
        amount_EUR = dataBaseMoney.getBalanceBy(username, "EUR");
        amount_RON = dataBaseMoney.getBalanceBy(username, "RON");

    }

    public long getAmount_RON() {
        return amount_RON;
    }

    public void setAmount_RON(long amount_RON) {
        this.amount_RON = amount_RON;
    }

    public long getAmount_USD() {
        return amount_USD;
    }

    public void setAmount_USD(long amount_USD) {
        this.amount_USD = amount_USD;
    }

    public long getAmount_EUR() {
        return amount_EUR;
    }

    public void setAmount_EUR(long amount_EUR) {
        this.amount_EUR = amount_EUR;
    }


    /////withdraw : Logic: if not enough money found in starter account keep searching in next 2 one by one ... in a generalized app with more than 3 currencies ,
    //// the check would try to cover the deficit in all accounts until 1 is met to fulfill payment , here money bits can be consumed by more than 1 OR 2 accounts

    @Override
    public boolean withdraw(long balance, String type) {
        return switch (type) {
            case "RON" -> withdrawFromRON(balance);
            case "USD" -> withdrawFromUSD(balance);
            case "EUR" -> withdrawFromEUR(balance);
            default -> false;
        };
    }
    private boolean withdrawFromRON(long balance) {
        long copyRON = getAmount_RON(); /// balance stored in DB using integer precision ( 100 = 10000, 1 = 100, 0.1 = 1)
        long intermediate = copyRON - balance; /// resulted balance after withdraw

        if (intermediate >= 0) { ///  enough money in the RON account
            dataBaseUpdateDelete.update_current_balance(getFull_name(), intermediate, "RON");
            setAmount_RON(intermediate); /// reset in account
        JOptionPane.showMessageDialog(null,"Taken "+dataBaseMoney.balanceFormatted(balance)+" from RON account");
        } else { /// not enough money in RON account, try subtracting to cover the deficit
            long copyUSD = getAmount_USD();
            long deficitUSD = CurrencyConverter.convertRONToUSD(-intermediate);
            long intermediateUSD = copyUSD - deficitUSD; /// subtract remaining amount

            if (intermediateUSD >= 0) {
                setAmount_RON(0); /// All gone
                setAmount_USD(intermediateUSD);
                dataBaseUpdateDelete.update_current_balance(getFull_name(), intermediateUSD, "USD");
                dataBaseUpdateDelete.update_current_balance(getFull_name(), 0, "RON");
                JOptionPane.showMessageDialog(null, "Not enough RON. Taken " + dataBaseMoney.balanceFormatted(deficitUSD) + " USD to cover the deficit.");
            } else {
                long copyEUR = getAmount_EUR();
                long deficitEUR = CurrencyConverter.convertUSDToEUR(-intermediateUSD);
                long intermediateEUR = copyEUR - deficitEUR;

                if (intermediateEUR >= 0) {
                    setAmount_RON(0);
                    setAmount_USD(0);
                    setAmount_EUR(intermediateEUR);
                    dataBaseUpdateDelete.update_current_balance(getFull_name(), intermediateEUR, "EUR");
                    dataBaseUpdateDelete.update_current_balance(getFull_name(), 0, "RON");
                    dataBaseUpdateDelete.update_current_balance(getFull_name(), 0, "USD");
                    JOptionPane.showMessageDialog(null, "Not enough RON and USD. Taken " + dataBaseMoney.balanceFormatted(deficitEUR) + " EUR to cover the deficit.");
                } else {
                    JOptionPane.showMessageDialog(null, "Not enough money in all accounts!");
                    return false;
                }
            }
        }
        return true;
    }
    private boolean withdrawFromUSD(long balance) {
        long copyUSD = getAmount_USD();
        long intermediate = copyUSD - balance;

        if (intermediate >= 0) {
            dataBaseUpdateDelete.update_current_balance(getFull_name(), intermediate, "USD");
            setAmount_USD(intermediate);
            JOptionPane.showMessageDialog(null,"Taken "+dataBaseMoney.balanceFormatted(balance)+" from USD account");
        } else {
            long copyRON = getAmount_RON();
            long deficitRON = CurrencyConverter.convertUSDToRON(-intermediate);
            long intermediateRON = copyRON - deficitRON;

            if (intermediateRON >= 0) {
                setAmount_USD(0);
                setAmount_RON(intermediateRON);
                dataBaseUpdateDelete.update_current_balance(getFull_name(), intermediateRON, "RON");
                dataBaseUpdateDelete.update_current_balance(getFull_name(), 0, "USD");
                JOptionPane.showMessageDialog(null, "Not enough USD. Taken " + dataBaseMoney.balanceFormatted(deficitRON) + " RON to cover the deficit.");
            } else {
                long copyEUR = getAmount_EUR();
                long deficitEUR = CurrencyConverter.convertRONToEUR(-intermediateRON);
                long intermediateEUR = copyEUR - deficitEUR;

                if (intermediateEUR >= 0) {
                    setAmount_USD(0);
                    setAmount_RON(0);
                    setAmount_EUR(intermediateEUR);
                    dataBaseUpdateDelete.update_current_balance(getFull_name(), intermediateEUR, "EUR");
                    dataBaseUpdateDelete.update_current_balance(getFull_name(), 0, "USD");
                    dataBaseUpdateDelete.update_current_balance(getFull_name(), 0, "RON");
                    JOptionPane.showMessageDialog(null, "Not enough USD and RON. Taken " + dataBaseMoney.balanceFormatted(deficitEUR) + " EUR to cover the deficit.");
                } else {
                    JOptionPane.showMessageDialog(null, "Not enough money in all accounts!");
                    return false;
                }
            }
        }
        return true;
    }
    private boolean withdrawFromEUR(long balance) {
        long copyEUR = getAmount_EUR();
        long intermediate = copyEUR - balance;

        if (intermediate >= 0) {
            dataBaseUpdateDelete.update_current_balance(getFull_name(), intermediate, "EUR");
            setAmount_EUR(intermediate);
            JOptionPane.showMessageDialog(null,"Taken "+dataBaseMoney.balanceFormatted(balance)+" from EUR account");
        } else {
            long copyRON = getAmount_RON();
            long deficitRON = CurrencyConverter.convertEURToRON(-intermediate);
            long intermediateRON = copyRON - deficitRON;

            if (intermediateRON >= 0) {
                setAmount_EUR(0);
                setAmount_RON(intermediateRON);
                dataBaseUpdateDelete.update_current_balance(getFull_name(), intermediateRON, "RON");
                dataBaseUpdateDelete.update_current_balance(getFull_name(), 0, "EUR");
                JOptionPane.showMessageDialog(null, "Not enough EUR. Taken " + dataBaseMoney.balanceFormatted(deficitRON) + " RON to cover the deficit.");
            } else {
                long copyUSD = getAmount_USD();
                long deficitUSD = CurrencyConverter.convertRONToUSD(-intermediateRON);
                long intermediateUSD = copyUSD - deficitUSD;

                if (intermediateUSD >= 0) {
                    setAmount_EUR(0);
                    setAmount_RON(0);
                    setAmount_USD(intermediateUSD);
                    dataBaseUpdateDelete.update_current_balance(getFull_name(), intermediateUSD, "USD");
                    dataBaseUpdateDelete.update_current_balance(getFull_name(), 0, "EUR");
                    dataBaseUpdateDelete.update_current_balance(getFull_name(), 0, "RON");
                    JOptionPane.showMessageDialog(null, "Not enough EUR and RON. Taken " + dataBaseMoney.balanceFormatted(deficitUSD) + " USD to cover the deficit.");
                } else {
                    JOptionPane.showMessageDialog(null, "Not enough money in all accounts!");
                    return  false;
                }
            }
        }
        return true;
    }

    //// deposit : in real life some bank/credit card transaction should be made between accounts , here just add number
    @Override
    public void deposit(long balance, String type) {
        switch (type) {
            case "RON" -> depositToRON(balance);
            case "USD" -> depositToUSD(balance);
            case "EUR" -> depositToEUR(balance);
        }
    }
    private void depositToRON(long balance) {
        long newBalance = getAmount_RON() + balance; /// add to already existing balance
        dataBaseUpdateDelete.update_current_balance(getFull_name(), newBalance, "RON");
        setAmount_RON(newBalance);
    }
    private void depositToUSD(long balance) {
        long newBalance = getAmount_USD() + balance;
        dataBaseUpdateDelete.update_current_balance(getFull_name(), newBalance, "USD");
        setAmount_USD(newBalance);
    }
    private void depositToEUR(long balance) {
        long newBalance = getAmount_EUR() + balance;
        dataBaseUpdateDelete.update_current_balance(getFull_name(),newBalance, "EUR");
        setAmount_EUR(newBalance);

    }

    ///////////////////////////////////////////////////////////////////////////////////////

    ///// select cases
    @Override
    public boolean transfer(String whatPerson, long balance, String moneyFrom, String moneyWith, String moneyTo) {
        boolean success;
        switch (moneyFrom) {
            case "RON" -> success = transferFromRON(balance, whatPerson, moneyWith, moneyTo);
            case "USD" -> success = transferFromUSD(balance, whatPerson, moneyWith, moneyTo);
            case "EUR" -> success = transferFromEUR(balance, whatPerson, moneyWith, moneyTo);
            default -> success = false;
        }
        return success;
    }
    private boolean transferFromRON(long balance, String toWhom, String moneyWith, String moneyTo) {
        long convertedBalance1 = 0;
        long convertedBalance2 = 0;

        /// FIRST: WITHDRAW MONEY FROM SENDER ACCOUNT USING "WITH" CURRENCY
        if (moneyWith.equals("USD")) {
            convertedBalance1 = CurrencyConverter.convertUSDToRON(balance);
        } else if (moneyWith.equals("EUR")) {
            convertedBalance1 = CurrencyConverter.convertEURToRON(balance);
        } else if (moneyWith.equals("RON")) {
            convertedBalance1 = balance;
        }
        if (!withdraw(convertedBalance1, "RON")) { /// failed to withdraw (not enough money in all accounts)
            return false;
        }

        /// SECOND: DEPOSIT WITHDREW AMOUNT INTO FINAL ACCOUNT
        if (moneyTo.equals("USD")) {
            convertedBalance2 = CurrencyConverter.convertRONToUSD(convertedBalance1);
        } else if (moneyTo.equals("EUR")) {
            convertedBalance2 = CurrencyConverter.convertRONToEUR(convertedBalance1);
        } else if (moneyTo.equals("RON")) {
            convertedBalance2 = convertedBalance1;
        }

        long tobedeposited = convertedBalance2 + dataBaseMoney.getBalanceBy(dataBaseMoney.getUsernameByFullName(toWhom), moneyTo);
        depositToRecipient(toWhom, tobedeposited, moneyTo);
        JOptionPane.showMessageDialog(null, "Transferred " + dataBaseMoney.balanceFormatted(balance) + " " + moneyWith + " to " + toWhom + " (" + dataBaseMoney.balanceFormatted(convertedBalance2) + " " + moneyTo + ")");
        return true;
    }
    private boolean transferFromUSD(long balance, String toWhom, String moneyWith, String moneyTo) {
        long convertedBalance1 = 0;
        long convertedBalance2 = 0;

        if (moneyWith.equals("USD")) {
            convertedBalance1 = balance;
        } else if (moneyWith.equals("EUR")) {
            convertedBalance1 = CurrencyConverter.convertEURToUSD(balance);
        } else if (moneyWith.equals("RON")) {
            convertedBalance1 = CurrencyConverter.convertRONToUSD(balance);
        }

        if (!withdraw(convertedBalance1, "USD")) {
            return false;
        }

        if (moneyTo.equals("USD")) {
            convertedBalance2 = convertedBalance1;
        } else if (moneyTo.equals("EUR")) {
            convertedBalance2 = CurrencyConverter.convertUSDToEUR(convertedBalance1);
        } else if (moneyTo.equals("RON")) {
            convertedBalance2 = CurrencyConverter.convertUSDToRON(convertedBalance1);
        }

        long tobedeposited = convertedBalance2 + dataBaseMoney.getBalanceBy(dataBaseMoney.getUsernameByFullName(toWhom), moneyTo);
        depositToRecipient(toWhom, tobedeposited, moneyTo);
        JOptionPane.showMessageDialog(null, "Transferred " + dataBaseMoney.balanceFormatted(balance) + " " + moneyWith + " to " + toWhom + " (" + dataBaseMoney.balanceFormatted(convertedBalance2) + " " + moneyTo + ")");
        return true;
    }
    private boolean transferFromEUR(long balance, String toWhom, String moneyWith, String moneyTo) {
        long convertedBalance1 = 0;
        long convertedBalance2 = 0;

        if (moneyWith.equals("EUR")) {
            convertedBalance1 = balance;
        } else if (moneyWith.equals("USD")) {
            convertedBalance1 = CurrencyConverter.convertUSDToEUR(balance);
        } else if (moneyWith.equals("RON")) {
            convertedBalance1 = CurrencyConverter.convertRONToEUR(balance);
        }

        if (!withdraw(convertedBalance1, "EUR")) {
            return false;
        }

        if (moneyTo.equals("EUR")) {
            convertedBalance2 = convertedBalance1;
        } else if (moneyTo.equals("USD")) {
            convertedBalance2 = CurrencyConverter.convertEURToUSD(convertedBalance1);
        } else if (moneyTo.equals("RON")) {
            convertedBalance2 = CurrencyConverter.convertEURToRON(convertedBalance1);
        }

        long tobedeposited = convertedBalance2 + dataBaseMoney.getBalanceBy(dataBaseMoney.getUsernameByFullName(toWhom), moneyTo);
        depositToRecipient(toWhom, tobedeposited, moneyTo);
        JOptionPane.showMessageDialog(null, "Transferred " + dataBaseMoney.balanceFormatted(balance) + " " + moneyWith + " to " + toWhom + " (" + dataBaseMoney.balanceFormatted(convertedBalance2) + " " + moneyTo + ")");
        return true;
    }
    private void depositToRecipient(String toWhom, long balance, String typeWhom) {
        switch (typeWhom) { /// call functions
            case "RON" -> dataBaseUpdateDelete.update_current_balance(toWhom, balance, "RON");
            case "USD" -> dataBaseUpdateDelete.update_current_balance(toWhom, balance, "USD");
            case "EUR" -> dataBaseUpdateDelete.update_current_balance(toWhom, balance, "EUR");
        }
    }

    @Override
    public long retrieve_balance(String type){
        return dataBaseMoney.getBalanceBy(getUsername(), type);
    }

    ////////////////////////////////////////////////////////////////////////////////////////

}
