package view_model;

import entity.FetchedData;

import java.util.ArrayList;
import java.util.List;

public class ScrollResultsPanelState {
    private List<FetchedData> fetchedDataList = new ArrayList<>();
    private List<Boolean> dataIsStarredList = new ArrayList<>();
    private int totalResults;
    private int resultsPerPage = SearchViewModel.DEFAULT_RESULTS_PER_PAGE;
    private String lastQuery;
    private int currentPage;

    public List<FetchedData> getFetchedDataList() {
        return fetchedDataList;
    }

    public void setFetchedDataList(List<FetchedData> fetchedDataList) {
        this.fetchedDataList = fetchedDataList;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getResultsPerPage() {
        return resultsPerPage;
    }

    public void setResultsPerPage(int resultsPerPage) {
        this.resultsPerPage = resultsPerPage;
    }

    public String getLastQueryKeywords() {
        return lastQuery;
    }

    public void setLastQuery(String lastQuery) {
        this.lastQuery = lastQuery;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<Boolean> getDataIsStarredList() {
        return dataIsStarredList;
    }

    public void setDataIsStarredList(List<Boolean> dataIsStarredList) {
        this.dataIsStarredList = dataIsStarredList;
    }

    public void toggleStarOfData(FetchedData data) {
        int index = fetchedDataList.indexOf(data);
        dataIsStarredList.set(index, !dataIsStarredList.get(index));
    }
}
