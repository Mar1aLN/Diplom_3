package model.pageObject;

import constants.SiteUrls;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MainPage {
    private final String mainPageUrl = SiteUrls.STELLAR_BURGERS_URL + "/";
    private final By enterAccountButton = By.xpath("//button[text()='Войти в аккаунт']");
    private final By personalCabinetButton = By.xpath("//p[text()='Личный Кабинет']/parent::a");
    private final By bunsLink = By.xpath("//span[text()='Булки']");
    private final By saucesLink = By.xpath("//span[text()='Соусы']");
    private final By ingredientsLink = By.xpath("//span[text()='Начинки']");
    private final By menuItems = By.xpath("//a[@class='BurgerIngredient_ingredient__1TVf6 ml-4 mr-4 mb-8']");
    private final By bunsTitle = By.xpath("//div[@class='BurgerIngredients_ingredients__menuContainer__Xu3Mo']/h2[text()='Булки']");
    private final By saucesTitle = By.xpath("//div[@class='BurgerIngredients_ingredients__menuContainer__Xu3Mo']/h2[text()='Соусы']");
    private final By ingredientsTitle = By.xpath("//div[@class='BurgerIngredients_ingredients__menuContainer__Xu3Mo']/h2[text()='Начинки']");
    private final WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Загрузка страницы")
    public void waitForPageToLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.urlToBe(mainPageUrl));
        LoadingAnimation.waitForLoadingAnimation(driver);
    }

    @Step("Клик кнопки Войти в аккаунт")
    public void clickLoginButton() {
        LoadingAnimation.waitForLoadingAnimation(driver);
        driver.findElement(enterAccountButton).click();
    }

    @Step("Клик кнопки Личный кабинет")
    public void clickEnterPersonalCabinetButton() {
        LoadingAnimation.waitForLoadingAnimation(driver);
        driver.findElement(personalCabinetButton).click();
    }

    @Step("Клик кнопки Булки")
    public void clickBunsLink() {
        driver.findElement(bunsLink).click();
    }

    @Step("Клик кнопки Соусы")
    public void clickSaucesLink() {
        driver.findElement(saucesLink).click();
    }

    @Step("Клик кнопки Начинки")
    public void clickIngredientsLink() {
        driver.findElement(ingredientsLink).click();
    }

    @Step("Скорлл меню к последнему элементу")
    public void scrollToLastMenuItem() {
        List<WebElement> items = driver.findElements(menuItems);

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", items.get(items.size() - 1));
    }

    public void waitAndClickEnterAccountButton() {
        waitForPageToLoad();
        clickLoginButton();
    }

    public void waitAndClickPersonalCabinetButton() {
        waitForPageToLoad();
        clickEnterPersonalCabinetButton();
    }

    public void waitAndClickBunsLink() {
        waitForPageToLoad();
        scrollToLastMenuItem();
        clickBunsLink();

        new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.visibilityOf(driver.findElement(bunsTitle)));
    }

    public void waitAndClickSaucesLink() {
        waitForPageToLoad();
        scrollToLastMenuItem();
        clickSaucesLink();

        new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.visibilityOf(driver.findElement(saucesTitle)));
    }

    public void waitAndClickIngredientsLink() {
        waitForPageToLoad();
        clickIngredientsLink();

        new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.visibilityOf(driver.findElement(ingredientsTitle)));
    }

    public boolean isBunsVisible() {
        return driver.findElement(bunsTitle).isDisplayed();
    }

    public boolean isSaucesVisible() {
        return driver.findElement(saucesTitle).isDisplayed();
    }

    public boolean isIngredientsVisible() {
        return driver.findElement(ingredientsTitle).isDisplayed();
    }


}
