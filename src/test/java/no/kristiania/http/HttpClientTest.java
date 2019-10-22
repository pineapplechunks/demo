package no.kristiania.http;

import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpClientTest {
    @Test
    void shouldReturnStatusCode() {
        HttpClient httpClient = new HttpClient("urlecho.appspot.com", 80, "/echo");
        HttpResponse httpResponse = httpClient.getResponse();

        assertEquals(200, httpResponse.getStatusCode());
    }
}

