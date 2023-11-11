package use_case.load_from_DAO;

import use_case.HistoryDataAccessInterface;
import use_case.query.QueryDataAccessInterface;
import use_case.star.StarDataAccessInterface;

public class LoadFromDAOInteractor implements LoadFromDAOInputBoundary{
    private final LoadFromDAOOutputBoundary loadFromDAOPresenter;
    private final QueryDataAccessInterface queryDAO;
    private final StarDataAccessInterface starDAO;
    private final HistoryDataAccessInterface historyDAO;

    public LoadFromDAOInteractor(LoadFromDAOOutputBoundary loadFromDAOPresenter, QueryDataAccessInterface queryDAO, StarDataAccessInterface starDAO, HistoryDataAccessInterface historyDAO) {
        this.loadFromDAOPresenter = loadFromDAOPresenter;
        this.queryDAO = queryDAO;
        this.starDAO = starDAO;
        this.historyDAO = historyDAO;
    }

    @Override
    public void execute() {
        loadFromDAOPresenter.updateStatesWithData(new LoadFromDAOOutputData(queryDAO.getEntryKeys(), starDAO.getStarredDataList(), historyDAO.getHistoryQueryList()));
    }
}
