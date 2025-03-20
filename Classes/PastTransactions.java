import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PastTransactions extends JDialog {

    ArrayList<TransferClass> list;

    JScrollPane scrollPane; /// add scroll bar

    PastTransactions(JFrame parent, String title, ArrayList<TransferClass> list) {

        super(parent, title, true);
        this.list = list;
        DataBaseUpdateDelete db = new DataBaseUpdateDelete();

        JPanel panelContainer = new JPanel(); /// the panel of JDialog frame
        panelContainer.setLayout(new BoxLayout(panelContainer, BoxLayout.Y_AXIS));
        Font labelFont = new Font(null, Font.PLAIN, 17);

        for (TransferClass t : list) { /// for each transaction , display in frame

            JPanel panel = new JPanel(); /// create separate panel which will be stacked
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setPreferredSize(new Dimension(600, 150));
            panel.setMaximumSize(new Dimension(600, 200));
            panel.setMinimumSize(new Dimension(600, 100));

            //////////////////////// Usernames
            String fromAccountName = t.getFromAccountName();
            String fromAccountUsername = t.getFromAccountUsername();
            String toAccountName = t.getToAccountName();
            String toAccountUsername = t.getToAccountUsername();

            ///// search for null values and replace
            if (fromAccountName == null || fromAccountUsername == null) {
                fromAccountName = "User deleted";
                fromAccountUsername = "User deleted";
            }

            if (toAccountName == null || toAccountUsername == null) {
                toAccountName = "User deleted";
                toAccountUsername = "User deleted";
            }

            String fromText = fromAccountName + " (" + fromAccountUsername + ")";
            String toText = toAccountName + " (" + toAccountUsername + ")";
            JLabel userLabel = new JLabel(fromText + " ==> " + toText);
            userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            userLabel.setFont(labelFont);
            panel.add(userLabel);


            ///////////////////////Transaction Details
            String detailsText = "Sent " + db.balanceFormatted(t.getAmount()) + " " + t.getCurrencyWith() + " from " + t.getCurrencyFrom() + " to " + t.getCurrencyTo() + " account";
            JLabel detailsLabel = new JLabel(detailsText);
            detailsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            detailsLabel.setFont(labelFont);
            panel.add(detailsLabel);

            ///////Time
            JLabel timeLabel = new JLabel("Time: " + t.getTime());
            timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            timeLabel.setFont(labelFont);
            panel.add(timeLabel);

            ///Separate them
            panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            panel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelContainer.add(panel); /// add to bigger panel
        }

        scrollPane = new JScrollPane(panelContainer);
        add(scrollPane);

        setMinimumSize(new Dimension(600, 600));
        setSize(650, 700);
        setLocationRelativeTo(parent);
    }
}
