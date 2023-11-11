package sandbox;


import javax.swing.*;
import java.awt.*;

public class Sandbox {
    public static void main(String[] args) {
        JFrame frame = new JFrame("BoxLayout Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel with BoxLayout
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Add components at the top
        panel.add(new JButton("Button 1"));
        panel.add(new JButton("Button 2"));

        // Component that should stretch
        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        panel.add(scrollPane);

        // Invisible filler for space at the bottom
        Component filler = Box.createVerticalGlue();
        panel.add(filler);

        // Add a component at the bottom
        panel.add(new JButton("Button 3"));

        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}