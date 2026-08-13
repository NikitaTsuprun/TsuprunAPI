package validators;

import io.qameta.allure.Step;
import utils.PropertyReader;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasKey;

public class RateValidator {
    private static final String RATE_URL = PropertyReader.getProperty("onliner.rate_url");
    private static final String RATE_TYPE = PropertyReader.getProperty("onliner.rate_type");

    @Step("Проверяем ответ по схеме, ожидаемый код {statusCode}")
    public void validateSchema(String currency, int statusCode) {
        given()
                .log().all()
                .when()
                .get("%s?currency=%s&type=%s".formatted(RATE_URL, currency, RATE_TYPE))
                .then().log().all()
                .body(matchesJsonSchemaInClasspath("schemas/rate_schema.json"))
                .statusCode(statusCode);
    }

    @Step("Проверяем заголовки ответа для {currency}")
    public void validateHeaders(String currency) {
        given()
                .log().all()
                .when()
                .get("%s?currency=%s&type=%s".formatted(RATE_URL, currency, RATE_TYPE))
                .then().log().all()
                .statusCode(200)
                .header("Content-Type", containsString("application/json"));
    }

    @Step("Проверяем наличие ключей в ответе для {currency}")
    public void validateKeys(String currency) {
        given()
                .log().all()
                .when()
                .get("%s?currency=%s&type=%s".formatted(RATE_URL, currency, RATE_TYPE))
                .then().log().all()
                .statusCode(200)
                .body("$", hasKey("amount"))
                .body("$", hasKey("grow"))
                .body("$", hasKey("scale"));
    }
}
