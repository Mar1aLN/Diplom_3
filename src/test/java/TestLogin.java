import constants.ApiUrls;
import constants.SiteUrls;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import model.api.UserApi;
import model.body.LoginRequestBody;
import model.body.RegisterRequestBody;
import model.pageObject.ForgotPasswordPage;
import model.pageObject.LoginPage;
import model.pageObject.MainPage;
import model.pageObject.RegisterPage;
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
public class TestLogin {
    private static final String mainPage = SiteUrls.STELLAR_BURGERS_URL;

    private final String registerPageUrl = ApiUrls.STELLAR_BURGERS_URL + SiteUrls.REGISTER_URL;

    private final String forgotPasswordUrl = ApiUrls.STELLAR_BURGERS_URL + SiteUrls.FORGOT_PASSWORD_URL;

    private final String email;

    private final String password;

    private final String username;

    private final WebDriverHelper webDriverHelper;

    private final boolean isSuccessExpected;

    private final String comment;

    private WebDriver driver;

    public TestLogin(String email, String password, String username, WebDriverHelper webDriverHelper, boolean isSuccessExpected, String comment) {
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
                {"Nikitina3@email.org", "123456", "Мария", new ChromeDriverHelper(), true, "Позитивная проверка. Chrome"},
                {"Nikitina3@email.org", "123456", "Мария", new YandexDriverHelper(), true, "Позитивная проверка. Яндекс.Браузер"},
                {"Nikitina3@email.org", "123456", "Мария", new ChromeDriverHelper(), false, "Негативная проверка, неправильный пароль. Chrome"},
                {"Nikitina3@email.org", "123456", "Мария", new YandexDriverHelper(), false, "Негативная проверка, неправильный пароль. Яндекс.Браузер"},
        };
    }

    @Before
    @Step("Создание пользователя перед тестом")
    public void before() {
        driver = webDriverHelper.setUpDriver();
        UserApi.register(new RegisterRequestBody(email, password, username));
    }

    @Test
    public void testMainPagePersonalAccountPage() {
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Вход через кнопку 'Лисный кабинет' главной страницы. " + comment));
        driver.get(TestLogin.mainPage);
        MainPage mainPage = new MainPage(driver);
        mainPage.waitAndClickPersonalCabinetButton();
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertEquals("Успех логина не соответсвует ожидаемому", isSuccessExpected, loginPage.tryLogin(email, isSuccessExpected ? password : "321321"));
    }

    @Test
    public void testMainPageEnterAccountPage() {
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Вход через кнопку 'Войти в аккаунт' главной страницы. " + comment));
        driver.get(TestLogin.mainPage);
        MainPage mainPage = new MainPage(driver);
        mainPage.waitAndClickEnterAccountButton();
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertEquals("Успех логина не соответсвует ожидаемому", isSuccessExpected, loginPage.tryLogin(email, isSuccessExpected ? password : "321321"));
    }

    @Test
    public void testLoginFromRegisterPage() {
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Вход через кнопку в форме регистрации. " + comment));
        driver.get(registerPageUrl);
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.clickLoginLink();
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertEquals("Успех логина не соответсвует ожидаемому", isSuccessExpected, loginPage.tryLogin(email, isSuccessExpected ? password : "321321"));
    }

    @Test
    public void testLoginFromForgotPasswordPage() {
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Вход через кнопку в форме сброса пароля. " + comment));
        driver.get(forgotPasswordUrl);
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);
        forgotPasswordPage.waitAndClickLogin();
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertEquals("Успех логина не соответсвует ожидаемому", isSuccessExpected, loginPage.tryLogin(email, isSuccessExpected ? password : "321321"));
    }

    @After
    @Step("Удаление пользователя после теста")
    public void after() {
        driver.quit();
        UserApi.tryLoginAndDelete(new LoginRequestBody(email, password));
    }
}
