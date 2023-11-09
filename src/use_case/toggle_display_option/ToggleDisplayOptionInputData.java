package use_case.toggle_display_option;

public class ToggleDisplayOptionInputData {
    private final int databaseIndex;
    private final String entryKey;

    public ToggleDisplayOptionInputData(int databaseIndex, String entryKey) {
        this.databaseIndex = databaseIndex;
        this.entryKey = entryKey;
    }

    public int getDatabaseIndex() {
        return databaseIndex;
    }

    public String getEntryKey() {
        return entryKey;
    }
}
