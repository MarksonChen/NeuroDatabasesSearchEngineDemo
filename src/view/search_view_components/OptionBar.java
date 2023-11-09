package view.search_view_components;

import data_access.Database;
import interface_adapter.toggle_display_option.ToggleDisplayOptionController;
import interface_adapter.view_model.SearchViewModel;
import interface_adapter.view_model.SearchViewState;

import javax.swing.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class OptionBar extends JPanel {

    private final ToggleDisplayOptionController toggleDisplayOptionController;
    private final SearchViewModel searchViewModel;

    public OptionBar(ToggleDisplayOptionController toggleDisplayOptionController, SearchViewModel searchViewModel) {
        this.toggleDisplayOptionController = toggleDisplayOptionController;
        this.searchViewModel = searchViewModel;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // The panels are not made until the String[][] EntryKey is passed from DAO by the "load from DAO" use case
    }
    public void makePanels() {
        removeAll();
        JPanel[] databasePanels = new JPanel[Database.length];
        String databaseOption = searchViewModel.getState().getDatabaseOption();
        if (databaseOption.equals(SearchViewModel.ALL_DATABASES)) {
            for (int i = 0; i < Database.length; i++) {
                databasePanels[i] = makePanel(i);
                add(databasePanels[i]);
            }
        } else {
            int databaseIndex = Database.indexOf(Database.valueOf(databaseOption));
            add(makePanel(databaseIndex));
        }
        revalidate();
        repaint();
    }

    private JPanel makePanel(int databaseIndex) {
        JPanel databasePanel = new JPanel();
        databasePanel.setLayout(new BoxLayout(databasePanel, BoxLayout.Y_AXIS));

        SearchViewState state = searchViewModel.getState();
        LinkedHashMap<String, Boolean>[] entryDisplayedMap = state.getDetailEntryDisplayed();
        for (Map.Entry<String, Boolean> entryDisplayed: entryDisplayedMap[databaseIndex].entrySet()) {
            String entryKey = entryDisplayed.getKey();
            boolean displayed = entryDisplayed.getValue();
            JCheckBox entryKeyButton = new JCheckBox(entryKey);
            entryKeyButton.setSelected(displayed);
            entryKeyButton.addActionListener(e -> toggleDisplayOptionController.execute(databaseIndex, entryKey));
            databasePanel.add(entryKeyButton);
        }

        databasePanel.setBorder(BorderFactory.createTitledBorder(Database.getDatabaseNames()[databaseIndex]));
        return databasePanel;
    }
}