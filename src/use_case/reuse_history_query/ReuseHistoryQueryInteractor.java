package use_case.reuse_history_query;

import entity.Query;

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
