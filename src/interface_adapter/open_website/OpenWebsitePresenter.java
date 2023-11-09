package interface_adapter.open_website;

import interface_adapter.view_model.MainFrameViewModel;
import use_case.open_website.OpenWebsiteOutputBoundary;

public class OpenWebsitePresenter implements OpenWebsiteOutputBoundary {
    private final MainFrameViewModel mainFrameViewModel;

    public OpenWebsitePresenter(MainFrameViewModel mainFrameViewModel) {
        this.mainFrameViewModel = mainFrameViewModel;
    }

    @Override
    public void prepareFailView(String errorMessage) {
        mainFrameViewModel.getState().setErrorMessage(errorMessage);
        mainFrameViewModel.firePropertyChanged(MainFrameViewModel.ERROR);
    }
}
