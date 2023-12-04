package view.search_view_components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class HintTextField extends JTextField implements FocusListener {
    // Code from https://stackoverflow.com/questions/1738966/java-jtextfield-with-input-hint
    // Used to create hinted text field in Query Bar
    private final String hint;
    private boolean showingHint;
    private final Color hintColor = Color.gray;

    public HintTextField(final String hint, int columns) {
        super(hint, columns);
        this.hint = hint;
        this.showingHint = true;
        this.setForeground(hintColor);
        super.addFocusListener(this);
    }

    @Override
    public void focusGained(FocusEvent e) {
        if(this.getText().isEmpty()) {
            super.setText("");
            showingHint = false;
            this.setForeground(Color.BLACK);
        }
    }
    @Override
    public void focusLost(FocusEvent e) {
        if(this.getText().isEmpty()) {
            super.setText(hint);
            showingHint = true;
            this.setForeground(hintColor);
        }
    }

    @Override
    public String getText() {
        return showingHint ? "" : super.getText();
    }
    @Override
    public void setText(String text) {
        requestFocusInWindow();
        focusGained(null);
        super.setText(text);
    }
}