package view.search_view_components;

import data_access.Database;
import interface_adapter.query.QueryOneController;
import interface_adapter.view_model.SearchViewModel;

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

        Database[] databases = Database.values();
        for (int i = 0; i < databases.length; i++) {
            this.addTab(databases[i].name(), scrollResultsPanels[i]);
        }
//        for (Map.Entry<String, ScrollResultsPanel> entry: scrollResultsPanels.entrySet()){
//            this.addTab(entry.getKey(), entry.getValue());
//        }
    }

//    public void updateOnePanel(List<FetchedData> fetchedData, int totalReults, int resultsPerPage) {
//        scrollResultsPanels[i].displayData(fetchedData, totalReults, resultsPerPage);
//        revalidate();
//        repaint();
//    }
}