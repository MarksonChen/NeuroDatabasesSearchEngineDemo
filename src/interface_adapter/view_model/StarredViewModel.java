package interface_adapter.view_model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class StarredViewModel implements ObserverViewModel{
    public static final String VIEW_NAME = "Starred Frame";
    public static final String STAR = "STAR";
    public static final String REFRESH = "REFRESH";
    public static final String FILL_DETAIL = "FILL_DETAIL";
    public static final String EMPTY_MESSAGE = "You currently have no starred entries.";
    private final StarredViewState state = new StarredViewState();
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    public void firePropertyChanged(String propertyName) {
        support.firePropertyChange(propertyName, null, this.state);
    }
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public StarredViewState getState() {
        return state;
    }
}
