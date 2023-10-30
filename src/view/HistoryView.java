package view;

import interface_adapter.query.QueryController;
import interface_adapter.view_model.HistoryViewModel;
import use_case.clear_history.HistoryDataAccessInterface;

import javax.swing.*;
import java.awt.*;

public class HistoryView extends JFrame {
    public HistoryView(HistoryViewModel historyViewModel, HistoryDataAccessInterface historyDataAccessObject, QueryController queryController) {
        add(new JLabel("History"));
        setPreferredSize(new Dimension(890, 650));
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        pack();
    }
}
