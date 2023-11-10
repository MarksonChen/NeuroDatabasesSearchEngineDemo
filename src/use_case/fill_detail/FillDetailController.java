package use_case.fill_detail;

import entity.FetchedData;
import use_case.fill_detail.FillDetailInputBoundary;

public class FillDetailController {
    private final FillDetailInputBoundary fillDetailInteractor;

    public FillDetailController(FillDetailInputBoundary fillDetailInteractor) {
        this.fillDetailInteractor = fillDetailInteractor;
    }

    public void execute(FetchedData data) {
        fillDetailInteractor.execute(data);
    }
}
