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
public class TestTransferFromPersonalAccount {
    private static final String loginUrl = SiteUrls.STELLAR_BURGERS_URL + SiteUrls.LOGIN_URL;

    private static final String personalAccountUrl = SiteUrls.STELLAR_BURGERS_URL + SiteUrls.PROFILE_URL;

    private static final String mainPage = SiteUrls.STELLAR_BURGERS_URL + "/";
    private final String email;
    private final String password;
    private final String username;
    private final WebDriverHelper webDriverHelper;
    private final String comment;
    private WebDriver driver;

    public TestTransferFromPersonalAccount(String email, String password, String username, WebDriverHelper webDriverHelper, String comment) {
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

    @Step("Логин перед проверкой")
    private void login() {
        driver.get(loginUrl);
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue("Успех логина не соответсвует ожидаемому", loginPage.tryLogin(email, password));
    }

    @Step("Вход в личный кабинет")
    private void enterAccount() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickEnterPersonalCabinetButton();
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.urlToBe(personalAccountUrl));
        Assert.assertEquals("После перехода в личный кабинет не отобразилась соответствующая страница", personalAccountUrl, driver.getCurrentUrl());
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
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Тест клика на логотип Stellar Burgers. " + comment));
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.clickMainLogoAndWait();
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.urlToBe(mainPage));
    }

    @Test
    public void goToConstructorTest() {
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Тест клика кнопки контсруктор. " + comment));
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.clickConstructorAndWait();
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.urlToBe(mainPage));
    }


    @Test
    public void exitTest() {
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Тест клика кнопки Выход. " + comment));
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.clickExitAndWait();
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.urlToBe(loginUrl));
    }

    @After
    @Step("Удаление пользователя после теста")
    public void after() {
        driver.quit();
        UserApi.tryLoginAndDelete(new LoginRequestBody(email, password));
    }
}
