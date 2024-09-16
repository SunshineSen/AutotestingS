package org.example;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class HttpClientWrapper {

    private final CloseableHttpClient httpClient;

    public HttpClientWrapper() {
        this.httpClient = HttpClients.createDefault();
    }

    public CloseableHttpResponse executeGetRequest(String url) throws IOException {
        HttpGet request = new HttpGet(url);
        return httpClient.execute(request);
    }

    public void close() throws IOException {
        this.httpClient.close();
    }
}