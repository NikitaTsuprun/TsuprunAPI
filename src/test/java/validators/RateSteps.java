package validators;

import io.qameta.allure.Step;
import utils.PropertyReader;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class RateSteps {
    private static final String RATE_URL = PropertyReader.getProperty("onliner.rate_url");
    private static final String RATE_TYPE = PropertyReader.getProperty("onliner.rate_type");

    @Step("Запрашиваем курс валюты {currency}")
    public String getResponse(String currency) {
        return given()
                .log().all()
                .when()
                .get("%s?currency=%s&type=%s".formatted(RATE_URL, currency, RATE_TYPE))
                .then().log().all()
                .statusCode(200)
                .header("Content-Type", containsString("application/json"))
                .body("amount", matchesPattern("^\\d+,\\d{4}$"))
                .body("$", hasKey("amount"))
                .body("$", hasKey("grow"))
                .body("$", hasKey("scale"))
                .extract().asString();
    }
}
