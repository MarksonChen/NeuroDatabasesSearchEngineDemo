package interface_adapter.view_model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class HistoryViewModel implements ObserverViewModel{
    public static final String VIEW_NAME = "History Frame";
    public static final String REFRESH = "Refresh";
    public static final String CLEAR_HISTORY = "Clear History";
    public static final String EMPTY_MESSAGE = "You currently have no query history.";

    private final HistoryViewState state = new HistoryViewState();

    public HistoryViewState getState() {
        return state;
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    public void firePropertyChanged(String propertyName) {
        support.firePropertyChange(propertyName, null, this.state);
    }
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
