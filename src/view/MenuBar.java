package view;

import use_case.open_frame.OpenFrameController;
import use_case.open_website.OpenWebsiteController;
import view_model.HistoryViewModel;
import view_model.MainFrameViewModel;
import view_model.StarredViewModel;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuBar extends JMenuBar {

    public MenuBar(OpenFrameController openFrameController, OpenWebsiteController openWebsiteController) {
        JMenu starredMenu = new JMenu(MainFrameViewModel.STARRED_MENU_BUTTON_LABEL);
        starredMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openFrameController.execute(StarredViewModel.VIEW_NAME);
            }
        });
        add(starredMenu);

        JMenu historyMenu = new JMenu(MainFrameViewModel.HISTORY_MENU_BUTTON_LABEL);
        historyMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openFrameController.execute(HistoryViewModel.VIEW_NAME);
            }
        });
        add(historyMenu);

        JMenu helpMenu = new JMenu("Help");
        helpMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openWebsiteController.execute(MainFrameViewModel.HELP_REDIRECT_URL);
            }
        });
        add(helpMenu);
    }
}
