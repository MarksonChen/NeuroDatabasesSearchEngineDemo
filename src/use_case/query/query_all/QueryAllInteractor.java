package use_case.query.query_all;

import entity.FetchedData;
import entity.Query;
import use_case.HistoryDataAccessInterface;
import use_case.query.QueryDataAccessInterface;
import use_case.star.StarDataAccessInterface;

import java.io.IOException;
import java.util.List;

public class QueryAllInteractor implements QueryAllInputBoundary {
    private final QueryAllOutputBoundary queryAllPresenter;
    private final QueryDataAccessInterface queryDAO;
    private final StarDataAccessInterface starDAO;
    private final HistoryDataAccessInterface historyDAO;

    public QueryAllInteractor(QueryAllOutputBoundary queryAllOutputBoundary, QueryDataAccessInterface queryDAO, StarDataAccessInterface starDAO, HistoryDataAccessInterface historyDAO) {
        this.queryAllPresenter = queryAllOutputBoundary;
        this.queryDAO = queryDAO;
        this.starDAO = starDAO;
        this.historyDAO = historyDAO;
    }

    @Override
    public void execute(QueryAllInputData inputData) {
        String keywords = inputData.getKeywords();
        Query query = new Query(keywords);
        if (query.getKeywords().trim().isEmpty()){
            queryAllPresenter.prepareFailView("Cannot search with empty query, please try again");
            return;
        }

        try {
            historyDAO.add(query);
            historyDAO.saveToFile();
            List<FetchedData>[] fetchedData = queryDAO.queryAll(query, inputData.getResultsPerPage(), 1);
            List<Boolean>[] starredStateListArr = starDAO.checkIfDataStarred(fetchedData);
            List<Query> historyQueryList = historyDAO.getHistoryQueryList();
            int[] queryAllTotalResults = queryDAO.getQueryAllTotalResults();

            QueryAllOutputData outputData = new QueryAllOutputData(keywords, fetchedData, starredStateListArr, historyQueryList, queryAllTotalResults);
            queryAllPresenter.prepareSuccessView(outputData);
        } catch (IOException e) {
            queryAllPresenter.prepareFailView("An error occured while fetching query for \"" + keywords + "\"");
        }
    }
}
