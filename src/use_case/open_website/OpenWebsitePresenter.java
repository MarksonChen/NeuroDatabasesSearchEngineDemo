package use_case.open_website;

import view_model.MainFrameViewModel;

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
