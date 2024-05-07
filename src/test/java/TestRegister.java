import constants.ApiUrls;
import constants.SiteUrls;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import model.api.UserApi;
import model.body.LoginRequestBody;
import model.pageobject.RegisterPage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import service.ChromeDriverHelper;
import service.WebDriverHelper;
import service.YandexDriverHelper;

@RunWith(Parameterized.class)
public class TestRegister {
    private final String email;

    private final String password;

    private final String username;
    private final WebDriverHelper webDriverHelper;
    private final boolean isSuccessExpected;
    private final String comment;
    private WebDriver driver;

    public TestRegister(String email, String password, String username, WebDriverHelper webDriverHelper, boolean isSuccessExpected, String comment) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.webDriverHelper = webDriverHelper;
        this.isSuccessExpected = isSuccessExpected;
        this.comment = comment;
    }

    @Parameterized.Parameters(name = "{5}")
    public static Object[][] parameters() {
        return new Object[][]{
                {"Nikitina3@email.org", "123456", "Мария", new ChromeDriverHelper(), true, "Позитивная проверка регистрации: пароль 6 символов. Chrome"},
                {"Nikitina3@email.org", "1234567", "Мария", new ChromeDriverHelper(), true, "Позитивная проверка регистрации: пароль 7 символов. Chrome"},
                {"Nikitina3@email.org", "123456", "Мария", new YandexDriverHelper(), true, "Позитивная проверка регистрации: пароль 6 символов. Яндекс.Браузер"},
                {"Nikitina3@email.org", "1234567", "Мария", new YandexDriverHelper(), true, "Позитивная проверка регистрации: пароль 7 символов. Яндекс.Браузер"},
                {"Nikitina3@email.org", "12345", "Мария", new ChromeDriverHelper(), false, "Негативная проверка регистрации: пароль 5 символов. Chrome"},
                {"Nikitina3@email.org", "1", "Мария", new ChromeDriverHelper(), false, "Негативная проверка регистрации: пароль 1 символ. Chrome"},
                {"Nikitina3@email.org", "12345", "Мария", new YandexDriverHelper(), false, "Негативная проверка регистрации: пароль 5 символов. Яндекс.Браузер"},
                {"Nikitina3@email.org", "1", "Мария", new YandexDriverHelper(), false, "Негативная проверка регистрации: пароль 1 символ. Яндекс.Браузер"},
        };
    }

    @Before
    @Step("Удаление пользователя перед тестом")
    public void before() {
        UserApi.tryLoginAndDelete(new LoginRequestBody(email, password));
        driver = webDriverHelper.setUpDriver();
    }

    @Test
    public void testRegister() {
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName(comment));
        driver.get(ApiUrls.STELLAR_BURGERS_URL + SiteUrls.REGISTER_URL);

        RegisterPage registerPage = new RegisterPage(driver);
        Assert.assertEquals("Результат рабобты интерфейса не соответствуует ожидаемому", isSuccessExpected, registerPage.tryToRegister(username, email, password));

        String authToken = UserApi.loginAndGetToken(new LoginRequestBody(email, password));
        Assert.assertEquals("Проверка логина пользователя через API не соответствует ожидаемому результату", isSuccessExpected, authToken != null && !authToken.isEmpty());
    }

    @After
    @Step("Удаление пользователя после теста")
    public void after() {
        driver.quit();
        UserApi.tryLoginAndDelete(new LoginRequestBody(email, password));
    }
}
