package use_case.query.query_one;

import entity.FetchedData;
import entity.Query;
import use_case.HistoryDataAccessInterface;
import use_case.query.QueryDataAccessInterface;
import use_case.star.StarDataAccessInterface;

import java.io.IOException;
import java.util.List;

public class QueryOneInteractor implements QueryOneInputBoundary{
    private final QueryOneOutputBoundary queryOnePresenter;
    private final QueryDataAccessInterface queryDAO;
    private final StarDataAccessInterface starDAO;
    private final HistoryDataAccessInterface historyDAO;

    public QueryOneInteractor(QueryOneOutputBoundary queryOnePresenter, QueryDataAccessInterface queryDAO, StarDataAccessInterface starDAO, HistoryDataAccessInterface historyDAO) {
        this.queryOnePresenter = queryOnePresenter;
        this.queryDAO = queryDAO;
        this.starDAO = starDAO;
        this.historyDAO = historyDAO;
    }

    @Override
    public void execute(QueryOneInputData inputData) {
        String keywords = inputData.getKeywords();
        Query query = new Query(keywords);
        if (query.getKeywords().trim().isEmpty()){
            queryOnePresenter.prepareFailView("Cannot search with empty query, please try again");
            return;
        }

        historyDAO.add(query);
        try {
            historyDAO.saveToFile();
        } catch (IOException e) {
            e.printStackTrace(); // No need to push an alert if this automatic process is not working
        }

        try {
            List<FetchedData> fetchedData = queryDAO.queryOne(inputData.getDatabase(), query, inputData.getResultsPerPage(), inputData.getPage());
            List<Boolean> dataStarredStateList = starDAO.checkIfDataStarred(fetchedData);
            List<Query> historyQueryList = historyDAO.getHistoryQueryList();
            QueryOneOutputData outputData = new QueryOneOutputData(inputData.getDatabase(), keywords, fetchedData, dataStarredStateList, historyQueryList, queryDAO.getQueryOneTotalResults(), inputData.getPage());
            queryOnePresenter.prepareSuccessView(outputData);
        } catch (IOException e) {
            queryOnePresenter.prepareFailView("An error occured while fetching query for \"" + keywords + "\"");
        }
    }
}
