package use_case.open_frame;

public class OpenFrameInteractor implements OpenFrameInputBoundary {
    private final OpenFrameOutputBoundary openFramePresenter;

    public OpenFrameInteractor(OpenFrameOutputBoundary switchViewPresenter) {
        this.openFramePresenter = switchViewPresenter;
    }

    @Override
    public void execute(String viewName) {
        openFramePresenter.openFrame(viewName);
    }
}
