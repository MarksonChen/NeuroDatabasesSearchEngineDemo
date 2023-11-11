package use_case.switch_view;

public class SwitchViewController {
    private final SwitchViewInputBoundary switchViewInteractor;

    public SwitchViewController(SwitchViewInputBoundary switchViewInteractor) {
        this.switchViewInteractor = switchViewInteractor;
    }

    public void execute(String viewName) {
        switchViewInteractor.execute(viewName);
    }
}
