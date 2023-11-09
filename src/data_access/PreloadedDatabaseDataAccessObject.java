package data_access;

import entity.FetchedData;
import entity.Query;

import java.io.IOException;
import java.util.*;

abstract public class PreloadedDatabaseDataAccessObject implements DatabaseDataAcecssInterface{
    private static boolean loaded = false;
    // This is marked "protected" because ideally, it should be accessible
    // only by the subclass, but there isn't a "subclass only" access
    // modifier in Java.
    private int totalResults;
    private Query lastQuery;
    private List<String> queryResults;
    abstract protected void load() throws IOException;
    // it is marked "protected" because ideally, this should be a method
    // to be implemented and only used by the implementing subclass

    public List<FetchedData> query(Database database, Map<String, String> preloadedEntries, Query query, int resultsPerPage, int page) throws IOException {
        if (!loaded) load();
        if (!query.equals(lastQuery)){
            queryResults = DatabaseDataAcecssInterface.getRelevantItems(preloadedEntries.keySet(), query.getKeywords());
            totalResults = queryResults.size();
            lastQuery = query;
        }
        List<FetchedData> fetchedDataList = new ArrayList<>();
        for (int i = resultsPerPage * (page-1); i < resultsPerPage * page && i < totalResults; i++){
            String entry = queryResults.get(i);
            String id = preloadedEntries.get(entry);
            fetchedDataList.add(new FetchedData(entry, id, getURL(id), database));
        }

        return fetchedDataList;
    }

    @Override
    public int getTotalResults() {
        return totalResults;
    }

    abstract public LinkedHashMap<String, String> getEntryDetail(String id) throws IOException;
}
