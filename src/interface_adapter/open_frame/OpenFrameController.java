package interface_adapter.open_frame;

import use_case.open_frame.OpenFrameInputBoundary;
import use_case.open_frame.OpenFrameInputData;
import use_case.switch_view.SwitchViewInputData;

public class OpenFrameController {
    private final OpenFrameInputBoundary openFrameInteractor;

    public OpenFrameController(OpenFrameInputBoundary openFrameInteractor) {
        this.openFrameInteractor = openFrameInteractor;
    }

    public void execute(String viewName) {
        openFrameInteractor.execute(new OpenFrameInputData(viewName));
    }
}
