package view.search_view_components;

import data_access.Database;
import use_case.query.query_one.QueryOneController;
import view_model.SearchViewModel;

import javax.swing.*;

public class TabbedResultsPanel extends JTabbedPane {

    private final SearchViewModel searchViewModel;
    private final QueryOneController queryOneController;
    private final ScrollResultsPanel[] scrollResultsPanels;
    // A LinkedHashMap is required so it can be iterated in a fixed order

    public TabbedResultsPanel(ScrollResultsPanel[] scrollResultsPanels, SearchViewModel searchViewModel, QueryOneController queryOneController) {
        this.searchViewModel = searchViewModel;
        this.queryOneController = queryOneController;
        this.scrollResultsPanels = scrollResultsPanels;

        setBackground(SearchViewModel.BACKGROUND_COLOR);

        for (int i = 0; i < Database.length; i++) {
            this.addTab(Database.get(i).name(), scrollResultsPanels[i]);
        }
    }
}