package view;

import interface_adapter.open_frame.OpenFrameController;
import interface_adapter.open_website.OpenWebsiteController;
import interface_adapter.view_model.HistoryViewModel;
import interface_adapter.view_model.MainFrameViewModel;
import interface_adapter.view_model.StarredViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

public class MenuBar extends JMenuBar {
    private final MainFrameViewModel mainFrameViewModel;
    private final OpenFrameController switchViewController;
    private final OpenWebsiteController openWebsiteController;
    private JMenu starredMenu;
    private JMenu historyMenu;
    private JMenu helpMenu;

    public MenuBar(MainFrameViewModel mainFrameViewModel, OpenFrameController openFrameController, OpenWebsiteController openWebsiteController) {
        this.mainFrameViewModel = mainFrameViewModel;
        this.switchViewController = openFrameController;
        this.openWebsiteController = openWebsiteController;

        starredMenu = new JMenu(MainFrameViewModel.STARRED_MENU_BUTTON_LABEL);
        starredMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openFrameController.execute(StarredViewModel.VIEW_NAME);
            }
        });
        add(starredMenu);

        historyMenu = new JMenu(MainFrameViewModel.HISTORY_MENU_BUTTON_LABEL);
        historyMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openFrameController.execute(HistoryViewModel.VIEW_NAME);
            }
        });
        add(historyMenu);

        helpMenu = new JMenu("Help");
        helpMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openWebsiteController.execute(MainFrameViewModel.HELP_REDIRECT_URL);
            }
        });
        add(helpMenu);
    }
}
