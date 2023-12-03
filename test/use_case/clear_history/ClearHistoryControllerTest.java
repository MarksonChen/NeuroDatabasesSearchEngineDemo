package use_case.clear_history;

import data_access.HistoryDataAccessObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class ClearHistoryControllerTest {
    private HistoryDataAccessObject historyDAO;

    @BeforeEach
    void setUp() {
        try {
            historyDAO = new HistoryDataAccessObject("resources/serializables/historyQueries.ser");
            historyDAO.clear();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testHistoryCleared() {
        ClearHistoryOutputBoundary mockPresenter = new ClearHistoryOutputBoundary() {
            @Override
            public void prepareSuccessView() {
                // Not part of the test
            }
        };

        ClearHistoryInputBoundary clearHistoryInteractor = new ClearHistoryInteractor(mockPresenter, historyDAO);
        ClearHistoryController clearHistoryController = new ClearHistoryController(clearHistoryInteractor);
        clearHistoryController.execute();

        Assertions.assertEquals(historyDAO.getHistoryQueryList().size(), 0);
    }
}

