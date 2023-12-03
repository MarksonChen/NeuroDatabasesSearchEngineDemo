package use_case.clear_history;

import data_access.HistoryDataAccessObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class ClearHistoryInteractorTest {
    private HistoryDataAccessObject historyDAO;
    private ClearHistoryOutputBoundary mockPresenter;

    @BeforeEach
    void setUp() {
        try {
            historyDAO = new HistoryDataAccessObject("resources/serializables/historyQueries.ser");
            historyDAO.clear();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        mockPresenter = new ClearHistoryOutputBoundary() {
            @Override
            public void prepareSuccessView() {
                // Not part of the test
            }
        };
    }

    @Test
    void testHistoryCleared() {

        ClearHistoryInputBoundary clearHistoryInteractor = new ClearHistoryInteractor(mockPresenter, historyDAO);
        clearHistoryInteractor.execute();

        Assertions.assertEquals(historyDAO.getHistoryQueryList().size(), 0);
    }


    @Test
    void testThrowsException(){
        HistoryDataAccessObject exceptionHistoryDAO;
        try {
            exceptionHistoryDAO = new HistoryDataAccessObject("resources/serializables/historyQueries.ser"){
                @Override
                public void saveToFile() throws IOException {
                    throw new IOException();
                }
            };
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        ClearHistoryInputBoundary clearHistoryInteractor = new ClearHistoryInteractor(mockPresenter, exceptionHistoryDAO);

        Assertions.assertThrows(RuntimeException.class, () -> {
            clearHistoryInteractor.execute();
        });
    }

}
