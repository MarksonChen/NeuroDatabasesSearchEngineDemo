package use_case.star;

import entity.FetchedData;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface StarDataAccessInterface {
    void toggleStar(FetchedData data);

    List<Boolean> checkIfDataStarred(List<FetchedData> fetchedData);

    List<Boolean>[] checkIfDataStarred(List<FetchedData>[] fetchedData);

    Boolean dataIsStarred(FetchedData data);
    List<FetchedData> getStarredDataList();
    void saveStarredData() throws IOException;
}
