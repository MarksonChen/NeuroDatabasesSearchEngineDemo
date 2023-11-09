package use_case.query.query_all;

public class QueryAllInputData {
    private final String keywords;
    private final int resultsPerPage;

    public QueryAllInputData(String keywords, int resultsPerPage) {
        this.keywords = keywords;
        this.resultsPerPage = resultsPerPage;
    }

    public String getKeywords() {
        return keywords;
    }

    public int getResultsPerPage() {
        return resultsPerPage;
    }
}
