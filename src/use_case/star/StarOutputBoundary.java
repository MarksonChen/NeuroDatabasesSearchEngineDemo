package use_case.star;

import entity.FetchedData;

public interface StarOutputBoundary {
    void prepareSuccessView(FetchedData outputData);
}
