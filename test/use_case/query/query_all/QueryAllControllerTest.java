package use_case.query.query_all;

import data_access.*;
import entity.FetchedData;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.HistoryDataAccessInterface;
import use_case.query.QueryDataAccessInterface;
import use_case.star.StarDataAccessInterface;
import view_model.HistoryViewModel;
import view_model.ScrollResultsPanelModel;
import view_model.SearchViewModel;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QueryAllControllerTest {

    @Test
    void execute() {
        // Note that this Controller + Interactor integration test, along with the
        // interactor unit tests, pushed the test coverage to >90% lines, and that's
        // good enough.

        QueryDataAccessInterface queryDAO;
        StarDataAccessInterface starDAO;
        HistoryDataAccessInterface historyDAO;
        try {
            queryDAO = new InMemoryQueryDataAccessObject(new CacheDataAccessObject(), "resources/serializables/fetchedDataListArr.ser");
            starDAO = new StarDataAccessObject("resources/serializables/starredData.ser");
            starDAO.clear();
            historyDAO = new HistoryDataAccessObject("resources/serializables/historyQueries.ser");
            historyDAO.clear();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        QueryAllOutputBoundary mockPresenter = createMockPresenter();

        QueryAllInputBoundary queryInteractor = new QueryAllInteractor(mockPresenter, queryDAO, starDAO, historyDAO);
        QueryAllController queryAllController = new QueryAllController(queryInteractor);

        queryAllController.execute("hippocampus", SearchViewModel.DEFAULT_RESULTS_PER_PAGE);
    }

    @NotNull
    private static QueryAllOutputBoundary createMockPresenter() {
        QueryAllOutputBoundary mockPresenter = new QueryAllOutputBoundary() {
            @Override
            public void prepareSuccessView(QueryAllOutputData outputData) {
                List<FetchedData>[] fetchedData = outputData.getFetchedData();
                List<Boolean>[] starredStateListArr = outputData.getStarredStateListArr();
                for (int i = 0; i < Database.length; i++) {
                    Assertions.assertFalse(fetchedData[i].isEmpty());
                    Assertions.assertFalse(starredStateListArr[i].isEmpty());
                }
                Assertions.assertEquals(outputData.getQueryAllTotalResults().length, Database.length);
            }

            @Override
            public void prepareFailView(String s) {
                Assertions.fail("Failure unexpected");
            }
        };
        return mockPresenter;
    }
}