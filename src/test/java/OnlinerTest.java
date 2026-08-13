import io.qameta.allure.*;
import io.qameta.allure.testng.AllureTestNg;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import validators.RateSteps;
import validators.RateValidator;

import static enums.Currency.*;

@Epic("Курсы валют Onliner")
@Feature("Курс по НБРБ")
@Owner("Tsuprun Nikita @corazonrosi20")
@Listeners({AllureTestNg.class})
public class OnlinerTest {
    private final RateSteps steps = new RateSteps();
    private final RateValidator validator = new RateValidator();

    @DataProvider(name = "currencies")
    public Object[][] currencyProvider() {
        return new Object[][]{
                {USD.getCode()},
                {EUR.getCode()},
                {RUB.getCode()}
        };
    }

    @Story("Получение курса валюты")
    @Test(dataProvider = "currencies", description = "Курс валюты приходит в корректном формате")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Проверка схемы, заголовков и полей")
    @TmsLink("TsuprunAPI")
    @Issue("TsuprunAPI")
    public void checkRates(String currency) {
        steps.getResponse(currency);
        validator.validateSchema(currency, 200);
        validator.validateHeaders(currency);
        validator.validateKeys(currency);
    }
}
