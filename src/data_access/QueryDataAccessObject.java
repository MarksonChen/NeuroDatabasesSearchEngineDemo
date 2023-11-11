package data_access;

import entity.FetchedData;
import entity.Query;
import use_case.open_website.WebDataAccessInterface;
import use_case.query.QueryDataAccessInterface;

import java.io.*;
import java.util.*;

public class QueryDataAccessObject implements QueryDataAccessInterface {
    private final DatabaseDataAcecssInterface[] queryDAO;
    private final CacheDataAccessInterface dataCacheDAO;

    private int queryOneTotalResults;
    private int[] queryAllTotalResults;
    public QueryDataAccessObject(CacheDataAccessInterface dataCacheDAO, WebDataAccessInterface webDAO) {
        this.dataCacheDAO = dataCacheDAO;
        // This is a Facade design pattern, where each database's
        // responsibility are isolated into independent classes
        this.queryDAO = new DatabaseDataAcecssInterface[]{
                new NeuroMorphoDataAccessObject(webDAO),
                new NeuroElectroDataAccessObject(webDAO),
                new ModelDBDataAccessObject(webDAO)};
    }

    @Override
    public List<FetchedData>[] queryAll(Query query, int resultsPerPage, int page) throws IOException {
        List<FetchedData>[] queryResults = new List[Database.length];
        queryAllTotalResults = new int[Database.length];
        for (int i = 0; i < Database.length; i++) {
            queryResults[i] = queryOne(Database.get(i), query, resultsPerPage, page);
            queryAllTotalResults[i] = queryDAO[i].getTotalResults();
        }
        return queryResults;
    }

    public List<FetchedData> queryOne(Database database, Query query, int resultsPerPage, int page) throws IOException{
        if(dataCacheDAO.hasCache(database, query, page)) {
            return dataCacheDAO.getDataOnPage(database, page);
        }
        DatabaseDataAcecssInterface databaseDAO = queryDAO[Database.indexOf(database)];
        List<FetchedData> queryResults = databaseDAO.query(query, resultsPerPage, page);
        dataCacheDAO.cache(database, query, queryResults);
        queryOneTotalResults = databaseDAO.getTotalResults();
        return queryResults;
    }

    public int getQueryOneTotalResults() {
        return queryOneTotalResults;
    }

    public int[] getQueryAllTotalResults() {
        return queryAllTotalResults;
    }

    @Override
    public String[][] getEntryKeys() {
        String[][] entryKeys = new String[Database.length][];
        for (int i = 0; i < Database.length; i++) {
            entryKeys[i] = queryDAO[i].getEntryKeys();
        }
        return entryKeys;
    }

    @Override
    public FetchedData fillDetails(FetchedData data) throws IOException {
        // This method will only be called by instances of PreloadedDatabaseDataAccessObject
        PreloadedDatabaseDataAccessObject databaseDAO = (PreloadedDatabaseDataAccessObject) queryDAO[Database.indexOf(data.getDatabase())];
        data.setDetails(databaseDAO.getEntryDetail(data.getId()));
        dataCacheDAO.replaceData(data); // It replaces the data in-place using alias

//        dataCacheDAO.saveCache("resources/serializables/fetchedDataListArr.ser");
        // Uncomment the line above to save the data for InMemoryQueryDataAccessObject
        return data;
    }
}
