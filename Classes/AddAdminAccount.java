
import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddAdminAccount extends JDialog implements ActionListener {

    JTextField usernameField;
    JPasswordField passwordField;
    JPasswordField passwordconfirmField;

    JLabel userNameLabel;
    JLabel passwordLabel;
    JLabel passwordconfirmLabel;

    JButton addAccountButton = new JButton("Add Account");

    DataBaseOp dataBaseOp;

    AddAdminAccount(JFrame parent, DataBaseOp dataBaseOp) {

        super(parent, "Add Admin Account", true); /// modal JDialog panel
        this.dataBaseOp = dataBaseOp;

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        passwordconfirmField = new JPasswordField();

        userNameLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");
        passwordconfirmLabel = new JLabel("Password Confirmation:");

        usernameField.setMaximumSize(new Dimension(200, 30));
        passwordField.setMaximumSize(new Dimension(200, 30));
        passwordconfirmField.setMaximumSize(new Dimension(200, 30));

        DocumentFilter filter = new NoSpaceDocumentFilter();
        ((AbstractDocument) usernameField.getDocument()).setDocumentFilter(filter);
        ((AbstractDocument) passwordField.getDocument()).setDocumentFilter(filter);
        ((AbstractDocument) passwordconfirmField.getDocument()).setDocumentFilter(filter);

        Font labelFont = new Font(null, Font.PLAIN, 16);
        userNameLabel.setFont(labelFont);
        passwordLabel.setFont(labelFont);
        passwordconfirmLabel.setFont(labelFont);

        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordconfirmField.setAlignmentX(Component.CENTER_ALIGNMENT);
        userNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordconfirmLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        addAccountButton.setPreferredSize(new Dimension(150, 50));
        addAccountButton.setFocusable(false);
        addAccountButton.addActionListener(this);
        addAccountButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(userNameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(passwordconfirmLabel);
        add(passwordconfirmField);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(addAccountButton);

        setMinimumSize(new Dimension(300, 300));
        setSize(400, 400);
        setLocationRelativeTo(parent);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addAccountButton) {
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());
            String passwordConfirmation = String.valueOf(passwordconfirmField.getPassword());

            if (verify(username, password, passwordConfirmation)) {
                dataBaseOp.insertUser(username, password, "ADMIN");
                JOptionPane.showMessageDialog(this, "Account Added Successfully");
                dispose();
            }
        }
    }

    private boolean verify(String username, String password, String passwordConfirmation) {
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Blank Username Field");
            return false;
        }

        if (username.length() < 6) {
            JOptionPane.showMessageDialog(null, "Username Field must be at least 6 characters long");
            return false;
        }

        if (username.length() > 30) {
            JOptionPane.showMessageDialog(null, "Username Field way too long");
            return false;
        }

        if (dataBaseOp.check_username(username)) {
            JOptionPane.showMessageDialog(null, "Already existing user");
            return false;
        }

        if (password.isEmpty() || passwordConfirmation.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Blank Password Field");
            return false;
        }

        if (password.length() < 6) {
            JOptionPane.showMessageDialog(null, "Password Field must be at least 6 characters long");
            return false;
        }

        if (password.length() > 30) {
            JOptionPane.showMessageDialog(null, "Password Field way too long");
            return false;
        }

        if (!password.equals(passwordConfirmation)) {
            JOptionPane.showMessageDialog(null, "Passwords do not match!");
            return false;
        }

        return true;
    }
}
