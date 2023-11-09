package use_case.reuse_history_query;

import entity.Query;

public interface ReuseHistoryQueryInputBoundary {
    void execute(Query query);
}
