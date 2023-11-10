package view_model;

import entity.Query;

import java.util.List;

public class HistoryViewState {
    public List<Query> getHistoryQueryList() {
        return historyQueryList;
    }

    public void setHistoryQueryList(List<Query> historyQueryList) {
        this.historyQueryList = historyQueryList;
    }

    List<Query> historyQueryList;
}
