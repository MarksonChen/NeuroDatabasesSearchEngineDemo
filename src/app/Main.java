package app;

import data_access.*;
import interface_adapter.clear_history.ClearHistoryController;
import interface_adapter.clear_history.ClearHistoryPresenter;
import interface_adapter.fill_detail.FillDetailController;
import interface_adapter.fill_detail.FillDetailPresenter;
import interface_adapter.load_from_DAO.LoadFromDAOController;
import interface_adapter.load_from_DAO.LoadFromDAOPresenter;
import interface_adapter.open_website.OpenWebsiteController;
import interface_adapter.open_website.OpenWebsitePresenter;
import interface_adapter.query.QueryAllPresenter;
import interface_adapter.open_frame.OpenFrameController;
import interface_adapter.open_frame.OpenFramePresenter;
import interface_adapter.query.QueryAllController;
import interface_adapter.query.QueryOneController;
import interface_adapter.query.QueryOnePresenter;
import interface_adapter.reuse_history_query.ReuseHistoryQueryController;
import interface_adapter.reuse_history_query.ReuseHistoryQueryPresenter;
import interface_adapter.star.StarController;
import interface_adapter.star.StarPresenter;
import interface_adapter.switch_results_panel.SwitchResultsPanelController;
import interface_adapter.switch_results_panel.SwitchResultsPanelPresenter;
import interface_adapter.switch_view.SwitchViewController;
import interface_adapter.switch_view.SwitchViewPresenter;
import interface_adapter.toggle_display_option.ToggleDisplayOptionController;
import interface_adapter.toggle_display_option.ToggleDisplayOptionPresenter;
import interface_adapter.view_model.*;
import use_case.clear_history.ClearHistoryInputBoundary;
import use_case.clear_history.ClearHistoryInteractor;
import use_case.clear_history.ClearHistoryOutputBoundary;
import use_case.fill_detail.FillDetailInputBoundary;
import use_case.fill_detail.FillDetailInteractor;
import use_case.fill_detail.FillDetailOutputBoundary;
import use_case.load_from_DAO.LoadFromDAOInputBoundary;
import use_case.load_from_DAO.LoadFromDAOInteractor;
import use_case.load_from_DAO.LoadFromDAOOutputBoundary;
import use_case.open_website.OpenWebsiteInputBoundary;
import use_case.open_website.OpenWebsiteInteractor;
import use_case.open_website.OpenWebsiteOutputBoundary;
import use_case.open_website.WebDataAccessInterface;
import use_case.query.query_all.QueryAllInputBoundary;
import use_case.query.query_all.QueryAllInteractor;
import use_case.query.query_all.QueryAllOutputBoundary;
import use_case.query.QueryDataAccessInterface;
import use_case.clear_history.HistoryDataAccessInterface;
import use_case.open_frame.OpenFrameInputBoundary;
import use_case.open_frame.OpenFrameInteractor;
import use_case.open_frame.OpenFrameOutputBoundary;
import use_case.query.query_one.QueryOneInputBoundary;
import use_case.query.query_one.QueryOneInteractor;
import use_case.query.query_one.QueryOneOutputBoundary;
import use_case.reuse_history_query.ReuseHistoryQueryInputBoundary;
import use_case.reuse_history_query.ReuseHistoryQueryInteractor;
import use_case.reuse_history_query.ReuseHistoryQueryOutputBoundary;
import use_case.star.StarDataAccessInterface;
import use_case.star.StarInputBoundary;
import use_case.star.StarInteractor;
import use_case.star.StarOutputBoundary;
import use_case.switch_results_panel.SwitchResultsPanelInputBoundary;
import use_case.switch_results_panel.SwitchResultsPanelInteractor;
import use_case.switch_results_panel.SwitchResultsPanelOutputBoundary;
import use_case.switch_view.SwitchViewInputBoundary;
import use_case.switch_view.SwitchViewInteractor;
import use_case.switch_view.SwitchViewOutputBoundary;
import use_case.toggle_display_option.ToggleDisplayOptionInputBoundary;
import use_case.toggle_display_option.ToggleDisplayOptionInteractor;
import use_case.toggle_display_option.ToggleDisplayOptionOutputBoundary;
import view.*;
import view.MenuBar;
import view.HistoryView;
import view.search_view_components.ScrollResultsPanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
//        FlatLightLaf.setup();  // FlatLaf UI Look & Feel setup before any Swing codes

        //View Models
        SearchViewModel searchViewModel = new SearchViewModel();
        FrontPageViewModel frontPageViewModel = new FrontPageViewModel();
        StarredViewModel starredViewModel = new StarredViewModel();
        HistoryViewModel historyViewModel = new HistoryViewModel();
        MainFrameViewModel mainFrameViewModel = new MainFrameViewModel();
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        FrameManagerModel frameManagerModel = new FrameManagerModel();

        Database[] databases = Database.values();
        ScrollResultsPanelModel[] resultsPanelModels = new ScrollResultsPanelModel[databases.length];
        for (int i = 0; i < databases.length; i++) {
            resultsPanelModels[i] = new ScrollResultsPanelModel(databases[i]);
        }

        //DAOs
        WebDataAccessInterface webDAO = new WebDataAccessObject();
        CacheDataAccessInterface dataCacheDAO = new CacheDataAccessObject();
        // Change between InMemoryQueryDAO (for testing & development only) and real QueryDAO here
