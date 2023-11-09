package view.search_view_components;

import data_access.Database;
import interface_adapter.query.QueryAllController;
import interface_adapter.query.QueryOneController;
import interface_adapter.switch_results_panel.SwitchResultsPanelController;
import interface_adapter.switch_view.SwitchViewController;
import interface_adapter.view_model.SearchViewModel;
import interface_adapter.view_model.SearchViewState;
import interface_adapter.view_model.FrontPageViewModel;
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
    private JButton switchViewButton;
    private JTextField searchField;
    private JButton searchButton;
    private JComboBox<String> databasesComboBox;
//    private JSpinner resultsPerPageSpinner;

    public QueryBar(SearchViewModel searchViewModel, SwitchViewController switchViewController, SwitchResultsPanelController switchResultsPanelController, QueryAllController queryAllController, QueryOneController queryOneController) {
        this.searchViewModel = searchViewModel;
        this.switchViewController = switchViewController;
        this.switchResultsPanelController = switchResultsPanelController;
        this.queryAllController = queryAllController;
        this.queryOneController = queryOneController;
        switchViewButton = new JButton("ᐊ");
        switchViewButton.setPreferredSize(new Dimension(30, 30));

        searchField = new JTextField(30);

        searchButton = new ImageButton("/icons/magnifier.png", 0.04);

        String[] databaseNames = Database.getDatabaseNames();

        String[] databaseOptions = new String[databaseNames.length + 1];
        databaseOptions[0] = SearchViewModel.ALL_DATABASES;
        System.arraycopy(databaseNames, 0, databaseOptions, 1, databaseNames.length);
        databasesComboBox = new JComboBox<>(databaseOptions);

//        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(
//                SearchViewModel.DEFAULT_RESULTS_PER_PAGE, 1, 20, 1); // Default to 10, min 1, max 20, step 1
//        resultsPerPageSpinner = new JSpinner(spinnerModel);

        addListeners();

        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(switchViewButton);
        add(searchField);
        add(searchButton);
        add(databasesComboBox);
//        add(resultsPerPageSpinner);
//        add(new JLabel("results per page"));
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
//        resultsPerPageSpinner.addChangeListener(e -> {
//            SearchViewState currentState = searchViewModel.getState();
//            currentState.setResultsPerPage((int) resultsPerPageSpinner.getValue());
//            //TODO: real time change
//        });
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

    public void focusOntoSearchTextField() {
        searchField.requestFocusInWindow();
    }

    public void setSearchTextFieldText(String searchFieldText) {
        searchField.setText(searchFieldText);
    }
}
