package view.search_view_components;

import entity.FetchedData;
import use_case.fill_detail.FillDetailController;
import use_case.open_website.OpenWebsiteController;
import use_case.star.StarController;
import view_model.SearchViewModel;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class ResultPanel extends JPanel {
    private final JPanel dataInfoPanel;
    private final LinkedHashMap<String, Boolean> detailsDisplayed;
    private final StarButton starButton;
    private JButton queryButton;

    public ResultPanel(SearchViewModel searchViewModel, LinkedHashMap<String, Boolean> detailsDisplayed, FetchedData data, Boolean isStarred, FillDetailController fillDetailController, StarController starController, OpenWebsiteController openWebsiteController) {
        this.detailsDisplayed = detailsDisplayed;
        starButton = new StarButton(isStarred);
        starButton.addActionListener(e -> {
            starController.execute(data);
        });

        queryButton = new JButton(searchViewModel.QUERY_BUTTON_LABEL);
//        queryButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        queryButton.addActionListener(e -> {
            fillDetailController.execute(data);
        });

        OpenWebsiteTitleButton titleButton = new OpenWebsiteTitleButton(data.getTitle(), data.getURL(), openWebsiteController);

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
//        titlePanel.setLayout(new BorderLayout());
//        titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setComponentOrientation(
                ComponentOrientation.LEFT_TO_RIGHT);

        titlePanel.setBackground(SearchViewModel.BACKGROUND_COLOR);
        titlePanel.add(starButton);
        titlePanel.add(titleButton);
        titlePanel.setAlignmentX(LEFT_ALIGNMENT);

        dataInfoPanel = new JPanel();
        dataInfoPanel.setBackground(SearchViewModel.BACKGROUND_COLOR);
        dataInfoPanel.setLayout(new BoxLayout(dataInfoPanel, BoxLayout.PAGE_AXIS));
        dataInfoPanel.setAlignmentX(LEFT_ALIGNMENT);
        updateDataInfoPanel(data);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(SearchViewModel.BACKGROUND_COLOR);
        add(titlePanel);
        add(dataInfoPanel);
//        setBorder(BorderFactory.createRaisedBevelBorder());
    }

    public void updateDataInfoPanel(FetchedData data) {
        dataInfoPanel.removeAll();
        if (data.hasDetails()){
            displayDataDetails(data);
        } else {
            addQueryButtonPanel();
        }
        revalidate();
        repaint();
    }

    private void addQueryButtonPanel() {
        // The only way to add inset to a JButton inside a BorderLayout
        // would be to place it in another JPanel and add insets
        JPanel queryButtonPanel = new JPanel();
        queryButtonPanel.setBackground(SearchViewModel.BACKGROUND_COLOR);
        queryButtonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,35,5,0);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1;
        queryButtonPanel.add(queryButton, gbc);
        dataInfoPanel.add(queryButtonPanel);
    }

    private void displayDataDetails(FetchedData data) {
        for (Map.Entry<String, String> entry : data.getDetails().entrySet()) {
            if(detailsDisplayed.get(entry.getKey())) {
                JTextArea dataLabel = new ParagraphTextArea(entry.getKey() + ": " + entry.getValue());
                dataInfoPanel.add(dataLabel);
            }
        }
    }

//    private JTextArea createParagraphTextArea(String string) {
//        JTextArea textArea = new JTextArea(string);
//        textArea.setWrapStyleWord(true);
//        textArea.setLineWrap(true);
////        textArea.setRows(rows);
//        textArea.setEditable(false);
//        textArea.setBackground(SearchViewModel.BACKGROUND_COLOR);
//        return textArea;
//    }

    public void toggleStar(Boolean isStarred) {
        starButton.toggleStar(isStarred);
    }
}
