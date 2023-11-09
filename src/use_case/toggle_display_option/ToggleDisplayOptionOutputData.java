package use_case.toggle_display_option;

public class ToggleDisplayOptionOutputData {
    private final int databaseIndex;
    private final String entryKey;

    public ToggleDisplayOptionOutputData(int databaseIndex, String entryKey) {
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
