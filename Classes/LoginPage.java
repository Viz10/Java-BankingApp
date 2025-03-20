import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginPage implements ActionListener {

    // Window of the program
    JFrame frame = new JFrame();

    // Buttons
    JButton loginButton = new JButton("Login");
    JButton resetButton = new JButton("Reset");

    // Text fields to entry data
    JTextField userField = new JTextField();
    JPasswordField userPasswordField = new JPasswordField();

    // Text Labels
    JLabel userIDlabel = new JLabel("userID:");
    JLabel userIDPassword = new JLabel("password:");
    JLabel register = new JLabel("Don't have an account? Click here to register");
    JLabel messageLabel = new JLabel();

    // DB connection
    DataBaseOp dataBaseOp;

    public LoginPage() {

        dataBaseOp = new DataBaseOp();

        userIDlabel.setBounds(50, 100, 75, 25);
        userIDPassword.setBounds(50, 150, 75, 25);
        messageLabel.setBounds(130, 250, 400, 25);
        messageLabel.setFont(new Font(null, Font.BOLD, 12));
        userField.setBounds(125, 100, 200, 25);
        userPasswordField.setBounds(125, 150, 200, 25);
        register.setBounds(100, 300, 300, 35);

        register.addMouseListener(new MouseAdapter() { /// user interactivity with register text
            @Override
            public void mouseClicked(MouseEvent e) {
                new RegisterPage(); /// advance to register page
            }

            /// hover
            @Override
            public void mouseEntered(MouseEvent e) {
                register.setForeground(Color.BLUE);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                register.setForeground(Color.BLACK);
            }
        });

        loginButton.setFocusable(false);
        resetButton.setFocusable(false);
        loginButton.setBounds(125, 200, 100, 25);
        resetButton.setBounds(225, 200, 100, 25);
        loginButton.addActionListener(this);
        resetButton.addActionListener(this);

        frame.add(register);
        frame.add(userIDlabel);
        frame.add(userIDPassword);
        frame.add(messageLabel);
        frame.add(userField);
        frame.add(userPasswordField);
        frame.add(loginButton);
        frame.add(resetButton);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setTitle("Login Page");
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resetButton) {
            userField.setText("");
            userPasswordField.setText("");
        }
        if (e.getSource() == loginButton) {

            String UserName = userField.getText();
            String UserPassword = String.valueOf(userPasswordField.getPassword());
            String result = dataBaseOp.check_login_user_password(UserName, UserPassword);

            /// if there is a possibility that there exists 2 customer and admin accounts with same data , check will be done by their user type
            if (result.equals("CUSTOMER")) {

                if(dataBaseOp.account_is_blocked(UserName)) {
                    JOptionPane.showMessageDialog(frame, "Account is blocked.\nRequest support from admin.");
                }
                else {
                    UserMenuPage page2C = new UserMenuPage(UserName);
                }
                userField.setText("");
                userPasswordField.setText("");

            } else if (result.equals("ADMIN")) {
                UserAdminPage page2A = new UserAdminPage(UserName);
                userField.setText("");
                userPasswordField.setText("");
            } else {
                messageLabel.setText(result);
                messageLabel.setForeground(Color.RED);
                userField.setText("");
                userPasswordField.setText("");
            }
        }
    }
}
