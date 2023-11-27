package view.search_view_components;

import use_case.open_website.OpenWebsiteController;
import view_model.MainFrameViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OpenWebsiteTitleButton extends JButton {
    public OpenWebsiteTitleButton(String title, String URL, OpenWebsiteController openWebsiteController) {
        super();
        setText(createHTMLText(title, false));
        addActionListener(e -> openWebsiteController.execute(URL));
        setFont(new Font("Arial", Font.PLAIN, 18));
        setBorderPainted(false);
        setOpaque(false);
        setBorder(null);
        setBackground(MainFrameViewModel.BACKGROUND_COLOR);
        setRolloverEnabled(false);
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
        Color c = MainFrameViewModel.TITLE_COLOR;
        return String.format("<html><span style='color: rgb(%d, %d, %d); font-family: Helventica; font-weight: 500; %s'>%s</span></html>",
                c.getRed(), c.getGreen(), c.getBlue(), (underlined? "text-decoration: underline" : ""), title);
//        return "<html><span style='color: rgb(98, 155, 225)"
//                + (underlined? ";text-decoration: underline" : "")
//                + "'>" + title + "</span></html>";
    }
}
