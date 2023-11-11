package data_access;

import entity.FetchedData;
import entity.Query;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.open_website.WebDataAccessInterface;

import java.io.IOException;
import java.util.*;

public class NeuroMorphoDataAccessObject implements DatabaseDataAcecssInterface{

    private final WebDataAccessInterface webDAO;
    private static final Set<String> brainRegions = new HashSet<>();
    private static final Set<String> cellTypes = new HashSet<>();
    private static final boolean loaded = false;
    private int totalResults;
    private final String[] entryKeys = {"species", "strain", "soma_surface", "volume", "reference doi"};

    public NeuroMorphoDataAccessObject(WebDataAccessInterface webDAO) {
        this.webDAO = webDAO;
    }

    private void load() throws IOException {
        loadBrainRegions();
        loadCellTypes();
    }

    @Override
    public String[] getEntryKeys() {
        return entryKeys;
    }

    private void loadBrainRegions() throws IOException {
        String response = webDAO.getResponse("https://neuromorpho.org/api/neuron/fields/brain_region");
        JSONObject responseObject = new JSONObject(response);
        JSONArray neuronsArray = responseObject.getJSONArray("fields");
        for (int i = 0; i < neuronsArray.length(); i++) {
            brainRegions.add(neuronsArray.getString(i).toLowerCase());
        }
    }

    private void loadCellTypes() throws IOException {
        String response = webDAO.getResponse("https://neuromorpho.org/api/neuron/fields/cell_type");
        JSONObject responseObject = new JSONObject(response);
        JSONArray neuronsArray = responseObject.getJSONArray("fields");
        for (int i = 0; i < neuronsArray.length(); i++) {
            cellTypes.add(neuronsArray.getString(i).toLowerCase());
        }
    }

    public List<FetchedData> query(Query query, int resultsPerPage, int page) throws IOException {
        if (!loaded) load();
        JSONArray queryResults = fetchResults(query, resultsPerPage, page);
        if (queryResults == null) {
            return new ArrayList<>();
        }
        List<FetchedData> entries = new ArrayList<>();
        for (int i = 0; i < queryResults.length(); i++) {
            entries.add(parseEntry(queryResults.getJSONObject(i)));
        }
        return entries;
        // This is an empty ArrayList if query lastQuery is empty
        // or the page requested exceeds totalResults
    }

    private JSONArray fetchResults(Query query, int resultsPerPage, int page) throws IOException {
        String[] keywords = DatabaseDataAcecssInterface.splitWords(query.getKeywords());
        // We want to get the cell type and brain region from the keywords string

        String cellType = null, brainRegion = null;
        for (String keyword: keywords){
            if (cellTypes.contains(keyword)){
                cellType = keyword;
                break;
            }
        }
        for (String keyword: keywords){
            if (brainRegions.contains(keyword) && (cellType == null || !cellType.equals(keyword))){
                brainRegion = keyword;
                break;
            }
        }
        if (cellType == null && brainRegion == null){
            totalResults = 0;
            return null;  // No brain region / cell types matched, so no results were fetched
        }

        String path = buildPath(resultsPerPage, page, cellType, brainRegion);
        String response = webDAO.getResponse(path);
        JSONObject responseJSON = new JSONObject(response);
        totalResults = responseJSON.getJSONObject("page").getInt("totalElements");
        return responseJSON.getJSONObject("_embedded").getJSONArray("neuronResources");
    }

    private static String buildPath(int resultsPerPage, int page, String cellType, String brainRegion) {
        // Java actually has a Builder class created specifically for Strings
        // We could do the same using String.format, but this is a bit more "elegant"
        StringBuilder path = new StringBuilder("https://neuromorpho.org/api/neuron/select?q=");
        if (cellType != null && brainRegion != null){
            path.append("cell_type:").append(cellType)
                    .append(",brain_region:").append(brainRegion);
        } else if (cellType != null){
            path.append("cell_type:").append(cellType);
        } else if (brainRegion != null){
            path.append("brain_region:").append(brainRegion);
        }
        path.append("&size=").append(resultsPerPage).append("&page=").append(page);
        return path.toString();
    }

    @Override
    public int getTotalResults() {
        return totalResults;
    }

    @Override
    public String getURL(String id) {
        return "https://neuromorpho.org/neuron_info.jsp?neuron_name=" + id;
    }

    private FetchedData parseEntry(JSONObject entry) {
        String title = entry.getJSONArray("brain_region").getString(0) + ", "
                + joinJSONArray(entry.getJSONArray("cell_type"));
        String id = entry.getString("neuron_name");
        LinkedHashMap<String, String> details = new LinkedHashMap<>();
        details.put(entryKeys[0], entry.getString("species"));
        details.put(entryKeys[1], entry.getString("strain"));
        details.put(entryKeys[2], entry.getString("soma_surface"));
        details.put(entryKeys[3], entry.getString("volume"));
        details.put(entryKeys[4], entry.getJSONArray("reference_doi").getString(0));
        return new FetchedData(title, id, getURL(id), Database.NeuroMorpho, details);
    }
    private static String joinJSONArray(JSONArray jsonArr) {
        // Different databases have different structures of storing
        // information inside JSONArray
        List<String> list = new ArrayList<>();
        for (int i = 0; i < jsonArr.length(); i++) {
            list.add(jsonArr.getString(i));
        }
        return String.join(", ", list);
    }
}
