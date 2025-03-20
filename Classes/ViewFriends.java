import javax.swing.*;
import java.awt.*;

import java.util.ArrayList;
import java.util.Map;

public class ViewFriends extends JDialog {

    public ViewFriends(JFrame parent, String title, ArrayList<String> data) {

        super(parent, title, false);
        setLayout(new BorderLayout(10, 10));

        JLabel infoLabel = new JLabel(title); /// label
        infoLabel.setFont(new Font(null, Font.BOLD, 20));
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JTextArea dataTextArea = new JTextArea(); /// text area
        dataTextArea.setEditable(false);
        dataTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));

        StringBuilder dataBuilder = new StringBuilder();

        for(String s : data){
            String[] words = s.split(" ");
            if(words[0]==null){
                dataBuilder.append("Deleted User").append("\n");
            }
            else{
                dataBuilder.append(s).append("\n");
            }
        }

        dataTextArea.setText(dataBuilder.toString());

        JScrollPane scrollPane = new JScrollPane(dataTextArea);

        add(infoLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setMinimumSize(new Dimension(400, 400));
        setSize(500, 500);
        setLocationRelativeTo(parent);
    }
}
