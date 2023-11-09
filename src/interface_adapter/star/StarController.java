package interface_adapter.star;

import entity.FetchedData;
import use_case.star.StarInputBoundary;

public class StarController {
    private final StarInputBoundary starInteractor;

    public StarController(StarInputBoundary starInteractor) {
        this.starInteractor = starInteractor;
    }

    public void execute(FetchedData data) {
        starInteractor.execute(data);
    }
}
