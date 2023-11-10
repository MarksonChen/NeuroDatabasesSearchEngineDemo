package use_case.clear_history;

import view_model.HistoryViewModel;

import java.util.ArrayList;

public class ClearHistoryPresenter implements ClearHistoryOutputBoundary {
    private final HistoryViewModel historyViewModel;

    public ClearHistoryPresenter(HistoryViewModel historyViewModel) {
        this.historyViewModel = historyViewModel;
    }

    @Override
    public void prepareSuccessView() {
        historyViewModel.getState().setHistoryQueryList(new ArrayList<>());
        historyViewModel.firePropertyChanged(HistoryViewModel.CLEAR_HISTORY);
    }
}
