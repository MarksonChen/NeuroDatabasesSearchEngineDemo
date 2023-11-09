package data_access;

import entity.FetchedData;
import entity.Query;
import use_case.query.QueryDataAccessInterface;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryQueryDataAccessObject implements QueryDataAccessInterface {
    private final CacheDataAccessInterface dataCacheDAO;
    // Note: Always use InMemoryDataAccessObject unless you are specifically
    // testing QueryDataAccessObject! (Reduces the load for the open database servers)
    // This is for test purpose only, so it assumes the Databases are
    // NeuroMorpho, NeuroElectro, and ModelDB and directly reads the FetchedData
    // from stored serialization files.
    private List<FetchedData>[] fetchedDataListArr; // Read from ser file; same order as Database.values()
    private List<FetchedData>[] unfilledDataListArr; // resembles actual data gotten, where NeuroElectro & ModelDB's data all have no details intially
    private List<String>[] dataIDArr;
    private Database lastQueryDatabase;
    public InMemoryQueryDataAccessObject(CacheDataAccessInterface dataCacheDAO, String serializableFilePath) throws IOException, ClassNotFoundException {
        this.dataCacheDAO = dataCacheDAO;

        // Load fetchedDataListArr
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(serializableFilePath));
        fetchedDataListArr = (List<FetchedData>[]) ois.readObject();

        // Load dataIDArr
        dataIDArr = new List[Database.length];
        for (int i = 0; i < Database.length; i++) {
            dataIDArr[i] = fetchedDataListArr[i].stream().map(FetchedData::getId).collect(Collectors.toList());
        }

        // Load unfilledDataListArr
        unfilledDataListArr = new List[Database.length];
        unfilledDataListArr[0] = fetchedDataListArr[0]; // NeuroMorpho always returns filled Data
        for (int i = 1; i < Database.length; i++) {                  // NeuroElectro & ModelDB always return unfilled Data
            unfilledDataListArr[i] = new ArrayList<>();
            for (int j = 0; j < fetchedDataListArr[i].size(); j++) {
                FetchedData data = fetchedDataListArr[i].get(j);
                unfilledDataListArr[i].add(new FetchedData(data.getTitle(), data.getId(), data.getURL(), data.getDatabase()));
            }
        }
    }

    @Override
    public List<FetchedData> queryOne(Database database, Query query, int resultsPerPage, int page) throws IOException {
        lastQueryDatabase = database;
        if(dataCacheDAO.hasCache(database, query, page)) {
            return dataCacheDAO.getDataOnPage(database, page);
        }
        List<FetchedData> fetchedDataList = unfilledDataListArr[Database.indexOf(database)];
        return fetchedDataList.subList((page-1) * resultsPerPage, Math.min(fetchedDataList.size(),(page) * resultsPerPage));
    }

    @Override
    public List<FetchedData>[] queryAll(Query query, int resultsPerPage, int page) throws IOException {
        List<FetchedData>[] queryResult = new List[Database.length];
        for (int i = 0; i < Database.length; i++) {
            queryResult[i] = unfilledDataListArr[i].subList((page-1) * resultsPerPage, (page) * resultsPerPage);
        }
        return queryResult;
    }

    @Override
    public FetchedData fillDetails(FetchedData data) throws IOException {
        int arrIndex = Database.indexOf(data.getDatabase());
        int listIndex = dataIDArr[arrIndex].indexOf(data.getId());
        FetchedData filledData = fetchedDataListArr[arrIndex].get(listIndex);
        data.setDetails(filledData.getDetails());
        return data;
    }

    @Override
    public int getQueryOneTotalResults() {
        return unfilledDataListArr[Database.indexOf(lastQueryDatabase)].size();
    }

    @Override
    public int[] getQueryAllTotalResults() {
        return new int[]{unfilledDataListArr[0].size(), unfilledDataListArr[1].size(), unfilledDataListArr[2].size()};
    }

    @Override
    public String[][] getEntryKeys() {
        return new String[][]{
                new NeuroMorphoDataAccessObject(null).getEntryKeys(),
                new NeuroElectroDataAccessObject(null).getEntryKeys(),
                new ModelDBDataAccessObject(null).getEntryKeys()};
    }
}
