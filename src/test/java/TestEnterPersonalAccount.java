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
import service.WebDriverHelperFactory;
import service.YandexDriverHelper;

import java.time.Duration;

public class TestEnterPersonalAccount {
    private static final String LOGIN_URL = SiteUrls.STELLAR_BURGERS_URL + SiteUrls.LOGIN_URL;

    private static final String PERSONAL_ACCOUNT_URL = SiteUrls.STELLAR_BURGERS_URL + SiteUrls.PROFILE_URL;

    private static final String email = "Nikitina3@email.org";

    private static final String password = "123456";

    private static final String username = "Мария";

    private final WebDriverHelper webDriverHelper;

    private WebDriver driver;

    public TestEnterPersonalAccount() {
        webDriverHelper = new WebDriverHelperFactory().createWebDriverHelper();
    }


    @Step("Логин перед проверкой перехода в личный кабинет")
    private void login() {
        driver.get(LOGIN_URL);
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
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Тест перехода в личный каинет. " + webDriverHelper.getCaption()));
        MainPage mainPage = new MainPage(driver);
        mainPage.clickEnterPersonalCabinetButton();
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.urlToBe(PERSONAL_ACCOUNT_URL));
        Assert.assertEquals("После перехода в личный кабинет не отобразилась соответствующая страница", PERSONAL_ACCOUNT_URL, driver.getCurrentUrl());
    }


    @After
    @Step("Удаление пользователя после теста")
    public void after() {
        driver.quit();
        UserApi.tryLoginAndDelete(new LoginRequestBody(email, password));
    }
}
