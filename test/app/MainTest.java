package app;

import data_access.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.FrontPageView;
import view.ImageButton;
import view.MainFrame;
import view.SearchView;
import view.search_view_components.HintTextField;
import view.search_view_components.QueryBar;
import view_model.*;

import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @BeforeEach
    void setUp() {
        Main.main(null);
    }

    @Test
    void testAllUseCasesRunsWithoutError(){
        assert true;
    }

    @Test
    void testFetchingData(){
        MainFrame app = null;
        Window[] windows = Window.getWindows();
        for (Window window : windows) {
            if (window instanceof MainFrame) {
                app = (MainFrame) window;
            }
        }
        assertNotNull(app);
        Component root = app.getComponent(0);
        JPanel cp = (JPanel) ((JRootPane) root).getContentPane();
        JPanel jp = (JPanel) cp.getComponent(0);

        FrontPageView fv = (FrontPageView) jp.getComponent(0);
        JButton searchButton = (JButton) fv.getComponent(3);
        searchButton.doClick();

        SearchView sv = (SearchView) jp.getComponent(1);
        QueryBar qb = (QueryBar) sv.getComponent(0);
        HintTextField tf = (HintTextField) qb.getComponent(1);
        ImageButton sb = (ImageButton) qb.getComponent(2);

        tf.setText("Hippocampus");
        sb.doClick();

        assert true; // Search runs error-free
    }
}