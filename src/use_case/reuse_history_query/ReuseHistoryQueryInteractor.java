package use_case.reuse_history_query;

import entity.Query;
import use_case.clear_history.HistoryDataAccessInterface;

public class ReuseHistoryQueryInteractor implements ReuseHistoryQueryInputBoundary{
    private final ReuseHistoryQueryOutputBoundary reuseHistoryQueryPresenter;

    public ReuseHistoryQueryInteractor(ReuseHistoryQueryOutputBoundary reuseHistoryQueryPresenter) {
        this.reuseHistoryQueryPresenter = reuseHistoryQueryPresenter;
    }

    @Override
    public void execute(Query query) {
        reuseHistoryQueryPresenter.enterQuery(query);
    }
}
