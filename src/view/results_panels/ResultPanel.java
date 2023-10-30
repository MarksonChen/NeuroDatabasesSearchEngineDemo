package view.results_panels;

import app.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Map;

public class ResultPanel extends JPanel {

    private JButton starButton;
    private JLabel titleLabel;

    public ResultPanel(Map<String, String> data) {
        //TODO: Use ViewModel; implement button method

        // Panel Layout
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Star Button and Title
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        starButton = new JButton("☆");
        starButton.setPreferredSize(new Dimension(35, 35));
//        starButton.setBorder(null);
        titleLabel = new JLabel(data.get("title"));
        titlePanel.add(starButton);
        titlePanel.add(titleLabel);

        // Data Info
        JPanel dataInfoPanel = new JPanel();
        dataInfoPanel.setLayout(new BoxLayout(dataInfoPanel, BoxLayout.PAGE_AXIS));
        for (Map.Entry<String, String> entry : data.entrySet()) {
            if (!entry.getKey().equals("title") && !entry.getKey().equals("id")) {
                JTextArea dataLabel = createParagraphTextArea(entry.getKey() + ": " + entry.getValue(), 2);
                dataInfoPanel.add(dataLabel);
            }
        }

        titlePanel.setAlignmentX(LEFT_ALIGNMENT);
        dataInfoPanel.setAlignmentX(LEFT_ALIGNMENT);

        add(titlePanel);
        add(dataInfoPanel);
//        setPreferredSize(new Dimension(650, getSize().height));
        setBorder(BorderFactory.createRaisedBevelBorder());
    }

    private JTextArea createParagraphTextArea(String string, int rows) {
        JTextArea textArea = new JTextArea(string);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
//        textArea.setRows(rows);
        textArea.setEditable(false);
        textArea.setBackground(getBackground());
        return textArea;
    }
}
