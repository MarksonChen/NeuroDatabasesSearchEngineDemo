package view;

import interface_adapter.view_model.FrameManagerModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrameManager implements PropertyChangeListener {
    private final FrameManagerModel frameManagerModel;
    private final Map<String, JFrame> frames;

    public FrameManager(FrameManagerModel frameManagerModel, Map<String, JFrame> frames) {
        this.frameManagerModel = frameManagerModel;
        this.frames = frames;
        this.frameManagerModel.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("view")) {
            String frameName = (String) evt.getNewValue();
            JFrame frame = frames.get(frameName);
            frame.setLocation(MouseInfo.getPointerInfo().getLocation());
            frame.setVisible(true);
        }
    }
}
