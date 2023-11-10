package data_access;

import java.io.Serializable;
import java.util.Arrays;

public enum Database implements Serializable {
    NeuroMorpho, NeuroElectro, ModelDB;
    // They are not UPPERCASE because their name() methods will be used
    public static String[] getDatabaseNames() {
        return Arrays.stream(Database.values())
                .map(Database::name).toArray(String[]::new);
        // The order is the same as the order in the Database enum
        // Stream is more suitable for this job
    }
    public static final int length = Database.values().length;
    public static int indexOf(Database database){
        return Arrays.asList(Database.values()).indexOf(database);
        // Not the optimal method but gets the job done
    }
    public static Database get(int i) {
        return Database.values()[i];
    }
}
