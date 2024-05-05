package model.pageObject;

import constants.ApiUrls;
import constants.SiteUrls;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class RegisterPage {
    private final String registerPageUrl = ApiUrls.STELLAR_BURGERS_URL + SiteUrls.REGISTER_URL;

    private final String loginPageUrl = ApiUrls.STELLAR_BURGERS_URL + SiteUrls.LOGIN_URL;

    private final WebDriver driver;
    ////*[@id="root"]/div/main/div/form/fieldset[2]/div
    private final By nameField = By.xpath("//label[text()='Имя']/parent::div/input");

    private final By emailField = By.xpath("//label[text()='Email']/parent::div/input");

    private final By passwordField = By.xpath("//label[text()='Пароль']/parent::div/input");

    private final By registerButton = By.xpath("//button[text()='Зарегистрироваться']");

    private final By wrongPasswordLabel = By.className("input__error");

    private final By loginLink = By.className("Auth_link__1fOlj");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Загрузка страницы регистрации")
    public void waitForPageToLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.urlToBe(registerPageUrl));
        LoadingAnimation.waitForLoadingAnimation(driver);
    }

    @Step("Заполнение поля Имя")
    public void fillName(String name) {
        driver.findElement(nameField).sendKeys(name);
    }

    @Step("Заполнение поля Email")
    public void fillEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    @Step("Заполнение поля пароль")
    public void fillPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    @Step("Клик кнопки Зарегистрироватья")
    public void clickRegisterButton() {
        driver.findElement(registerButton).click();
    }

    @Step("Проверка, был ли совершен переход на страницу логина(при успешной регистрации)")
    public boolean isUrlChangedToLogin() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(loginPageUrl));
        return driver.getCurrentUrl().equals(loginPageUrl);
    }

    @Step("Проверка, отображено ли сообщение о некорретной длинне пароля")
    public boolean isWrongPasswordLabelVisible() {
        List<WebElement> elements = driver.findElements(wrongPasswordLabel);
        return !elements.isEmpty() && elements.get(0).isDisplayed();
    }

    @Step("Клик по ссылке 'Войти'")
    public void clickLoginLink() {
//        System.out.println(((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE));
        LoadingAnimation.waitForLoadingAnimation(driver);
        driver.findElement(loginLink).click();
    }

    public boolean tryToRegister(String name, String email, String password) {
        waitForPageToLoad();
        fillName(name);
        fillEmail(email);
        fillPassword(password);
        clickRegisterButton();
        return !isWrongPasswordLabelVisible() && isUrlChangedToLogin();
    }

    public void gotoLoginPage() {
        waitForPageToLoad();
        clickLoginLink();
    }
}
