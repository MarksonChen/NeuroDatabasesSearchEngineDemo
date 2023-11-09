package interface_adapter.view_model;

import entity.FetchedData;

import java.util.ArrayList;
import java.util.List;

public class StarredViewState {
    private List<FetchedData> starredDataList = new ArrayList<>();

    public List<FetchedData> getStarredDataList() {
        return starredDataList;
    }

    public void setStarredDataList(List<FetchedData> starredDataList) {
        this.starredDataList = starredDataList;
    }
}
