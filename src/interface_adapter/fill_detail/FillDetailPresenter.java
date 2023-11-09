package interface_adapter.fill_detail;

import data_access.Database;
import entity.FetchedData;
import interface_adapter.view_model.ScrollResultsPanelModel;
import interface_adapter.view_model.StarredViewModel;
import use_case.fill_detail.FillDetailOutputBoundary;

import java.util.List;

public class FillDetailPresenter implements FillDetailOutputBoundary {
    private final ScrollResultsPanelModel[] resultsPanelModels;
    private final StarredViewModel starredViewModel;

    public FillDetailPresenter(ScrollResultsPanelModel[] resultsPanelModels, StarredViewModel starredViewModel) {
        this.resultsPanelModels = resultsPanelModels;
        this.starredViewModel = starredViewModel;
    }

    @Override
    public void prepareSuccessView(FetchedData outputData) {
        starredViewModel.firePropertyChanged(StarredViewModel.FILL_DETAIL);
        ScrollResultsPanelModel model = resultsPanelModels[Database.indexOf(outputData.getDatabase())];
        List<FetchedData> stateDataList = model.getState().getFetchedDataList();
        if (stateDataList.contains(outputData)){
            // Fill Detail can be called from both from the SearchView and the StarredView
            // But the ScrollResultsPanelState only contains the current page
            // If Fill Detail is called before any search take place, the list can even be empty.
            model.firePropertyChanged(ScrollResultsPanelModel.REFRESH_DATA_INFO_PANEL);
        }
    }
}
