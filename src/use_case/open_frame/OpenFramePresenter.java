package use_case.open_frame;

import view_model.FrameManagerModel;
import view_model.StarredViewModel;

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
