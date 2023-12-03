package use_case.star;

import data_access.StarDataAccessObject;
import entity.FetchedData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StarControllerTest {
    private StarDataAccessInterface starDAO;

    @BeforeEach
    void setUp() {
        try {
            starDAO = new StarDataAccessObject("resources/serializables/starredData.ser");
            starDAO.clear();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void testStarAdded() {
        // Create a test instance of FetchedData
        FetchedData testData = new FetchedData("Sample Title", "Sample ID", "http://sampleurl.com", null, new LinkedHashMap<>());

        StarOutputBoundary mockPresenter = new StarOutputBoundary() {
            @Override
            public void prepareSuccessView(FetchedData outputData) {
                assertEquals(starDAO.getStarredDataList().get(0), testData);
                assertEquals(starDAO.getStarredDataList().size(), 1);
            }
        };

        StarInputBoundary starInteractor = new StarInteractor(mockPresenter, starDAO);
        StarController starController = new StarController(starInteractor);

        starController.execute(testData);
    }
}


