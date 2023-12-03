package use_case.star;

import data_access.StarDataAccessObject;
import entity.FetchedData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StarInteractorTest {

    private StarDataAccessInterface starDAO;
    private FetchedData testData;
    private StarOutputBoundary mockPresenter;

    @BeforeEach
    void setUp() {
        try {
            starDAO = new StarDataAccessObject("resources/serializables/starredData.ser");
            starDAO.clear();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        testData = new FetchedData("Sample Title", "Sample ID", "http://sampleurl.com", null, new LinkedHashMap<>());


        mockPresenter = new StarOutputBoundary() {
            @Override
            public void prepareSuccessView(FetchedData outputData) {
                // Not part of the test
            }
        };
    }

    @Test
    void testStarred() {
        StarOutputBoundary mockPresenter = new StarOutputBoundary() {
            @Override
            public void prepareSuccessView(FetchedData outputData) {
                assertEquals(starDAO.getStarredDataList().get(0), testData);
            }
        };

        StarInputBoundary starInteractor = new StarInteractor(mockPresenter, starDAO);
        StarController starController = new StarController(starInteractor);

        starController.execute(testData);
    }

    @Test
    void testUnstarred() {
        StarInputBoundary starInteractor = new StarInteractor(mockPresenter, starDAO);
        StarController starController = new StarController(starInteractor);

        starController.execute(testData);
        starController.execute(testData);
        assertEquals(starDAO.getStarredDataList().size(), 0);
    }

    @Test
    void testThrowsException(){
        StarDataAccessObject exceptionStarDAO;
        try {
            exceptionStarDAO = new StarDataAccessObject("resources/serializables/starredData.ser"){
                @Override
                public void saveStarredData() throws IOException {
                    throw new IOException();
                }
            };
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        StarInputBoundary starInteractor = new StarInteractor(mockPresenter, exceptionStarDAO);

        Assertions.assertThrows(RuntimeException.class, () -> {
            starInteractor.execute(testData);
        });
    }
}




