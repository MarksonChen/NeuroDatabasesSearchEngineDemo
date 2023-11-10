package use_case;

import entity.Query;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface HistoryDataAccessInterface {
    List<Query> getHistoryQueryList();

    void add(Query query);

    void removeHistory();

    void saveToFile() throws IOException;
}
