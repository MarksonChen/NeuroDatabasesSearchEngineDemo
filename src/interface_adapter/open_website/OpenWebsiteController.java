package interface_adapter.open_website;

import use_case.open_website.OpenWebsiteInputBoundary;

public class OpenWebsiteController {
    private final OpenWebsiteInputBoundary openWebsiteInteractor;

    public OpenWebsiteController(OpenWebsiteInputBoundary openWebsiteInteractor) {
        this.openWebsiteInteractor = openWebsiteInteractor;
    }

    public void execute(String url) {
        openWebsiteInteractor.execute(url);
    }
}
