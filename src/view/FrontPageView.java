package view;

import interface_adapter.open_website.OpenWebsiteController;
import interface_adapter.switch_view.SwitchViewController;
import interface_adapter.view_model.SearchViewModel;
import interface_adapter.view_model.FrontPageViewModel;

import javax.swing.*;
import java.awt.*;
import java.net.URI;

public class FrontPageView extends JPanel {

    private final FrontPageViewModel frontPageViewModel;
    private final SwitchViewController switchViewController;

    private final JButton appTitle, searchButton;


    public FrontPageView(FrontPageViewModel frontPageViewModel, SwitchViewController switchViewController, OpenWebsiteController openWebsiteController) {
        this.frontPageViewModel = frontPageViewModel;
        this.switchViewController = switchViewController;

        appTitle        = new ImageButton(FrontPageViewModel.APP_TITLE_IMAGE_PATH, 0.35);
        searchButton = new ImageButton(FrontPageViewModel.SEARCH_BUTTON_IMAGE_PATH, 0.35);

        appTitle.addActionListener(evt -> {
            try {
                openWebsiteController.execute("https://github.com/MarksonChen/NeuroDatabasesSearchApp");
            } catch (Exception ignored) {
                // It's fine if it can't be opened
            }
        });

        searchButton.addActionListener(evt -> {
            switchViewController.execute(SearchViewModel.VIEW_NAME);
        });

        JPanel searchPanel = new JPanel();
        searchPanel.add(searchButton);

        searchPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        appTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.add(Box.createVerticalStrut(30));
        this.add(appTitle);
        this.add(Box.createVerticalStrut(20));
        this.add(searchPanel);
        this.setBackground(Color.white);
        searchPanel.setBackground(Color.white);
    }
}
