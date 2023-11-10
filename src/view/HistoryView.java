package view;

import entity.Query;
import use_case.clear_history.ClearHistoryController;
import use_case.reuse_history_query.ReuseHistoryQueryController;
import use_case.switch_view.SwitchViewController;
import view_model.HistoryViewModel;
import view_model.SearchViewModel;

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
        contentPanel.setLayout(new GridBagLayout());
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
            case HistoryViewModel.CLOSE -> setVisible(false);
        }
    }

    private void refresh() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.weighty = 0;

        contentPanel.removeAll();

        List<Query> queryList = historyViewModel.getState().getHistoryQueryList();
        if (queryList.isEmpty()){
            contentPanel.add(new JLabel(HistoryViewModel.EMPTY_MESSAGE), gbc);
        } else {
            JLabel message = new JLabel(HistoryViewModel.GUIDE_MESSAGE);
            message.setForeground(Color.gray);
            contentPanel.add(message, gbc);
            for (int i = queryList.size() - 1; i >= 0; i--) { // Last queried, first shown
                HistoryQueryButton historyQueryButton = new HistoryQueryButton(queryList.get(i), reuseHistoryQueryController, switchViewController);
                historyQueryButton.setFocusPainted(false);
                contentPanel.add(historyQueryButton, gbc);
            }
        }

        gbc.weighty = 1;
        contentPanel.add(new JLabel(), gbc);

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
