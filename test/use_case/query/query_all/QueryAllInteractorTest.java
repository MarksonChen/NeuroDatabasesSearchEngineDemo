package use_case.query.query_all;

import data_access.*;
import entity.FetchedData;
import org.junit.jupiter.api.Assertions;
import use_case.HistoryDataAccessInterface;
import use_case.query.QueryDataAccessInterface;
import use_case.star.StarDataAccessInterface;
import view_model.SearchViewModel;

import java.io.IOException;
import java.util.List;

class QueryAllInteractorTest {

    private QueryDataAccessInterface queryDAO;
    private StarDataAccessInterface starDAO;
    private HistoryDataAccessInterface historyDAO;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        try {
            queryDAO = new InMemoryQueryDataAccessObject(new CacheDataAccessObject(), "resources/serializables/fetchedDataListArr.ser");
            starDAO = new StarDataAccessObject("resources/serializables/starredData.ser");
            starDAO.clear();
            historyDAO = new HistoryDataAccessObject("resources/serializables/historyQueries.ser");
            historyDAO.clear();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @org.junit.jupiter.api.Test
    void successTestBasic() {
        QueryAllInputData inputData = new QueryAllInputData("Hippocampus", SearchViewModel.DEFAULT_RESULTS_PER_PAGE);

        QueryAllOutputBoundary mockPresenter = new QueryAllOutputBoundary() {
            @Override
            public void prepareSuccessView(QueryAllOutputData outputData) {
                List<FetchedData>[] fetchedData = outputData.getFetchedData();
                List<Boolean>[] starredStateListArr = outputData.getStarredStateListArr();
                for (int i = 0; i < Database.length; i++) {
                    Assertions.assertFalse(fetchedData[i].isEmpty());
                    Assertions.assertFalse(starredStateListArr[i].isEmpty());
                }
                Assertions.assertEquals(outputData.getQueryKeywords(), inputData.getKeywords());
                Assertions.assertEquals(outputData.getQueryAllTotalResults().length, Database.length);
            }

            @Override
            public void prepareFailView(String s) {
                Assertions.fail("Failure unexpected");
            }
        };

        QueryAllInputBoundary queryInteractor = new QueryAllInteractor(mockPresenter, queryDAO, starDAO, historyDAO);
        queryInteractor.execute(inputData);
    }


    @org.junit.jupiter.api.Test
    void failTestEmptyQuery(){
        QueryAllInputData inputData = new QueryAllInputData("", SearchViewModel.DEFAULT_RESULTS_PER_PAGE);

        QueryAllOutputBoundary mockPresenter = new QueryAllOutputBoundary() {
            @Override
            public void prepareSuccessView(QueryAllOutputData outputData) {
                Assertions.fail("Success unexpected");
            }

            @Override
            public void prepareFailView(String s) {
                //Test passed
            }
        };

        QueryAllInputBoundary queryInteractor = new QueryAllInteractor(mockPresenter, queryDAO, starDAO, historyDAO);
        queryInteractor.execute(inputData);
    }


    @org.junit.jupiter.api.Test
    void testHistoryAdded(){
        QueryAllInputData inputData = new QueryAllInputData("Hippocampus", SearchViewModel.DEFAULT_RESULTS_PER_PAGE);

        QueryAllOutputBoundary mockPresenter = new QueryAllOutputBoundary() {
            @Override
            public void prepareSuccessView(QueryAllOutputData outputData) {
                // Not part of the test
            }
            @Override
            public void prepareFailView(String s) {
                // Not part of the test
            }
        };

        QueryAllInputBoundary queryInteractor = new QueryAllInteractor(mockPresenter, queryDAO, starDAO, historyDAO);
        queryInteractor.execute(inputData);

        Assertions.assertEquals(historyDAO.getHistoryQueryList().get(0).getKeywords(), "Hippocampus");
    }
}