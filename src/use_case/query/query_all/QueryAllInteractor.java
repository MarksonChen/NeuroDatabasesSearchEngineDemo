package use_case.query.query_all;

import entity.FetchedData;
import entity.Query;
import use_case.clear_history.HistoryDataAccessInterface;
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
        Query query = new Query(inputData.getKeywords());
        if (query.getKeywords().trim().isEmpty()){
            queryAllPresenter.prepareFailView("Cannot search with empty query, please try again");
            return;
        }
        try {
            historyDAO.add(query);
            historyDAO.saveToFile();
            List<FetchedData>[] fetchedData = queryDAO.queryAll(query, inputData.getResultsPerPage(), 1);
            List<Boolean>[] starredStateListArr = starDAO.checkIfDataStarred(fetchedData);
            QueryAllOutputData outputData = new QueryAllOutputData(inputData.getKeywords(), fetchedData, starredStateListArr, historyDAO.getHistoryQueryList(), queryDAO.getQueryAllTotalResults());
            queryAllPresenter.prepareSuccessView(outputData);
        } catch (IOException e) {
            //TODO: prepare fail view
        }
    }
}
