package data_access;

import entity.Query;
import use_case.HistoryDataAccessInterface;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class HistoryDataAccessObject implements HistoryDataAccessInterface {
    private final String serializableFilePath;
    List<Query> historyQueryList;

    public HistoryDataAccessObject(String serializableFilePath) throws IOException, ClassNotFoundException {
        this.serializableFilePath = serializableFilePath;
        if(!Files.exists(Path.of(serializableFilePath))){
            historyQueryList = new ArrayList<>();
        } else {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(serializableFilePath));
            historyQueryList = (ArrayList<Query>) ois.readObject();
        }
    }

    @Override
    public List<Query> getHistoryQueryList() {
        return historyQueryList;
    }

    @Override
    public void add(Query query) {
        if (historyQueryList.contains(query)){
            historyQueryList.remove(query);   // bubbles the query to the top
        }
        historyQueryList.add(query);
    }

    @Override
    public void removeHistory() {
        historyQueryList.clear();
    }

    @Override
    public void saveToFile() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(serializableFilePath));
        oos.writeObject(historyQueryList);
    }

    @Override
    public void clear() throws IOException {
        historyQueryList.clear();
        saveToFile();
    }
}
