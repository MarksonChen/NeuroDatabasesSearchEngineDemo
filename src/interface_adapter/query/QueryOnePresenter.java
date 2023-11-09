package interface_adapter.query;

import data_access.Database;
import interface_adapter.view_model.HistoryViewModel;
import interface_adapter.view_model.ScrollResultsPanelModel;
import interface_adapter.view_model.ScrollResultsPanelState;
import interface_adapter.view_model.SearchViewModel;
import use_case.query.query_one.QueryOneOutputBoundary;
import use_case.query.query_one.QueryOneOutputData;

import java.util.HashMap;
import java.util.Map;

public class QueryOnePresenter implements QueryOneOutputBoundary {
    private final SearchViewModel searchViewModel;
    private final HistoryViewModel historyViewModel;
    private final Map<Database, ScrollResultsPanelModel> resultsPanelModelsMap;

    public QueryOnePresenter(SearchViewModel searchViewModel, ScrollResultsPanelModel[] resultsPanelModels, HistoryViewModel historyViewModel) {
        this.searchViewModel = searchViewModel;
        this.historyViewModel = historyViewModel;
        this.resultsPanelModelsMap = new HashMap<>();
        Database[] databases = Database.values();
        for (int i = 0; i < databases.length; i++) {
            resultsPanelModelsMap.put(databases[i], resultsPanelModels[i]);
        }
    }

    @Override
    public void prepareSuccessView(QueryOneOutputData outputData) {
        ScrollResultsPanelModel model = resultsPanelModelsMap.get(outputData.getDatabase());
        ScrollResultsPanelState state = model.getState();

        state.setFetchedDataList(outputData.getFetchedData());
        state.setTotalResults(outputData.getQueryOneTotalResults());
        state.setCurrentPage(outputData.getPage());
        state.setLastQuery(outputData.getQueryKeyword());
        state.setDataIsStarredList(outputData.getDataStarredStateList());

        model.firePropertyChanged(ScrollResultsPanelModel.REFRESH_ALL);

        historyViewModel.getState().setHistoryQueryList(outputData.getHistoryQueryList());
        historyViewModel.firePropertyChanged(historyViewModel.REFRESH);
    }

    @Override
    public void prepareFailView(String errorMessage) {
        searchViewModel.getState().setErrorMessage(errorMessage);
        searchViewModel.firePropertyChanged(SearchViewModel.ERROR);
    }
}
