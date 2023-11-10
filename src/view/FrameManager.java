package view;

import view_model.FrameManagerModel;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
        if (evt.getPropertyName().equals(FrameManagerModel.OPEN)) {
            String frameName = (String) evt.getNewValue();
            JFrame frame = frames.get(frameName);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.toFront();
        }
    }
}
