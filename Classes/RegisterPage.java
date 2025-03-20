import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class RegisterPage extends RegisterComponents implements ActionListener {

     JFrame frame;
     JLabel welcomeLabel;
     JButton finishButton;

     JPanel up;
     JPanel down;
     JPanel left;
     JPanel right;
     JPanel centre;

     final String malestring = "Male";
     final String femalestring = "Female";
     JRadioButton male;
     JRadioButton female;
     ButtonGroup group;

     JCheckBox check;

    public RegisterPage() {

        super(); /// instantiate the components inherited

        frame = new JFrame();
        welcomeLabel = new JLabel("Fill in with your data:");
        finishButton = new JButton("Check and Finish");

        up = new JPanel();
        down = new JPanel();
        left = new JPanel();
        right = new JPanel();
        centre = new JPanel();

        male = new JRadioButton(malestring);
        female = new JRadioButton(femalestring);
        group = new ButtonGroup();

        check = new JCheckBox("I hereby agree to this application to process and store my data.");

        frame.setLayout(new BorderLayout(10, 10)); // Margin between all panels

        up.setPreferredSize(new Dimension(100, 50));
        down.setPreferredSize(new Dimension(100, 80));
        left.setPreferredSize(new Dimension(50, 100));
        right.setPreferredSize(new Dimension(50, 100));
        centre.setPreferredSize(new Dimension(100, 100));

        //////////////////////////////////////////////////////////////////////

        up.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        welcomeLabel.setFont(new Font(null, Font.BOLD, 20));
        welcomeLabel.setForeground(Color.BLUE);
        up.add(welcomeLabel);

        ///////////////////////////////////////////////////////

        centre.setLayout(new BoxLayout(centre, BoxLayout.Y_AXIS));

        Dimension fieldSize = new Dimension(200, 30);
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


        Font labelFont = new Font(null, Font.PLAIN, 16);
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

        check.addActionListener(this);
        male.addActionListener(this);
        female.addActionListener(this);
        group.add(male);
        group.add(female);

        /// Center alignment of components
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
        male.setAlignmentX(Component.CENTER_ALIGNMENT);
        female.setAlignmentX(Component.CENTER_ALIGNMENT);
        gender.setAlignmentX(Component.CENTER_ALIGNMENT);
        check.setAlignmentX(Component.CENTER_ALIGNMENT);
        cityField.setAlignmentX(Component.CENTER_ALIGNMENT);
        cityLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centre.add(SurnameLabel);
        centre.add(SurnameField);
        centre.add(FirstNameLabel);
        centre.add(FirstNameField);
        centre.add(phoneLabel);
        centre.add(phoneField);
        centre.add(cityLabel);
        centre.add(cityField);
        centre.add(streetLabel);
        centre.add(streetField);
        centre.add(streetnumberLabel);
        centre.add(streetnumberField);
        centre.add(residenceLabel);
        centre.add(residenceField);
        centre.add(emailLabel);
        centre.add(emailField);

        centre.add(Box.createRigidArea(new Dimension(0, 20)));

        centre.add(userNameLabel);
        centre.add(usernameField);
        centre.add(passwordLabel);
        centre.add(passwordField);
        centre.add(passwordconfirmLabel);
        centre.add(passwordconfirmField);
        centre.add(gender);
        centre.add(male);
        centre.add(female);

        centre.add(Box.createRigidArea(new Dimension(0, 20)));

        centre.add(check);

        centre.add(Box.createRigidArea(new Dimension(0, 40)));

        /////////////////////////////////////////

        down.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        finishButton.setPreferredSize(new Dimension(150, 50));
        finishButton.setFocusable(false);
        finishButton.addActionListener(this);
        down.add(finishButton);

        /////////////////////////////////////////////////

        frame.add(up, BorderLayout.NORTH);
        frame.add(down, BorderLayout.SOUTH);
        frame.add(left, BorderLayout.WEST);
        frame.add(right, BorderLayout.EAST);
        frame.add(centre, BorderLayout.CENTER);

        frame.setMinimumSize(new Dimension(600, 800));
        frame.getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1000, 1000));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setTitle("SIGN UP");
        frame.setResizable(true);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == finishButton) {

            error.message="ok";

            /// check one by one all
            if (!validateAndConcatNames(FirstNameField.getText(), SurnameField.getText())) {
                error_msg.setText(error.message);
                JOptionPane.showMessageDialog(null, error_msg);
            }
            else if(!validatePhone(phoneField.getText())){
                error_msg.setText(error.message);
                JOptionPane.showMessageDialog(null, error_msg);
            }else if (!validateFullAddress(cityField.getText(), streetField.getText(), streetnumberField.getText())) {
                error_msg.setText(error.message);
                JOptionPane.showMessageDialog(null, error_msg);
            } else if (validateCountry(residenceField.getText()) == -1) {
                error_msg.setText(error.message);
                JOptionPane.showMessageDialog(null, error_msg);
            } else if (!validateEmail(emailField.getText())) {
                error_msg.setText(error.message);
                JOptionPane.showMessageDialog(null, error_msg);
            } else if (!validateUsername(usernameField.getText())) {
                error_msg.setText(error.message);
                JOptionPane.showMessageDialog(null, error_msg);
            }else if(!validatePassword(String.valueOf(passwordField.getPassword()), String.valueOf(passwordconfirmField.getPassword()))){
                error_msg.setText(error.message);
                JOptionPane.showMessageDialog(null, error_msg);
            }
            else if (!validateButtons()) {
                error_msg.setText(error.message);
                JOptionPane.showMessageDialog(null, error_msg);
            }

            if (Objects.equals(error.message, "ok") && databaseInsertion()) {
                JOptionPane.showMessageDialog(null, "SUCCESSFULLY REGISTERED");
                frame.dispose();
            }
        }
    }

    /// ADDED CHAIN VERIFICATION , IF ACCOUNT INSERT FAIL , PERSON/USER HAVE ALREADY BEEN ADDED , MUST BE DELETED
    private boolean databaseInsertion() {

        /// here , user personal data should contain all the correct fields to be inserted
        if(dataBaseOp.insertPerson(userPersonalData.getFullName(), userPersonalData.getAddress(), userPersonalData.getEmail(), userPersonalData.getPhoneNumber(), userPersonalData.getCountry(), userPersonalData.getGender())){
            if(dataBaseOp.insertUser(userPersonalData.getUserName(), userPersonalData.getPassword(), "CUSTOMER")){
                if(dataBaseOp.insertAccount(userPersonalData.getUserName(), userPersonalData.getFullName())){
                    JOptionPane.showMessageDialog(null, "SUCCESSFULLY REGISTERED");
                    return true;
                }
                else {
                    dataBaseOp.remove_user_by_username(userPersonalData.getUserName());
                    dataBaseOp.remove_person_by_full_name(userPersonalData.getFullName());
                    return false;
                }
            }
            else{
                dataBaseOp.remove_person_by_full_name(userPersonalData.getFullName());
                return false;
            }
        }
        JOptionPane.showMessageDialog(null, "Something went wrong");
       return false;
    }

    private boolean validateButtons() {

        if (!male.isSelected() && !female.isSelected()) {
            error.message = "Please select a gender";
            return false;
        }

        if (!check.isSelected()) {
            error.message = "Please select a check the box";
            return false;
        }

        if (male.isSelected())
            userPersonalData.setGender("male");
        else
            userPersonalData.setGender("female");

        return true;
    }

}
