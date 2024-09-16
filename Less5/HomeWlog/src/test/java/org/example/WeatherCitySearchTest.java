package org.example;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class WeatherCitySearchTest {
    private static WireMockServer wireMockServer;
    private final String API_KEY = "EtP3EK12I0Bd6HlAA0xyHqKAG5PkX8GC";
    private static final Logger logger = LoggerFactory.getLogger(WeatherCitySearchTest.class);

    @BeforeAll    public static void setup() {
        logger.info("Starting WireMock server...");
        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(8080));
        wireMockServer.start();
        RestAssured.baseURI = "http://localhost:8080";

        logger.info("Setting up stubs for WireMock server...");
        wireMockServer.stubFor(get(urlPathEqualTo("/locations/v1/cities/search"))
                .withQueryParam("apikey", equalTo("EtP3EK12I0Bd6HlAA0xyHqKAG5PkX8GC"))
                .withQueryParam("q", equalTo("Saratov"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("city-search-response.json")
                        .withStatus(200)));
    }
    @AfterAll
    public static void teardown() {
        logger.info("Stopping WireMock server...");
        wireMockServer.stop();
        logger.info("WireMock server stopped.");
    }

    @Test /* JSON -> список объектов City, первый город в ответе содержит необходимые свойства */
    public void testFirstElementHasNecessaryProperties() throws IOException {
        logger.info("Running test: testFirstElementHasNecessaryProperties");
        String searchQuery = "Saratov";
        Response response = given()
                .queryParam("apikey", API_KEY)
                .queryParam("q", searchQuery).when()
                .get("/locations/v1/cities/search")
                .then()
                .extract()
                .response();

        ObjectMapper objectMapper = new ObjectMapper();
        List<City> cities = objectMapper.readValue(response.asString(), new TypeReference<List<City>>() {});
        if (!cities.isEmpty()) {
            City firstCity = cities.get(0);
            Assertions.assertNotNull(firstCity.getKey(), "First city should contain 'Key'");
            Assertions.assertNotNull(firstCity.getLocalizedName(), "First city should contain 'LocalizedName'");
            Assertions.assertNotNull(firstCity.getCountry(), "First city should contain 'Country'");
            Assertions.assertNotNull(firstCity.getCountry().getLocalizedName(), "Country should contain 'LocalizedName'");
        }
        logger.info("Test testFirstElementHasNecessaryProperties passed.");
    }

    @Test /* JSON -> список объектов City, каждый город в ответе имеет заданные свойства */
    public void testAllElementsHaveNecessaryProperties() throws IOException {
        logger.info("Running test: testAllElementsHaveNecessaryProperties");
        String searchQuery = "Saratov";
        Response response = given()
                .queryParam("apikey", API_KEY)
                .queryParam("q", searchQuery)
                .when()
                .get("/locations/v1/cities/search")
                .then()
                .extract()
                .response();

        ObjectMapper objectMapper = new ObjectMapper();
        List<City> cities = objectMapper.readValue(response.asString(), new TypeReference<List<City>>() {});
        for (City city : cities) {
            Assertions.assertNotNull(city.getKey(), "City should contain 'Key'");
            Assertions.assertNotNull(city.getLocalizedName(), "City should contain 'LocalizedName'");
            Assertions.assertNotNull(city.getCountry(), "City should contain 'Country'");
            Assertions.assertNotNull(city.getCountry().getLocalizedName(), "Country should contain 'LocalizedName'");
        }
        logger.info("Test testAllElementsHaveNecessaryProperties passed.");
    }

    @Test /* JSON -> список объектов City, имя первого города в ответе содержит текст запроса */
    public void testFirstCityMatchesSearchQuery() throws IOException {
        logger.info("Running test: testFirstCityMatchesSearchQuery");
        String searchQuery = "Saratov";
        Response response = given()
                .queryParam("apikey", API_KEY)
                .queryParam("q", searchQuery)
                .when()
                .get("/locations/v1/cities/search")
                .then()
                .extract()
                .response();

        ObjectMapper objectMapper = new ObjectMapper();
        List<City> cities = objectMapper.readValue(response.asString(), new TypeReference<List<City>>() {});
        if (!cities.isEmpty()) {
            City firstCity = cities.get(0);
            Assertions.assertTrue(firstCity.getLocalizedName().contains(searchQuery),
                    "First city should match the search query");
        }
        logger.info("Test testFirstCityMatchesSearchQuery passed.");
    }
}