package data_access;

import entity.FetchedData;
import entity.Query;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.open_website.WebDataAccessInterface;

import java.io.IOException;
import java.util.*;

public class ModelDBDataAccessObject extends PreloadedDatabaseDataAccessObject{
    private final WebDataAccessInterface webDAO;
    private static final String[] entryKeys = {"description", "neurons", "keywords", "model paper"};

    private final Map<String, String> preloadedEntries;
    public ModelDBDataAccessObject(WebDataAccessInterface webDAO) {
        this.webDAO = webDAO;
        preloadedEntries = new HashMap<>();
    }
    public void load() throws IOException {
        // https://modeldb.science/api/v1/models/name
        String modelNames = webDAO.getResponse("https://modeldb.science/api/v1/models/name");
        String modelID = webDAO.getResponse("https://modeldb.science/api/v1/models");
        JSONArray namesArray = new JSONArray(modelNames);
        JSONArray idArray = new JSONArray(modelID);
        for (int i = 0; i < namesArray.length(); i++) {
            preloadedEntries.put(namesArray.getString(i), Integer.toString(idArray.getInt(i)));
        }
    }

    public LinkedHashMap<String, String> getEntryDetail(String id) throws IOException {
        String response = webDAO.getResponse("https://modeldb.science/api/v1/models/" + id);
        JSONObject entryDetail = new JSONObject(response);

        LinkedHashMap<String, String> details = new LinkedHashMap<>();

        if (entryDetail.has("notes")) {
            String description = entryDetail.getJSONObject("notes").getString("value");
            details.put(entryKeys[0], description);
        }
        if (entryDetail.has("neurons")) {
            JSONArray neuronsArr = entryDetail.getJSONObject("neurons").getJSONArray("value");
            String neurons = joinJSONArray(neuronsArr);
            details.put(entryKeys[1], neurons);
        }
        if (entryDetail.has("model_concept")) {
            JSONArray keywordsArr = entryDetail.getJSONObject("model_concept").getJSONArray("value");
            String keywords = joinJSONArray(keywordsArr);
            details.put(entryKeys[2], keywords);
        }
        if (entryDetail.has("model_paper")) {
            String modelPaper = entryDetail.getJSONObject("model_paper").getJSONArray("value")
                    .getJSONObject(0).getString("object_name");
            details.put(entryKeys[3], modelPaper);
        }
        return details;
    }

    @Override
    public List<FetchedData> query(Query query, int resultsPerPage, int page) throws IOException {
        return query(Database.ModelDB, preloadedEntries, query, resultsPerPage, page);
    }

    @Override
    public String getURL(String id) {
        return "https://modeldb.science/" + id;
    }

    @Override
    public String[] getEntryKeys() {
        return entryKeys;
    }

    private static String joinJSONArray(JSONArray jsonArr) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < jsonArr.length(); i++) {
            list.add(jsonArr.getJSONObject(i).getString("object_name"));
        }
        return String.join(", ", list);
    }
}
