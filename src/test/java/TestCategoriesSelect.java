import constants.MenuSections;
import constants.SiteUrls;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import model.api.UserApi;
import model.body.LoginRequestBody;
import model.body.RegisterRequestBody;
import model.pageobject.MainPage;
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
public class TestCategoriesSelect {
    private static final String mainPageUrl = SiteUrls.STELLAR_BURGERS_URL;

    private final String email;

    private final String password;

    private final String username;

    private final WebDriverHelper webDriverHelper;

    private final String comment;

    private WebDriver driver;

    private MainPage mainPage;

    public TestCategoriesSelect(String email, String password, String username, WebDriverHelper webDriverHelper, String comment) {
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

    @Before
    @Step("Создание пользователя перед тестом")
    public void before() {
        driver = webDriverHelper.setUpDriver();

        driver.get(mainPageUrl);

        mainPage = new MainPage(driver);

        UserApi.register(new RegisterRequestBody(email, password, username));
    }

    @Test()
    public void testSelectBuns() {
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Тест выбора категории Булки. " + comment));

        mainPage.waitAndClickBunsLink();

        Assert.assertEquals("Не был выполнен скролл к разделу меню", MenuSections.BUNS_TEXT.toLowerCase(), mainPage.getCurrentSectionText().toLowerCase());
    }

    @Test()
    public void testSelectSauces() {
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Тест выбора категории Соусы. " + comment));

        mainPage.waitAndClickSaucesLink();

        Assert.assertEquals("Не был выполнен скролл к разделу меню", MenuSections.SAUCES_TEXT.toLowerCase(), mainPage.getCurrentSectionText().toLowerCase());
    }

    @Test()
    public void testSelectIngredients() {
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Тест выбора категории Ингредиенты. " + comment));

        mainPage.waitAndClickIngredientsLink();

        Assert.assertEquals("Не был выполнен скролл к разделу меню", MenuSections.INGREDIENTS_TEXT.toLowerCase(),mainPage.getCurrentSectionText().toLowerCase());
    }

    @After
    @Step("Удаление пользователя после теста")
    public void after() {
        driver.quit();

        UserApi.tryLoginAndDelete(new LoginRequestBody(email, password));
    }
}
