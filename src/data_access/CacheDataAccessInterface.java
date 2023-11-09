package data_access;

import entity.FetchedData;
import entity.Query;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

public interface CacheDataAccessInterface {
    boolean hasCache(Database database, Query query, int page);

    void cache(Database database, Query query, List<FetchedData> fetchedDataList);

    void replaceData(FetchedData fetchedData);

    List<FetchedData> getDataOnPage(Database database, int page);

    List<FetchedData>[] getCachedData();
    void saveCache(String pathName) throws IOException;
}
