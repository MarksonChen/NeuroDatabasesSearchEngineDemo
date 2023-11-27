package view.search_view_components;

import data_access.Database;
import use_case.query.query_all.QueryAllController;
import use_case.query.query_one.QueryOneController;
import use_case.switch_results_panel.SwitchResultsPanelController;
import use_case.switch_view.SwitchViewController;
import view_model.MainFrameViewModel;
import view_model.SearchViewModel;
import view_model.SearchViewState;
import view_model.FrontPageViewModel;
import view.ImageButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class QueryBar extends JPanel {
    private final SearchViewModel searchViewModel;
    private final SwitchViewController switchViewController;
    private final SwitchResultsPanelController switchResultsPanelController;
    private final QueryAllController queryAllController;
    private final QueryOneController queryOneController;
    private final JButton switchViewButton;
    private final HintTextField searchField;
    private final JButton searchButton;
    private final JComboBox<String> databasesComboBox;

    public QueryBar(SearchViewModel searchViewModel, SwitchViewController switchViewController, SwitchResultsPanelController switchResultsPanelController, QueryAllController queryAllController, QueryOneController queryOneController) {
        this.searchViewModel = searchViewModel;
        this.switchViewController = switchViewController;
        this.switchResultsPanelController = switchResultsPanelController;
        this.queryAllController = queryAllController;
        this.queryOneController = queryOneController;
        switchViewButton = new JButton("ᐊ"); // or ❮
        switchViewButton.setBorder(null);
        switchViewButton.setFocusPainted(false);
        switchViewButton.setPreferredSize(new Dimension(25, 25));

        searchField = new HintTextField(SearchViewModel.SEARCH_FIELD_HINT, 40);
        searchButton = new ImageButton(SearchViewModel.SEARCH_BUTTON_IMAGE_PATH, SearchViewModel.SEARCH_BUTTON_IMAGE_SCALE);

        String[] databaseNames = Database.getDatabaseNames();

        String[] databaseOptions = new String[databaseNames.length + 1];
        databaseOptions[0] = SearchViewModel.ALL_DATABASES;
        System.arraycopy(databaseNames, 0, databaseOptions, 1, databaseNames.length);
        databasesComboBox = new JComboBox<>(databaseOptions);

        addListeners();

        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        setBackground(MainFrameViewModel.BACKGROUND_COLOR);
        add(switchViewButton);
        add(searchField);
        add(searchButton);
        add(databasesComboBox);
    }
    private void addListeners() {
        switchViewButton.addActionListener(e ->
                switchViewController.execute(FrontPageViewModel.VIEW_NAME));
        searchButton.addActionListener(e -> performSearch(searchViewModel, queryAllController, queryOneController));
        searchField.addActionListener(e -> performSearch(searchViewModel, queryAllController, queryOneController));
        searchField.addKeyListener(
                new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        SearchViewState currentState = searchViewModel.getState();
                        String text = searchField.getText() + e.getKeyChar();
                        currentState.setSearchFieldText(text);
//                        searchViewModel.setState(currentState);
                    }
                    @Override
                    public void keyPressed(KeyEvent e) { }
                    @Override
                    public void keyReleased(KeyEvent e) { }
                });
        databasesComboBox.addActionListener(e -> {
            SearchViewState currentState = searchViewModel.getState();
            String databaseOption = (String) databasesComboBox.getSelectedItem();
            currentState.setDatabaseOption(databaseOption);
            switchResultsPanelController.execute(databaseOption);
        });

    }
    private static void performSearch(SearchViewModel searchViewModel, QueryAllController queryAllController, QueryOneController queryOneController){
        SearchViewState state = searchViewModel.getState();
        String option = state.getDatabaseOption();
        if (option.equals(SearchViewModel.ALL_DATABASES)){
            queryAllController.execute(state.getSearchFieldText(), state.getResultsPerPage());
        } else{
            queryOneController.execute(Database.valueOf(option), state.getSearchFieldText(), state.getResultsPerPage(), 1);
            // The option must matches one of the databases since we directly
            // referenced the names of all databases in creating the combo box
        }
    }

    public void setSearchTextFieldText(String searchFieldText) {
        searchField.setText(searchFieldText);
    }
}