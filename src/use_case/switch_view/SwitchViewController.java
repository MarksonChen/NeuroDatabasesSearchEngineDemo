package use_case.switch_view;

import use_case.switch_view.SwitchViewInputBoundary;

public class SwitchViewController {
    private final SwitchViewInputBoundary switchViewInteractor;

    public SwitchViewController(SwitchViewInputBoundary switchViewInteractor) {
        this.switchViewInteractor = switchViewInteractor;
    }

    public void execute(String viewName) {
        switchViewInteractor.execute(viewName);
    }
}
