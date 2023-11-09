package interface_adapter.query;

import use_case.query.query_all.QueryAllInputBoundary;
import use_case.query.query_all.QueryAllInputData;

public class QueryAllController {
    private final QueryAllInputBoundary queryInteractor;

    public QueryAllController(QueryAllInputBoundary queryInteractor) {
        this.queryInteractor = queryInteractor;
    }

    public void execute(String keywords, int resultsPerPage) {
        QueryAllInputData inputData = new QueryAllInputData(keywords.trim(), resultsPerPage);
        queryInteractor.execute(inputData);
    }
}
