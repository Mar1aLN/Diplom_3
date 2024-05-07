package model.pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoadingAnimation {
    private final static By loadingAnimation = By.xpath("//img[@alt='loading animation']");

    @Step("Ожидание анимации загрузки")
    public static void waitForLoadingAnimation(WebDriver driver) {
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.invisibilityOfElementLocated(loadingAnimation));
    }
}
