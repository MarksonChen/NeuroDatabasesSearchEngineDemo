package view_model;

import java.beans.PropertyChangeListener;

public interface ObserverViewModel {
    public abstract void firePropertyChanged(String propertyName);
    public abstract void addPropertyChangeListener(PropertyChangeListener listener);
}
