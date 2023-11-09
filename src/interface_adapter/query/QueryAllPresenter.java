package interface_adapter.query;

import data_access.Database;
import entity.FetchedData;
import interface_adapter.view_model.HistoryViewModel;
import interface_adapter.view_model.ScrollResultsPanelModel;
import interface_adapter.view_model.ScrollResultsPanelState;
import interface_adapter.view_model.SearchViewModel;
import use_case.query.query_all.QueryAllOutputBoundary;
import use_case.query.query_all.QueryAllOutputData;

import java.util.List;

public class QueryAllPresenter implements QueryAllOutputBoundary {
    private final SearchViewModel searchViewModel;
    private final ScrollResultsPanelModel[] resultsPanelModels;
    private final HistoryViewModel historyViewModel;

    public QueryAllPresenter(SearchViewModel searchViewModel, ScrollResultsPanelModel[] resultsPanelModels, HistoryViewModel historyViewModel) {
        this.searchViewModel = searchViewModel;
        this.resultsPanelModels = resultsPanelModels;
        this.historyViewModel = historyViewModel;
    }

    @Override
    public void prepareSuccessView(QueryAllOutputData outputData) {
        List<FetchedData>[] fetchedDataListArr = outputData.getFetchedData();
        List<Boolean>[] starredStateListArr = outputData.getStarredStateListArr();
        int[] totalResultsList = outputData.getQueryAllTotalResults();
        for (int i = 0; i < Database.values().length; i++) {
            ScrollResultsPanelState state = resultsPanelModels[i].getState();
            state.setFetchedDataList(fetchedDataListArr[i]);
            state.setTotalResults(totalResultsList[i]);
            state.setCurrentPage(1);
            state.setLastQuery(outputData.getQueryKeywords());
            state.setDataIsStarredList(starredStateListArr[i]);
            resultsPanelModels[i].firePropertyChanged(ScrollResultsPanelModel.REFRESH_ALL);
        }

        historyViewModel.getState().setHistoryQueryList(outputData.getHistoryQueryList());
        historyViewModel.firePropertyChanged(HistoryViewModel.REFRESH);
    }

    @Override
    public void prepareFailView(String errorMessage) {
        searchViewModel.getState().setErrorMessage(errorMessage);
        searchViewModel.firePropertyChanged(SearchViewModel.ERROR);
    }
}
