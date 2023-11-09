package view.search_view_components;

import interface_adapter.open_website.OpenWebsiteController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OpenWebsiteTitleButton extends JButton {
    public OpenWebsiteTitleButton(String title, String URL, OpenWebsiteController openWebsiteController) {
        super();
        setText(createHTMLText(title, false));
        addActionListener(e -> openWebsiteController.execute(URL));
        setForeground(new Color(24, 14, 164));
        setFont(new Font("Arial", Font.PLAIN, 18));
        setBorderPainted(false);
        setOpaque(false);
        setBackground(Color.white);
        setToolTipText(URL);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Change to underlined text when the mouse enters the button area
                OpenWebsiteTitleButton.this.setText(createHTMLText(title, true));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Change back to plain text when the mouse exits the button area
                OpenWebsiteTitleButton.this.setText(createHTMLText(title, false));
            }
        });
    }

    private static String createHTMLText(String title, boolean underlined) {
        return "<html><span style='color: blue;"
                + (underlined? "text-decoration: underline;" : "")
                + "'>" + title + "</span></html>";
    }
}
