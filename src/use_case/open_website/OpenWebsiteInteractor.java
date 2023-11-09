package use_case.open_website;

import java.io.IOException;
import java.net.URISyntaxException;

public class OpenWebsiteInteractor implements OpenWebsiteInputBoundary{
    private final WebDataAccessInterface webDAO;
    private final OpenWebsiteOutputBoundary openWebsitePresenter;

    public OpenWebsiteInteractor(WebDataAccessInterface webDAO, OpenWebsiteOutputBoundary openWebsitePresenter) {
        this.webDAO = webDAO;
        this.openWebsitePresenter = openWebsitePresenter;
    }

    @Override
    public void execute(String url) {
        try {
            webDAO.openWebsite(url);
        } catch (URISyntaxException | IOException e) {
            openWebsitePresenter.prepareFailView("Error: Website " + url + " cannot be opened");
        }
    }
}
