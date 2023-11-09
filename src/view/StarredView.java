package view;

import data_access.Database;
import entity.FetchedData;
import interface_adapter.fill_detail.FillDetailController;
import interface_adapter.open_website.OpenWebsiteController;
import interface_adapter.star.StarController;
import interface_adapter.view_model.SearchViewModel;
import interface_adapter.view_model.StarredViewModel;
import view.search_view_components.ResultPanel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedHashMap;
import java.util.List;

public class StarredView extends JFrame implements PropertyChangeListener {
    private final SearchViewModel searchViewModel;
    private final StarredViewModel starredViewModel;
    private final FillDetailController fillDetailController;
    private final StarController starController;
    private final OpenWebsiteController openWebsiteController;
    private JPanel contentPanel;
    public StarredView(SearchViewModel searchViewModel, StarredViewModel starredViewModel, FillDetailController fillDetailController, StarController starController, OpenWebsiteController openWebsiteController) {
        this.searchViewModel = searchViewModel;
        this.starredViewModel = starredViewModel;
        this.fillDetailController = fillDetailController;
        this.starController = starController;
        this.openWebsiteController = openWebsiteController;
        starredViewModel.addPropertyChangeListener(this);
        setPreferredSize(new Dimension(890, 650));
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        add(new JScrollPane(contentPanel));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()){  // TODO change switch syntax
            case StarredViewModel.REFRESH -> refresh();
            case StarredViewModel.STAR -> refresh();
            // Here we could introduce complexity and ensure that unstarred entries
            // are kept on the page until the page is refreshed again.
            // But that requires we also keep a list of "recent changes" in the starredViewModel,
            // which is doable but not worth the time for such a minor feature.
            // However, the potential for change is kept here by this switch syntax.
            case StarredViewModel.FILL_DETAIL -> refresh();
            // Here we could prevent a whole page refresh and only update the changed entry,
            // but still, that is doable but not worth the time as a minor feature.
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
            ResultPanel resultPanel = new ResultPanel(detailsDisplayed[databaseIndex], data, true, fillDetailController, starController, openWebsiteController);
            contentPanel.add(resultPanel);
            contentPanel.add(Box.createVerticalStrut(5));
        }
        revalidate();
        repaint();
    }
}
