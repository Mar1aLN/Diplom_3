package model.pageobject;

import constants.SiteUrls;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private final WebDriver driver;

    private final String loginUrl = SiteUrls.STELLAR_BURGERS_URL + SiteUrls.LOGIN_URL;

    private final By emailField = By.xpath("//label[text()='Email']/parent::div/input");

    private final By passwordField = By.xpath("//label[text()='Пароль']/parent::div/input");

    private final By loginButton = By.xpath("//button[text()='Войти']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Загрузка страницы регистрации")
    public void waitForPageToLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.urlToBe(loginUrl));
    }

    @Step("Заполнение поля Email")
    public void fillEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    @Step("Заполнение поля пароль")
    public void fillPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    @Step("Клик кнопки Войти")
    public void clickLoginButton() {
        LoadingAnimation.waitForLoadingAnimation(driver);
        driver.findElement(loginButton).click();
    }

    public boolean tryLogin(String email, String password) {
        waitForPageToLoad();
        fillEmail(email);
        fillPassword(password);
        clickLoginButton();
        LoadingAnimation.waitForLoadingAnimation(driver);
        try {
            new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.urlToBe(SiteUrls.STELLAR_BURGERS_URL + "/"));
        } catch (Exception e) {
            return driver.getCurrentUrl().equals(SiteUrls.STELLAR_BURGERS_URL) || driver.getCurrentUrl().equals(SiteUrls.STELLAR_BURGERS_URL + "/");
        }
        return driver.getCurrentUrl().equals(SiteUrls.STELLAR_BURGERS_URL) || driver.getCurrentUrl().equals(SiteUrls.STELLAR_BURGERS_URL + "/");
    }
}
