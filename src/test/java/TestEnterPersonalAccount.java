import constants.SiteUrls;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import model.api.UserApi;
import model.body.LoginRequestBody;
import model.body.RegisterRequestBody;
import model.pageobject.LoginPage;
import model.pageobject.MainPage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import service.ChromeDriverHelper;
import service.WebDriverHelper;
import service.YandexDriverHelper;

import java.time.Duration;

@RunWith(Parameterized.class)
public class TestEnterPersonalAccount {
    private static final String loginUrl = SiteUrls.STELLAR_BURGERS_URL + SiteUrls.LOGIN_URL;

    private static final String personalAccountUrl = SiteUrls.STELLAR_BURGERS_URL + SiteUrls.PROFILE_URL;

    private final String email;

    private final String password;

    private final String username;

    private final WebDriverHelper webDriverHelper;

    private final String comment;

    private WebDriver driver;

    public TestEnterPersonalAccount(String email, String password, String username, WebDriverHelper webDriverHelper, String comment) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.webDriverHelper = webDriverHelper;
        this.comment = comment;
    }

    @Parameterized.Parameters(name = "{4}")
    public static Object[][] parameters() {
        return new Object[][]{
                {"Nikitina3@email.org", "123456", "Мария", new ChromeDriverHelper(), "Chrome"},
                {"Nikitina3@email.org", "123456", "Мария", new YandexDriverHelper(), "Яндекс.Браузер"},
        };
    }

    @Step("Логин перед проверкой перехода в личный кабинет")
    private void login() {
        driver.get(loginUrl);
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue("Успех логина не соответсвует ожидаемому", loginPage.tryLogin(email, password));
    }

    @Before
    @Step("Создание пользователя перед тестом входа в личный кабинет")
    public void before() {
        driver = webDriverHelper.setUpDriver();
        UserApi.register(new RegisterRequestBody(email, password, username));

        login();
    }

    @Test
    public void enterPersonalCabinetTest() {
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Тест перехода в личный каинет. " + comment));
        MainPage mainPage = new MainPage(driver);
        mainPage.clickEnterPersonalCabinetButton();
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.urlToBe(personalAccountUrl));
        Assert.assertEquals("После перехода в личный кабинет не отобразилась соответствующая страница", personalAccountUrl, driver.getCurrentUrl());
    }


    @After
    @Step("Удаление пользователя после теста")
    public void after() {
        driver.quit();
        UserApi.tryLoginAndDelete(new LoginRequestBody(email, password));
    }
}
