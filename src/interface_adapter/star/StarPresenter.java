package interface_adapter.star;

import data_access.Database;
import entity.FetchedData;
import interface_adapter.view_model.ScrollResultsPanelModel;
import interface_adapter.view_model.ScrollResultsPanelState;
import interface_adapter.view_model.StarredViewModel;
import use_case.star.StarOutputBoundary;

import java.util.List;

public class StarPresenter implements StarOutputBoundary {
    private final ScrollResultsPanelModel[] resultsPanelModels;
    private final StarredViewModel starredViewModel;

    public StarPresenter(ScrollResultsPanelModel[] resultsPanelModels, StarredViewModel starredViewModel) {
        this.resultsPanelModels = resultsPanelModels;
        this.starredViewModel = starredViewModel;
    }

    @Override
    public void prepareSuccessView(FetchedData outputData) {
        List<FetchedData> starredDataList = starredViewModel.getState().getStarredDataList();
        if (starredDataList.contains(outputData)){
            starredDataList.remove(outputData);
        } else {
            starredDataList.add(outputData);
        }
        starredViewModel.firePropertyChanged(starredViewModel.STAR);

        ScrollResultsPanelModel model = resultsPanelModels[Database.indexOf(outputData.getDatabase())];
        ScrollResultsPanelState state = model.getState();
        if (state.getFetchedDataList().contains(outputData)) {
            // A data can be starred both from the SearchView and the StarredView
            // But the state only contains the current page
            state.toggleStarOfData(outputData);
            model.firePropertyChanged(ScrollResultsPanelModel.REFRESH_STAR_STATES);
        }
    }
}
