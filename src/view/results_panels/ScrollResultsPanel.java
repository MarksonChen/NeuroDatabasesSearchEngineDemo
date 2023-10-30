package view.results_panels;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class ScrollResultsPanel extends JScrollPane {
    private JPanel contentPanel;
    private JButton prevButton;
    private JButton nextButton;
    List<Map<String, String>> results;
    private int currentPage;
    private int totalPage;
    private int resultsPerPage;

    public ScrollResultsPanel(List<Map<String, String>> results, int resultsPerPage) {
        this.results = results;
        this.resultsPerPage = resultsPerPage;
        this.currentPage = 1;
        this.totalPage = (int) Math.ceil((double) results.size() / resultsPerPage);
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        setPreferredSize(new Dimension(650, 700));
        setViewportView(contentPanel);

        prevButton = new JButton("<Previous");
        nextButton = new JButton("Next>");

        displayPage(currentPage);

        prevButton.addActionListener(e -> {
            displayPage(currentPage - 1);
            // Precondition: this is valid
            // The precondition is always held since the button
            // is disabled when it is at the first page.
        });

        nextButton.addActionListener(e -> {
            displayPage(currentPage + 1);
        });
    }

    public void displayPage(int pageNumber){
        currentPage = pageNumber;
        contentPanel.removeAll();

        if (currentPage == 1)
            contentPanel.add(new JLabel(results.size() + " results fetched"));

        int startFrom = (currentPage-1) * resultsPerPage;
        // Note that this is an index
        // e.g. startFrom = 5 refers to starting from the sixth item

        for (int i = 0; i < resultsPerPage && (i + startFrom) < results.size(); i++) {
            Map<String, String> resultData = results.get(startFrom + i);
            ResultPanel resultPanel = new ResultPanel(resultData);
            contentPanel.add(resultPanel);
            contentPanel.add(Box.createVerticalStrut(5));
        }
        prevButton.setEnabled(currentPage > 1);
        nextButton.setEnabled(currentPage < totalPage);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);
        buttonPanel.setPreferredSize(new Dimension(650, 40));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(buttonPanel);

        revalidate();
        repaint();
    }
}
