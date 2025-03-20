import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import java.awt.*;


public class UpdateComponents extends RegisterComponents{

    protected String admin;
    protected String customerName;
    protected DataBaseUpdateDelete dataBaseUpdateDelete;

    protected JFrame frame;

    protected JPanel up;
    protected JPanel down;
    protected JPanel left;
    protected JPanel right;
    protected JPanel center;

    protected JButton removePersonalAccount;
    protected JButton removeSavingsAccount;
    protected JButton finishButton;
    protected JButton checkpersonalButton;

    protected JLabel currency_choice;

    protected JLabel genreLabel;

    protected JLabel update1;
    protected JLabel update2;
    protected JLabel update3;

    protected JLabel accountBalanceLabel;
    protected JLabel savingsBalanceLabel;
    protected JLabel setInterestRateLabel;

    protected JTextField balanceAccountField;
    protected JTextField balanceSavingsField;
    protected JTextField interest_rateSavingsField;

    protected JComboBox<String> comboBox1;
    protected JComboBox<String> comboBox2;
    protected JComboBox<String> comboBox3;

    protected JButton blockButton;
    protected JButton unblockButton;
    protected JButton changeBalanceButton;
    protected JButton changeSavingsBalanceButton;
    protected JButton changeinterestrateButton;
    protected JButton seePersonButton;
    protected JButton seeSavingsAccountButton;
    protected JButton seeCurrentAccountButton;

    protected JPanel blockPanel;
    protected JPanel changeBalancePanel;
    protected JPanel balanceSavingsPanel;
    protected JPanel interestratePanel;


    UpdateComponents(){

        super();

         dataBaseUpdateDelete  = new DataBaseUpdateDelete();

         frame = new JFrame();

         up = new JPanel();
         down = new JPanel();
         left = new JPanel();
         right = new JPanel();
         center = new JPanel(new GridLayout(1, 2));

         /// UP DOWN and CENTRE will form the update/delete page , with centre holding 2 equaly pannels

         removePersonalAccount = new JButton("Delete user full account ");
         removeSavingsAccount = new JButton("Remove savings account");
         finishButton = new JButton("Done");/// DONE
         checkpersonalButton = new JButton("Update personal/user data");

         currency_choice = new JLabel("Select saving account currency: ");
         genreLabel = new JLabel("Select account genre: ");

         update1 = new JLabel("Update user/personal data: ");
         update2 = new JLabel("Update account data: ");
         update3 = new JLabel("Update savings account data: ");

         accountBalanceLabel = new JLabel("Set balance and currency: ");
         savingsBalanceLabel = new JLabel("Set balance and currency: ");
         setInterestRateLabel = new JLabel("Set interest rate: ");

         balanceAccountField = new JTextField(10);
         balanceSavingsField = new JTextField(10);
         interest_rateSavingsField = new JTextField(10);

         blockButton = new JButton("Block");
         unblockButton = new JButton("Unblock");
         changeBalanceButton = new JButton("Update balance");
         changeSavingsBalanceButton = new JButton("Update savings balance");
         changeinterestrateButton = new JButton("Update interest rate");
         seePersonButton= new JButton("View personal data ");
         seeSavingsAccountButton= new JButton("View savings account");
         seeCurrentAccountButton= new JButton("View current account");

         blockPanel = new JPanel(new FlowLayout());
         changeBalancePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
         balanceSavingsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
         interestratePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        DocumentFilter filter = new NoSpaceDocumentFilter();
        ((AbstractDocument) balanceAccountField.getDocument()).setDocumentFilter(filter);
        ((AbstractDocument) interest_rateSavingsField.getDocument()).setDocumentFilter(filter);
        ((AbstractDocument) balanceSavingsField.getDocument()).setDocumentFilter(filter);

        blockPanel.setBackground(new Color(117, 117, 72));
        changeBalancePanel.setBackground(new Color(117, 117, 72));
        balanceSavingsPanel.setBackground(new Color(117, 117, 72));
        interestratePanel.setBackground(new Color(117, 117, 72));

    }

    protected boolean validatePassword(String password) {
        if (password.isEmpty()) {
            error.message = "Blank Password Field";
            return false;
        }

        if (password.length() < 6) {
            error.message = "Password must be at least 6 characters long";
            return false;
        }

        if (password.length() > 30) {
            error.message = "Password is too long";
            return false;
        }

        boolean foundLetterInPassword = false;
        boolean foundDigitInPassword = false;
        boolean foundSpecialCharInPassword = false;
        for (char ch : password.toCharArray()) {
            if (Character.isLetter(ch)) {
                foundLetterInPassword = true;
            }
            if (Character.isDigit(ch)) {
                foundDigitInPassword = true;
            }
            if (!Character.isLetterOrDigit(ch)) {
                foundSpecialCharInPassword = true;
            }
            if (foundLetterInPassword && foundDigitInPassword && foundSpecialCharInPassword) {
                break;
            }
        }

        if (!foundLetterInPassword || !foundDigitInPassword || !foundSpecialCharInPassword) {
            error.message = "Password must contain at least one letter, one digit, and one special character";
            return false;
        }


        userPersonalData.setPassword(password);

        return true;
    }

    protected boolean validateBalanceFromKeyboard(String balance) {

        if(balance.isEmpty()){
            error.message="Blank Balance Field";
            return false;
        }

        if (balance.contains(".") || balance.contains(",")) {
            error.message = "Fractional values are not allowed.";
            return false;
        }

        if (!balance.matches("\\d+")) {
            error.message = "Balance must only contain digits ";
            return false;
        }

        long rez=Long.parseLong(balance);
        if(rez>100000){
            error.message = "Balance must be less than or equal to 100.000 ";
            return false;
        }

        return true;
    }

    protected boolean validateInterestRateSavingsField(String interestRate) {
        if (interestRate.isEmpty()) {
            error.message = "Interest rate field cannot be empty.";
            return false;
        }

        if (!interestRate.matches("\\d+(\\.\\d{1,2})?")) {
            error.message = "Interest rate must be a valid number between 1.00% and 5.00%";
            return false;
        }

        double rez;
        try {
            rez = Double.parseDouble(interestRate);
        } catch (NumberFormatException e) {
            error.message = "Interest rate must be a valid number  be between 1.00% and 5.00%";
            return false;
        }

        if (rez < 1 || rez > 5) {
            error.message = "Interest rate must be between 1.00% and 5.00%";
            return false;
        }

        return true;
    }

}
