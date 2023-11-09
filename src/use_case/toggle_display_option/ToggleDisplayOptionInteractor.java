package use_case.toggle_display_option;

public class ToggleDisplayOptionInteractor implements ToggleDisplayOptionInputBoundary{
    private final ToggleDisplayOptionOutputBoundary toggleDisplayOptionPresenter;

    public ToggleDisplayOptionInteractor(ToggleDisplayOptionOutputBoundary toggleDisplayOptionPresenter) {
        this.toggleDisplayOptionPresenter = toggleDisplayOptionPresenter;
    }

    @Override
    public void execute(ToggleDisplayOptionInputData inputData) {
        toggleDisplayOptionPresenter.prepareSuccessView(new ToggleDisplayOptionOutputData(inputData.getDatabaseIndex(), inputData.getEntryKey()));
    }
}
