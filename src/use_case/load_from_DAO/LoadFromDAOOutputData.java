package use_case.load_from_DAO;

import entity.FetchedData;
import entity.Query;

import java.util.List;

public class LoadFromDAOOutputData {
    private final String[][] entryKeys;
    private final List<FetchedData> starredDataList;
    private final List<Query> historyQueryList;

    public LoadFromDAOOutputData(String[][] entryKeys, List<FetchedData> starredDataList, List<Query> historyQueryList) {
        this.entryKeys = entryKeys;
        this.starredDataList = starredDataList;
        this.historyQueryList = historyQueryList;
    }

    public String[][] getEntryKeys() {
        return entryKeys;
    }

    public List<FetchedData> getStarredDataList() {
        return starredDataList;
    }

    public List<Query> getHistoryQueryList() {
        return historyQueryList;
    }
}
