package view.search_view_components;

import entity.FetchedData;
import interface_adapter.fill_detail.FillDetailController;
import interface_adapter.open_website.OpenWebsiteController;
import interface_adapter.star.StarController;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class ResultPanel extends JPanel {
    private final JPanel dataInfoPanel;
    private final LinkedHashMap<String, Boolean> detailsDisplayed;
    private final StarButton starButton;
    private JButton queryButton;

    public ResultPanel(LinkedHashMap<String, Boolean> detailsDisplayed, FetchedData data, Boolean isStarred, FillDetailController fillDetailController, StarController starController, OpenWebsiteController openWebsiteController) {
        this.detailsDisplayed = detailsDisplayed;
        starButton = new StarButton(isStarred);
        starButton.addActionListener(e -> {
            starController.execute(data);
        });

        queryButton = new JButton("Query for details"); // TODO fetch from viewModel
        queryButton.addActionListener(e -> {
            fillDetailController.execute(data);
        });

        OpenWebsiteTitleButton titleButton = new OpenWebsiteTitleButton(data.getTitle(), data.getURL(), openWebsiteController);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        titlePanel.add(starButton);
        titlePanel.add(titleButton);
        titlePanel.setAlignmentX(LEFT_ALIGNMENT);

        dataInfoPanel = new JPanel();
        createDataInfoPanel(data);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(titlePanel);
        add(dataInfoPanel);
        setBorder(BorderFactory.createRaisedBevelBorder());
    }

    @NotNull
    private JPanel createDataInfoPanel(FetchedData data) {
        dataInfoPanel.setLayout(new BoxLayout(dataInfoPanel, BoxLayout.PAGE_AXIS));
        dataInfoPanel.setAlignmentX(LEFT_ALIGNMENT);
        updateDataInfoPanel(data);
        return dataInfoPanel;
    }

    public void updateDataInfoPanel(FetchedData data) {
        dataInfoPanel.removeAll();
        if (data.hasDetails()){
            displayDataDetails(data);
        } else {
            dataInfoPanel.add(queryButton);
        }
        revalidate();
        repaint();
    }

    private void displayDataDetails(FetchedData data) {
        for (Map.Entry<String, String> entry : data.getDetails().entrySet()) {
            if(detailsDisplayed.get(entry.getKey())) {
                JTextArea dataLabel = createParagraphTextArea(entry.getKey() + ": " + entry.getValue());
                dataInfoPanel.add(dataLabel); // TODO merge into one area
            }
        }
    }

    private JTextArea createParagraphTextArea(String string) {
        JTextArea textArea = new JTextArea(string);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
//        textArea.setRows(rows);
        textArea.setEditable(false);
        textArea.setBackground(getBackground());
        return textArea;
    }

    public void toggleStar(Boolean isStarred) {
        starButton.toggleStar(isStarred);
    }
}
