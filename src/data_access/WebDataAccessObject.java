package data_access;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import use_case.open_website.WebDataAccessInterface;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebDataAccessObject implements WebDataAccessInterface {
    private final OkHttpClient client;
    private final Desktop desktop;

    public WebDataAccessObject() {
        client = new OkHttpClient().newBuilder()
                .build();
        desktop = Desktop.getDesktop();
    }

    @Override
    public String getResponse(String path) throws IOException {
        Request request = new Request.Builder()
                .url(path)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    @Override
    public void openWebsite(String path) throws URISyntaxException, IOException {
        desktop.browse(new URI(path));
    }
}
