package use_case.query.query_one;

import data_access.Database;
import view_model.HistoryViewModel;
import view_model.ScrollResultsPanelModel;
import view_model.ScrollResultsPanelState;
import view_model.SearchViewModel;

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
        for (int i = 0; i < Database.length; i++) {
            resultsPanelModelsMap.put(Database.get(i), resultsPanelModels[i]);
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
