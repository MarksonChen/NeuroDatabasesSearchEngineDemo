package view.results_panels;

import view.ImageButton;

import javax.swing.*;
import java.awt.*;

public class QueryBar extends JPanel {
    private JButton switchViewButton;
    private JTextField searchField;
    private JButton searchButton;
    private JComboBox<String> viewOptionComboBox;
    private JComboBox<String> databasesComboBox;
    private JSpinner resultsPerPageSpinner;

    public QueryBar(String[] databaseNames) {
        switchViewButton = new JButton("ᐊ");
        switchViewButton.setPreferredSize(new Dimension(30, 30));

        searchField = new JTextField(20);

        searchButton = new ImageButton("/magnifier.png", 0.04);

        String[] viewOptions = {"Split View", "Integrated"};
        viewOptionComboBox = new JComboBox<>(viewOptions);

        String[] databaseOptions = new String[databaseNames.length + 1];
        databaseOptions[0] = "All Databases";
        System.arraycopy(databaseNames, 0, databaseOptions, 1, databaseNames.length);
        databasesComboBox = new JComboBox<>(databaseOptions);

        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(5, 1, 20, 1); // Default to 10, min 1, max 20, step 1
        resultsPerPageSpinner = new JSpinner(spinnerModel);

        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(switchViewButton);
        add(searchField);
        add(searchButton);
        add(viewOptionComboBox);
        add(databasesComboBox);
        add(resultsPerPageSpinner);
        add(new JLabel("results per page"));
    }

    public JComboBox<String> getViewOptionComboBox() {
        return viewOptionComboBox;
    }

    public JComboBox<String> getDatabasesComboBox() {
        return databasesComboBox;
    }
    public int getResultsPerPage() {
        return (int) resultsPerPageSpinner.getValue();
    }
}
