package use_case.clear_history;

import java.io.IOException;

public class ClearHistoryInteractor implements ClearHistoryInputBoundary {
    private final ClearHistoryOutputBoundary clearHistoryPresenter;
    private final HistoryDataAccessInterface historyDAO;

    public ClearHistoryInteractor(ClearHistoryOutputBoundary clearHistoryPresenter, HistoryDataAccessInterface historyDAO) {
        this.clearHistoryPresenter = clearHistoryPresenter;
        this.historyDAO = historyDAO;
    }

    @Override
    public void execute() {
        historyDAO.removeHistory();
        try {
            historyDAO.saveToFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        clearHistoryPresenter.prepareSuccessView();
    }
}
