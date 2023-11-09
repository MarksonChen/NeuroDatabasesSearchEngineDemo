package view.search_view_components;

import javax.swing.*;
import java.awt.*;

public class StarButton extends JButton {
    public StarButton(Boolean isStarred) {
        toggleStar(isStarred);
        setPreferredSize(new Dimension(35, 35));
//        setBorder(null);
    }
    public void toggleStar(Boolean isStarred) {
        setText(isStarred? "★":"☆");
    }
}
