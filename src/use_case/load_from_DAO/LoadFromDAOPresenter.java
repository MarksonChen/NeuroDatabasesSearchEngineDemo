package use_case.load_from_DAO;

import data_access.Database;
import entity.FetchedData;
import entity.Query;
import view_model.*;

import java.util.*;

public class LoadFromDAOPresenter implements LoadFromDAOOutputBoundary {
    private final ScrollResultsPanelModel[] resultsPanelModels;
    private final SearchViewModel searchViewModel;
    private final StarredViewModel starredViewModel;
    private final HistoryViewModel historyViewModel;

    public LoadFromDAOPresenter(ScrollResultsPanelModel[] resultsPanelModels, SearchViewModel searchViewModel, StarredViewModel starredViewModel, HistoryViewModel historyViewModel) {
        this.resultsPanelModels = resultsPanelModels;
        this.searchViewModel = searchViewModel;
        this.starredViewModel = starredViewModel;
        this.historyViewModel = historyViewModel;
    }

    @Override
    public void updateStatesWithData(LoadFromDAOOutputData outputData){
        String[][] entryKeysArr = outputData.getEntryKeys();
        SearchViewState searchViewState = searchViewModel.getState();
        searchViewState.setDetailEntryDisplayed(createDetailEntryDisplayedMap(entryKeysArr));
        searchViewModel.firePropertyChanged(SearchViewModel.REFRESH_OPTION_BAR);
        for (int i = 0; i < Database.length; i++) {
            resultsPanelModels[i].firePropertyChanged(ScrollResultsPanelModel.REFRESH_DATA_INFO_PANEL);
        }

        List<FetchedData> starredDataList = outputData.getStarredDataList();
        starredViewModel.getState().setStarredDataList(new ArrayList<>(starredDataList));
        starredViewModel.firePropertyChanged(StarredViewModel.REFRESH);
        // Note that here we must create another List to prevent alias

        List<Query> historyQueryList = outputData.getHistoryQueryList();
        historyViewModel.getState().setHistoryQueryList(new ArrayList<>(historyQueryList));
        historyViewModel.firePropertyChanged(HistoryViewModel.REFRESH);
    }

    private LinkedHashMap<String, Boolean>[] createDetailEntryDisplayedMap(String[][] entryKeysArr) {
        LinkedHashMap<String, Boolean>[] mapArr = new LinkedHashMap[Database.length];
        for (int i = 0; i < Database.length; i++) {
            mapArr[i] = new LinkedHashMap<>();
            for (int j = 0; j < entryKeysArr[i].length; j++) {
                mapArr[i].put(entryKeysArr[i][j], true);
            }
        }
        return mapArr;
    }

//    private boolean[][] mapToTrue(String[][] entryKeysArr) {
//        boolean[][] arr = new boolean[entryKeysArr.length][];
//        for (int i = 0; i < entryKeysArr.length; i++) {
////            arr[i] = Arrays.stream(entryKeysArr[i]).map(s -> true).toArray(Boolean[]::new);
//            // This cool stream code unfortunately does not work because there is no way to collect to boolean[]
//            arr[i] = new boolean[entryKeysArr[i].length];
//            Arrays.fill(arr[i], true);
//        }
//        return arr;
//    }
}
