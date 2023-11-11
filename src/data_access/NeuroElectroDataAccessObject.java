package data_access;

import entity.FetchedData;
import entity.Query;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.open_website.WebDataAccessInterface;

import java.io.IOException;
import java.util.*;

public class NeuroElectroDataAccessObject extends PreloadedDatabaseDataAccessObject{

    private final WebDataAccessInterface webDAO;
    private final String[] entryKeys = {"input resistance", "resting membrane potential",
            "spike threshold", "spike half-width", "spike amplitude", "membrane time constant"};

    private final Map<String, String> preloadedEntries;
    public NeuroElectroDataAccessObject(WebDataAccessInterface webDAO) {
        this.webDAO = webDAO;
        preloadedEntries = new HashMap<>();
    }

    public LinkedHashMap<String, String> getEntryDetail(String id) throws IOException {
        LinkedHashMap<String, String> details = new LinkedHashMap<>();
        if (id == null){
            throw new RuntimeException("IT IS NULL");
        }
        String response = webDAO.getResponse("https://neuroelectro.org/api/1/nes/?n=" + id);
        JSONArray propertiesArr = new JSONObject(response).getJSONArray("objects");
        Map<String, Integer> propertiesIndex = getPropertiesIndex(propertiesArr);
        for (String property : entryKeys) {
            if (propertiesIndex.keySet().contains(property)) {
                details.put(property, parsePropertyValue(propertiesArr.getJSONObject(propertiesIndex.get(property))));
            }
        }
        return details;
    }

    @Override
    public List<FetchedData> query(Query query, int resultsPerPage, int page) throws IOException {
        return query(Database.NeuroElectro, preloadedEntries, query, resultsPerPage, page);
    }

    @Override
    public String getURL(String id) {
        return "https://www.neuroelectro.org/neuron/" + id;
    }

    @Override
    public String[] getEntryKeys() {
        return entryKeys;
    }

    private Map<String, Integer> getPropertiesIndex(JSONArray propertiesArr) {
        Map<String, Integer> indices = new HashMap<>();
        for(int i = 0; i < propertiesArr.length(); i++){
            indices.put(propertiesArr.getJSONObject(i).getJSONObject("e").getString("name"), i);
        }
        return indices;
    }

    private String parsePropertyValue(JSONObject propertyJSON) {
        StringBuilder value = new StringBuilder();
        String mean = String.valueOf(propertyJSON.getDouble("value_mean"));
        String sd = String.valueOf(propertyJSON.getDouble("value_sd"));
        JSONObject units = propertyJSON.getJSONObject("e").getJSONObject("units");
        value.append(mean, 0, Math.min(mean.length(), 6))
                .append(" ± ")
                .append(sd, 0, Math.min(sd.length(), 6))
                .append(" ")
                .append(units.getString("prefix"))
                .append(units.getString("name"));
        return value.toString();
    }

    protected void load() throws IOException {
        String response = webDAO.getResponse("https://neuroelectro.org/api/1/n/");
        JSONObject responseObject = new JSONObject(response);
        JSONArray neuronsArray = responseObject.getJSONArray("objects");
        for (int i = 0; i < neuronsArray.length(); i++) {
            JSONObject neuronEntry = neuronsArray.getJSONObject(i);
            String id = String.valueOf(neuronEntry.getInt("id"));
            preloadedEntries.put(neuronEntry.getString("name"), id);
        }
    }
}
