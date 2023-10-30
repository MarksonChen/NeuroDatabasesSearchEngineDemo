package view;

import interface_adapter.query.QueryController;
import interface_adapter.star.StarController;
import interface_adapter.switch_view.SwitchViewController;
import interface_adapter.view_model.CellularDBSearchViewModel;
import view.results_panels.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class CellularDBSearchView extends SearchView {
    private QueryBar queryBar;
    private OptionBar optionBar;
    private JPanel resultsPanel;
    private CardLayout cardLayout;

    private final String TABBED_VIEW = "Tabbed View";
    private final String SINGLE_VIEW = "Single View";

    public CellularDBSearchView(CellularDBSearchViewModel cellularDBSearchViewModel, SwitchViewController switchViewController, QueryController queryController, StarController starController) {
        String[] databaseNames = new String[]{"Database 1", "Database 2", "Database 3"};
        List<Map<String,String>>[] results = MockData.getAllResults();
        setLayout(new BorderLayout());

        // Initialize components
        queryBar = new QueryBar(databaseNames);
        optionBar = new OptionBar(databaseNames);
        resultsPanel = new JPanel();

        cardLayout = new CardLayout();
        resultsPanel.setLayout(cardLayout);

        // Add TabbedResultsPanel and ScrollResultsPanel to resultsPanel
        TabbedResultsPanel tabbedResultsPanel = new TabbedResultsPanel(results, databaseNames, queryBar.getResultsPerPage());
        ScrollResultsPanel singleResultsPanel = new ScrollResultsPanel(results[0], queryBar.getResultsPerPage());
        resultsPanel.add(tabbedResultsPanel, TABBED_VIEW);
        resultsPanel.add(singleResultsPanel, SINGLE_VIEW);

        // Default to Tabbed View
        cardLayout.show(resultsPanel, TABBED_VIEW);

        // Add components to SearchView
        add(queryBar, BorderLayout.NORTH);
        add(optionBar, BorderLayout.WEST);
        add(resultsPanel, BorderLayout.CENTER);
    }

    public void switchToTabbedView() {
        cardLayout.show(resultsPanel, TABBED_VIEW);
    }

    public void switchToSingleView() {
        cardLayout.show(resultsPanel, SINGLE_VIEW);
    }
}
