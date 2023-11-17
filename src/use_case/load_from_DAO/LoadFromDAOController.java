package use_case.load_from_DAO;

public class LoadFromDAOController {
    private final LoadFromDAOInputBoundary loadFromDAOInteractor;

    public LoadFromDAOController(LoadFromDAOInputBoundary loadFromDAOInteractor) {
        this.loadFromDAOInteractor = loadFromDAOInteractor;
    }

    public void execute() {
        loadFromDAOInteractor.execute();
    }
}
