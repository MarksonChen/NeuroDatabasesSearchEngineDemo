package view;

import interface_adapter.switch_view.SwitchViewController;
import interface_adapter.view_model.CellularDBSearchViewModel;
import interface_adapter.view_model.DatasetDBSearchViewModel;
import interface_adapter.view_model.FrontPageViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

public class FrontPageView extends JPanel {

    private final FrontPageViewModel frontPageViewModel;
    private final SwitchViewController switchViewController;

    private final JButton appTitle, datasetSearch, cellularSearch;


    public FrontPageView(FrontPageViewModel frontPageViewModel, SwitchViewController switchViewController) {
        this.frontPageViewModel = frontPageViewModel;
        this.switchViewController = switchViewController;

        appTitle        = new ImageButton(FrontPageViewModel.APP_TITLE_IMAGE_PATH, 0.3);
        cellularSearch  = new ImageButton(FrontPageViewModel.CELLULAR_SEARCH_IMAGE_PATH, 0.35);
        datasetSearch   = new ImageButton(FrontPageViewModel.DATASET_SEARCH_IMAGE_PATH, 0.35);

        appTitle.addActionListener(evt -> {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/MarksonChen/NeuroDatabasesSearchApp"));
            } catch (Exception ignored) {
                // It's fine if it can't be opened
            }
        });

        datasetSearch.addActionListener(evt -> {
//            switchViewController.execute(DatasetDBSearchViewModel.VIEW_NAME);
            // Not yet implemented
        });

        cellularSearch.addActionListener(evt -> {
            switchViewController.execute(CellularDBSearchViewModel.VIEW_NAME);
        });

        JPanel searchPanel = new JPanel();
        searchPanel.add(datasetSearch);
        searchPanel.add(cellularSearch);

        searchPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        appTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.add(Box.createVerticalStrut(60));
        this.add(appTitle);
        this.add(Box.createVerticalStrut(20));
        this.add(searchPanel);
        this.setBackground(Color.white);
        searchPanel.setBackground(Color.white);
    }
}
