package use_case.star;

import data_access.Database;
import entity.FetchedData;

import java.io.IOException;

public class StarInteractor implements StarInputBoundary{
    private final StarOutputBoundary starPresenter;
    private final StarDataAccessInterface starDAO;

    public StarInteractor(StarOutputBoundary starPresenter, StarDataAccessInterface starDAO) {
        this.starPresenter = starPresenter;
        this.starDAO = starDAO;
    }

    @Override
    public void execute(FetchedData inputData) {
        starDAO.toggleStar(inputData);
        starPresenter.prepareSuccessView(inputData);
        try {
            starDAO.saveStarredData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
