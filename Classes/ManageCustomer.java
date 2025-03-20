import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

public class ManageCustomer extends UpdateComponents implements ActionListener {

    ManageCustomer(String customerName, String admin) {

        super();  /// Inherit data fields and labels for update segment

        dataBaseUpdateDelete = new DataBaseUpdateDelete();
        this.customerName = customerName;
        this.admin = admin;

        frame.setLayout(new BorderLayout(10, 10));

        up.setPreferredSize(new Dimension(100, 90));
        down.setPreferredSize(new Dimension(100, 70));
        left.setPreferredSize(new Dimension(700, 200));
        right.setPreferredSize(new Dimension(700, 200));

        up.setBackground(new Color(204, 153, 255));
        down.setBackground(new Color(102, 255, 102));

        /////////////////////////////////////////////////////////////////////////////////////////////////// UP

        up.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        removePersonalAccount.setPreferredSize(new Dimension(200, 60));
        removeSavingsAccount.setPreferredSize(new Dimension(200, 60));

        removePersonalAccount.setFocusable(false);
        removePersonalAccount.addActionListener(this);
        removeSavingsAccount.setFocusable(false);
        removeSavingsAccount.addActionListener(this);


        String[] choice = {"RON", "USD", "EUR"};
        comboBox1 = new JComboBox<>(choice);
        comboBox1.addActionListener(this);
        comboBox2 = new JComboBox<>(choice);
        comboBox2.addActionListener(this);

        currency_choice.setFont(new Font(null, Font.PLAIN, 16));

        up.add(removePersonalAccount);
        up.add(removeSavingsAccount);
        up.add(currency_choice);
        up.add(comboBox1);

        ///////////////////////////////////////////////////////////////////////////////////////////////////// CENTRE

        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        left.setBackground(new Color(47, 137, 241));
        right.setBackground(new Color(117, 117, 72));


        blockButton.addActionListener(this);
        blockButton.setFocusable(false);
        unblockButton.addActionListener(this);
        unblockButton.setFocusable(false);

        changeBalanceButton.addActionListener(this);
        changeBalanceButton.setFocusable(false);

        changeinterestrateButton.addActionListener(this);
        changeinterestrateButton.setFocusable(false);
        changeSavingsBalanceButton.addActionListener(this);
        changeSavingsBalanceButton.setFocusable(false);


        Dimension fieldSize = new Dimension(200, 60); /// TEXT FIELDS
        FirstNameField.setMaximumSize(fieldSize);
        SurnameField.setMaximumSize(fieldSize);
        emailField.setMaximumSize(fieldSize);
        passwordField.setMaximumSize(fieldSize);
        passwordconfirmField.setMaximumSize(fieldSize);
        usernameField.setMaximumSize(fieldSize);
        streetField.setMaximumSize(fieldSize);
        streetnumberField.setMaximumSize(fieldSize);
        phoneField.setMaximumSize(fieldSize);
        residenceField.setMaximumSize(fieldSize);
        cityField.setMaximumSize(fieldSize);
        genre_field.setMaximumSize(fieldSize);

        Font labelFont = new Font(null, Font.PLAIN, 15); /// LABEL
        userNameLabel.setFont(labelFont);
        emailLabel.setFont(labelFont);
        passwordLabel.setFont(labelFont);
        passwordconfirmLabel.setFont(labelFont);
        FirstNameLabel.setFont(labelFont);
        SurnameLabel.setFont(labelFont);
        phoneLabel.setFont(labelFont);
        residenceLabel.setFont(labelFont);
        gender.setFont(labelFont);
        streetLabel.setFont(labelFont);
        streetnumberLabel.setFont(labelFont);
        cityLabel.setFont(labelFont);
        genreLabel.setFont(labelFont);
        update1.setFont(new Font(null, Font.PLAIN, 20));
        accountBalanceLabel.setFont(labelFont);
        update2.setFont(new Font(null, Font.PLAIN, 20));
        comboBox2.setMaximumSize(new Dimension(100, 40));


        update1.setAlignmentX(Component.CENTER_ALIGNMENT);
        userNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        FirstNameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        FirstNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        SurnameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        SurnameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordconfirmLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordconfirmField.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailField.setAlignmentX(Component.CENTER_ALIGNMENT);
        streetLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        streetField.setAlignmentX(Component.CENTER_ALIGNMENT);
        streetnumberLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        streetnumberField.setAlignmentX(Component.CENTER_ALIGNMENT);
        phoneLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        phoneField.setAlignmentX(Component.CENTER_ALIGNMENT);
        residenceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        residenceField.setAlignmentX(Component.CENTER_ALIGNMENT);
        gender.setAlignmentX(Component.CENTER_ALIGNMENT);
        cityField.setAlignmentX(Component.CENTER_ALIGNMENT);
        cityLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        genreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        genre_field.setAlignmentX(Component.CENTER_ALIGNMENT);

        comboBox2.setAlignmentX(Component.CENTER_ALIGNMENT);
        accountBalanceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        update2.setAlignmentX(Component.CENTER_ALIGNMENT);

        blockPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        changeBalancePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        checkpersonalButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        left.add(update1);
        left.add(Box.createRigidArea(new Dimension(0, 20)));
        left.add(userNameLabel);
        left.add(usernameField);
        left.add(passwordLabel);
        left.add(passwordField);
        left.add(Box.createRigidArea(new Dimension(0, 50)));
        left.add(SurnameLabel);
        left.add(SurnameField);
        left.add(FirstNameLabel);
        left.add(FirstNameField);
        left.add(phoneLabel);
        left.add(phoneField);
        left.add(cityLabel);
        left.add(cityField);
        left.add(streetLabel);
        left.add(streetField);
        left.add(streetnumberLabel);
        left.add(streetnumberField);
        left.add(residenceLabel);
        left.add(residenceField);
        left.add(emailLabel);
        left.add(emailField);
        left.add(genreLabel);
        left.add(genre_field);

        checkpersonalButton.setPreferredSize(new Dimension(200, 40));
        checkpersonalButton.addActionListener(this);
        checkpersonalButton.setFocusable(false);
        left.add(Box.createRigidArea(new Dimension(0, 20)));
        left.add(checkpersonalButton);

        ///////////////////////////////////////////////////////////////////////////////// RIGHT

        blockPanel.setPreferredSize(new Dimension(500, 50));
        blockPanel.setMaximumSize(new Dimension(700, 50));
        blockButton.setPreferredSize(new Dimension(100, 40));
        unblockButton.setPreferredSize(new Dimension(100, 40));
        blockPanel.add(blockButton);
        blockPanel.add(unblockButton);

        changeBalancePanel.setPreferredSize(new Dimension(500, 60));
        changeBalancePanel.setMaximumSize(new Dimension(700, 60));
        balanceAccountField.setPreferredSize(new Dimension(80, 40));
        changeBalanceButton.setPreferredSize(new Dimension(120, 40));
        changeBalancePanel.add(balanceAccountField);
        changeBalancePanel.add(changeBalanceButton);

        right.add(Box.createRigidArea(new Dimension(0, 70)));
        right.add(update2);
        right.add(Box.createRigidArea(new Dimension(0, 20)));
        right.add(blockPanel);
        right.add(Box.createRigidArea(new Dimension(0, 20)));
        right.add(accountBalanceLabel);
        right.add(Box.createRigidArea(new Dimension(0, 20)));
        right.add(comboBox2);
        right.add(Box.createRigidArea(new Dimension(0, 20)));
        right.add(changeBalancePanel);

        ArrayList<String> currencies;
        currencies = dataBaseUpdateDelete.get_currency(customerName);

        if (currencies.isEmpty()) {
            JLabel error = new JLabel("User has no associated saving accounts");
            error.setForeground(Color.RED);
            error.setFont(new Font(null, Font.PLAIN, 20));
            error.setAlignmentX(Component.CENTER_ALIGNMENT);
            right.add(Box.createRigidArea(new Dimension(0, 80)));
            right.add(error);
        }
        else {

            comboBox3 = new JComboBox<>();
            for (String iso : currencies) { /// set available currencies
                comboBox3.addItem(iso);
            }
            comboBox3.addActionListener(this);

            balanceSavingsPanel.setPreferredSize(new Dimension(500, 60));
            balanceSavingsPanel.setMaximumSize(new Dimension(700, 60));
            balanceSavingsField.setPreferredSize(new Dimension(100, 40));
            changeSavingsBalanceButton.setPreferredSize(new Dimension(170, 40));
            balanceSavingsPanel.add(balanceSavingsField);
            balanceSavingsPanel.add(changeSavingsBalanceButton);


            interestratePanel.setPreferredSize(new Dimension(500, 60));
            interestratePanel.setMaximumSize(new Dimension(700, 60));
            interest_rateSavingsField.setPreferredSize(new Dimension(100, 40));
            changeinterestrateButton.setPreferredSize(new Dimension(170, 40));
            interestratePanel.add(interest_rateSavingsField);
            interestratePanel.add(changeinterestrateButton);

            comboBox3.setMaximumSize(new Dimension(100, 40));
            update3.setFont(new Font(null, Font.PLAIN, 20));

            savingsBalanceLabel.setFont(labelFont);
            setInterestRateLabel.setFont(labelFont);


            update3.setAlignmentX(Component.CENTER_ALIGNMENT);
            savingsBalanceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            setInterestRateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            comboBox3.setAlignmentX(Component.CENTER_ALIGNMENT);
            balanceSavingsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            interestratePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            right.add(Box.createRigidArea(new Dimension(0, 25)));
            right.add(update3);
            right.add(Box.createRigidArea(new Dimension(0, 5)));
            right.add(savingsBalanceLabel);
            right.add(Box.createRigidArea(new Dimension(0, 10)));
            right.add(comboBox3);
            right.add(balanceSavingsPanel);
            right.add(Box.createRigidArea(new Dimension(0, 10)));
            right.add(setInterestRateLabel);
            right.add(interestratePanel);
            right.add(Box.createRigidArea(new Dimension(0, 10)));

        }

        center.add(left);
        center.add(right);

        ///////////////////////////////////////////////////////////////////////////////////////////////////// DOWN

        down.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        seeCurrentAccountButton.addActionListener(this);
        seeCurrentAccountButton.setFocusable(false);
        seeSavingsAccountButton.addActionListener(this);
        seeSavingsAccountButton.setFocusable(false);
        seePersonButton.addActionListener(this);
        seePersonButton.setFocusable(false);

        finishButton.setPreferredSize(new Dimension(100, 50));
        finishButton.setFocusable(false);
        finishButton.addActionListener(this);
        finishButton.setBackground(Color.GRAY);
        seePersonButton.setFocusable(false);
        seeSavingsAccountButton.setFocusable(false);
        seeCurrentAccountButton.setFocusable(false);

        seePersonButton.setPreferredSize(new Dimension(150, 50));
        seeSavingsAccountButton.setPreferredSize(new Dimension(180, 50));
        seeCurrentAccountButton.setPreferredSize(new Dimension(180, 50));


        down.add(seePersonButton);
        down.add(finishButton);
        down.add(seeCurrentAccountButton);
        down.add(seeSavingsAccountButton);

        /////////////////////////////////////////////////////////////////////////////////////////////// FRAME

        frame.add(up, BorderLayout.NORTH);
        frame.add(down, BorderLayout.SOUTH);
        frame.add(center, BorderLayout.CENTER);

        frame.pack();
        frame.setMinimumSize(new Dimension(1000, 900));
        frame.setSize(1500, 1000);
        frame.setTitle("Customer Management System");
        frame.setLocationRelativeTo(null);
        frame.getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == removePersonalAccount) {

            String entered_password = JOptionPane.showInputDialog("Are you sure you want to delete " + customerName + " bank account ? " + "\nEnter admin password:");
            if (entered_password == null || entered_password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Blank Password");
            } else if (!dataBaseUpdateDelete.check_admin_password(admin, entered_password)) {
                JOptionPane.showMessageDialog(null, "Incorrect password.");
            } else {
                dataBaseUpdateDelete.remove_user_account(customerName);
                frame.dispose();
            }
        }
        else if (e.getSource() == removeSavingsAccount) {
            if (dataBaseUpdateDelete.get_currency(customerName).isEmpty()) {
                JOptionPane.showMessageDialog(null, "User has no associated saving accounts");
            } else {
                String entered_password = JOptionPane.showInputDialog("Are you sure you want to delete " + customerName + " savings account in " + comboBox1.getSelectedItem() + "?" + "\nEnter admin password:");
                if (entered_password == null || entered_password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Blank Password");
                } else if (!dataBaseUpdateDelete.check_admin_password(admin, entered_password)) {
                    JOptionPane.showMessageDialog(null, "Incorrect password.");
                } else {
                    dataBaseUpdateDelete.remove_savings_account(customerName, comboBox1.getSelectedItem().toString());
                }
            }
        }
        else if (e.getSource() == blockButton) {
            String entered_password = JOptionPane.showInputDialog("Are you sure you want to block " + customerName + " bank account ? " + "\nEnter admin password:");
            if (entered_password == null || entered_password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Blank Password");
            } else if (!dataBaseUpdateDelete.check_admin_password(admin, entered_password)) {
                JOptionPane.showMessageDialog(null, "Incorrect password.");
            } else {
                dataBaseUpdateDelete.block_account(customerName, true);
            }

        }
        else if (e.getSource() == unblockButton) {
            String entered_password = JOptionPane.showInputDialog("Are you sure you want to unblock " + customerName + " bank account ? " + "\nEnter admin password:");
            if (entered_password == null || entered_password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Blank Password");
            } else if (!dataBaseUpdateDelete.check_admin_password(admin, entered_password)) {
                JOptionPane.showMessageDialog(null, "Incorrect password.");
            } else {
                dataBaseUpdateDelete.block_account(customerName, false);
            }
        }
        else if (e.getSource()==changeBalanceButton){
            String entered_password = JOptionPane.showInputDialog("Are you sure you want to change " + customerName + " balance in "+ comboBox2.getSelectedItem() + "\nEnter admin password:");
            if (entered_password == null || entered_password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Blank Password");
            } else if (!dataBaseUpdateDelete.check_admin_password(admin, entered_password)) {
                JOptionPane.showMessageDialog(null, "Incorrect password.");
            } else {
                if(!validateBalanceFromKeyboard(balanceAccountField.getText())){
                    error_msg.setText(error.message);
                    JOptionPane.showMessageDialog(null, error_msg);
                }
                else{
                    String data=balanceAccountField.getText();
                    long rez=Long.parseLong(data); /// input from user will be represented in integer value
                    rez=rez*1000000;
                    dataBaseUpdateDelete.update_current_balance(customerName,rez,comboBox2.getSelectedItem().toString());
                    balanceAccountField.setText("");
                }
            }
        }
        else if (e.getSource() == changeSavingsBalanceButton){
            String entered_password = JOptionPane.showInputDialog("Are you sure you want to change " + customerName + " savings balance in "+ comboBox3.getSelectedItem() + "\nEnter admin password:");
            if (entered_password == null || entered_password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Blank Password");
            } else if (!dataBaseUpdateDelete.check_admin_password(admin, entered_password)) {
                JOptionPane.showMessageDialog(null, "Incorrect password.");
            } else {
                if(!validateBalanceFromKeyboard(balanceSavingsField.getText())){
                    error_msg.setText(error.message);
                    JOptionPane.showMessageDialog(null, error_msg);
                }
                else{
                    String data=balanceSavingsField.getText();
                    long rez=Long.parseLong(data); /// input from user will be represented in integer value
                    rez=rez*1000000;
                    dataBaseUpdateDelete.update_savings_balance(customerName,rez,comboBox3.getSelectedItem().toString());
                    balanceSavingsField.setText("");
                }
            }
        }
        else if (e.getSource() == changeinterestrateButton){

            String entered_password = JOptionPane.showInputDialog("Are you sure you want to change " + customerName + " savings interest rate in "+ comboBox3.getSelectedItem() + "\nEnter admin password:");
            if (entered_password == null || entered_password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Blank Password");
            } else if (!dataBaseUpdateDelete.check_admin_password(admin, entered_password)) {
                JOptionPane.showMessageDialog(null, "Incorrect password.");
            } else {
                if (!validateInterestRateSavingsField(interest_rateSavingsField.getText())) {
                    error_msg.setText(error.message);
                    JOptionPane.showMessageDialog(null,error_msg);
                } else {
                    dataBaseUpdateDelete.update_interest_rate(customerName,interest_rateSavingsField.getText(),comboBox3.getSelectedItem().toString());
                    interest_rateSavingsField.setText("");
                }
            }
        }
        else if (e.getSource() == checkpersonalButton){
            String entered_password = JOptionPane.showInputDialog("Are you sure you want to change " + customerName + " personal data ?\nEnter admin password:");
            if (entered_password == null || entered_password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Blank Password");
            } else if (!dataBaseUpdateDelete.check_admin_password(admin, entered_password)) {
                JOptionPane.showMessageDialog(null, "Incorrect password.");
            } else {
                error.message="ok";
                dataFunc();
                error_msg.setText(error.message);
            }
        }
        else if (e.getSource() == finishButton) {
            frame.dispose();
        }
        else if (e.getSource() == seePersonButton) {
            Map<String, String> userData = dataBaseUpdateDelete.getUserData(getCustomerName(), "ADMIN"); /// retrieve map of personal data to display
            if (userData != null) {
                ViewDataDialog viewDataDialog = new ViewDataDialog(frame, "User Data", userData); /// create dialog box
                viewDataDialog.setVisible(true);
            }
            else{
                JOptionPane.showMessageDialog(frame, "Error");
            }
        }
        else if (e.getSource() == seeCurrentAccountButton) {
            Map<String, String> accountData = dataBaseUpdateDelete.getAccountData(getCustomerName(), comboBox2.getSelectedItem().toString());
            if (accountData != null) {
                ViewDataDialog viewDataDialog = new ViewDataDialog(frame, "Current Account Data", accountData);
                viewDataDialog.setVisible(true);
            }
            else{
                JOptionPane.showMessageDialog(null, "No Account Data Found");
            }

        }
        else if (e.getSource() == seeSavingsAccountButton) {
            Map<String, String> savingsAccountData = dataBaseUpdateDelete.getSavingsAccountData(getCustomerName(), comboBox3.getSelectedItem().toString());
            if (savingsAccountData != null) {
                ViewDataDialog viewDataDialog = new ViewDataDialog(frame, "Savings Account Data", savingsAccountData);
                viewDataDialog.setVisible(true);
            }
            else{
                JOptionPane.showMessageDialog(null, "No Savings Account Data Found");
            }
        }
    }

    private boolean appendErrorMessage(StringBuilder errorMessages, JTextField field, String validationType, String FieldType, String full_name) {
        if (!field.getText().isEmpty() && !validateField(field.getText(), validationType)) { /// invalid field
            errorMessages.append(error.message).append("\n");
        } else if (!field.getText().isEmpty()) { /// valid string
            boolean isSuccess;
            if (validationType.equals("username") || validationType.equals("password")) {
                isSuccess = dataBaseUpdateDelete.update_user_by_full_name(full_name, field.getText(), FieldType);
            } else {
                isSuccess = dataBaseUpdateDelete.update_person_by(full_name, field.getText(), FieldType,errorMessages);
            }
            if (isSuccess) {
                field.setText(""); /// upon update , remove entered data
                return true;
            }
        }
        return false;
    }

    private void dataFunc() {

        StringBuilder errorMessages = new StringBuilder();
        String newFullName;
        String[] segments = getCustomerName().split(" "); /// get the searched customer full name
        String first =segments[1];
        String last = segments[0];

        boolean lastNameUpdated = appendErrorMessage(errorMessages, SurnameField, "last_name", "last_name", customerName);
        boolean firstNameUpdated;

        /// if validation is correct , name from userPersonalData will get updated

        if (lastNameUpdated) { /// if the last name has been updated
            firstNameUpdated = appendErrorMessage(errorMessages, FirstNameField, "first_name", "first_name", userPersonalData.getLastName() + " " + first);
            if (firstNameUpdated) { /// if also the first name has been updated
                newFullName = userPersonalData.getLastName() + " " + userPersonalData.getFirstName();
            } else {
                newFullName = userPersonalData.getLastName()+" "+first;
            }
            setCustomerName(newFullName); /// update
        } else {  /// last name not updated
            firstNameUpdated = appendErrorMessage(errorMessages, FirstNameField, "first_name", "first_name", last+" "+first);
            if (firstNameUpdated) {
                newFullName=last+userPersonalData.getFirstName();
            }
            else{ /// neither were updated
                newFullName=last+" "+first;
            }
            setCustomerName(newFullName);
            /// update , because if the window is open,after update, customer name remains the same , on retry of update , must reflect changes
        }

        appendErrorMessage(errorMessages, usernameField, "username", "username", getCustomerName());
        appendErrorMessage(errorMessages, passwordField, "password", "password", getCustomerName());
        appendErrorMessage(errorMessages, phoneField, "phone", "phone", getCustomerName());
        appendErrorMessage(errorMessages, cityField, "city", "city", getCustomerName());
        appendErrorMessage(errorMessages, streetField, "street", "street", getCustomerName());
        appendErrorMessage(errorMessages, streetnumberField, "street_number", "number", getCustomerName());
        appendErrorMessage(errorMessages, residenceField, "fiscal_residence", "fiscal_residence_id", getCustomerName());
        appendErrorMessage(errorMessages, emailField, "email", "email", getCustomerName());
        appendErrorMessage(errorMessages, genre_field, "gender", "gen", getCustomerName());

        if (!errorMessages.isEmpty()) { /// found errors , display them
            JOptionPane.showMessageDialog(null, errorMessages.toString());
        }
    }

    private boolean validateField(String data, String validationType) {
        /// depending on which field we`re at , it will call the proper validation function for it
        switch (validationType) {
            case "username":
                return validateUsername(data);
            case "password":
                return validatePassword(data);
            case "last_name":
                return validateLastName(data);
            case "first_name":
                return validateFirstName(data);
            case "phone":
                return validatePhone(data);
            case "city":
                return validateCity(data);
            case "street":
                return validateStreet(data);
            case "street_number":
                return validateStreetNumber(data);
            case "email":
                return validateEmail(data);
            case "gender":
                return validateGender(data);
            case "fiscal_residence":
                return validateCountry(data) != -1;
            default:
                return false;
        }
    }

    private String getCustomerName() {
        return customerName;
    }

    private void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

}
