package model.pageobject;

import constants.SiteUrls;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ForgotPasswordPage {
    private final String forgotPasswordUrl = SiteUrls.STELLAR_BURGERS_URL + SiteUrls.FORGOT_PASSWORD_URL;

    private final WebDriver driver;

    private final By loginLink = By.className("Auth_link__1fOlj");

    public ForgotPasswordPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Загрузка страницы восстановления пароля")
    public void waitForPageToLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.urlToBe(forgotPasswordUrl));
    }

    @Step("Клик ссылки входа в систему")
    public void clickLoginLink() {
        LoadingAnimation.waitForLoadingAnimation(driver);
        driver.findElement(loginLink).click();
    }

    public void waitAndClickLogin() {
        waitForPageToLoad();
        clickLoginLink();
    }
}
