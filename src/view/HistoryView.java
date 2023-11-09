package view;

import entity.Query;
import interface_adapter.clear_history.ClearHistoryController;
import interface_adapter.reuse_history_query.ReuseHistoryQueryController;
import interface_adapter.switch_view.SwitchViewController;
import interface_adapter.view_model.HistoryViewModel;
import interface_adapter.view_model.SearchViewModel;
import interface_adapter.view_model.StarredViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class HistoryView extends JFrame implements PropertyChangeListener {
    private static final String CLEAR_HISTORY_MENU_BUTTON_LABEL = "Clear History";
    private final HistoryViewModel historyViewModel;
    private final SwitchViewController switchViewController;
    private final ReuseHistoryQueryController reuseHistoryQueryController;
    private final ClearHistoryController clearHistoryController;
    private JPanel contentPanel;
    public HistoryView(HistoryViewModel historyViewModel, SwitchViewController switchViewController, ReuseHistoryQueryController reuseHistoryQueryController, ClearHistoryController clearHistoryController) {
        this.historyViewModel = historyViewModel;
        this.switchViewController = switchViewController;
        this.reuseHistoryQueryController = reuseHistoryQueryController;
        this.clearHistoryController = clearHistoryController;
        historyViewModel.addPropertyChangeListener(this);
        setPreferredSize(new Dimension(890, 650));
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        add(new JScrollPane(contentPanel));

        JMenu clearHistoryMenuButton = new JMenu(HistoryView.CLEAR_HISTORY_MENU_BUTTON_LABEL);
        clearHistoryMenuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clearHistoryController.execute();
            }
        });
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(clearHistoryMenuButton);
        setJMenuBar(menuBar);
}

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()){
            case HistoryViewModel.REFRESH -> refresh();
            case HistoryViewModel.CLEAR_HISTORY -> {
                refresh();
                JOptionPane.showMessageDialog(this, "History Cleared");
            }
        }
    }

    private void refresh() {
        contentPanel.removeAll();
        List<Query> queryList = historyViewModel.getState().getHistoryQueryList();
        if (queryList.isEmpty()){
            contentPanel.add(new JLabel(HistoryViewModel.EMPTY_MESSAGE));
        }
        for (int i = queryList.size() - 1; i >= 0; i--) { // Last queried, first shown
            HistoryQueryButton historyQueryButton = new HistoryQueryButton(queryList.get(i), reuseHistoryQueryController, switchViewController);
            contentPanel.add(historyQueryButton);
            contentPanel.add(Box.createVerticalStrut(5));
        }
        revalidate();
        repaint();
    }

    private class HistoryQueryButton extends  JButton {
        public HistoryQueryButton(Query query, ReuseHistoryQueryController reuseHistoryQueryController, SwitchViewController switchViewController) {
            super(query.getKeywords());
            this.addActionListener(e -> {
                reuseHistoryQueryController.execute(query);
                switchViewController.execute(SearchViewModel.VIEW_NAME);
            });
        }
    }
}
