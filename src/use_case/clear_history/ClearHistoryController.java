package use_case.clear_history;

import use_case.clear_history.ClearHistoryInputBoundary;

public class ClearHistoryController {
    private final ClearHistoryInputBoundary clearHistoryInteractor;

    public ClearHistoryController(ClearHistoryInputBoundary clearHistoryInteractor) {
        this.clearHistoryInteractor = clearHistoryInteractor;
    }

    public void execute() {
        clearHistoryInteractor.execute();
    }
}
