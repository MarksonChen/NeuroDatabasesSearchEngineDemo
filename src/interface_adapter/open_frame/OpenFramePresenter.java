package interface_adapter.open_frame;

import interface_adapter.view_model.FrameManagerModel;
import interface_adapter.view_model.StarredViewModel;
import use_case.open_frame.OpenFrameOutputBoundary;

public class OpenFramePresenter implements OpenFrameOutputBoundary {
    private final FrameManagerModel frameManagerModel;
    private final StarredViewModel starredViewModel;

    public OpenFramePresenter(FrameManagerModel frameManagerModel, StarredViewModel starredViewModel) {
        this.frameManagerModel = frameManagerModel;
        this.starredViewModel = starredViewModel;
    }

    @Override
    public void openFrame(String viewName) {
        frameManagerModel.setActiveView(viewName);
        frameManagerModel.firePropertyChanged(FrameManagerModel.OPEN);
        starredViewModel.firePropertyChanged(StarredViewModel.REFRESH);
    }
}
