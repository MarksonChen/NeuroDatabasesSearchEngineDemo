package use_case.switch_view;

public class SwitchViewInteractor implements SwitchViewInputBoundary{
    private final SwitchViewOutputBoundary switchViewPresenter;

    public SwitchViewInteractor(SwitchViewOutputBoundary switchViewPresenter) {
        this.switchViewPresenter = switchViewPresenter;
    }

    @Override
    public void execute(SwitchViewInputData switchViewInputData) {
        switchViewPresenter.switchToView(switchViewInputData.getViewName());
    }
}
