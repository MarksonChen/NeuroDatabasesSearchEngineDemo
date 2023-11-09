package use_case.query.query_one;

import data_access.Database;
import entity.FetchedData;
import entity.Query;

import java.util.List;

public class QueryOneOutputData { // TODO are these really necessary
    private final Database database;
    private final List<FetchedData> fetchedData;
    private final List<Boolean> dataStarredStateList;
    private final List<Query> historyQueryList;
    private final int queryOneTotalResults;
    private final int page;
    private final String queryKeyword;

    public QueryOneOutputData(Database database, String queryKeyword, List<FetchedData> fetchedData, List<Boolean> dataStarredStateList, List<Query> historyQueryList, int queryOneTotalResults, int page) {
        this.database = database;
        this.queryKeyword = queryKeyword;
        this.fetchedData = fetchedData;
        this.dataStarredStateList = dataStarredStateList;
        this.historyQueryList = historyQueryList;
        this.queryOneTotalResults = queryOneTotalResults;
        this.page = page;
    }

    public List<FetchedData> getFetchedData() {
        return fetchedData;
    }

    public int getQueryOneTotalResults() {
        return queryOneTotalResults;
    }

    public Database getDatabase() {
        return database;
    }

    public int getPage() {
        return page;
    }

    public String getQueryKeyword() {
        return queryKeyword;
    }

    public List<Boolean> getDataStarredStateList() {
        return dataStarredStateList;
    }

    public List<Query> getHistoryQueryList() {
        return historyQueryList;
    }
}
