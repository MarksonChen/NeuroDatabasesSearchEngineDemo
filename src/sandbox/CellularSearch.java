package sandbox;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

public class CellularSearch {
//    public static void main(String[] args) {
//        String search = "Retina ganglion channel";
//
//        try {
////            Map<String, String> neuroElectroNeuronNames = getNeuroElectroNeuronNames();
////            Map<String, String> modelDBModelNames = getModelDBModelNames();
//            Set<String> neuroMorphoBrainRegions = getNeuroMorphoBrainRegions();
//            Set<String> neuroMorphoCellTypes = getNeuroMorphoCellTypes();
//
////            System.out.println(getRelevantItems(neuroElectroNeuronNames.keySet(), search));
////            System.out.println(getRelevantItems(modelDBModelNames.keySet(), search));
//            System.out.println(getRelevantItems(neuroMorphoBrainRegions, search));
//            System.out.println(getRelevantItems(neuroMorphoCellTypes, search));
//
////            refetchBaseData();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private static List<String> getRelevantItems(Set<String> set, String search) {
//        Map<String, Integer> keywordsCount = new HashMap<>();
//        for (String item: set){
//            item = item.toLowerCase();
//            int count = 0;
//            for (String word: search.toLowerCase().split(" ")){
//                if (item.contains(word)){
//                    count++;
//                }
//            }
//            if (count > 0){
//                keywordsCount.put(item, count);
//            }
//        }
//        List<String> results = new ArrayList<>(keywordsCount.keySet());
//        Collections.sort(results, Comparator.comparingInt(String::length));
//        Collections.sort(results, Comparator.comparing(keywordsCount::get).reversed());
//
//        for (String result: results){
//            System.out.println(keywordsCount.get(result) + ": " + result);
//        }
//        return results;
//    }
//
//    /*
//    * ModelDB: Title, id, description, neuron, keywords
//    * NeuroElectro: Neuron name (title), id, input resistance, resting membrane potential, spike threshold
//    * (format the values as 00.00 ± 00.00: two decimal digits or 4 sig figs)
//    * NeuroMorpho: title (123 Brain region, 1 cell type), id (cell name), Species Name, Volume, branches,
//    *               (Stems) (Bifurcations) (Dimension (Width x Depth x Height)) (Fractal Dimension)
//    * in DAO: id to URL method
//    * [See Detail] button instead of 20 HTTP pull requests
//    * */
//
////    public static void refetchBaseData() throws IOException {
////        String response = getResponse("https://modeldb.science/api/v1/models/name");
////        System.out.println(response);
////    }
//
//    private static Map<String, String> getModelDBModelNames() throws IOException {
//        // https://modeldb.science/api/v1/models/name
//        Map<String, String> modelDBModelNames = new HashMap<>();
//        String modelNames = getResponse("https://modeldb.science/api/v1/models/name");
//        String modelID = getResponse("https://modeldb.science/api/v1/models");
//        JSONArray namesArray = new JSONArray(modelNames);
//        JSONArray idArray = new JSONArray(modelID);
//        for (int i = 0; i < namesArray.length(); i++) {
//            modelDBModelNames.put(namesArray.getString(i), Integer.toString(idArray.getInt(i)));
//        }
//
//        return modelDBModelNames;
//    }
//
//    private static String getResponse(String path) throws IOException {
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        Request request = new Request.Builder()
//                .url(path)
//                .build();
//
//        Response response = client.newCall(request).execute();
//        return response.body().string();
//    }
//
//    private static Map<String, String> getNeuroElectroNeuronNames() throws IOException {
//        // https://neuroelectro.org/api/1/n/
//        Map<String, String> neuroElectroNeurons = new HashMap<>();
//
//        String response = getResponse("https://neuroelectro.org/api/1/n/");
//        JSONObject responseObject = new JSONObject(response);
//        JSONArray neuronsArray = responseObject.getJSONArray("objects");
//        for (int i = 0; i < neuronsArray.length(); i++) {
//            JSONObject neuronEntry = neuronsArray.getJSONObject(i);
//            if (neuronEntry.isNull("nlex_id")){
//                continue;
//            }
//            String id = neuronEntry.getString("nlex_id");
//            if (!neuroElectroNeurons.containsValue(id)){
//                neuroElectroNeurons.put(neuronEntry.getString("name"), id);
//            }
//        }
//
//        return neuroElectroNeurons;
//    }
//
//    private static Set<String> getNeuroMorphoBrainRegions() throws IOException {
//        // https://neuromorpho.org/api/neuron/fields/brain_region
//        Set<String> neuroMorphoBrainRegions = new HashSet<>();
//
//        String response = getResponse("https://neuromorpho.org/api/neuron/fields/brain_region");
//        JSONObject responseObject = new JSONObject(response);
//        JSONArray neuronsArray = responseObject.getJSONArray("fields");
//        for (int i = 0; i < neuronsArray.length(); i++) {
//            neuroMorphoBrainRegions.add(neuronsArray.getString(i));
//        }
//
//        return neuroMorphoBrainRegions;
//    }
//
//    private static Set<String> getNeuroMorphoCellTypes() throws IOException {
//        // https://neuromorpho.org/api/neuron/fields/cell_type
//        Set<String> neuroMorphoCellTypes = new HashSet<>();
//
//        String response = getResponse("https://neuromorpho.org/api/neuron/fields/cell_type");
//        JSONObject responseObject = new JSONObject(response);
//        JSONArray neuronsArray = responseObject.getJSONArray("fields");
//        for (int i = 0; i < neuronsArray.length(); i++) {
//            neuroMorphoCellTypes.add(neuronsArray.getString(i));
//        }
//
//        return neuroMorphoCellTypes;
//    }
}
