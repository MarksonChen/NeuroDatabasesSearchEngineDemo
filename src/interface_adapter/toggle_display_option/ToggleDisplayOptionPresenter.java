package interface_adapter.toggle_display_option;

import interface_adapter.view_model.ScrollResultsPanelModel;
import interface_adapter.view_model.SearchViewModel;
import interface_adapter.view_model.StarredViewModel;
import use_case.toggle_display_option.ToggleDisplayOptionOutputBoundary;
import use_case.toggle_display_option.ToggleDisplayOptionOutputData;

import java.util.LinkedHashMap;

public class ToggleDisplayOptionPresenter implements ToggleDisplayOptionOutputBoundary {
    private final SearchViewModel searchViewModel;
    private final ScrollResultsPanelModel[] resultsPanelModels;
    private final StarredViewModel starredViewModel;

    public ToggleDisplayOptionPresenter(SearchViewModel searchViewModel, ScrollResultsPanelModel[] resultsPanelModels, StarredViewModel starredViewModel) {
        this.searchViewModel = searchViewModel;
        this.resultsPanelModels = resultsPanelModels;
        this.starredViewModel = starredViewModel;
    }

    @Override
    public void prepareSuccessView(ToggleDisplayOptionOutputData outputData) {
        int databaseIndex = outputData.getDatabaseIndex();
        String entryKey = outputData.getEntryKey();

        LinkedHashMap<String, Boolean>[] detailEntryDisplayed = searchViewModel.getState().getDetailEntryDisplayed();
        boolean displayed = detailEntryDisplayed[databaseIndex].get(entryKey);
        detailEntryDisplayed[databaseIndex].put(entryKey, !displayed);
        searchViewModel.firePropertyChanged(SearchViewModel.REFRESH_OPTION_BAR);

        ScrollResultsPanelModel resultsPanelModel = resultsPanelModels[databaseIndex];
        resultsPanelModel.firePropertyChanged(ScrollResultsPanelModel.REFRESH_DATA_INFO_PANEL);

        starredViewModel.firePropertyChanged(StarredViewModel.REFRESH);
    }
}
