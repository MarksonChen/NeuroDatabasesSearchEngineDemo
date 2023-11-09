package use_case.query.query_one;

import data_access.Database;

public class QueryOneInputData {
    private final Database database;
    private final String keywords;
    private final int resultsPerPage;
    private final int page;

    public QueryOneInputData(Database database, String keywords, int resultsPerPage, int page) {

        this.database = database;
        this.keywords = keywords;
        this.resultsPerPage = resultsPerPage;
        this.page = page;
    }

    public Database getDatabase() {
        return database;
    }

    public String getKeywords() {
        return keywords;
    }

    public int getResultsPerPage() {
        return resultsPerPage;
    }

    public int getPage() {
        return page;
    }
}
