package use_case.load_from_DAO;

import entity.FetchedData;
import entity.Query;

import java.util.List;

public interface LoadFromDAOOutputBoundary {
    void updateStatesWithData(LoadFromDAOOutputData outputData);
}
