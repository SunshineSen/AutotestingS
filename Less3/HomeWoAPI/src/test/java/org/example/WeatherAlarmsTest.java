package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class WeatherAlarmsTest {
    private static final String apiKey = "EtP3EK12I0Bd6HlAA0xyHqKAG5PkX8GC";
    private static final String locationKey = "295382";
    private static final String urlT = "http://dataservice.accuweather.com/alarms/v1/10day/%s?apikey=%s";

    private HttpClientWrapper httpClientWrapper;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        httpClientWrapper = new HttpClientWrapper();
        objectMapper = new ObjectMapper();
    }

    @AfterEach
    public void tearDown() throws IOException {
        httpClientWrapper.close();
    }

    @Test /* сервер возвращает ответ 200 */
    public void requestIsSuccessful() throws IOException {
        String url = String.format(urlT, locationKey, apiKey);
        try (CloseableHttpResponse response = httpClientWrapper.executeGetRequest(url)) {
            int statusCode = response.getStatusLine().getStatusCode();
            assertEquals(200, statusCode, "HTTP request was not successful");

            assertNotNull(response.getEntity(), "Response entity is null");

            // Read JSON response
            WeatherAlarm[] jsonResponse = objectMapper.readValue(response.getEntity().getContent(), WeatherAlarm[].class);
            assertNotNull(jsonResponse, "JSON response could not be parsed");
        }
    }
    @Test /* провериьт формат ответа */
    public void responseIsJSON() throws IOException {
        String url = String.format(urlT, locationKey, apiKey);
        try (CloseableHttpResponse response = httpClientWrapper.executeGetRequest(url)) {
            String contentType = response.getEntity().getContentType().getValue();
            assertTrue(contentType.contains("application/json"), "Response is not JSON");
        }
    }
    @Test /* есть необходимые поля*/
    public void responseContainsNecessaryFields() throws IOException {
        String url = String.format(urlT, locationKey, apiKey);
        try (CloseableHttpResponse response = httpClientWrapper.executeGetRequest(url)) {
            WeatherAlarm[] jsonData = objectMapper.readValue(response.getEntity().getContent(), WeatherAlarm[].class);

            assertTrue(jsonData.length > 0, "Response array is empty");

            for (WeatherAlarm item : jsonData) {
                assertNotNull(item.getDate(), "Item does not contain 'Date'");
                assertNotNull(item.getAlarmType(), "Item does not contain 'AlarmType'");
            }
        }
    }
    @Test /* есть 10 дней данных */
    public void responseContains10DaysOfData() throws IOException {
        String url = String.format(urlT, locationKey, apiKey);
        try (CloseableHttpResponse response = httpClientWrapper.executeGetRequest(url)) {
            WeatherAlarm[] jsonData = objectMapper.readValue(response.getEntity().getContent(), WeatherAlarm[].class);

            assertEquals(10, jsonData.length, "Response does not contain 10 days of data");
        }
    }
}