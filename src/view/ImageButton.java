package view;

import view_model.MainFrameViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ImageButton extends JButton {
    public ImageButton(String imagePath, double scale) {
        super(getScaledImageIcon(imagePath, scale));
//        setBorderPainted(true);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setRolloverEnabled(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBackground(MainFrameViewModel.ACCENT_COLOR);

        // Draw a border when the mouse hovers above the image button
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setContentAreaFilled(true);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setContentAreaFilled(false);
            }
        });
    }

    private static ImageIcon getScaledImageIcon(String imagePath, double scale) {
        ImageIcon imageIcon = new ImageIcon(FrontPageView.class.getResource(imagePath));
        Image scaledImage = imageIcon.getImage().getScaledInstance(
                (int)(imageIcon.getIconWidth() * scale),
                (int)(imageIcon.getIconHeight() * scale), Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
}
