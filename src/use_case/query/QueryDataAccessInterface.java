package use_case.query;

import data_access.Database;
import entity.FetchedData;
import entity.Query;

import java.io.IOException;
import java.util.List;

public interface QueryDataAccessInterface {
    List<FetchedData> queryOne(Database database, Query query, int resultsPerPage, int page) throws IOException;
    List<FetchedData>[] queryAll(Query query, int resultsPerPage, int page) throws IOException;
    FetchedData fillDetails(FetchedData data) throws IOException;
    int getQueryOneTotalResults();
    int[] getQueryAllTotalResults();
    String[][] getEntryKeys();
}
