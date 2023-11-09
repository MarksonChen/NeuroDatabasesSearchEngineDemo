package use_case.query.query_one;

import entity.FetchedData;
import entity.Query;
import use_case.clear_history.HistoryDataAccessInterface;
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
        Query query = new Query(inputData.getKeywords());
        if (query.getKeywords().trim().isEmpty()){
            queryOnePresenter.prepareFailView("Cannot search with empty query, please try again");
            return;
        }
        try {
            historyDAO.add(query);
            historyDAO.saveToFile();
            List<FetchedData> fetchedData = queryDAO.queryOne(inputData.getDatabase(), query, inputData.getResultsPerPage(), inputData.getPage());
            List<Boolean> dataStarredStateList = starDAO.checkIfDataStarred(fetchedData);
            List<Query> historyQueryList = historyDAO.getHistoryQueryList();
            QueryOneOutputData outputData = new QueryOneOutputData(inputData.getDatabase(), inputData.getKeywords(), fetchedData, dataStarredStateList, historyQueryList, queryDAO.getQueryOneTotalResults(), inputData.getPage());
            queryOnePresenter.prepareSuccessView(outputData);
        } catch (IOException e) {
            //TODO: prepare fail view
        }
    }
}
