package entity;

import data_access.Database;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class FetchedData implements Serializable {
    private final Database database;
    private final String id;
    private final String title;
    private LinkedHashMap<String, String> details;
    private String URL;
    // We require this to be LinkedHashMap because it preserves insertion order.
    // Many codes depend on this property, so we make this a hard requirement.
    /*
    * We discussed this with our TA, and we agreed that this is
    * the design decision that satisfies OCP the most.
    * */

    public FetchedData(String title, String id, String URL, Database database, LinkedHashMap<String, String> details) {
        this.title = title;
        this.id = id;
        this.URL = URL;
        this.database = database;
        this.details = details; // To be gotten when the user presses "Query for details"
    }

    public FetchedData(String title, String id, String URL, Database database) {
        this(title, id, URL, database,null);
        // The info are to be gotten when the user presses "Query for details"
    }

    public boolean hasDetails(){
        return details != null;
    }

    public void setDetails(LinkedHashMap<String, String> details){
        this.details = details;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LinkedHashMap<String, String> getDetails() {
        return details;
    }

    public Database getDatabase() {
        return database;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FetchedData that = (FetchedData) o;
        return database == that.database && id.equals(that.id);
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String url) {
        this.URL = url;
    }
}
