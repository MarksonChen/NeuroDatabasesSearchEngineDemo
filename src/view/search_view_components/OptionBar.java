package view.search_view_components;

import data_access.Database;
import use_case.toggle_display_option.ToggleDisplayOptionController;
import view_model.MainFrameViewModel;
import view_model.SearchViewModel;
import view_model.SearchViewState;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class OptionBar extends JPanel {

    private final ToggleDisplayOptionController toggleDisplayOptionController;
    private final SearchViewModel searchViewModel;

    public OptionBar(ToggleDisplayOptionController toggleDisplayOptionController, SearchViewModel searchViewModel) {
        this.toggleDisplayOptionController = toggleDisplayOptionController;
        this.searchViewModel = searchViewModel;
        setLayout(new GridBagLayout());
        setBackground(MainFrameViewModel.BACKGROUND_COLOR);
        setBorder(BorderFactory.createEmptyBorder(5, 15, 20, 10));
        // The panels are not made until the LinkedHashMap<String, Boolean>[] entryDisplayedMap
        // is passed from DAO by the "load from DAO" use case
    }
    public void makePanels() {
        removeAll();
        JPanel[] databasePanels = new JPanel[Database.length];
        String databaseOption = searchViewModel.getState().getDatabaseOption();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(24,0,0,0);

        if (databaseOption.equals(SearchViewModel.ALL_DATABASES)) {
            for (int i = 0; i < Database.length; i++) {
                databasePanels[i] = makePanel(i);
                add(databasePanels[i], gbc);
                gbc.insets = new Insets(15,0,0,0);
                gbc.gridy++;
            }
        } else {
            int databaseIndex = Database.indexOf(Database.valueOf(databaseOption));
            add(makePanel(databaseIndex));
            gbc.gridy++;
        }

        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;
        JPanel filler = new JPanel();
        filler.setBackground(MainFrameViewModel.BACKGROUND_COLOR);
        add(filler, gbc);
        // This panel will stretch as much as possible
        // so that the databasePanels are stacked from the top

        revalidate();
        repaint();
    }

    private JPanel makePanel(int databaseIndex) {
        JPanel databasePanel = new JPanel();
        databasePanel.setLayout(new BoxLayout(databasePanel, BoxLayout.Y_AXIS));
        databasePanel.setBackground(MainFrameViewModel.BACKGROUND_COLOR);

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

        TitledBorder border = BorderFactory.createTitledBorder(Database.getDatabaseNames()[databaseIndex]);
//        border.setTitleFont(border.getTitleFont().deriveFont(Font.BOLD));
//        border.setTitleColor(MainFrameViewModel.TITLE_COLOR);
        databasePanel.setBorder(border);
        return databasePanel;
    }
}