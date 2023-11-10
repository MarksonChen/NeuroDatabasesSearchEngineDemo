package use_case.toggle_display_option;

import use_case.toggle_display_option.ToggleDisplayOptionInputBoundary;
import use_case.toggle_display_option.ToggleDisplayOptionInputData;

public class ToggleDisplayOptionController {
    private final ToggleDisplayOptionInputBoundary toggleDisplayOptionInteractor;

    public ToggleDisplayOptionController(ToggleDisplayOptionInputBoundary toggleDisplayOptionInteractor) {
        this.toggleDisplayOptionInteractor = toggleDisplayOptionInteractor;
    }

    public void execute(int databaseIndex, String entryKey) {
        toggleDisplayOptionInteractor.execute(new ToggleDisplayOptionInputData(databaseIndex, entryKey));
    }
}
