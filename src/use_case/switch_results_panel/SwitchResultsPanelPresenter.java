package use_case.switch_results_panel;

import view_model.SearchViewModel;
import view_model.SearchViewState;

public class SwitchResultsPanelPresenter implements SwitchResultsPanelOutputBoundary {
    private final SearchViewModel searchViewModel;

    public SwitchResultsPanelPresenter(SearchViewModel searchViewModel) {
        this.searchViewModel = searchViewModel;
    }

    @Override
    public void switchResultsPanel(String databaseOption) {
        SearchViewState state = searchViewModel.getState();
        state.setDatabaseOption(databaseOption);
        searchViewModel.firePropertyChanged(SearchViewModel.RESULTS_PANEL_SWITCHED);
    }
}
