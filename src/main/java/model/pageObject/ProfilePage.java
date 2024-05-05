package model.pageObject;

import constants.SiteUrls;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProfilePage {
    private final String mainPageUrl = SiteUrls.STELLAR_BURGERS_URL + "/";

    private final String profilePageUrl = SiteUrls.STELLAR_BURGERS_URL + SiteUrls.PROFILE_URL;

    private final String loginUrl = SiteUrls.STELLAR_BURGERS_URL + SiteUrls.LOGIN_URL;

    private final WebDriver driver;

    private final By mainLogo = By.xpath("//div[@class='AppHeader_header__logo__2D0X2']");

    private final By constructorLink = By.xpath("//p[@class='AppHeader_header__linkText__3q_va ml-2']");

    private final By exitButton = By.xpath("//button[text()='Выход']");

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Ожидание загрузки страницы профиля")
    public void waitForPageToLoad() {
        LoadingAnimation.waitForLoadingAnimation(driver);
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.urlToBe(profilePageUrl));
    }

    @Step("Клик логотипа")
    public void clickMainLogo() {
        LoadingAnimation.waitForLoadingAnimation(driver);
        driver.findElement(mainLogo).click();
    }

    @Step("Клик ссылки 'Конструктор'")
    public void clickConstructorLink() {
        LoadingAnimation.waitForLoadingAnimation(driver);
        driver.findElement(constructorLink).click();
    }

    @Step("Клик кнопки 'Выход'")
    public void clickExit() {
        LoadingAnimation.waitForLoadingAnimation(driver);
        driver.findElement(exitButton).click();
    }

    public void clickMainLogoAndWait() {
        waitForPageToLoad();

        clickMainLogo();
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(mainPageUrl));
    }

    public void clickConstructorAndWait() {
        waitForPageToLoad();

        clickConstructorLink();
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(mainPageUrl));
    }

    public void clickExitAndWait() {
        waitForPageToLoad();

        clickExit();
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(loginUrl));
    }
}
