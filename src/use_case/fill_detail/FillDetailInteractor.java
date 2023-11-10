package use_case.fill_detail;

import entity.FetchedData;
import use_case.query.QueryDataAccessInterface;
import use_case.star.StarDataAccessInterface;

import java.io.IOException;

public class FillDetailInteractor implements FillDetailInputBoundary{
    private final FillDetailOutputBoundary fillDetailPresenter;
    private final QueryDataAccessInterface queryDAO;
    private final StarDataAccessInterface starDAO;

    public FillDetailInteractor(FillDetailOutputBoundary fillDetailPresenter, QueryDataAccessInterface queryDAO, StarDataAccessInterface starDAO) {
        this.fillDetailPresenter = fillDetailPresenter;
        this.queryDAO = queryDAO;
        this.starDAO = starDAO;
    }

    @Override
    public void execute(FetchedData inputData) {
        try {
            FetchedData updatedData = queryDAO.fillDetails(inputData);
            fillDetailPresenter.prepareSuccessView(updatedData);
        } catch (IOException e) {
            fillDetailPresenter.prepareFailView("Failed filling details for entry \"" + inputData.getTitle() + "\"");
        }
        if (starDAO.getStarredDataList().contains(inputData)){
            // If starDAO also contains the same fetched data,
            // then its data will also be filled of details because of alias.
            // Then, we must keep the persistent file the same as the in-memory state.
            try {
                starDAO.saveStarredData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
