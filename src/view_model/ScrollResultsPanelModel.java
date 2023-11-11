package view_model;

import data_access.Database;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ScrollResultsPanelModel implements ObserverViewModel{
    public static final String REFRESH_ALL = "Refresh All";
    public static final String REFRESH_DATA_INFO_PANEL = "Refresh Data Info Panel";
    public static final String REFRESH_STAR_STATES = "Refresh Star States";
    private final Database database;
    private final ScrollResultsPanelState state = new ScrollResultsPanelState();

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public ScrollResultsPanelModel(Database database) {
        this.database = database;
    }

    @Override
    public void firePropertyChanged(String propertyName) {
        support.firePropertyChange(propertyName, null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public ScrollResultsPanelState getState() {
        return state;
    }

    public Database getDatabase() {
        return database;
    }
}
