package view;

import interface_adapter.view_model.MainFrameViewModel;
import interface_adapter.view_model.MainFrameViewState;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MainFrame extends JFrame implements PropertyChangeListener {
    private final MainFrameViewModel mainFrameViewModel;

    public MainFrame(MainFrameViewModel mainFrameViewModel) {
        super(MainFrameViewModel.FRAME_TITLE);
        this.mainFrameViewModel = mainFrameViewModel;
        mainFrameViewModel.addPropertyChangeListener(this);

        // We can move views-related things here
        // But by SRP, we choose to keep everything related to "hooking everything together"
        // in the Main method.
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        MainFrameViewState state = mainFrameViewModel.getState();
        switch (evt.getPropertyName()){
            case MainFrameViewModel.ERROR -> JOptionPane.showMessageDialog(this, state.getErrorMessage());
        }
    }

    public void start() {
        setPreferredSize(new Dimension(1200, 800));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
