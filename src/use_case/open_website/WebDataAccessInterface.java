package use_case.open_website;

import java.io.IOException;
import java.net.URISyntaxException;

public interface WebDataAccessInterface {

    String getResponse(String path) throws IOException;
    void openWebsite(String path) throws URISyntaxException, IOException;
}
