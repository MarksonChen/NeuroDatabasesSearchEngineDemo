package view;

import interface_adapter.open_frame.OpenFrameController;
import interface_adapter.switch_view.SwitchViewController;
import interface_adapter.view_model.HistoryViewModel;
import interface_adapter.view_model.MenuBarViewModel;
import interface_adapter.view_model.StarredViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MenuBar extends JMenuBar {
    private final MenuBarViewModel menuBarViewModel;
    private final OpenFrameController switchViewController;
    private JMenu starredMenu;
    private JMenu historyMenu;
    private JMenu helpMenu;

    public MenuBar(MenuBarViewModel menuBarViewModel, OpenFrameController openFrameController) {
        this.menuBarViewModel = menuBarViewModel;
        this.switchViewController = openFrameController;
        // Starred Menu
        starredMenu = new JMenu("Starred");
        starredMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openFrameController.execute(StarredViewModel.VIEW_NAME);
            }
        });
        add(starredMenu);

        // History Menu
        historyMenu = new JMenu("History");
        historyMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openFrameController.execute(HistoryViewModel.VIEW_NAME);
            }
        });
        add(historyMenu);

        // Help Menu
        helpMenu = new JMenu("Help");
        helpMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://github.com/MarksonChen/NeuroDatabasesSearchApp"));
                } catch (Exception ignored) {
                    // It is fine if it can't be opened
                }
            }
        });
        add(helpMenu);
    }
}
