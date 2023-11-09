package interface_adapter.reuse_history_query;

import entity.Query;
import interface_adapter.view_model.*;
import use_case.reuse_history_query.ReuseHistoryQueryOutputBoundary;
import view.SearchView;

import java.util.List;

public class ReuseHistoryQueryPresenter implements ReuseHistoryQueryOutputBoundary {
    private final FrameManagerModel frameManagerModel;
    private final ViewManagerModel viewManagerModel;
    private final HistoryViewModel historyViewModel;
    private final SearchViewModel searchViewModel;

    public ReuseHistoryQueryPresenter(FrameManagerModel frameManagerModel, ViewManagerModel viewManagerModel, HistoryViewModel historyViewModel, SearchViewModel searchViewModel) {
        this.frameManagerModel = frameManagerModel;
        this.viewManagerModel = viewManagerModel;
        this.historyViewModel = historyViewModel;
        this.searchViewModel = searchViewModel;
    }

    @Override
    public void enterQuery(Query query) {
        frameManagerModel.setActiveView(MainFrameViewModel.VIEW_NAME);
        frameManagerModel.firePropertyChanged(FrameManagerModel.OPEN); // sets main frame on top
        viewManagerModel.setActiveView(SearchViewModel.VIEW_NAME);

        searchViewModel.getState().setSearchFieldText(query.getKeywords());
        searchViewModel.firePropertyChanged(SearchViewModel.QUERY_ENTERED);
        List<Query> historyQueryList = historyViewModel.getState().getHistoryQueryList();
        if (historyQueryList.contains(query)){
            historyQueryList.remove(query);  // "Bubbles" the Query onto the top
        }
        historyQueryList.add(query);
    }
}
