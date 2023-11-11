package view_model;

import java.beans.PropertyChangeListener;

public interface ObserverViewModel {
    void firePropertyChanged(String propertyName);
    void addPropertyChangeListener(PropertyChangeListener listener);
}
