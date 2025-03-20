import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.text.*;

public class UserAdminPage implements ActionListener {

     DataBaseOp dataBaseOp;
     String admin;
     JFrame frame = new JFrame();
     JLabel welcomeLabel = new JLabel();

     JButton logoutButton = new JButton("Logout");
     JButton insert_adminButton = new JButton("Add admin user");
     JButton manage_customerButton = new JButton("Edit Customer Account");

     JPanel up = new JPanel();
     JPanel down = new JPanel();
     JPanel left = new JPanel();
     JPanel right = new JPanel();
     JPanel centre = new JPanel();
     JPanel centre1 = new JPanel();
     JPanel centre2 = new JPanel();
     JPanel centre3 = new JPanel();

     JLabel username_label = new JLabel("Customer full name:");
     JTextField customer_name_field = new JTextField();

    UserAdminPage(String admin) {

        dataBaseOp = new DataBaseOp();
        this.admin = admin;

        frame.setLayout(new BorderLayout(10, 10)); // Margin between all panels

        up.setPreferredSize(new Dimension(100, 50));
        down.setPreferredSize(new Dimension(100, 80));
        left.setPreferredSize(new Dimension(50, 100));
        right.setPreferredSize(new Dimension(50, 100));
        centre.setPreferredSize(new Dimension(50, 100));
        centre1.setPreferredSize(new Dimension(100, 200));
        centre2.setPreferredSize(new Dimension(100, 100));
        centre3.setPreferredSize(new Dimension(100, 200));

        centre1.setBackground(Color.darkGray);
        centre2.setBackground(Color.GRAY);
        centre3.setBackground(Color.PINK);

        //////////////////////////////////////////////////////////////////////

        up.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        welcomeLabel.setFont(new Font(null, Font.BOLD, 20));
        welcomeLabel.setForeground(Color.BLUE);
        welcomeLabel.setText("Welcome to your ADMIN account, " + admin);
        up.add(welcomeLabel);

        ///////////////////////////////////////////////////////

        centre.setLayout(new BorderLayout(10, 10));

        insert_adminButton.addActionListener(this);
        manage_customerButton.addActionListener(this);


        insert_adminButton.setFocusable(Boolean.FALSE);
        manage_customerButton.setFocusable(Boolean.FALSE);


        Dimension buttonSize = new Dimension(250, 70);

        insert_adminButton.setPreferredSize(buttonSize);
        manage_customerButton.setPreferredSize(buttonSize);


        centre2.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        centre2.add(insert_adminButton);
        centre2.add(manage_customerButton);

        centre3.setLayout(new BoxLayout(centre3, BoxLayout.Y_AXIS));
        customer_name_field.setMaximumSize(new Dimension(200, 30));
        username_label.setFont(new Font(null, Font.PLAIN, 16));

        customer_name_field.setAlignmentX(Component.CENTER_ALIGNMENT);
        username_label.setAlignmentX(Component.CENTER_ALIGNMENT);

        centre3.add(username_label);
        centre3.add(customer_name_field);

        centre.add(centre1, BorderLayout.NORTH);
        centre.add(centre2, BorderLayout.CENTER);
        centre.add(centre3, BorderLayout.SOUTH);

        /////////////////////////////////////////////////

        down.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        logoutButton.setPreferredSize(new Dimension(150, 50));
        logoutButton.setFocusable(false);
        logoutButton.addActionListener(this);
        down.add(logoutButton);

        /////////////////////////////////////////////////

        frame.add(up, BorderLayout.NORTH);
        frame.add(down, BorderLayout.SOUTH);
        frame.add(left, BorderLayout.WEST);
        frame.add(right, BorderLayout.EAST);
        frame.add(centre, BorderLayout.CENTER);

        frame.setMinimumSize(new Dimension(700, 800));
        frame.getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1000, 850));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setTitle("ADMIN ACCOUNT");
        frame.setResizable(true);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == insert_adminButton) {
            AddAdminAccount addAdminAccount = new AddAdminAccount(frame, dataBaseOp);
            addAdminAccount.setVisible(true);
        } else if (e.getSource() == manage_customerButton) { /// DELETE, UPDATE POSSIBLE FIELDS, BLOCK
            if (customer_name_field.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter customer full name");
            }
            else{
                int rez=dataBaseOp.get_person_id(customer_name_field.getText());
                if(rez==-1){
                    JOptionPane.showMessageDialog(null, "Customer does not exist"+"\nUse capital letters and ' - ' if needed... ");
                }
                else{
                    ManageCustomer manageCustomer=new ManageCustomer(customer_name_field.getText(),admin);
                    customer_name_field.setText("");
                }
            }
        } else if (e.getSource() == logoutButton) {
            JOptionPane.showMessageDialog(null, "You have logged out!");
            frame.dispose();
        }
    }



}
