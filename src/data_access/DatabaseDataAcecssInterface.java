package data_access;

import entity.FetchedData;
import entity.Query;

import java.io.IOException;
import java.util.*;

public interface DatabaseDataAcecssInterface {
    List<FetchedData> query(Query query, int resultsPerPage, int page) throws IOException;
//    List<FetchedData> getRelevantEntries(String keywords);
    int getTotalResults();
    // Precondition: query is called once before this method is called
    String getURL(String id);
    String[] getEntryKeys();

    static List<String> getRelevantItems(Set<String> entries, String keywords) {
        // This is a method to get relevant elements from a set of Strings using
        // keywords. Only the instances of this interface will use this method,
        // so we believe it is simpler to write it here than use a separate Strategy
        //  class to isolate this responsibility.

        Map<String, Integer> keywordsCount = new HashMap<>();
        for (String entry: entries){
            String lowerCaseEntry = entry.toLowerCase();
            int count = 0;
            for (String keyword: splitWords(keywords)){
                if (lowerCaseEntry.contains(keyword)){
                    count++;
                }
            }
            if (count > 0){
                keywordsCount.put(entry, count);
            }
        }
        List<String> results = new ArrayList<>(keywordsCount.keySet());
        results.sort(Comparator.comparingInt(String::length));
        results.sort(Comparator.comparing(keywordsCount::get).reversed());

        return results;
    }
    static String[] splitWords(String keywords){
        return keywords.toLowerCase().split("\\s+");
    }
}
