package data_access;

import entity.FetchedData;
import use_case.star.StarDataAccessInterface;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class StarDataAccessObject implements StarDataAccessInterface {
    private final List<FetchedData> starredDataList;
    private final String serializableFilePath;

    public StarDataAccessObject(String serializableFilePath) throws IOException, ClassNotFoundException {
        this.serializableFilePath = serializableFilePath;
        if(!Files.exists(Path.of(serializableFilePath))){
            starredDataList = new ArrayList<>();
        } else {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(serializableFilePath));
            starredDataList = (ArrayList<FetchedData>) ois.readObject();
        }
    }

    @Override
    public void toggleStar(FetchedData data) {
        if(dataIsStarred(data)){
            starredDataList.remove(data);
        } else {
            starredDataList.add(data);
        }
    }

    @Override
    public List<Boolean> checkIfDataStarred(List<FetchedData> fetchedData) {
        return fetchedData.stream().map(starredDataList::contains).collect(Collectors.toList());
    }

    @Override
    public List<Boolean>[] checkIfDataStarred(List<FetchedData>[] fetchedData) {
        List<Boolean>[] starredStateListArr = new List[Database.length];
        for (int i = 0; i < Database.length; i++) {
            starredStateListArr[i] = checkIfDataStarred(fetchedData[i]);
        }
        return starredStateListArr;
    }

    @Override
    public List<FetchedData> getStarredDataList() {
        return starredDataList;
    }
    public Boolean dataIsStarred(FetchedData data) {
        return starredDataList.contains(data);
    }

    @Override
    public void saveStarredData() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(serializableFilePath));
        oos.writeObject(starredDataList);
    }
}
