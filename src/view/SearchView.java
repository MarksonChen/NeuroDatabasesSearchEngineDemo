package view;

import data_access.Database;
import use_case.query.query_all.QueryAllController;
import use_case.query.query_one.QueryOneController;
import use_case.switch_results_panel.SwitchResultsPanelController;
import use_case.switch_view.SwitchViewController;
import use_case.toggle_display_option.ToggleDisplayOptionController;
import view_model.SearchViewModel;
import view_model.SearchViewState;
import view.search_view_components.*;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedHashMap;

public class SearchView extends JPanel implements PropertyChangeListener {
    private final TabbedResultsPanel tabbedView;
    LinkedHashMap<String, ScrollResultsPanel> singleViewResultsPanelsMap;
    // A LinkedHashMap is required, so it can be iterated in a fixed order
    private QueryBar queryBar;
    private OptionBar optionBar;
    private JPanel resultsPanel;
    private CardLayout cardLayout;
    SearchViewModel searchViewModel;

    public SearchView(ScrollResultsPanel[] tabbedViewResultPanels, ScrollResultsPanel[] singleViewResultPanels,
                      SearchViewModel searchViewModel, SwitchViewController switchViewController,
                      SwitchResultsPanelController switchResultsPanelController, QueryAllController queryAllController,
                      QueryOneController queryOneController, ToggleDisplayOptionController toggleDisplayOptionController) {
        this.searchViewModel = searchViewModel;

        setLayout(new BorderLayout());

        queryBar = new QueryBar(this.searchViewModel, switchViewController, switchResultsPanelController, queryAllController, queryOneController);
        optionBar = new OptionBar(toggleDisplayOptionController, searchViewModel);
        resultsPanel = new JPanel();

        cardLayout = new CardLayout();
        resultsPanel.setLayout(cardLayout);
        resultsPanel.setBackground(SearchViewModel.BACKGROUND_COLOR);
        resultsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));

        singleViewResultsPanelsMap = new LinkedHashMap<>();
        for (int i = 0; i < tabbedViewResultPanels.length; i++) {
            String databaseName = Database.get(i).name();
            singleViewResultsPanelsMap.put(databaseName, singleViewResultPanels[i]);
            resultsPanel.add(singleViewResultPanels[i], databaseName);
        }

        tabbedView = new TabbedResultsPanel(tabbedViewResultPanels, this.searchViewModel, queryOneController);
        resultsPanel.add(tabbedView, SearchViewModel.ALL_DATABASES);

        // Default to Tabbed View
        cardLayout.show(resultsPanel, SearchViewModel.ALL_DATABASES);

        add(queryBar, BorderLayout.NORTH);
        add(optionBar, BorderLayout.WEST);
        add(resultsPanel, BorderLayout.CENTER);

        searchViewModel.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SearchViewState state = searchViewModel.getState();
        switch (evt.getPropertyName()){
            case SearchViewModel.RESULTS_PANEL_SWITCHED -> {
                cardLayout.show(resultsPanel, state.getDatabaseOption());
                optionBar.makePanels();
            }
            case SearchViewModel.QUERY_ENTERED -> {
                queryBar.setSearchTextFieldText(state.getSearchFieldText());
                queryBar.focusOntoSearchTextField();
            }
            case SearchViewModel.REFRESH_OPTION_BAR -> optionBar.makePanels();
            case SearchViewModel.ERROR -> JOptionPane.showMessageDialog(this, state.getErrorMessage());
        }
        revalidate();
        repaint();
    }
}
