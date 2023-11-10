package use_case.fill_detail;

import data_access.Database;
import entity.FetchedData;
import view_model.MainFrameViewModel;
import view_model.ScrollResultsPanelModel;
import view_model.StarredViewModel;

import java.util.List;

public class FillDetailPresenter implements FillDetailOutputBoundary {
    private final ScrollResultsPanelModel[] resultsPanelModels;
    private final StarredViewModel starredViewModel;
    private final MainFrameViewModel mainFrameViewModel;

    public FillDetailPresenter(ScrollResultsPanelModel[] resultsPanelModels, StarredViewModel starredViewModel, MainFrameViewModel mainFrameViewModel) {
        this.resultsPanelModels = resultsPanelModels;
        this.starredViewModel = starredViewModel;
        this.mainFrameViewModel = mainFrameViewModel;
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

    @Override
    public void prepareFailView(String errorMessage) {
        mainFrameViewModel.getState().setErrorMessage(errorMessage);
        mainFrameViewModel.firePropertyChanged(MainFrameViewModel.ERROR);
    }
}
