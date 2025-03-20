
public class Main {

    public static void main(String[] args) {

        /// start the application with login gui + perform real time check to update saving accounts with interest rate
        DataBaseMoney dataBaseMoney = new DataBaseMoney();
        dataBaseMoney.savings_after_interest();
        LoginPage loginpage = new LoginPage();
    }
}
