package view.results_panels;

import javax.swing.*;
import java.util.List;
import java.util.Map;

public class TabbedResultsPanel extends JTabbedPane {

    public TabbedResultsPanel(List<Map<String, String>>[] results, String[] databaseNames, int resultsPerPage) {
        for (int i = 0; i < results.length; i++) {
            ScrollResultsPanel scrollResultsPanel = new ScrollResultsPanel(results[i], resultsPerPage);
            this.addTab(databaseNames[i], scrollResultsPanel);
        }
    }
}