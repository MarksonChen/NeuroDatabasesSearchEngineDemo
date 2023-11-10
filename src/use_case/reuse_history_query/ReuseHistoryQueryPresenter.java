package use_case.reuse_history_query;

import entity.Query;
import view_model.*;

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
        historyViewModel.firePropertyChanged(HistoryViewModel.CLOSE);
        historyViewModel.firePropertyChanged(HistoryViewModel.REFRESH);
    }
}
