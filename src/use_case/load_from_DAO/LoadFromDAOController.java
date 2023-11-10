package use_case.load_from_DAO;

import use_case.load_from_DAO.LoadFromDAOInputBoundary;

public class LoadFromDAOController {
    private final LoadFromDAOInputBoundary loadFromDAOInteractor;

    public LoadFromDAOController(LoadFromDAOInputBoundary loadFromDAOInteractor) {
        this.loadFromDAOInteractor = loadFromDAOInteractor;
    }

    public void execute() {
        loadFromDAOInteractor.execute();
    }
}
