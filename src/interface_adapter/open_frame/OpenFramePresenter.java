package interface_adapter.open_frame;

import interface_adapter.view_model.FrameManagerModel;
import use_case.open_frame.OpenFrameOutputBoundary;

public class OpenFramePresenter implements OpenFrameOutputBoundary {
    private final FrameManagerModel frameManagerModel;

    public OpenFramePresenter(FrameManagerModel frameManagerModel) {
        this.frameManagerModel = frameManagerModel;
    }

    @Override
    public void openFrame(String viewName) {
        frameManagerModel.setActiveView(viewName);
        frameManagerModel.firePropertyChanged();
    }
}