//        QueryDataAccessInterface queryDAO = new QueryDataAccessObject(dataCacheDAO, webDAO);
        QueryDataAccessInterface queryDAO;
        try {
            queryDAO = new InMemoryQueryDataAccessObject(dataCacheDAO, "resources/serializables/fetchedDataListArr.ser");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Load FetchedData failed. Need to update FetchedDta.ser files.");
            throw new RuntimeException(e);
        }

        StarDataAccessInterface starDAO;
        try {
            starDAO = new StarDataAccessObject("resources/serializables/starredData.ser");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        HistoryDataAccessInterface historyDAO = null;
        try {
            historyDAO = new HistoryDataAccessObject("resources/serializables/historyQueries.ser");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        //Controllers
        SwitchViewController switchViewController = createSwitchViewController(viewManagerModel);
        SwitchResultsPanelController switchResultsPanelController = createSwitchResultsPanelController(searchViewModel);
        QueryAllController queryAllController = createQueryAllController(searchViewModel, resultsPanelModels, historyViewModel, queryDAO, starDAO, historyDAO);
        QueryOneController queryOneController = createQueryOneController(searchViewModel, resultsPanelModels, historyViewModel, queryDAO, starDAO, historyDAO);
        FillDetailController fillDetailController = createFillDetailController(resultsPanelModels, starredViewModel, queryDAO, starDAO);
        StarController starController = createStarController(resultsPanelModels, starredViewModel, starDAO);
        ReuseHistoryQueryController reuseHistoryQueryController = createReuseHistoryQueryController(frameManagerModel, viewManagerModel, historyViewModel, searchViewModel);
        ClearHistoryController clearHistoryController = createClearHistoryController(historyViewModel, historyDAO);
        OpenFrameController openFrameController = createOpenFrameController(frameManagerModel, starredViewModel);
        LoadFromDAOController loadFromDAOController = createLoadFromDAOController(resultsPanelModels, searchViewModel, starredViewModel, historyViewModel, queryDAO, starDAO, historyDAO);
        ToggleDisplayOptionController toggleDisplayOptionController = createToggleDisplayOptionController(searchViewModel, resultsPanelModels, starredViewModel);
        OpenWebsiteController openWebsiteController = createOpenWebsiteController(mainFrameViewModel, webDAO);


        //Main Frame
        MainFrame mainFrame = new MainFrame(mainFrameViewModel);
        MenuBar menuBar = new MenuBar(mainFrameViewModel, openFrameController, openWebsiteController);
        mainFrame.setJMenuBar(menuBar);

        CardLayout cardLayout = new CardLayout();
        JPanel views = new JPanel(cardLayout);
        mainFrame.add(views);

        //Views
        FrontPageView frontPageView = new FrontPageView(frontPageViewModel, switchViewController, openWebsiteController);
        views.add(frontPageView, FrontPageViewModel.VIEW_NAME);
        // There are two sets of result panels because there are two views the user can choose to display the data in
        // However, Swing does not allow the same component to be added to two different places.
        // Hooking every pair of result panels to the same model ensures synchronized updates of the two result panels.
        ScrollResultsPanel[] tabbedViewResultPanels = new ScrollResultsPanel[databases.length];
        ScrollResultsPanel[] singleViewResultPanels = new ScrollResultsPanel[databases.length];
        for (int i = 0; i < databases.length; i++) {
            tabbedViewResultPanels[i] = new ScrollResultsPanel(searchViewModel, resultsPanelModels[i], queryOneController, fillDetailController, starController, openWebsiteController);
            singleViewResultPanels[i] = new ScrollResultsPanel(searchViewModel, resultsPanelModels[i], queryOneController, fillDetailController, starController, openWebsiteController);
        }
        SearchView searchView = new SearchView(tabbedViewResultPanels, singleViewResultPanels, searchViewModel, switchViewController,
                switchResultsPanelController, queryAllController, queryOneController, toggleDisplayOptionController);
        views.add(searchView, SearchViewModel.VIEW_NAME);

        new ViewManager(views, cardLayout, viewManagerModel); // Finished adding views


        //Frame Manager
        Map<String, JFrame> frames = new HashMap<>();
        frames.put(MainFrameViewModel.VIEW_NAME, mainFrame);

        HistoryView historyView = new HistoryView(historyViewModel, switchViewController, reuseHistoryQueryController, clearHistoryController);
        frames.put(HistoryViewModel.VIEW_NAME, historyView);

        StarredView starredView = new StarredView(searchViewModel, starredViewModel, fillDetailController, starController, openWebsiteController);
        frames.put(StarredViewModel.VIEW_NAME, starredView);

        new FrameManager(frameManagerModel, frames);

        switchViewController.execute(FrontPageViewModel.VIEW_NAME);
        loadFromDAOController.execute();

        mainFrame.start();
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

    private static SwitchResultsPanelController createSwitchResultsPanelController(SearchViewModel searchViewModel) {
        SwitchResultsPanelOutputBoundary switchResultsPanelPresenter = new SwitchResultsPanelPresenter(searchViewModel);
        SwitchResultsPanelInputBoundary switchResultsPanelInteractor = new SwitchResultsPanelInteractor(switchResultsPanelPresenter);
        return new SwitchResultsPanelController(switchResultsPanelInteractor);
    }

    private static OpenFrameController createOpenFrameController(FrameManagerModel frameManagerModel, StarredViewModel starredViewModel) {
        OpenFrameOutputBoundary openFramePresenter = new OpenFramePresenter(frameManagerModel, starredViewModel);
        OpenFrameInputBoundary openFrameInteractor = new OpenFrameInteractor(openFramePresenter);
        return new OpenFrameController(openFrameInteractor);
    }

    private static QueryAllController createQueryAllController(SearchViewModel searchViewModel, ScrollResultsPanelModel[] resultsPanelModels, HistoryViewModel historyViewModel, QueryDataAccessInterface queryDAO, StarDataAccessInterface starDAO, HistoryDataAccessInterface historyDAO) {
        QueryAllOutputBoundary queryPresenter = new QueryAllPresenter(searchViewModel, resultsPanelModels, historyViewModel);
        QueryAllInputBoundary queryInteractor = new QueryAllInteractor(queryPresenter, queryDAO, starDAO, historyDAO);
        return new QueryAllController(queryInteractor);
    }

    private static QueryOneController createQueryOneController(SearchViewModel searchViewModel, ScrollResultsPanelModel[] resultsPanelModels, HistoryViewModel historyViewModel, QueryDataAccessInterface queryDAO, StarDataAccessInterface starDAO, HistoryDataAccessInterface historyDAO) {
        QueryOneOutputBoundary queryPresenter = new QueryOnePresenter(searchViewModel, resultsPanelModels, historyViewModel);
        QueryOneInputBoundary queryInteractor = new QueryOneInteractor(queryPresenter, queryDAO, starDAO, historyDAO);
        return new QueryOneController(queryInteractor);
    }

    private static FillDetailController createFillDetailController(ScrollResultsPanelModel[] resultsPanelModels, StarredViewModel starredViewModel, QueryDataAccessInterface queryDAO, StarDataAccessInterface starDAO) {
        FillDetailOutputBoundary fillDetailPresenter = new FillDetailPresenter(resultsPanelModels, starredViewModel);
        FillDetailInputBoundary fillDetailInteractor = new FillDetailInteractor(fillDetailPresenter, queryDAO, starDAO);
        return new FillDetailController(fillDetailInteractor);
    }

    private static StarController createStarController(ScrollResultsPanelModel[] resultsPanelModels, StarredViewModel starredViewModel, StarDataAccessInterface starDAO) {
        StarOutputBoundary starPresenter = new StarPresenter(resultsPanelModels, starredViewModel);
        StarInputBoundary starInteractor = new StarInteractor(starPresenter, starDAO);
        return new StarController(starInteractor);
    }

    private static ClearHistoryController createClearHistoryController(HistoryViewModel historyViewModel, HistoryDataAccessInterface historyDAO) {
        ClearHistoryOutputBoundary clearHistoryPresenter = new ClearHistoryPresenter(historyViewModel);
        ClearHistoryInputBoundary clearHistoryInteractor = new ClearHistoryInteractor(clearHistoryPresenter, historyDAO);
        return new ClearHistoryController(clearHistoryInteractor);
    }

    private static ReuseHistoryQueryController createReuseHistoryQueryController(FrameManagerModel frameManagerModel, ViewManagerModel viewManagerModel, HistoryViewModel historyViewModel, SearchViewModel searchViewModel) {
        ReuseHistoryQueryOutputBoundary reuseHistoryQueryPresenter = new ReuseHistoryQueryPresenter(frameManagerModel, viewManagerModel, historyViewModel, searchViewModel);
        ReuseHistoryQueryInputBoundary reuseHistoryQueryInteractor = new ReuseHistoryQueryInteractor(reuseHistoryQueryPresenter);
        return new ReuseHistoryQueryController(reuseHistoryQueryInteractor);
    }

    private static LoadFromDAOController createLoadFromDAOController(ScrollResultsPanelModel[] resultsPanelModels, SearchViewModel searchViewModel, StarredViewModel starredViewModel, HistoryViewModel historyViewModel, QueryDataAccessInterface queryDAO, StarDataAccessInterface starDAO, HistoryDataAccessInterface historyDAO) {
        LoadFromDAOOutputBoundary loadFromDAOPresenter = new LoadFromDAOPresenter(resultsPanelModels, searchViewModel, starredViewModel, historyViewModel);
        LoadFromDAOInputBoundary loadFromDAOInteractor = new LoadFromDAOInteractor(searchViewModel, loadFromDAOPresenter, queryDAO, starDAO, historyDAO);
        return new LoadFromDAOController(loadFromDAOInteractor);
    }

    private static ToggleDisplayOptionController createToggleDisplayOptionController(SearchViewModel searchViewModel, ScrollResultsPanelModel[] resultsPanelModels, StarredViewModel starredViewModel) {
        ToggleDisplayOptionOutputBoundary toggleDisplayOptionPresenter = new ToggleDisplayOptionPresenter(searchViewModel, resultsPanelModels, starredViewModel);
        ToggleDisplayOptionInputBoundary toggleDisplayOptionInteractor = new ToggleDisplayOptionInteractor(toggleDisplayOptionPresenter);
        return new ToggleDisplayOptionController(toggleDisplayOptionInteractor);
    }

    private static OpenWebsiteController createOpenWebsiteController(MainFrameViewModel mainFrameViewModel, WebDataAccessInterface webDAO) {
        OpenWebsiteOutputBoundary openWebsitePresenter = new OpenWebsitePresenter(mainFrameViewModel);
        OpenWebsiteInputBoundary openWebsiteInteractor = new OpenWebsiteInteractor(webDAO, openWebsitePresenter);
        return new OpenWebsiteController(openWebsiteInteractor);
    }
}
