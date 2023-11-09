package interface_adapter.view_model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class SearchViewModel implements ObserverViewModel{
    public static final String VIEW_NAME = "Search View";
    public static final String ALL_DATABASES = "All Databases";
    public static final int DEFAULT_RESULTS_PER_PAGE = 5;
    public static final String RESULTS_PANEL_SWITCHED = "Results Panel Switched";
    public static final String QUERY_ENTERED = "Query Entered";
    public static final String REFRESH_OPTION_BAR = "Refresh Option Bar";
    public static final String ERROR = "Error";
    private final SearchViewState state = new SearchViewState();

    public SearchViewState getState() {
        return state;
    }

//    public void setState(SearchViewState state) {
//        this.state = state;
//    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    @Override
    public void firePropertyChanged(String propertyName) {
        support.firePropertyChange(propertyName, null, this.state);
    }
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
