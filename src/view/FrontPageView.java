package view;

import use_case.open_website.OpenWebsiteController;
import use_case.switch_view.SwitchViewController;
import view_model.MainFrameViewModel;
import view_model.SearchViewModel;
import view_model.FrontPageViewModel;

import javax.swing.*;
import java.awt.*;

public class FrontPageView extends JPanel {
    private final JButton appTitle, searchButton;


    public FrontPageView(FrontPageViewModel frontPageViewModel, SwitchViewController switchViewController, OpenWebsiteController openWebsiteController) {
        appTitle        = new ImageButton(FrontPageViewModel.TITLE_BUTTON_IMAGE_PATH, FrontPageViewModel.TITLE_BUTTON_IMAGE_SCALE);
        searchButton = new ImageButton(FrontPageViewModel.SEARCH_BUTTON_IMAGE_PATH, FrontPageViewModel.SEARCH_BUTTON_IMAGE_SCALE);

        appTitle.addActionListener(evt ->
            openWebsiteController.execute(FrontPageViewModel.TITLE_BUTTON_REDIRECT_URL)
        );

        searchButton.addActionListener(evt -> {
            switchViewController.execute(SearchViewModel.VIEW_NAME);
        });

        searchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        appTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.add(Box.createVerticalStrut(30));
        this.add(appTitle);
        this.add(Box.createVerticalStrut(20));
        this.add(searchButton);
        this.setBackground(MainFrameViewModel.BACKGROUND_COLOR);
    }
}
