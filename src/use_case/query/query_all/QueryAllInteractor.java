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

        historyDAO.add(query);
        try {
            historyDAO.saveToFile();
        } catch (IOException e) {
            e.printStackTrace(); // No need to push an alert if this automatic process is not working
        }

        try {
            List<FetchedData>[] fetchedData = queryDAO.queryAll(query, inputData.getResultsPerPage(), 1);
            List<Boolean>[] starredStateListArr = starDAO.checkIfDataStarred(fetchedData);
            QueryAllOutputData outputData = new QueryAllOutputData(keywords, fetchedData, starredStateListArr, historyDAO.getHistoryQueryList(), queryDAO.getQueryAllTotalResults());
            queryAllPresenter.prepareSuccessView(outputData);
        } catch (IOException e) {
            queryAllPresenter.prepareFailView("An error occured while fetching query for \"" + keywords + "\"");
        }
    }
}
