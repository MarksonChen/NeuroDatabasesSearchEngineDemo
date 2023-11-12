package view.search_view_components;

import data_access.Database;
import entity.FetchedData;
import use_case.fill_detail.FillDetailController;
import use_case.open_website.OpenWebsiteController;
import use_case.query.query_one.QueryOneController;
import use_case.star.StarController;
import view_model.ScrollResultsPanelModel;
import view_model.ScrollResultsPanelState;
import view_model.SearchViewModel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedHashMap;
import java.util.List;

public class ScrollResultsPanel extends JScrollPane implements PropertyChangeListener {
    private final SearchViewModel searchViewModel;
    private final ScrollResultsPanelModel model;
    private final JPanel contentPanel;
    private final JButton prevButton;
    private final JButton nextButton;
    private final QueryOneController queryOneController;
    private final FillDetailController fillDetailController;
    private final StarController starController;
    private final OpenWebsiteController openWebsiteController;
    private ResultPanel[] resultPanels;

    public ScrollResultsPanel(SearchViewModel searchViewModel, ScrollResultsPanelModel model, QueryOneController queryOneController,
                              FillDetailController fillDetailController, StarController starController, OpenWebsiteController openWebsiteController) {
        this.searchViewModel = searchViewModel;
        this.model = model;
        this.queryOneController = queryOneController;
        this.fillDetailController = fillDetailController;
        this.starController = starController;
        this.openWebsiteController = openWebsiteController;
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(SearchViewModel.BACKGROUND_COLOR);

        setPreferredSize(new Dimension(650, 700));
        setViewportView(contentPanel);
        fixScrollSpeed(this);

        model.addPropertyChangeListener(this);

        prevButton = new JButton(SearchViewModel.PREV_BUTTON_LABEL);
        nextButton = new JButton(SearchViewModel.NEXT_BUTTON_LABEL);

        prevButton.addActionListener(e -> {
            ScrollResultsPanelState state = model.getState();
            state.setCurrentPage(state.getCurrentPage() - 1);
            queryForNewPage();
        });

        nextButton.addActionListener(e -> {
            ScrollResultsPanelState state = model.getState();
            state.setCurrentPage(state.getCurrentPage() + 1);
            queryForNewPage();
        });
    }

    private void queryForNewPage() {
        ScrollResultsPanelState state = model.getState();
        queryOneController.execute(model.getDatabase(), state.getLastQueryKeywords(), state.getResultsPerPage(), state.getCurrentPage());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ScrollResultsPanelState state = (ScrollResultsPanelState) evt.getNewValue();
        switch (evt.getPropertyName()) {
            case ScrollResultsPanelModel.REFRESH_ALL -> {
                displayPage(state.getFetchedDataList(), state.getDataIsStarredList(), state.getTotalResults(), state.getResultsPerPage(), state.getCurrentPage());
            }
            case ScrollResultsPanelModel.REFRESH_DATA_INFO_PANEL -> {
                List<FetchedData> fetchedDataList = state.getFetchedDataList();
                for (int i = 0; i < fetchedDataList.size(); i++) {
                    resultPanels[i].updateDataInfoPanel(fetchedDataList.get(i));
                }
            }
            case ScrollResultsPanelModel.REFRESH_STAR_STATES -> {
                List<Boolean> dataIsStarredList = state.getDataIsStarredList();
                for (int i = 0; i < dataIsStarredList.size(); i++) {
                    resultPanels[i].toggleStar(dataIsStarredList.get(i));
                }
            }
        }
    }

    public void displayPage(List<FetchedData> fetchedData, List<Boolean> dataIsStarredList, int totalResults, int resultsPerPage, int currentPage){
        // Precondition: currentPage is a valid page
        // The precondition is always held since the prevButton and nextButton
        // are disabled when it is at the first/last page.

        contentPanel.removeAll();
        if (fetchedData.isEmpty()){
            contentPanel.add(new JLabel("Sorry, no result is fetched. Please try a different query."));
        } else {
            resultPanels = new ResultPanel[fetchedData.size()];

            if (currentPage == 1) {
                JLabel resultsFetchedLabel = new JLabel(totalResults + " results fetched");
                resultsFetchedLabel.setAlignmentX(LEFT_ALIGNMENT);
                resultsFetchedLabel.setBorder(new EmptyBorder(7,20,7,10));
                contentPanel.add(resultsFetchedLabel);
                contentPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
            }

            LinkedHashMap<String, Boolean> detailsDisplayed = searchViewModel.getState().getDetailEntryDisplayed()[Database.indexOf(model.getDatabase())];
            for (int i = 0; i < fetchedData.size(); i++) {
                ResultPanel resultPanel = new ResultPanel(searchViewModel, detailsDisplayed, fetchedData.get(i), dataIsStarredList.get(i), fillDetailController, starController, openWebsiteController);
                resultPanels[i] = resultPanel;
                contentPanel.add(resultPanel);
                contentPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
            }
            int totalPage = (int) Math.ceil((double) totalResults / resultsPerPage);
            prevButton.setEnabled(currentPage > 1);
            nextButton.setEnabled(currentPage < totalPage);

            contentPanel.add(Box.createVerticalStrut(5));
            contentPanel.add(createButtonPanel(currentPage, totalPage));
        }

        JPanel filler = new JPanel();
        filler.setBackground(SearchViewModel.BACKGROUND_COLOR);
        filler.setAlignmentX(LEFT_ALIGNMENT);
        filler.setMaximumSize(new Dimension(20, Integer.MAX_VALUE));
        contentPanel.add(filler);
        // This filler prevents the result panels spread evenly across the page

        revalidate();
        repaint();
    }

    @NotNull
    private JPanel createButtonPanel(int currentPage, int totalPage) {
        JPanel buttonPanel = new JPanel();

        JLabel label = new JLabel(String.format("Page %d/%d", currentPage, totalPage));
        label.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));

        buttonPanel.add(prevButton);
        buttonPanel.add(label);
        buttonPanel.add(nextButton);

        buttonPanel.setPreferredSize(new Dimension(650, 40));
        buttonPanel.setBackground(SearchViewModel.BACKGROUND_COLOR);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return buttonPanel;
    }

    public static void fixScrollSpeed(JScrollPane scrollpane) {
        // Swing has a bug that interprets scroll speed in pixels instead of lines of text
        // Solution: https://stackoverflow.com/questions/10119587/how-to-increase-the-slow-scroll-speed-on-a-jscrollpane
        JLabel systemLabel = new JLabel();
        FontMetrics metrics = systemLabel.getFontMetrics(systemLabel.getFont());
        int lineHeight = metrics.getHeight();
        int charWidth = metrics.getMaxAdvance();

        JScrollBar systemVBar = new JScrollBar(JScrollBar.VERTICAL);
        JScrollBar systemHBar = new JScrollBar(JScrollBar.HORIZONTAL);
        int verticalIncrement = systemVBar.getUnitIncrement();
        int horizontalIncrement = systemHBar.getUnitIncrement();

        scrollpane.getVerticalScrollBar().setUnitIncrement(lineHeight * verticalIncrement);
        scrollpane.getHorizontalScrollBar().setUnitIncrement(charWidth * horizontalIncrement);
    }
}
