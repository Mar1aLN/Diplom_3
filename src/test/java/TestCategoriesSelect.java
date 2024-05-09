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
import org.openqa.selenium.WebDriver;
import service.WebDriverHelper;
import service.WebDriverHelperFactory;

public class TestCategoriesSelect {
    private static final String MAIN_PAGE_URL = SiteUrls.STELLAR_BURGERS_URL;

    private static final String EMAIL = "Nikitina3@email.org";

    private static final String PASSWORD = "123456";

    private static final String USERNAME = "Мария";

    private final WebDriverHelper webDriverHelper;

    private WebDriver driver;

    private MainPage mainPage;

    public TestCategoriesSelect() {
        webDriverHelper = new WebDriverHelperFactory().createWebDriverHelper();
    }

    @Before
    @Step("Создание пользователя перед тестом")
    public void before() {
        driver = webDriverHelper.setUpDriver();

        driver.get(MAIN_PAGE_URL);

        mainPage = new MainPage(driver);

        UserApi.register(new RegisterRequestBody(EMAIL, PASSWORD, USERNAME));
    }

    @Test()
    public void testSelectBuns() {
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Тест выбора категории Булки. " + webDriverHelper.getCaption()));

        mainPage.waitAndClickBunsLink();

        Assert.assertEquals("Не был выполнен скролл к разделу меню", MenuSections.BUNS_TEXT.toLowerCase(), mainPage.getCurrentSectionText().toLowerCase());
    }

    @Test()
    public void testSelectSauces() {
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Тест выбора категории Соусы. " + webDriverHelper.getCaption()));

        mainPage.waitAndClickSaucesLink();

        Assert.assertEquals("Не был выполнен скролл к разделу меню", MenuSections.SAUCES_TEXT.toLowerCase(), mainPage.getCurrentSectionText().toLowerCase());
    }

    @Test()
    public void testSelectIngredients() {
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Тест выбора категории Ингредиенты. " + webDriverHelper.getCaption()));

        mainPage.waitAndClickIngredientsLink();

        Assert.assertEquals("Не был выполнен скролл к разделу меню", MenuSections.INGREDIENTS_TEXT.toLowerCase(), mainPage.getCurrentSectionText().toLowerCase());
    }

    @After
    @Step("Удаление пользователя после теста")
    public void after() {
        driver.quit();

        UserApi.tryLoginAndDelete(new LoginRequestBody(EMAIL, PASSWORD));
    }
}
