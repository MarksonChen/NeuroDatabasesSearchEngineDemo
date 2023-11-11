package view_model;

import java.awt.*;
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
    public static final String QUERY_BUTTON_LABEL = "Query for details";
    public static final Color BACKGROUND_COLOR = Color.white;
    public static final String SEARCH_BUTTON_IMAGE_PATH = "/icons/magnifier.png";
    public static final String SEARCH_FIELD_HINT = "Search... (try \"hippocampus\", \"retina ganglion\", or \"neocortex\")";
    public static final double SEARCH_BUTTON_IMAGE_SCALE = 0.04;
    public static final String PREV_BUTTON_LABEL = "<Previous";
    public static final String NEXT_BUTTON_LABEL = "Next>";
    private final SearchViewState state = new SearchViewState();

    public SearchViewState getState() {
        return state;
    }

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
