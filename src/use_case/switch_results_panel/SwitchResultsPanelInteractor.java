package use_case.switch_results_panel;

public class SwitchResultsPanelInteractor implements SwitchResultsPanelInputBoundary {
    private final SwitchResultsPanelOutputBoundary switchResultsPanelPresenter;

    public SwitchResultsPanelInteractor(SwitchResultsPanelOutputBoundary switchResultsPanelPresenter) {
        this.switchResultsPanelPresenter = switchResultsPanelPresenter;
    }

    @Override
    public void execute(String databaseOption) {
        switchResultsPanelPresenter.switchResultsPanel(databaseOption);
    }
}
