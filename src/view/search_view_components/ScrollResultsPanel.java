package view.search_view_components;

import data_access.Database;
import entity.FetchedData;
import interface_adapter.fill_detail.FillDetailController;
import interface_adapter.open_website.OpenWebsiteController;
import interface_adapter.query.QueryOneController;
import interface_adapter.star.StarController;
import interface_adapter.view_model.ScrollResultsPanelModel;
import interface_adapter.view_model.ScrollResultsPanelState;
import interface_adapter.view_model.SearchViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedHashMap;
import java.util.List;

public class ScrollResultsPanel extends JScrollPane implements PropertyChangeListener {
    private final SearchViewModel searchViewModel;
    private final ScrollResultsPanelModel model;
    private JPanel contentPanel;
    private JButton prevButton, nextButton;
    private QueryOneController queryOneController;
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

        setPreferredSize(new Dimension(650, 700));
        setViewportView(contentPanel);

        model.addPropertyChangeListener(this);

        prevButton = new JButton("<Previous");
        nextButton = new JButton("Next>");

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
            case ScrollResultsPanelModel.REFRESH_ALL:
                displayPage(state.getFetchedDataList(), state.getDataIsStarredList(), state.getTotalResults(), state.getResultsPerPage(), state.getCurrentPage());
                break;
            case ScrollResultsPanelModel.REFRESH_DATA_INFO_PANEL:
                List<FetchedData> fetchedDataList = state.getFetchedDataList();
                for (int i = 0; i < fetchedDataList.size(); i++) {
                    resultPanels[i].updateDataInfoPanel(fetchedDataList.get(i));
                }
                break;
            case ScrollResultsPanelModel.REFRESH_STAR_STATES:
                List<Boolean> dataIsStarredList = state.getDataIsStarredList();
                for (int i = 0; i < dataIsStarredList.size(); i++) {
                    resultPanels[i].toggleStar(dataIsStarredList.get(i));
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

            if (currentPage == 1)
                contentPanel.add(new JLabel(totalResults + " results fetched"));

            LinkedHashMap<String, Boolean> detailsDisplayed = searchViewModel.getState().getDetailEntryDisplayed()[Database.indexOf(model.getDatabase())];
            for (int i = 0; i < fetchedData.size(); i++) {
                ResultPanel resultPanel = new ResultPanel(detailsDisplayed, fetchedData.get(i), dataIsStarredList.get(i), fillDetailController, starController, openWebsiteController);
                resultPanels[i] = resultPanel;
                contentPanel.add(resultPanel);
                contentPanel.add(Box.createVerticalStrut(5));
            }
            int totalPage = (int) Math.ceil((double) totalResults / resultsPerPage);
            prevButton.setEnabled(currentPage > 1);
            nextButton.setEnabled(currentPage < totalPage);

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(prevButton);
            buttonPanel.add(new JLabel(String.format("Page %d/%d", currentPage, totalPage)));
            buttonPanel.add(nextButton);
            buttonPanel.setPreferredSize(new Dimension(650, 40));
            buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            contentPanel.add(buttonPanel);
        }

        revalidate();
        repaint();
    }
}
