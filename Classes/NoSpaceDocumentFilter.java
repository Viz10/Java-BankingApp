import javax.swing.text.*;

public class NoSpaceDocumentFilter extends DocumentFilter {
    /**
     * insertString: This method is called when text is inserted into the JTextField.
     * It checks if the inserted string contains a space. If not, it allows the insertion.
     */
    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) {
        try {
            if (string != null && !string.contains(" ")) {
                super.insertString(fb, offset, string, attr);
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    /**
     * replace: This method is called when text is replaced in the JTextField.
     * It checks if the replacement text contains a space. If not, it allows the replacement.
     */
    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) {
        try {
            if (text != null && !text.contains(" ")) {
                super.replace(fb, offset, length, text, attrs);
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
