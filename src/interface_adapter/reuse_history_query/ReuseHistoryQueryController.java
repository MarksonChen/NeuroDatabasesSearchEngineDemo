package interface_adapter.reuse_history_query;

import entity.Query;
import use_case.reuse_history_query.ReuseHistoryQueryInputBoundary;

public class ReuseHistoryQueryController {
    private final ReuseHistoryQueryInputBoundary reuseHistoryQueryInteractor;

    public ReuseHistoryQueryController(ReuseHistoryQueryInputBoundary reuseHistoryQueryInteractor) {
        this.reuseHistoryQueryInteractor = reuseHistoryQueryInteractor;
    }

    public void execute(Query query) {
        reuseHistoryQueryInteractor.execute(query);
    }
}
