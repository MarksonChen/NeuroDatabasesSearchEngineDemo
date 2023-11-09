package entity;

import java.io.Serializable;

public class Query implements Serializable {
    private final String keywords;

    public Query(String keywords) {
        this.keywords = keywords;
    }

    public String getKeywords() {
        return keywords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Query that = (Query) o;
        return keywords.equals(that.keywords);
    }
}
