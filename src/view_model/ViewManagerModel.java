package view_model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ViewManagerModel implements ObserverViewModel{

    private String activeViewName;

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public String getActiveView() {
        return activeViewName;
    }

    public void setActiveView(String activeView) {
        this.activeViewName = activeView;
    }

    public void firePropertyChanged() {
        support.firePropertyChange("view", null, this.activeViewName);
    }

    @Override
    public void firePropertyChanged(String propertyName) { }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
