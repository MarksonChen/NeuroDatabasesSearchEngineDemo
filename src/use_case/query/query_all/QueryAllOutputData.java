package use_case.query.query_all;

import entity.FetchedData;
import entity.Query;

import java.util.List;

public class QueryAllOutputData {
    private final List<FetchedData>[] fetchedData;
    private final List<Boolean>[] starredStateListArr;
    private final List<Query> historyQueryList;
    private final int[] queryAllTotalResults;
    private final String queryKeywords;

    public QueryAllOutputData(String queryKeywords, List<FetchedData>[] fetchedData, List<Boolean>[] starredStateListArr, List<Query> historyQueryList, int[] queryAllTotalResults) {
        this.queryKeywords = queryKeywords;
        this.fetchedData = fetchedData;
        this.starredStateListArr = starredStateListArr;
        this.historyQueryList = historyQueryList;
        this.queryAllTotalResults = queryAllTotalResults;
    }

    public List<FetchedData>[] getFetchedData() {
        return fetchedData;
    }

    public int[] getQueryAllTotalResults() {
        return queryAllTotalResults;
    }

    public String getQueryKeywords() {
        return queryKeywords;
    }

    public List<Boolean>[] getStarredStateListArr() {
        return starredStateListArr;
    }

    public List<Query> getHistoryQueryList() {
        return historyQueryList;
    }
}
