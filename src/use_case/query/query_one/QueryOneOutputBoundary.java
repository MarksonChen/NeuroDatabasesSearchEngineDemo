package use_case.query.query_one;

public interface QueryOneOutputBoundary {
    void prepareSuccessView(QueryOneOutputData outputData);

    void prepareFailView(String s);
}
