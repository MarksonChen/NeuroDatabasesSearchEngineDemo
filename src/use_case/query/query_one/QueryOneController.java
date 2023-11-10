package use_case.query.query_one;

import data_access.Database;
import use_case.query.query_one.QueryOneInputBoundary;
import use_case.query.query_one.QueryOneInputData;

public class QueryOneController {
    private final QueryOneInputBoundary queryInteractor;

    public QueryOneController(QueryOneInputBoundary queryInteractor) {
        this.queryInteractor = queryInteractor;
    }

    public void execute(Database database, String keywords, int resultsPerPage, int page) {
        QueryOneInputData inputData = new QueryOneInputData(database, keywords.trim(), resultsPerPage, page);
        queryInteractor.execute(inputData);
    }
}
