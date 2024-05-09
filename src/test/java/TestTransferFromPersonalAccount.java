import constants.SiteUrls;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import model.api.UserApi;
import model.body.LoginRequestBody;
import model.body.RegisterRequestBody;
import model.pageobject.LoginPage;
import model.pageobject.MainPage;
import model.pageobject.ProfilePage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import service.WebDriverHelper;
import service.WebDriverHelperFactory;

import java.time.Duration;

public class TestTransferFromPersonalAccount {
    private static final String LOGIN_URL = SiteUrls.STELLAR_BURGERS_URL + SiteUrls.LOGIN_URL;

    private static final String PERSONAL_ACCOUNT_URL = SiteUrls.STELLAR_BURGERS_URL + SiteUrls.PROFILE_URL;

    private static final String MAIN_PAGE_URL = SiteUrls.STELLAR_BURGERS_URL + "/";

    private static final String email = "Nikitina3@email.org";

    private static final String password = "123456";

    private static final String username = "Мария";

    private final WebDriverHelper webDriverHelper;

    private WebDriver driver;

    public TestTransferFromPersonalAccount() {
        this.webDriverHelper = new WebDriverHelperFactory().createWebDriverHelper();
    }


    @Step("Логин перед проверкой")
    private void login() {
        driver.get(LOGIN_URL);
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue("Успех логина не соответсвует ожидаемому", loginPage.tryLogin(email, password));
    }

    @Step("Вход в личный кабинет")
    private void enterAccount() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickEnterPersonalCabinetButton();
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.urlToBe(PERSONAL_ACCOUNT_URL));
        Assert.assertEquals("После перехода в личный кабинет не отобразилась соответствующая страница", PERSONAL_ACCOUNT_URL, driver.getCurrentUrl());
    }

    @Before
    @Step("Создание пользователя перед тестом")
    public void before() {
        driver = webDriverHelper.setUpDriver();

        UserApi.register(new RegisterRequestBody(email, password, username));

        login();

        enterAccount();
    }

    @Test
    public void goToMainLogoTest() {
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Тест клика на логотип Stellar Burgers. " + webDriverHelper.getCaption()));
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.clickMainLogoAndWait();
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.urlToBe(MAIN_PAGE_URL));
    }

    @Test
    public void goToConstructorTest() {
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Тест клика кнопки контсруктор. " + webDriverHelper.getCaption()));
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.clickConstructorAndWait();
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.urlToBe(MAIN_PAGE_URL));
    }


    @Test
    public void exitTest() {
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Тест клика кнопки Выход. " + webDriverHelper.getCaption()));
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.clickExitAndWait();
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.urlToBe(LOGIN_URL));
    }

    @After
    @Step("Удаление пользователя после теста")
    public void after() {
        driver.quit();
        UserApi.tryLoginAndDelete(new LoginRequestBody(email, password));
    }
}
