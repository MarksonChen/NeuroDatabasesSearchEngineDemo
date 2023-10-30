package entity;

import java.io.Serializable;
import java.util.Map;

abstract public class FetchedData implements Serializable {
    String id;
    String title;
    Map<String, String> info;
}
