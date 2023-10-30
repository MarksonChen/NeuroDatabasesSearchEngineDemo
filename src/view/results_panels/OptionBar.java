package view.results_panels;

import javax.swing.*;
import java.awt.*;

public class OptionBar extends JPanel {
    private JPanel[] databasePanels;

    public OptionBar(String[] databaseNames) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        makePanels(databaseNames);
    }
    public void makePanels(String[] databaseNames) {
        // Remove all existing components (if any)
        removeAll();

        databasePanels = new JPanel[databaseNames.length];

        for (int i = 0; i < databaseNames.length; i++) {
            // Create a new panel for each database
            databasePanels[i] = new JPanel();
            databasePanels[i].setLayout(new BoxLayout(databasePanels[i], BoxLayout.Y_AXIS));

            JButton displayOptionsButton = new JButton("Display Options");
            // TODO: add displayOptionsButton.addActionListener(e -> {});
            // which switches view to the options frame, using SwitchViewController
            // and create the VIEW_NAME based on the databaseName.
            // There should be a method to create the VIEW_NAME based on the databaseName
            // used both when the option panels are initialized and here.

            JButton filterOptionsButton = new JButton("Filter Options");
            // filterOptionsButton.addActionListener(e -> {});

            databasePanels[i].add(displayOptionsButton);
            databasePanels[i].add(filterOptionsButton);
            databasePanels[i].setBorder(BorderFactory.createTitledBorder(databaseNames[i]));

            add(databasePanels[i]);
        }

        revalidate();
        repaint();
    }
}