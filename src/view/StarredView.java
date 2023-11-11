package view;

import data_access.Database;
import entity.FetchedData;
import use_case.fill_detail.FillDetailController;
import use_case.open_website.OpenWebsiteController;
import use_case.star.StarController;
import view_model.SearchViewModel;
import view_model.StarredViewModel;
import view.search_view_components.ResultPanel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedHashMap;
import java.util.List;

import static view.search_view_components.ScrollResultsPanel.fixScrollSpeed;

public class StarredView extends JFrame implements PropertyChangeListener {
    private final SearchViewModel searchViewModel;
    private final StarredViewModel starredViewModel;
    private final FillDetailController fillDetailController;
    private final StarController starController;
    private final OpenWebsiteController openWebsiteController;
    private final JPanel contentPanel;
    public StarredView(SearchViewModel searchViewModel, StarredViewModel starredViewModel, FillDetailController fillDetailController, StarController starController, OpenWebsiteController openWebsiteController) {
        this.searchViewModel = searchViewModel;
        this.starredViewModel = starredViewModel;
        this.fillDetailController = fillDetailController;
        this.starController = starController;
        this.openWebsiteController = openWebsiteController;
        starredViewModel.addPropertyChangeListener(this);

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(SearchViewModel.BACKGROUND_COLOR);
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        fixScrollSpeed(scrollPane);
        add(scrollPane);

        setPreferredSize(new Dimension(890, 650));
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()){
            case StarredViewModel.REFRESH, StarredViewModel.STAR, StarredViewModel.FILL_DETAIL
                    -> refresh();
            // Here we could introduce complexity and ensure that the only updated entries
            // for STAR & FILL_DETALI are changed, instead of doing a full page refresh,
            // which is doable but not worth the time as a minor improvement.
            // However, the potential for change is kept here by this switch syntax.
        }
    }

    private void refresh() {
        contentPanel.removeAll();
        List<FetchedData> starredDataList = starredViewModel.getState().getStarredDataList();
        if (starredDataList.isEmpty()){
            contentPanel.add(new JLabel(StarredViewModel.EMPTY_MESSAGE));
        }
        LinkedHashMap<String, Boolean>[] detailsDisplayed = searchViewModel.getState().getDetailEntryDisplayed();

        for (int i = starredDataList.size() - 1; i >= 0; i--) { // Last added, first shown
            FetchedData data = starredDataList.get(i);
            int databaseIndex = Database.indexOf(data.getDatabase());
            ResultPanel resultPanel = new ResultPanel(searchViewModel, detailsDisplayed[databaseIndex], data, true, fillDetailController, starController, openWebsiteController);
            contentPanel.add(Box.createVerticalStrut(5));
            contentPanel.add(resultPanel);
            contentPanel.add(Box.createVerticalStrut(5));
            contentPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        }
        revalidate();
        repaint();
    }
}
