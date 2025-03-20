
public class TransferClass {

    private String time;
    private long amount;

    private String fromAccountUsername;
    private String toAccountUsername;

    private String fromAccountName;
    private String toAccountName;

    private String currencyFrom;
    private String currencyTo;
    private String currencyWith;

    TransferClass(long amount, String fromAccountUsername, String toAccountUsername, String currencyFrom,String currencyWith,String currencyTo) {

        this.amount = amount;
        this.fromAccountUsername = fromAccountUsername;
        this.toAccountUsername = toAccountUsername;
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
        this.currencyWith = currencyWith;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getFromAccountUsername() {
        return fromAccountUsername;
    }

    public void setFromAccountUsername(String fromAccountUsername) {
        this.fromAccountUsername = fromAccountUsername;
    }

    public String getToAccountUsername() {
        return toAccountUsername;
    }

    public void setToAccountUsername(String toAccountUsername) {
        this.toAccountUsername = toAccountUsername;
    }

    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public void setCurrencyFrom(String currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    public String getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(String currencyTo) {
        this.currencyTo = currencyTo;
    }

    public String getCurrencyWith() {
        return currencyWith;
    }

    public void setCurrencyWith(String currencyWith) {
        this.currencyWith = currencyWith;
    }

    public String getFromAccountName() {
        return fromAccountName;
    }

    public void setFromAccountName(String fromAccountName) {
        this.fromAccountName = fromAccountName;
    }

    public String getToAccountName() {
        return toAccountName;
    }

    public void setToAccountName(String toAccountName) {
        this.toAccountName = toAccountName;
    }
}
