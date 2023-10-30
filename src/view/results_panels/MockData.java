package view.results_panels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockData {
    public static Map<String, String> getMockData(){
        Map<String, String> data = new HashMap<>();
        data.put("title", "Salamander retinal ganglion cell: ion channels (Fohlmeister, Miller 1997)");
        data.put("description", "A realistic five (5) channel spiking model reproduces the bursting behavior of tiger salamander ganglion cells in the retina. Please see the readme for more information.");
        data.put("id", "ModelDB3673");
        data.put("created", "2002-02-11T13:08:56");
        data.put("keywords", "Activity Patterns, Ion Channel Kinetics, Influence of Dendritic Geometry, Detailed Neuronal Models, Calcium dynamics");
        return data;
    }

    public static List<Map<String, String>> getResults(){
        List<Map<String, String>> results = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            results.add(getMockData());
        }
        return results;
    }

    public static List<Map<String, String>>[] getAllResults(){
        List<Map<String, String>>[] results = new ArrayList[3];
        results[0] = getResults();
        results[1] = getResults();
        results[2] = getResults();
        return results;
    }
}
