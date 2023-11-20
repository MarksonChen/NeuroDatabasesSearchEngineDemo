package use_case.open_frame;

import view_model.FrameManagerModel;
import view_model.HistoryViewModel;
import view_model.StarredViewModel;

public class OpenFramePresenter implements OpenFrameOutputBoundary {
    private final FrameManagerModel frameManagerModel;
    private final StarredViewModel starredViewModel;
    private final HistoryViewModel historyViewModel;

    public OpenFramePresenter(FrameManagerModel frameManagerModel, StarredViewModel starredViewModel, HistoryViewModel historyViewModel) {
        this.frameManagerModel = frameManagerModel;
        this.starredViewModel = starredViewModel;
        this.historyViewModel = historyViewModel;
    }

    @Override
    public void openFrame(String viewName) {
        frameManagerModel.setActiveView(viewName);
        frameManagerModel.firePropertyChanged(FrameManagerModel.OPEN);
        switch (viewName){
            case StarredViewModel.VIEW_NAME ->
                    starredViewModel.firePropertyChanged(StarredViewModel.REFRESH);
            case HistoryViewModel.VIEW_NAME ->
                    historyViewModel.firePropertyChanged(HistoryViewModel.REFRESH);
        }
    }
}
