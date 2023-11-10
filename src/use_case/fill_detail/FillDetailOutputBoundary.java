package use_case.fill_detail;

import entity.FetchedData;

public interface FillDetailOutputBoundary {
    void prepareSuccessView(FetchedData fillDetailOutputData);

    void prepareFailView(String errorMessage);
}
