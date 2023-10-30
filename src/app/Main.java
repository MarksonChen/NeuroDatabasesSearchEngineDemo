package app;

import data_access.HistoryDataAccessObject;
import data_access.StarDataAccessObject;
import interface_adapter.open_frame.OpenFrameController;
import interface_adapter.open_frame.OpenFramePresenter;
import interface_adapter.query.QueryController;
import interface_adapter.star.StarController;
import interface_adapter.switch_view.SwitchViewController;
import interface_adapter.switch_view.SwitchViewPresenter;
import interface_adapter.view_model.*;
import use_case.clear_history.HistoryDataAccessInterface;
import use_case.open_frame.OpenFrameInputBoundary;
import use_case.open_frame.OpenFrameInteractor;
import use_case.open_frame.OpenFrameOutputBoundary;
import use_case.star.StarDataAccessInterface;
import use_case.switch_view.SwitchViewInputBoundary;
import use_case.switch_view.SwitchViewInteractor;
import use_case.switch_view.SwitchViewOutputBoundary;
import view.*;
import view.MenuBar;
import view.HistoryView;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        JFrame application = new JFrame("Neuro-Databases Search Engine");

        CardLayout cardLayout = new CardLayout();
        JPanel views = new JPanel(cardLayout);
        application.add(views);

        //View Models
        CellularDBSearchViewModel cellularDBSearchViewModel = new CellularDBSearchViewModel();
        DatasetDBSearchViewModel datasetDBSearchViewModel = new DatasetDBSearchViewModel();
        FrontPageViewModel frontPageViewModel = new FrontPageViewModel();
        StarredViewModel starredViewModel = new StarredViewModel();
        HistoryViewModel historyViewModel = new HistoryViewModel();
        MenuBarViewModel menuBarViewModel = new MenuBarViewModel();

        ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(views, cardLayout, viewManagerModel);

        //Controllers
        SwitchViewController switchViewController = createSwitchViewController(viewManagerModel);
        QueryController queryController = createQueryController(datasetDBSearchViewModel, cellularDBSearchViewModel);
        StarController starController = createStarController(datasetDBSearchViewModel, cellularDBSearchViewModel);

        //Views for the application frame
        FrontPageView frontPageView = createFrontPageView(frontPageViewModel, switchViewController);
        views.add(frontPageView, FrontPageViewModel.VIEW_NAME);

        DatasetDBSearchView datasetDBSearchView = new DatasetDBSearchView(datasetDBSearchViewModel, switchViewController, queryController, starController);
        views.add(datasetDBSearchView, DatasetDBSearchViewModel.VIEW_NAME);

        CellularDBSearchView cellularDBSearchView = new CellularDBSearchView(cellularDBSearchViewModel, switchViewController, queryController, starController);
        views.add(cellularDBSearchView, CellularDBSearchViewModel.VIEW_NAME);

        viewManagerModel.setActiveView(FrontPageViewModel.VIEW_NAME);
        viewManagerModel.firePropertyChanged();


        //Standalone frames
        FrameManagerModel frameManagerModel = new FrameManagerModel();
        Map<String, JFrame> frames = new HashMap<>();
        new FrameManager(frameManagerModel, frames);

        HistoryDataAccessInterface HistoryDataAccessObject = new HistoryDataAccessObject();
        HistoryView historyView = new HistoryView(historyViewModel, HistoryDataAccessObject, queryController);
        frames.put(HistoryViewModel.VIEW_NAME, historyView);

        StarDataAccessInterface starDataAccessObject = new StarDataAccessObject();
        StarredView starredView = new StarredView(starredViewModel, starDataAccessObject, starController);
        frames.put(StarredViewModel.VIEW_NAME, starredView);

        OpenFrameController openFrameController = createOpenFrameController(frameManagerModel);
        MenuBar menuBar = new MenuBar(menuBarViewModel, openFrameController);
        application.setJMenuBar(menuBar);

        application.setPreferredSize(new Dimension(1200, 800));
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }

    private static SwitchViewController createSwitchViewController(ViewManagerModel viewManagerModel) {
        // Presenter depends on ViewModel
        SwitchViewOutputBoundary switchViewPresenter = new SwitchViewPresenter(viewManagerModel);
        // Interactor depends on Presenter (and optionally, DAO)
        SwitchViewInputBoundary switchViewInteractor = new SwitchViewInteractor(switchViewPresenter);
        // Controller depends on Interactor
        return new SwitchViewController(switchViewInteractor);
        // View depends on Controller
    }

    private static OpenFrameController createOpenFrameController(FrameManagerModel frameManagerModel) {
        OpenFrameOutputBoundary openFramePresenter = new OpenFramePresenter(frameManagerModel);
        OpenFrameInputBoundary openFrameInteractor = new OpenFrameInteractor(openFramePresenter);
        return new OpenFrameController(openFrameInteractor);
    }

    private static QueryController createQueryController(DatasetDBSearchViewModel datasetDBSearchViewModel, CellularDBSearchViewModel cellularDBSearchViewModel) {
        return new QueryController();
    }

    private static StarController createStarController(DatasetDBSearchViewModel datasetDBSearchViewModel, CellularDBSearchViewModel cellularDBSearchViewModel) {
        return new StarController();
    }

    private static FrontPageView createFrontPageView(FrontPageViewModel frontPageViewModel, SwitchViewController switchViewController) {
        return new FrontPageView(frontPageViewModel, switchViewController);
    }
}
