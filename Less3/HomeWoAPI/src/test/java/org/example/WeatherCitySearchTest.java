package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.given;
public class WeatherCitySearchTest {

    private final String url = "http://dataservice.accuweather.com";
    private final String apiKey = 	"EtP3EK12I0Bd6HlAA0xyHqKAG5PkX8GC";

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://dataservice.accuweather.com";
    }

    @Test /* сервер возвращает ответ 200 */
    public void testStatusCodeIs200() {
        String searchQuery = "Saratov";

        given()
                .queryParam("apikey", apiKey)
                .queryParam("q", searchQuery)
                .when()
                .get("/locations/v1/cities/search")
                .then()
                .statusCode(200);
    }

    @Test /* время ответа сервера меньше 2000 миллисекунд */
    public void testResponseTimeIsLessThan2000ms() {
        String searchQuery = "Saratov";

        given()
                .queryParam("apikey", apiKey)
                .queryParam("q", searchQuery)
                .when()
                .get("/locations/v1/cities/search")
                .then()
                .time(Matchers.lessThan(2000L));
    }

    @Test /* JSON -> список объектов City, список не пуст */
    public void testResponseIsNotEmpty() throws IOException {
        String searchQuery = "Saratov";
        Response response = given()
                .queryParam("apikey", apiKey)
                .queryParam("q", searchQuery)
                .when()
                .get("/locations/v1/cities/search")
                .then()
                .extract()
                .response();

        ObjectMapper mapper = new ObjectMapper();
        List<City> cities = mapper.readValue(response.asString(), new TypeReference<List<City>>(){});
        Assertions.assertFalse(cities.isEmpty(), "Specify at least one city");
    }

    @Test /* JSON -> список объектов City, первый город в ответе содержит необходимые свойства */
    public void testFirstElementHasNecessaryProperties() throws IOException {
        String searchQuery = "Saratov";
        Response response = given()
                .queryParam("apikey", apiKey)
                .queryParam("q", searchQuery)
                .when()
                .get("/locations/v1/cities/search")
                .then()
                .extract()
                .response();

        ObjectMapper mapper = new ObjectMapper();
        List<City> cities = mapper.readValue(response.asString(), new TypeReference<List<City>>(){});
        if (!cities.isEmpty()) {
            City firstCity = cities.get(0);
            Assertions.assertNotNull(firstCity.getKey(), "First city should contain 'Key'");
            Assertions.assertNotNull(firstCity.getLocalizedName(), "First city should contain 'LocalizedName'");
            Assertions.assertNotNull(firstCity.getCountry(), "First city should contain 'Country'");
            Assertions.assertNotNull(firstCity.getCountry().getLocalizedName(), "Country should contain 'LocalizedName'");
        }
    }

    @Test /* JSON -> список объектов City.каждый город в ответе имеет заданые свойства */
    public void testAllElementsHaveNecessaryProperties() throws IOException {
        String searchQuery = "Saratov";
        Response response = given()
                .queryParam("apikey", apiKey)
                .queryParam("q", searchQuery)
                .when()
                .get("/locations/v1/cities/search")
                .then()
                .extract()
                .response();

        ObjectMapper mapper = new ObjectMapper();
        List<City> cities = mapper.readValue(response.asString(), new TypeReference<List<City>>(){});
        for (City city : cities) {
            Assertions.assertNotNull(city.getKey(), "City should contain 'Key'");
            Assertions.assertNotNull(city.getLocalizedName(), "City should contain 'LocalizedName'");
            Assertions.assertNotNull(city.getCountry(), "City should contain 'Country'");
            Assertions.assertNotNull(city.getCountry().getLocalizedName(), "Country should contain 'LocalizedName'");
        }
    }

    @Test /* JSON -> список объектов City. имя первого города в ответе содержит текст запроса.*/
    public void testFirstCityMatchesSearchQuery() throws IOException {
        String searchQuery = "Saratov";
        Response response = given()
                .queryParam("apikey", apiKey)
                .queryParam("q", searchQuery)
                .when()
                .get("/locations/v1/cities/search")
                .then()
                .extract()
                .response();

        ObjectMapper mapper = new ObjectMapper();
        List<City> cities = mapper.readValue(response.asString(), new TypeReference<List<City>>(){});
        if (!cities.isEmpty()) {
            City firstCity = cities.get(0);
            Assertions.assertTrue(firstCity.getLocalizedName().contains(searchQuery),
                    "First city should match the search query");
        }
    }
}
