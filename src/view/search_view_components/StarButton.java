package view.search_view_components;

import javax.swing.*;
import java.awt.*;

public class StarButton extends JButton {
    public StarButton(Boolean isStarred) {
        toggleStar(isStarred);
        setFont(UIManager.getFont( "h3.font" ));
        int size = 25;
        setPreferredSize(new Dimension(size, size));
        setFocusPainted(false);
        setBorder(null);
    }
    public void toggleStar(Boolean isStarred) {
        setText(isStarred? "⭐":"☆");
    }
}
