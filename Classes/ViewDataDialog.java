import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.Map;

public class ViewDataDialog extends JDialog {

    public ViewDataDialog(JFrame parent, String title,Map<String, String> data) {

        super(parent, title, false);
        setLayout(new BorderLayout(10, 10));

        JLabel infoLabel = new JLabel(title); /// label
        infoLabel.setFont(new Font(null, Font.BOLD, 20));
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JTextArea dataTextArea = new JTextArea(); /// text area
        dataTextArea.setEditable(false);
        dataTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));

        StringBuilder dataBuilder = new StringBuilder();
        ///Iterator<String> keys = data.keySet().iterator();
        for (Map.Entry<String, String> entry : data.entrySet()) { /// for each pair obj from the map viewed as a set
            dataBuilder.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n"); /// append key + value from map into string builder
        }
        dataTextArea.setText(dataBuilder.toString()); /// display in the text area

        JScrollPane scrollPane = new JScrollPane(dataTextArea); /// add scroll bar to the text area

        add(infoLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setMinimumSize(new Dimension(400, 400));
        setSize(500, 500);
        setLocationRelativeTo(parent);
    }
}
