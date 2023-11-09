package use_case.query.query_all;

public interface QueryAllOutputBoundary {
    void prepareSuccessView(QueryAllOutputData outputData);

    void prepareFailView(String s);
}
