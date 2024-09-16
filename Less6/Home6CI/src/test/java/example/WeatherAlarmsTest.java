package example;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.qameta.allure.Step;
import io.qameta.allure.junit5.AllureJunit5;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WeatherAlarmsTest {
    private static final String LOCATION_KEY = "295382";
    private static final String API_KEY = "EtP3EK12I0Bd6HlAA0xyHqKAG5PkX8GC";
    private static final String URL = "/alarms/v1/10day/" + LOCATION_KEY + "?apikey=" + API_KEY;

    private static WireMockServer wireMockServer;
    private static final Logger logger = LoggerFactory.getLogger(WeatherAlarmsTest.class);

    @BeforeAll
    public static void setup() {
        logger.info("Starting WireMock server...");
        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(8080));
        wireMockServer.start();
        RestAssured.filters(new AllureRestAssured());
        logger.info("WireMock server started.");
    }

    @AfterAll
    public static void teardown() {
        logger.info("Stopping WireMock server...");
        wireMockServer.stop();
        logger.info("WireMock server stopped.");
    }

    @BeforeEach
    public void setupEach() {
        logger.info("Resetting WireMock server configurations...");
        configureFor("localhost", 8080);
        wireMockServer.resetAll();

        logger.info("Setting up stubs for WireMock server...");
        // Mocking a successful response
        wireMockServer.stubFor(get(urlEqualTo(URL))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("weather-alarms-10days.json"))); // Use a static JSON file for the response
    }

    @Test
    @Step("Checking that request is successful")
    public voidrequestIsSuccessful() {
        logger.info("Running test: requestIsSuccessful");
        given()
                .when()
                .get("http://localhost:8080" + URL)
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", not(empty()));
        logger.info("Test requestIsSuccessful passed.");
    }

    @Test
    @Step("Checking that response is JSON format")
    public void responseIsJSON() {
        logger.info("Running test: responseIsJSON");
        given()
                .when()
                .get("http://localhost:8080" + URL)
                .then()
                .assertThat()
                .contentType(ContentType.JSON);
        logger.info("Test responseIsJSON passed.");
    }

    @Test
    @Step("Checking that response contains necessary fields")
    public void responseContainsNecessaryFields() {
        logger.info("Running test: responseContainsNecessaryFields");
        given()
                .when()
                .get("http://localhost:8080" + URL)
                .then()
                .assertThat()
                .body("$", everyItem(hasKey("Date")))
                .body("$", everyItem(hasKey("AlarmType")));
        logger.info("Test responseContainsNecessaryFields passed.");
    }

    @Test
    @Step("Checking that response contains 10 days of data")
    public void responseContains10DaysOfData() {
        logger.info("Running test: responseContains10DaysOfData");
        given()
                .when()
                .get("http://localhost:8080" + URL)
                .then()
                .assertThat()
                .body("size()", is(10));
        logger.info("Test responseContains10DaysOfData passed.");
    }
}